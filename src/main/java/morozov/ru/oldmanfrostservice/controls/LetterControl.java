package morozov.ru.oldmanfrostservice.controls;

import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.notes.NoteBasic;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
import morozov.ru.oldmanfrostservice.models.utilmodels.Letter;
import morozov.ru.oldmanfrostservice.models.utilmodels.StringMessageUtil;
import morozov.ru.oldmanfrostservice.repositories.interfaces.GiftTypeRepo;
import morozov.ru.oldmanfrostservice.services.LetterControlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Контроллер, отвечающий за обработку писем.
 * Тут лежит важная часть с логикой:
 * morozov.ru.oldmanfrostservice.services.LetterControlUtil
 */
@RestController
public class LetterControl {

    private static final Logger LOG = LogManager.getLogger(LetterControl.class);

    @Value("${general.uri}")
    private String generalUri;
    @Value("${kinder.uri}")
    private String kinderUri;
    @Value("${frost.wait}")
    private String waitUri;

    private RestTemplate restTemplate;
    private GiftTypeRepo giftTypeRepo;
    private LetterControlUtil letterControlUtil;

    public LetterControl(
            RestTemplate restTemplate,
            GiftTypeRepo giftTypeRepo,
            LetterControlUtil letterControlUtil
    ) {
        this.restTemplate = restTemplate;
        this.giftTypeRepo = giftTypeRepo;
        this.letterControlUtil = letterControlUtil;
    }

    @PostMapping("/frost/letter")
    public NoteOfDone takeLetter(@RequestBody Letter letter, HttpServletResponse response) {
        GiftType neededGiftType = this.giftTypeRepo.getType(letter.getGiftType());
        NoteBasic info = new NoteBasic(
                letter.getName(),
                letter.getMiddleName(),
                letter.getLastName());
        String completeUri = generalUri + kinderUri;
        NoteOfDone result = null;
        Boolean isGood = this.restTemplate.postForObject(completeUri, info, Boolean.class);
        LOG.info("We received and check information about " + info.getKinderName());
        boolean flag = this.letterControlUtil.browsingKinderInfo(isGood, info, neededGiftType, response);
        if (flag) {
            LOG.info("ALL IS OK with flag: " + flag);
            result = this.letterControlUtil
                    .formingGiftForLetterControl(info, neededGiftType);
            if (result == null) {
                try {
                    response.sendRedirect(waitUri);
                } catch (IOException e) {
                    LOG.error("Error in LetterControl:", e);
                }
            }
        } else {
            LOG.info("ALL IS NOT OK with flag: " + flag);
        }
        return result;
    }

    @GetMapping("/frost/bad")
    public StringMessageUtil getNegativeAnswer() {
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData("Try to be good next year.");
        return msg;
    }

    @GetMapping("/frost/wait")
    public StringMessageUtil getWaitAnswer() {
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData("Dont be sad, Snow Maiden is looking for your gift.");
        return msg;
    }

    @GetMapping("/frost/unknown")
    public StringMessageUtil getUnknownAnswer() {
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData("This is an unknown type of gift. "
                + "We will send this one in a year, as soon as we set up production.");
        return msg;
    }

}
