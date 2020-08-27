package morozov.ru.oldmanfrostservice.repositories;

import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
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
public class DoneListRepoImplTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private DoneListRepoImpl doneListRepoImpl;

    @Before
    public void init() {
        GiftType type = new GiftType();
        type.setTypeName("test type");
        entityManager.persistAndFlush(type);
        Gift gift = new Gift();
        gift.setType(type);
        gift.setName("test gift");
        entityManager.persistAndFlush(gift);
        NoteOfDone note = new NoteOfDone();
        note.setKinderName("test name");
        note.setKinderMiddleName("test middle name");
        note.setKinderLastName("test last name");
        note.setGift(gift);
        doneListRepoImpl.saveNote(note);
    }

    @Test(expected = PersistenceException.class)
    public void whenSaveEmpty() {
        NoteOfDone note = new NoteOfDone();
        doneListRepoImpl.saveNote(note);
    }

    @Test
    public void whenGetByNameFirstCase() {
        NoteOfDone result = doneListRepoImpl.getByName("test name");
        assertEquals("test name", result.getKinderName());
    }

    @Test
    public void whenGetByNameSecondCase() {
        NoteOfDone result = doneListRepoImpl.getByName("null name");
        assertNull(result);
    }

    @Test
    public void whenGetAllFirstCase() {
        List<NoteOfDone> result = doneListRepoImpl.getAll();
        assertEquals(1, result.size());
    }

    @Test
    public void whenGetAllSecondCase() {
        NoteOfDone buffer = doneListRepoImpl.getAll().get(0);
        entityManager.remove(buffer);
        List<NoteOfDone> result = doneListRepoImpl.getAll();
        assertEquals(0, result.size());
    }
}