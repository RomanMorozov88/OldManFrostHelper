package morozov.ru.oldmanfrostservice.repositories.interfaces;

import morozov.ru.oldmanfrostservice.models.gifts.GiftType;

import java.util.List;

public interface GiftTypeRepo {

    void saveType(GiftType giftType);
    GiftType getType(String typeName);
    List<GiftType> getAllTypes();
    Boolean isPresent(String type);
}
