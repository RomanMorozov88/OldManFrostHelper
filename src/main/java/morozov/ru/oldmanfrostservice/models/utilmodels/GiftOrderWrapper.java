package morozov.ru.oldmanfrostservice.models.utilmodels;

import morozov.ru.oldmanfrostservice.models.gifts.GiftType;

/**
 * Обёртка над GiftOrder для выполнения в методе
 * WarehouseImpl.getGiftTypeCount(String type)
 */
public class GiftOrderWrapper {

    private GiftOrder giftOrder;

    public GiftOrderWrapper() {
    }

    public GiftOrderWrapper(GiftType type, Long count) {
        this.giftOrder = new GiftOrder(type, Math.toIntExact(count));
    }

    public GiftOrder getGiftOrder() {
        return giftOrder;
    }

    public void setGiftOrder(GiftOrder giftOrder) {
        this.giftOrder = giftOrder;
    }
}
