package morozov.ru.oldmanfrostservice.repositories;

import morozov.ru.oldmanfrostservice.models.notes.NoteOfWaiting;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WaitingListRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class WaitingListRepoImpl implements WaitingListRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public NoteOfWaiting save(NoteOfWaiting input) {
        this.entityManager.persist(input);
        return input;
    }

    @Override
    public List<NoteOfWaiting> getAll() {
        return this.entityManager
                .createQuery("select n from NoteOfWaiting n", NoteOfWaiting.class)
                .getResultList();
    }
}
