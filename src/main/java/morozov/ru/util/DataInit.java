package morozov.ru.util;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.repositories.interfaces.DoneListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.GiftTypeRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
public class DataInit {

    @Value("${q.count}")
    private int countQ;
    @Autowired
    private DoneListRepo doneListRepo;
    @Autowired
    private GiftTypeRepo giftTypeRepo;
    @Autowired
    private WarehouseRepo warehouseRepo;

    public DataInit() {
    }

    @PostConstruct
    @Transactional
    public void setDataInDB() {
        GiftType giftTypeOne = new GiftType();
        giftTypeOne.setType("Car");
        GiftType giftTypeTwo = new GiftType();
        giftTypeTwo.setType("Bear");

        this.giftTypeRepo.saveType(giftTypeOne);
        this.giftTypeRepo.saveType(giftTypeTwo);

        this.setGifts(giftTypeOne);
        this.setGifts(giftTypeTwo);

    }

    private void setGifts(GiftType giftType) {
        for (int i = 1; i <= this.countQ - 2; i++) {
            Gift buffer = new Gift();
            buffer.setType(giftType);
            buffer.setName(buffer.getType().getType() + i);
            this.warehouseRepo.add(buffer);
        }
    }
}