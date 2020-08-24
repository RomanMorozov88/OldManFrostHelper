package morozov.ru.oldmanfrostservice.controls;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.services.WaitingListUtil;
import morozov.ru.oldmanfrostservice.services.WarehouseControlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Получаем готовый заказ.
 * Метод сохранения в БД смотри в:
 * morozov.ru.oldmanfrostservice.services.WarehouseControlUtil
 */
@RestController
public class WarehouseControl {

    private static final Logger LOG = LogManager.getLogger(WarehouseControl.class);

    private WarehouseControlUtil warehouseControlUtil;
    private WaitingListUtil waitingListUtil;

    @Autowired
    public WarehouseControl(WarehouseControlUtil warehouseControlUtil, WaitingListUtil waitingListUtil) {
        this.warehouseControlUtil = warehouseControlUtil;
        this.waitingListUtil = waitingListUtil;
    }

    @PostMapping("/frost/warehouse")
    public void getCompletedOrder(@RequestBody List<Gift> gifts) {
        LOG.info("We got new gifts for the warehouse.");
        this.warehouseControlUtil.loadingGifts(gifts);
        LOG.info("Now we can look at waitings.");
        this.waitingListUtil.sendToWaitings();
    }

}
