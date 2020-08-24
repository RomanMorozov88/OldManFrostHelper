package morozov.ru.oldmanfrostservice.repositories;

import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;
import morozov.ru.oldmanfrostservice.models.gifts.Gift;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;
import morozov.ru.oldmanfrostservice.models.utilmodels.GiftOrder;
import morozov.ru.oldmanfrostservice.models.utilmodels.GiftOrderWrapper;
import morozov.ru.oldmanfrostservice.repositories.interfaces.WarehouseRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class WarehouseImpl implements WarehouseRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Gift getGift(GiftType type) {
        TypedQuery<Gift> query = this.entityManager
                .createQuery("select g from Gift g where g.type = :param and g.owner is empty", Gift.class);
        query.setParameter("param", type);
        return query
                .getResultList()
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Gift> getAll() {
        return this.entityManager
                .createQuery("select g from Gift g", Gift.class)
                .getResultList();
    }

    @Override
    public void sendGiftUpdate(Gift gift, NoteOfDone noteOfDone) {
        gift.setOwner(noteOfDone);
        this.entityManager.merge(gift);
    }

    @Override
    public Gift add(Gift gift) {
        this.entityManager.persist(gift);
        return gift;
    }

    @Override
    public GiftOrder getGiftTypeCount(String type) {
        GiftOrderWrapper result = this.entityManager
                .createQuery(
                        "select new morozov.ru.oldmanfrostservice.models.utilmodels.GiftOrderWrapper(t, count(g)) "
                                + "from Gift g join g.type t where t.typeName = :param and g.owner is empty ",
                        GiftOrderWrapper.class
                )
                .setParameter("param", type)
                .getSingleResult();
        return result.getGiftOrder();
    }


}
