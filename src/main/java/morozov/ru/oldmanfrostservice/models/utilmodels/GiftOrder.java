package morozov.ru.oldmanfrostservice.models.utilmodels;

import morozov.ru.oldmanfrostservice.models.gifts.GiftType;

/**
 * Класс, необходимый как для получения информации о количестве
 * подарков определённого типа на складе
 * так и для отправки заказа на фабрику.
 */
public class GiftOrder {

    private GiftType type;
    private Integer count;

    public GiftOrder() {
    }

    public GiftOrder(GiftType type, Integer count) {
        this.type = type;
        this.count = count;
    }

    public GiftType getType() {
        return type;
    }

    public void setType(GiftType type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}