package morozov.ru.oldmanfrostservice.repositories;

import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
import morozov.ru.oldmanfrostservice.repositories.interfaces.DoneListRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class DoneListRepoImpl implements DoneListRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public NoteOfDone saveNote(NoteOfDone input) {
        this.entityManager.persist(input);
        return input;
    }

    @Override
    public NoteOfDone getByName(String name) {
        TypedQuery<NoteOfDone> query = this.entityManager
                .createQuery("select n from NoteOfDone n where n.kinderName = :param ", NoteOfDone.class);
        return query
                .setParameter("param", name)
                .getResultList()
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public List<NoteOfDone> getAll() {
        return this.entityManager
                .createQuery("select n from NoteOfDone n", NoteOfDone.class)
                .getResultList();
    }
}
