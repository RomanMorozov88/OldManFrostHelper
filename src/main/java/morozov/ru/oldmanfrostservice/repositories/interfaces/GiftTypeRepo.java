package morozov.ru.oldmanfrostservice.repositories.interfaces;

import morozov.ru.oldmanfrostservice.models.gifts.GiftType;

import java.util.List;

public interface GiftTypeRepo {

    void saveType(GiftType giftType);

    GiftType getType(String typeName);

    List<GiftType> getAllTypes();

    /**
     * На самом деле этот метод и не нужен,
     * но просто для примера пусть будет-
     * статус тестового задания позволяет нам
     * всякие вольности по замусориванию.
     *
     * @param type
     * @return
     */
    Boolean isPresent(String type);
}
