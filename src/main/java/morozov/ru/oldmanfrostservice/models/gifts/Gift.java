package morozov.ru.oldmanfrostservice.models.gifts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import morozov.ru.oldmanfrostservice.models.notes.NoteOfDone;

import javax.persistence.*;

@Entity
@Table(name = "gift_warehouse")
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "type")
    @NotNull
    private GiftType type;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "gift")
    private NoteOfDone owner;

    public Gift() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GiftType getType() {
        return type;
    }

    public void setType(GiftType type) {
        this.type = type;
    }

    public NoteOfDone getOwner() {
        return owner;
    }

    public void setOwner(NoteOfDone owner) {
        this.owner = owner;
    }
}
