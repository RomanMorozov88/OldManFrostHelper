package morozov.ru.factoryservice.controls;

import morozov.ru.factoryservice.factoryutil.ProductionLine;
import morozov.ru.oldmanfrostservice.models.utilmodels.GiftOrder;
import morozov.ru.oldmanfrostservice.models.utilmodels.StringMessageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Получает заказ на производство.
 * Раскидывает заказы по разным производственным линиям
 * и возвращает ответ, что, мол, принято.
 */
@RestController
public class OrderControl {

    private static final Logger LOG = LogManager.getLogger(OrderControl.class);

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private RestTemplate restTemplate;
    @Value("${general.uri}")
    private String generalUri;
    @Value("${frost.uri}")
    private String frostUri;

    @Autowired
    public OrderControl(ThreadPoolTaskExecutor threadPoolTaskExecutor, RestTemplate restTemplate) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/factory/order")
    public StringMessageUtil acceptOrder(@RequestBody List<GiftOrder> orders) {
        LOG.info("We got new order.");
        String uri = generalUri + frostUri;
        for (GiftOrder o : orders) {
            threadPoolTaskExecutor.execute(new ProductionLine(o, restTemplate, uri));
        }
        StringMessageUtil msg = new StringMessageUtil();
        msg.setData("ACCEPT");
        return msg;
    }
}
