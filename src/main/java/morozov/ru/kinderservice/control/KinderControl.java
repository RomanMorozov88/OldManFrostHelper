package morozov.ru.kinderservice.control;

import morozov.ru.oldmanfrostservice.models.utilmodels.KinderInfo;
import morozov.ru.oldmanfrostservice.models.utilmodels.StringMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class KinderControl {

    @Autowired
    Random random;

    @PostMapping("kinder/about")
    public KinderInfo getKinderInfo(@RequestBody StringMessageUtil msg) {
        //DO LOGGER HERE
        KinderInfo response = new KinderInfo();
        response.setKinderName(msg.getData());
        response.setGood(random.nextBoolean());
        return response;
    }

}
