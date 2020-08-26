package morozov.ru.oldmanfrostservice.models.notes;

import com.sun.istack.NotNull;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "wait_list")
public class NoteOfWaiting extends NoteBasic{

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "type")
    @NotNull
    private GiftType type;

    public NoteOfWaiting() {
    }

    public GiftType getType() {
        return type;
    }

    public void setType(GiftType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof NoteBasic)) return false;
        if (!super.equals(o)) return false;
        NoteOfWaiting waiting = (NoteOfWaiting) o;
        return Objects.equals(type, waiting.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type);
    }
}
