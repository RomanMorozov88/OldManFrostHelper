package morozov.ru.oldmanfrostservice.controls;

import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
import morozov.ru.oldmanfrostservice.models.utilmodels.KinderInfo;
import morozov.ru.oldmanfrostservice.models.utilmodels.Letter;
import morozov.ru.oldmanfrostservice.models.utilmodels.StringMessageUtil;
import morozov.ru.oldmanfrostservice.repositories.interfaces.DoneListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WaitingListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LetterControl {

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

    @PostMapping("/frost/letter")
    public NoteOfDone takeLetter(@RequestBody Letter letter, HttpServletResponse response) {
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData(letter.getKinderName());
        KinderInfo info = this.restTemplate.postForObject(kinderUri, msg, KinderInfo.class);
        //DO LOGGER HERE
        if (info != null) {
            if (!info.isGood()) {
                try {
                    response.sendRedirect("/frost/bad");
                } catch (IOException e) {
                    e.printStackTrace();
                    //DO LOGGER HERE
                }
            }
        }
        NoteOfDone result = null;
        return result;
    }

    @GetMapping("frost/bad")
    public StringMessageUtil getNegativeAnswer() {
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData("Try to be good next year.");
        return msg;
    }

}
