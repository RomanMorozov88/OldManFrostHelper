package morozov.ru.oldmanfrostservice.services;

import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.utilmodels.GiftOrder;
import morozov.ru.oldmanfrostservice.models.utilmodels.StringMessageUtil;
import morozov.ru.oldmanfrostservice.repositories.interfaces.GiftTypeRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Некий инспектор, иногда посещающий склад и проверяющий что там как по подаркам.
 * В случае необходимости формирует заказы на недостоющее до максимального значения кол-во подарков
 * и отправляет их на фабрику.
 */
@Component
public class Inspector {

    private static final Logger LOG = LogManager.getLogger(Inspector.class);

    @Value("${q.count}")
    private int countQ;
    @Value("${q.criterion}")
    private int criterionQ;
    @Value("${general.uri}")
    private String generalUri;
    @Value("${factory.uri}")
    private String factoryUri;
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
            String uri = generalUri + factoryUri;
            StringMessageUtil msg = this.restTemplate.postForObject(uri, orders, StringMessageUtil.class);
            LOG.info(msg.getData());
        }

    }

    /**
     * Получает кол-во подарков каждого типа.
     * @param types
     * @return
     */
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

    /**
     * Сверяет кол-во подарков на складе с заданым количеством,
     * ниже которого не должно быть ниже.
     * @param giftOrder
     * @return null-если всё норм и подарков в достатке.
     */
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
