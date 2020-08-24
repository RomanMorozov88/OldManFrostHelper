package morozov.ru.oldmanfrostservice.services;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfWaiting;
import morozov.ru.oldmanfrostservice.models.utilmodels.StringMessageUtil;
import morozov.ru.oldmanfrostservice.repositories.interfaces.DoneListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WaitingListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class WaitingListUtil {

    private static final Logger LOG = LogManager.getLogger(WaitingListUtil.class);

    @Value("${general.uri}")
    private String generalUri;
    @Value("${office.uri}")
    private String officeUri;

    private RestTemplate restTemplate;
    private WaitingListRepo waitingListRepo;
    private WarehouseRepo warehouseRepo;
    private DoneListRepo doneListRepo;

    @Autowired
    public WaitingListUtil(
            RestTemplate restTemplate,
            WaitingListRepo waitingListRepo,
            WarehouseRepo warehouseRepo,
            DoneListRepo doneListRepo

    ) {
        this.restTemplate = restTemplate;
        this.waitingListRepo = waitingListRepo;
        this.warehouseRepo = warehouseRepo;
        this.doneListRepo = doneListRepo;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void sendToWaitings() {
        List<NoteOfWaiting> list = this.waitingListRepo.getAll();
        List<NoteOfDone> sendingList = this.runOverWaitings(list);
        if (sendingList.size() > 0) {
            String uri = generalUri + officeUri;
            StringMessageUtil msg = restTemplate.postForObject(uri, sendingList, StringMessageUtil.class);
            if (msg != null) {
                LOG.info(msg.getData());
            }
        } else {
            LOG.info("Nothing to send.");
        }
    }

    private List<NoteOfDone> runOverWaitings(List<NoteOfWaiting> list) {
        List<NoteOfDone> sendingList = new ArrayList<>();
        String kinderName = null;
        GiftType giftType = null;
        if (list != null && list.size() > 0) {
            for (NoteOfWaiting n : list) {
                kinderName = n.getKinderName();
                giftType = n.getType();
                Gift gift = this.warehouseRepo.getGift(giftType);
                if (gift != null) {
                    NoteOfDone noteOfDone = new NoteOfDone();
                    noteOfDone.setKinderName(kinderName);
                    noteOfDone.setGift(gift);
                    doneListRepo.saveNote(noteOfDone);
                    waitingListRepo.delete(n);
                    sendingList.add(noteOfDone);
                }
            }
            LOG.info("Waiting kid: " + kinderName);
        }
        return sendingList;
    }

}