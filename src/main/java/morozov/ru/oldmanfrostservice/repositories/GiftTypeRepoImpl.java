package morozov.ru.oldmanfrostservice.repositories;

import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.repositories.interfaces.GiftTypeRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class GiftTypeRepoImpl implements GiftTypeRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveType(GiftType giftType) {
        this.entityManager.persist(giftType);
    }

    @Override
    public GiftType getType(String typeName) {
        TypedQuery<GiftType> query = this.entityManager
                .createQuery("select gt from GiftType gt where gt.type = :param ", GiftType.class);
        return query
                .setParameter("param", typeName)
                .getResultList()
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public List<GiftType> getAllTypes() {
        return this.entityManager
                .createQuery("select gt from GiftType gt", GiftType.class)
                .getResultList();
    }

    @Override
    public Boolean isPresent(String type) {
        TypedQuery<Boolean> query = entityManager.createQuery(
                "select (count (t) > 0) from GiftType t where t.type = :param", Boolean.class)
                .setParameter("param", type);
        return query
                .getSingleResult();
    }
}
