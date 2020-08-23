package morozov.ru.postoffice.control;

import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
import morozov.ru.oldmanfrostservice.models.utilmodels.StringMessageUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OfficeControl {

    @PostMapping
    public StringMessageUtil takeGifts(@RequestBody List<NoteOfDone> list) {
        //DO LOGGER HERE
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData("Will be delivered!");
        return msg;
    }

}
