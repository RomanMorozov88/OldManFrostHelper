package morozov.ru.oldmanfrostservice.models.notes;

import com.sun.istack.NotNull;
import morozov.ru.oldmanfrostservice.models.gifts.GiftType;

import javax.persistence.*;

@Entity
@Table(name = "wait_list")
public class NoteOfWaiting implements NoteBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "kinder_name")
    @NotNull
    private String kinderName;
    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "type")
    @NotNull
    private GiftType type;

    public NoteOfWaiting() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKinderName(String kinderName) {
        this.kinderName = kinderName;
    }

    @Override
    public String getKinderName() {
        return this.kinderName;
    }

    public GiftType getType() {
        return type;
    }

    public void setType(GiftType type) {
        this.type = type;
    }
}
