package morozov.ru.oldmanfrostservice.services;

import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.utilmodels.GiftOrder;
import morozov.ru.oldmanfrostservice.models.utilmodels.StringMessageUtil;
import morozov.ru.oldmanfrostservice.repositories.interfaces.GiftTypeRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Некий инспектор, иногда посещающий склад и проверяющий что там как по подаркам.
 * В случае необходимости формирует заказы на недостоющее до максимально значения кол-во подарков
 * и отправляет их на фабрику.
 */
@Component
public class Inspector {

    @Value("${q.count}")
    private int countQ;
    @Value("${q.criterion}")
    private int criterionQ;
    @Value("${factory.uri}")
    private String uri;
    @Autowired
    private GiftTypeRepo giftTypeRepo;
    @Autowired
    private WarehouseRepo warehouseRepo;
    @Autowired
    private RestTemplate restTemplate;

    public Inspector() {
    }

    @Scheduled(
            initialDelayString = "${inspection.initial}",
            fixedDelayString = "${inspection.period}"
    )
    public void inspection() {
        List<GiftType> types = this.giftTypeRepo.getAllTypes();
        List<GiftOrder> orders = this.runOverTypes(types);
        if (orders.size() > 0) {
            StringMessageUtil msg = this.restTemplate.postForObject(uri, orders, StringMessageUtil.class);
            //DO LOGGER HERE
        }

    }

    private List<GiftOrder> runOverTypes(List<GiftType> types) {
        List<GiftOrder> result = new ArrayList<>();
        GiftOrder bufferOrder = null;
        for (GiftType t : types) {
            bufferOrder = this.controlCount(this.warehouseRepo.getGiftTypeCount(t.getType()));
            if (bufferOrder != null) {
                result.add(bufferOrder);
            }
        }
        return result;
    }

    private GiftOrder controlCount(GiftOrder giftOrder) {
        GiftOrder result = null;
        Integer bufferCount = giftOrder.getCount();
        if (bufferCount < countQ) {
            bufferCount = (countQ * criterionQ) - bufferCount;
            giftOrder.setCount(bufferCount);
            result = giftOrder;
        }
        return result;
    }
}
