package morozov.ru.oldmanfrostservice.controls;

import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
import morozov.ru.oldmanfrostservice.models.utilmodels.KinderInfo;
import morozov.ru.oldmanfrostservice.models.utilmodels.Letter;
import morozov.ru.oldmanfrostservice.models.utilmodels.StringMessageUtil;
import morozov.ru.oldmanfrostservice.repositories.interfaces.DoneListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.GiftTypeRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WaitingListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import morozov.ru.oldmanfrostservice.services.LetterControlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.BiConsumer;

@RestController
public class LetterControl {

    private static final Logger LOG = LogManager.getLogger(LetterControl.class);

    @Value("${general.uri}")
    private String generalUri;
    @Value("${kinder.uri}")
    private String kinderUri;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WarehouseRepo warehouseRepo;
    @Autowired
    private DoneListRepo doneListRepo;
    @Autowired
    private WaitingListRepo waitingListRepo;
    @Autowired
    private GiftTypeRepo giftTypeRepo;
    @Autowired
    private LetterControlUtil letterControlUtil;

    @PostMapping("/frost/letter")
    public NoteOfDone takeLetter(@RequestBody Letter letter, HttpServletResponse response) {
        GiftType neededGiftType = this.giftTypeRepo.getType(letter.getGiftType());
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData(letter.getKinderName());
        String completeUri = generalUri + kinderUri;
        KinderInfo info = this.restTemplate.postForObject(completeUri, msg, KinderInfo.class);
        LOG.info("We received and check information about " + info.getKinderName());
        boolean flag = this.letterControlUtil.browsingKinderInfo(info, neededGiftType, response, this.redirectMsg);
        NoteOfDone result = null;
        if (flag) {
            LOG.info("ALL IS OK with flag: " + flag);
        } else {
            LOG.info("ALL IS NOT OK with flag: " + flag);
        }
        return result;
    }

    @GetMapping("frost/bad")
    public StringMessageUtil getNegativeAnswer() {
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData("Try to be good next year.");
        return msg;
    }

    @GetMapping("frost/wait")
    public StringMessageUtil getWaitAnswer() {
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData("Dont be sad, Snow Maiden is looking for your gift.");
        return msg;
    }

    @GetMapping("frost/unknown")
    public StringMessageUtil getUnknownAnswer() {
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData("This is an unknown type of gift. "
                + "We will send this one in a year, as soon as we set up production.");
        return msg;
    }

    /**
     * Редиректим по мере необходимости.
     *
     * @param response
     * @param uri
     */
    private BiConsumer<HttpServletResponse, String> redirectMsg = (response, uri) -> {
        try {
            response.sendRedirect(uri);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("Error in LetterControl:", e);
        }
    };


}
