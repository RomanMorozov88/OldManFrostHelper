package morozov.ru.oldmanfrostservice.models.notes;

import com.sun.istack.NotNull;
import morozov.ru.oldmanfrostservice.models.gifts.Gift;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "done_list")
public class NoteOfDone extends NoteBasic {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    @NotNull
    private Gift gift;

    public NoteOfDone() {
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof NoteBasic)) return false;
        if (!super.equals(o)) return false;
        NoteOfDone that = (NoteOfDone) o;
        return Objects.equals(gift, that.gift);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), gift);
    }
}