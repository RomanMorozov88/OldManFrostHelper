package morozov.ru.oldmanfrostservice.repositories;

import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfWaiting;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestEntityManager
@ComponentScan("morozov.ru.oldmanfrostservice.repositories")
public class WaitingListRepoImplTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private WaitingListRepoImpl waitingListRepoImpl;
    private GiftType type;

    @Before
    public void initTypes() {
        GiftType type = new GiftType();
        type.setTypeName("test type");
        entityManager.persistAndFlush(type);
        this.type = type;
        NoteOfWaiting note = new NoteOfWaiting();
        note.setKinderName("1 test name");
        note.setKinderMiddleName("1 test middle name");
        note.setKinderLastName("1 test last name");
        note.setType(type);
        waitingListRepoImpl.saveNote(note);
        note = new NoteOfWaiting();
        note.setKinderName("2 test name");
        note.setKinderMiddleName("2 test middle name");
        note.setKinderLastName("2 test last name");
        note.setType(type);
        waitingListRepoImpl.saveNote(note);
    }

    @Test(expected = PersistenceException.class)
    public void whenSaveEmpty() {
        NoteOfWaiting note = new NoteOfWaiting();
        waitingListRepoImpl.saveNote(note);
    }

    @Test
    public void whenGetAll() {
        List<NoteOfWaiting> result = waitingListRepoImpl.getAll();
        assertEquals(2, result.size());

    }

    @Test
    public void whenDelete() {
        List<NoteOfWaiting> result = waitingListRepoImpl.getAll();
        NoteOfWaiting note = result.get(0);
        waitingListRepoImpl.delete(note);
        result = waitingListRepoImpl.getAll();
        assertEquals(1, result.size());

    }
}