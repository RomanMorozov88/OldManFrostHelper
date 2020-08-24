package morozov.ru.oldmanfrostservice.services;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сюда вынесен функционал для WarehouseControl
 * Что бы можно было обернуть в @Transactional
 * И что бы не захламлять сам контроллер.
 */
@Service
public class WarehouseControlUtil {

    private static final Logger LOG = LogManager.getLogger(WarehouseControlUtil.class);

    private WarehouseRepo warehouseRepo;

    @Autowired
    public WarehouseControlUtil(WarehouseRepo warehouseRepo) {
        this.warehouseRepo = warehouseRepo;
    }

    @Transactional
    public void loadingGifts(List<Gift> newArrival) {
        for (Gift g : newArrival) {
            LOG.info("Load " + g.getName() + " in the warehouse.");
            this.warehouseRepo.add(g);
        }
    }
}