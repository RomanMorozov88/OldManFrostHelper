package morozov.ru.factoryservice.factoryutil;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.models.utilmodels.GiftOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Поток для "производства" подарков нужного типа.
 */
@Component
@Scope("prototype")
public class ProductionLine implements Runnable {

    private static final Logger LOG = LogManager.getLogger(ProductionLine.class);

    private RestTemplate restTemplate;
    private String frostUri;
    private GiftOrder order;

    @Autowired
    public ProductionLine() {
    }

    public ProductionLine(GiftOrder order, RestTemplate restTemplate, String frostUri) {
        this.order = order;
        this.restTemplate = restTemplate;
        this.frostUri = frostUri;
    }

    @Override
    public void run() {
        List<Gift> gifts = new ArrayList<>();
        for (int i = 0; i < order.getCount(); i++) {
            try {
                Gift gift = new Gift();
                gift.setType(order.getType());
                gift.setName(i + " " + order.getType().getType());
                gifts.add(gift);
                LOG.info("creating of " + gift.getName());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOG.info(order.getType().getType() + " order done.");
        this.restTemplate.postForLocation(frostUri, gifts);
    }
}
