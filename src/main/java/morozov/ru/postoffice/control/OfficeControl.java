package morozov.ru.postoffice.control;

import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
import morozov.ru.oldmanfrostservice.models.utilmodels.StringMessageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Просто приниает список и делает вид, что рассылает подврки.
 */
@RestController
public class OfficeControl {

    private static final Logger LOG = LogManager.getLogger(OfficeControl.class);

    @PostMapping("office/send")
    public StringMessageUtil takeGifts(@RequestBody List<NoteOfDone> list) {
        LOG.info("Got list for delivery.");
        for (NoteOfDone n : list) {
            LOG.info("Sending to: " + n.getKinderName() + " : " + n.getGift().getName());
        }
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData("Will be delivered!");
        return msg;
    }

}
