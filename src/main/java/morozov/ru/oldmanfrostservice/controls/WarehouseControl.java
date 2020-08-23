package morozov.ru.oldmanfrostservice.controls;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WarehouseControl {

    @Autowired
    private WarehouseRepo warehouseRepo;

    @PostMapping("/frost/warehouse")
    public void getCompletedOrder(@RequestBody List<Gift> gifts) {
        for (Gift g : gifts) {
            this.warehouseRepo.add(g);
        }
        //DO LOGGER HERE
    }

}
