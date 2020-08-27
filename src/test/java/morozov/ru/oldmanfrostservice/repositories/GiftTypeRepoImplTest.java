package morozov.ru.oldmanfrostservice.repositories;

import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestEntityManager
@ComponentScan("morozov.ru.oldmanfrostservice.repositories")
public class GiftTypeRepoImplTest {

    @Autowired
    private GiftTypeRepoImpl giftTypeRepoImpl;
    private final static String FIRST_TYPE = "First Test Type";
    private final static String SECOND_TYPE = "Second Test Type";

    @Before
    public void setUp() {
        GiftType type = new GiftType();
        type.setTypeName(FIRST_TYPE);
        giftTypeRepoImpl.saveType(type);
        type = new GiftType();
        type.setTypeName(SECOND_TYPE);
        giftTypeRepoImpl.saveType(type);
    }

    @Test(expected = PersistenceException.class)
    public void whenSaveEmpty() {
        GiftType type = new GiftType();
        giftTypeRepoImpl.saveType(type);
    }

    @Test
    public void whenGetTypeFirstCase() {
        GiftType result = giftTypeRepoImpl.getType(FIRST_TYPE);
        assertEquals(FIRST_TYPE, result.getTypeName());
    }

    @Test
    public void whenGetTypeSecondCase() {
        GiftType result = giftTypeRepoImpl.getType("null type");
        assertNull(result);
    }

    @Test
    public void whenGetAllTypes() {
        List<GiftType> result = giftTypeRepoImpl.getAllTypes();
        assertEquals(2, result.size());
    }

    @Test
    public void whenTypeIsPresent() {
        Boolean result = giftTypeRepoImpl.isPresent(SECOND_TYPE);
        assertTrue(result);
    }

}