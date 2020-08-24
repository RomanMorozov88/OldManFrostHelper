package morozov.ru.util;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.repositories.interfaces.GiftTypeRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
public class DataInit {

    @Value("${q.count}")
    private int countQ;

    private GiftTypeRepo giftTypeRepo;
    private WarehouseRepo warehouseRepo;

    public DataInit(
            GiftTypeRepo giftTypeRepo,
            WarehouseRepo warehouseRepo
    ) {
        this.giftTypeRepo = giftTypeRepo;
        this.warehouseRepo = warehouseRepo;
    }

    @PostConstruct
    @Transactional
    public void setDataInDB() {
        GiftType giftTypeOne = new GiftType();
        giftTypeOne.setTypeName("Car");
        GiftType giftTypeTwo = new GiftType();
        giftTypeTwo.setTypeName("Bear");

        this.giftTypeRepo.saveType(giftTypeOne);
        this.giftTypeRepo.saveType(giftTypeTwo);

        this.setGifts(giftTypeOne);
        this.setGifts(giftTypeTwo);

    }

    private void setGifts(GiftType giftType) {
        for (int i = 1; i <= this.countQ - 2; i++) {
            Gift buffer = new Gift();
            buffer.setType(giftType);
            buffer.setName(buffer.getType().getTypeName() + i);
            this.warehouseRepo.add(buffer);
        }
    }
}