package morozov.ru.oldmanfrostservice.controls;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LetterControlTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LetterControl letterControl;

    @Test
    public void getIfBad() throws Exception {
        String result = "Try to be good next year.";
        this.mockMvc.perform(get("/frost/bad"))
                .andDo(print())
                .andExpect(content().string(containsString(result)));
    }

    @Test
    public void getIfUnknown() throws Exception {
        String result = "This is an unknown type of gift. "
                + "We will send this one in a year, as soon as we set up production.";
        this.mockMvc.perform(get("/frost/unknown"))
                .andDo(print())
                .andExpect(content().string(containsString(result)));
    }

    @Test
    public void getIfWait() throws Exception {
        String result = "Dont be sad, Snow Maiden is looking for your gift.";
        this.mockMvc.perform(get("/frost/wait"))
                .andDo(print())
                .andExpect(content().string(containsString(result)));
    }

}