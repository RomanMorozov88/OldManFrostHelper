package morozov.ru.oldmanfrostservice.repositories;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
import morozov.ru.oldmanfrostservice.models.utilmodels.GiftOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestEntityManager
@ComponentScan("morozov.ru.oldmanfrostservice.repositories")
public class WarehouseRepoImplTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private WarehouseRepoImpl warehouseRepoImpl;

    private GiftType typeOne;
    private GiftType typeTwo;

    /**
     * Во время инициализации исходных условий
     * определяем два типа подарков,
     * один NoteOfDone.
     * Первого типа- два подарка-
     * один из них с полем owner == null,
     * второй, соответственно, с полем owner != null
     * <p>
     * Втрого типа- один подарок с полем owner == null
     * <p>
     * ИТОГО: 3 подарка.
     */
    @Before
    public void setUp() {
        GiftType type = new GiftType();
        type.setTypeName("first type");
        this.entityManager.persistAndFlush(type);
        typeOne = type;
        this.initObjects(type, "first gift", null);
        NoteOfDone note = new NoteOfDone();
        note.setKinderName("test name");
        note.setKinderMiddleName("test middle name");
        note.setKinderLastName("test last name");
        this.initObjects(type, "second gift", note);
        type = new GiftType();
        type.setTypeName("second type");
        this.entityManager.persistAndFlush(type);
        typeTwo = type;
        this.initObjects(type, "third type", null);
    }

    private void initObjects(
            GiftType type,
            String giftName,
            NoteOfDone note
    ) {
        Gift gift = new Gift();
        gift.setName(giftName);
        gift.setType(type);
        warehouseRepoImpl.saveGift(gift);
        if (note != null) {
            note.setGift(gift);
            entityManager.persistAndFlush(note);
        }
    }

    @Test(expected = PersistenceException.class)
    public void whenSaveEmpty() {
        Gift gift = new Gift();
        warehouseRepoImpl.saveGift(gift);
    }

    @Test
    public void whenGetGiftByTypeFirstCase() {
        Gift result = warehouseRepoImpl.getGift(typeOne);
        assertEquals("first gift", result.getName());
    }

    @Test
    public void whenGetGiftByTypeSecondCase() {
        GiftType type = new GiftType();
        type.setTypeName("null type");
        Gift result = warehouseRepoImpl.getGift(type);
        assertNull(result);
    }

    @Test
    public void whenGetCountTypeFirstCase() {
        GiftOrder result = warehouseRepoImpl.getGiftTypeCount(typeTwo.getTypeName());
        assertEquals(new Integer(1), result.getCount());
    }

    @Test
    public void whenGetCountTypeSecondCase() {
        GiftOrder result = warehouseRepoImpl.getGiftTypeCount(typeOne.getTypeName());
        assertEquals(new Integer(1), result.getCount());
    }

    @Test
    public void whenGetAllGifts() {
        List<Gift> result = warehouseRepoImpl.getAll();
        assertEquals(3, result.size());
    }

}