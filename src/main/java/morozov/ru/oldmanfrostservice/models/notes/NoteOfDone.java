package morozov.ru.oldmanfrostservice.models.notes;

import com.sun.istack.NotNull;
import morozov.ru.oldmanfrostservice.models.gifts.Gift;

import javax.persistence.*;

@Entity
@Table(name = "done_list")
public class NoteOfDone implements NoteBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "kinder_name")
    @NotNull
    private String kinderName;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    @NotNull
    private Gift gift;

    public NoteOfDone() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKinderName() {
        return kinderName;
    }

    public void setKinderName(String kinderName) {
        this.kinderName = kinderName;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

}