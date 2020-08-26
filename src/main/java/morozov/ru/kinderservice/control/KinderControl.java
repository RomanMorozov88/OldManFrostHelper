package morozov.ru.kinderservice.control;

import morozov.ru.oldmanfrostservice.models.notes.NoteBasic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class KinderControl {

    private static final Logger LOG = LogManager.getLogger(KinderControl.class);

    private Random random;

    @Autowired
    public KinderControl(Random random) {
        this.random = random;
    }

    @PostMapping("kinder/about")
    public Boolean getKinderInfo(@RequestBody NoteBasic input) {
        LOG.info("Looking for information about " + input.getKinderName());
        Boolean isGood = random.nextBoolean();
        return isGood;
    }

}
