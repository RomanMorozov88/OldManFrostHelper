package morozov.ru.oldmanfrostservice.repositories.interfaces;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.utilmodels.GiftOrder;

import java.util.List;

public interface WarehouseRepo {

    Gift getGift(GiftType type);

    List<Gift> getAll();

    Gift saveGift(Gift gift);

    GiftOrder getGiftTypeCount(String type);
}
