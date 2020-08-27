package morozov.ru.oldmanfrostservice.repositories;

import morozov.ru.oldmanfrostservice.models.notes.NoteOfWaiting;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WaitingListRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class WaitingListRepoImpl implements WaitingListRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public NoteOfWaiting saveNote(NoteOfWaiting input) {
        this.entityManager.persist(input);
        return input;
    }

    @Override
    public List<NoteOfWaiting> getAll() {
        return this.entityManager
                .createQuery("select n from NoteOfWaiting n", NoteOfWaiting.class)
                .getResultList();
    }

    @Override
    public void delete(NoteOfWaiting input) {
        this.entityManager.remove(input);
    }
}
