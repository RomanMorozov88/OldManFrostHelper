package morozov.ru.oldmanfrostservice.services;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.notes.NoteBasic;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfWaiting;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.BiConsumer;

/**
 * Сюда вынесен функционал для LetteControl
 * Что бы можно было обернуть в @Transactional
 * И что бы не захламлять сам контроллер.
 */
@Service
public class LetterControlUtil {

    private static final Logger LOG = LogManager.getLogger(LetterControlUtil.class);

    @Value("${frost.bad}")
    private String badUri;
    @Value("${frost.unknown}")
    private String unknownUri;

    private WarehouseRepo warehouseRepo;
    private DoneListRepo doneListRepo;
    private WaitingListRepo waitingListRepo;

    @Autowired
    public LetterControlUtil(
            WarehouseRepo warehouseRepo,
            DoneListRepo doneListRepo,
            WaitingListRepo waitingListRepo
    ) {
        this.warehouseRepo = warehouseRepo;
        this.doneListRepo = doneListRepo;
        this.waitingListRepo = waitingListRepo;
    }

    /**
     * Редиректим по мере необходимости.
     *
     * @param response
     */
    private BiConsumer<HttpServletResponse, String> redirectMsg = (response, uri) -> {
        try {
            response.sendRedirect(uri);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("Error in LetterControlUtil:", e);
        }
    };

    /**
     * Перегоняем входящую информацию о ребёнке в конкретный нужный тип.
     */
    private BiConsumer<NoteBasic, NoteBasic> notesConvert = (result, input) -> {
        result.setKinderName(input.getKinderName());
        result.setKinderMiddleName(input.getKinderMiddleName());
        result.setKinderLastName(input.getKinderLastName());
    };

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
            Boolean isGood,
            NoteBasic info,
            GiftType neededGiftType,
            HttpServletResponse response
    ) {
        boolean result = true;
        if (info != null) {
            LOG.info("So, was the kid good? " + isGood);
            if (isGood == null || !isGood) {
                result = false;
                this.redirectMsg.accept(response, badUri);
            } else if (neededGiftType == null) {
                result = false;
                this.redirectMsg.accept(response, unknownUri);
            }
        } else {
            LOG.error("Something is wrong with info.");
        }
        return result;
    }

    /**
     * Формируем подарок.
     * В случае успеха возвращаем готовый объект и сохраняем в БД.
     * В случае, если подарка на складе нет- закидываем в списо ожидания
     * и возвращаем null.
     * @param info
     * @param neededGiftType
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public NoteOfDone formingGiftForLetterControl(
            NoteBasic info,
            GiftType neededGiftType
    ) {
        NoteOfDone result = null;
        Gift gift = this.warehouseRepo.getGift(neededGiftType);
        if (gift != null) {
            result = new NoteOfDone();
            this.notesConvert.accept(result, info);
            result.setGift(gift);
            this.doneListRepo.saveNote(result);
        } else {
            NoteOfWaiting waiting = new NoteOfWaiting();
            this.notesConvert.accept(waiting, info);
            waiting.setType(neededGiftType);
            this.waitingListRepo.save(waiting);
        }
        return result;
    }

}