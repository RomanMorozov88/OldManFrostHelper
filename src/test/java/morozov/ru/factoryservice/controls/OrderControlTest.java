package morozov.ru.factoryservice.controls;

import com.fasterxml.jackson.databind.ObjectMapper;
import morozov.ru.factoryservice.factoryutil.ProductionLine;
import morozov.ru.oldmanfrostservice.models.utilmodels.GiftOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderControl.class)
public class OrderControlTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void LetterToBadTest() throws Exception {
        List<GiftOrder> orders = new ArrayList<>();
        Mockito.doNothing()
                .when(threadPoolTaskExecutor)
                .execute(any(ProductionLine.class));
        this.mockMvc.perform(post("/factory/order")
                .content(mapper.writeValueAsString(orders))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(containsString("ACCEPT")));
    }

}