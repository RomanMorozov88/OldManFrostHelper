package morozov.ru.oldmanfrostservice.config;

import morozov.ru.oldmanfrostservice.repositories.DoneListImpl;
import morozov.ru.oldmanfrostservice.repositories.GiftTypeRepoImpl;
import morozov.ru.oldmanfrostservice.repositories.WarehouseImpl;
import morozov.ru.oldmanfrostservice.repositories.interfaces.DoneListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.GiftTypeRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@EnableScheduling
@Configuration
@ComponentScan("morozov.ru")
public class AppConfig {

    @Bean
    public DoneListRepo doneListRepo() {
        return new DoneListImpl();
    }

    @Bean
    public GiftTypeRepo giftTypeRepo() {
        return new GiftTypeRepoImpl();
    }

    @Bean
    public WarehouseRepo warehouseRepo() {
        return new WarehouseImpl();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
