package morozov.ru.oldmanfrostservice.controls;

import morozov.ru.oldmanfrostservice.repositories.interfaces.GiftTypeRepo;
import morozov.ru.oldmanfrostservice.services.LetterControlUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(LetterControl.class)
public class LetterControlTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LetterControlUtil letterControlUtil;
    @MockBean
    private GiftTypeRepo giftTypeRepo;
    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void whenGetIfBad() throws Exception {
        String result = "Try to be good next year.";
        this.mockMvc.perform(get("/frost/bad"))
                .andDo(print())
                .andExpect(content().string(containsString(result)));
    }

    @Test
    public void whenGetIfUnknown() throws Exception {
        String result = "This is an unknown type of gift. "
                + "We will send this one in a year, as soon as we set up production.";
        this.mockMvc.perform(get("/frost/unknown"))
                .andDo(print())
                .andExpect(content().string(containsString(result)));
    }

    @Test
    public void whenGetIfWait() throws Exception {
        String result = "Dont be sad, Snow Maiden is looking for your gift.";
        this.mockMvc.perform(get("/frost/wait"))
                .andDo(print())
                .andExpect(content().string(containsString(result)));
    }

}