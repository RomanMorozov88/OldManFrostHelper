package morozov.ru.oldmanfrostservice.controls;

import com.fasterxml.jackson.databind.ObjectMapper;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.notes.NoteBasic;
import morozov.ru.oldmanfrostservice.models.utilmodels.Letter;
import morozov.ru.oldmanfrostservice.repositories.interfaces.GiftTypeRepo;
import morozov.ru.oldmanfrostservice.services.LetterControlUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@WebMvcTest(LetterControl.class)
public class LetterControlTestMain {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LetterControlUtil letterControlUtil;
    @MockBean
    private GiftTypeRepo giftTypeRepo;
    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void LetterToBadTest() throws Exception {
        Letter letter = new Letter();
        Mockito.when(
                restTemplate.postForObject(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(NoteBasic.class),
                        ArgumentMatchers.any()
                )
        ).thenReturn(false);
        mockMvc.perform(post("/frost/letter")
                .content(mapper.writeValueAsString(letter))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/frost/bad"));
    }


    @Test
    public void LetterToWaitTest() throws Exception {

        Letter letter = new Letter();
        letter.setName("name");
        letter.setMiddleName("middle");
        letter.setLastName("last");
        letter.setGiftType("test type");
        Mockito.when(
                giftTypeRepo.getType(ArgumentMatchers.anyString())
        )
                .thenReturn(new GiftType());
        Mockito.when(
                restTemplate.postForObject(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(NoteBasic.class),
                        ArgumentMatchers.any()
                )
        ).thenReturn(true);
        Mockito.when(
                letterControlUtil.formingGiftForLetterControl(
                        ArgumentMatchers.any(NoteBasic.class),
                        ArgumentMatchers.any(GiftType.class)
                )
        ).thenReturn(null);
        mockMvc.perform(post("/frost/letter")
                .content(mapper.writeValueAsString(letter))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/frost/wait"));
    }

    @Test
    public void LetterToUnknownTest() throws Exception {

        Letter letter = new Letter();
        letter.setName("name");
        letter.setMiddleName("middle");
        letter.setLastName("last");
        letter.setGiftType("test type");
        Mockito.when(
                giftTypeRepo.getType(ArgumentMatchers.anyString())
        )
                .thenReturn(null);
        Mockito.when(
                restTemplate.postForObject(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(NoteBasic.class),
                        ArgumentMatchers.any()
                )
        ).thenReturn(true);
        mockMvc.perform(post("/frost/letter")
                .content(mapper.writeValueAsString(letter))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(redirectedUrl("/frost/unknown"));
    }


}
