package morozov.ru.oldmanfrostservice.config;

import morozov.ru.oldmanfrostservice.repositories.DoneListRepoImpl;
import morozov.ru.oldmanfrostservice.repositories.GiftTypeRepoImpl;
import morozov.ru.oldmanfrostservice.repositories.WaitingListRepoImpl;
import morozov.ru.oldmanfrostservice.repositories.WarehouseRepoImpl;
import morozov.ru.oldmanfrostservice.repositories.interfaces.DoneListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.GiftTypeRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WaitingListRepo;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@EnableScheduling
@Configuration
@ComponentScan("morozov.ru")
public class AppConfig {

    @Bean
    public DoneListRepo doneListRepo() {
        return new DoneListRepoImpl();
    }

    @Bean
    public GiftTypeRepo giftTypeRepo() {
        return new GiftTypeRepoImpl();
    }

    @Bean
    public WarehouseRepo warehouseRepo() {
        return new WarehouseRepoImpl();
    }

    @Bean
    public WaitingListRepo waitingListRepo() {
        return new WaitingListRepoImpl();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Random random() {
        return new Random();
    }

}
