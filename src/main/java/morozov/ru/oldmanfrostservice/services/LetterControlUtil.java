package morozov.ru.oldmanfrostservice.services;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfWaiting;
import morozov.ru.oldmanfrostservice.models.utilmodels.KinderInfo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.DoneListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WaitingListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.function.BiConsumer;

/**
 * Сюда вынесен функционал для LetteControl
 * Что бы можно было обернуть в @Transactional
 * И что бы не захламлять сам контроллер.
 */
@Service
@Transactional
public class LetterControlUtil {

    private static final Logger LOG = LogManager.getLogger(LetterControlUtil.class);

    @Value("${frost.bad}")
    private String badUri;
    @Value("${frost.wait}")
    private String waitUri;
    @Value("${frost.unknown}")
    private String unknownUri;

    @Autowired
    private WarehouseRepo warehouseRepo;
    @Autowired
    private DoneListRepo doneListRepo;
    @Autowired
    private WaitingListRepo waitingListRepo;

    /**
     * Проверяем полученную информацию- смотрим поведение, наличие нужного типа подарка
     * редиректим в случае необходимости.
     *
     * @param info
     * @param response
     * @param neededGiftType
     * @return true- если всё хорошо, и можно отправлять подарок.
     */
    public boolean browsingKinderInfo(
            KinderInfo info,
            GiftType neededGiftType,
            HttpServletResponse response,
            BiConsumer<HttpServletResponse, String> biConsumer
    ) {
        boolean result = true;
        LOG.info("So, was the kid good? " + info.isGood());
        if (info != null) {
            if (!info.isGood() || info.isGood() == null) {
                result = false;
                biConsumer.accept(response, badUri);
            } else if (neededGiftType == null) {
                result = false;
                biConsumer.accept(response, unknownUri);
            }
        } else {
            LOG.error("Something is wrong with info.");
        }
        return result;
    }

    public NoteOfDone formingGift(
            String kinderName,
            GiftType neededGiftType,
            HttpServletResponse response,
            BiConsumer<HttpServletResponse, String> biConsumer
    ) {
        NoteOfDone result = null;
        Gift gift = this.warehouseRepo.getGift(neededGiftType);
        if (gift != null) {
            result = new NoteOfDone();
            result.setKinderName(kinderName);
            result.setGift(gift);
            this.doneListRepo.saveNote(result);
        } else {
            NoteOfWaiting waiting = new NoteOfWaiting();
            waiting.setKinderName(kinderName);
            waiting.setType(neededGiftType);
            this.waitingListRepo.save(waiting);
            biConsumer.accept(response, waitUri);
        }
        return result;
    }

}