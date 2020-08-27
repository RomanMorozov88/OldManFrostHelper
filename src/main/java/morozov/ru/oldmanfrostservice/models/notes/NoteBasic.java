package morozov.ru.oldmanfrostservice.models.notes;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public class NoteBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "kinder_name", nullable = false)
    private String kinderName;
    @Column(name = "kinder_middle_name", nullable = false)
    private String kinderMiddleName;
    @Column(name = "kinder_last_name", nullable = false)
    private String kinderLastName;

    public NoteBasic() {
    }

    public NoteBasic(String kinderName, String kinderMiddleName, String kinderLastName) {
        this.kinderName = kinderName;
        this.kinderMiddleName = kinderMiddleName;
        this.kinderLastName = kinderLastName;
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

    public String getKinderMiddleName() {
        return kinderMiddleName;
    }

    public void setKinderMiddleName(String kinderMiddleName) {
        this.kinderMiddleName = kinderMiddleName;
    }

    public String getKinderLastName() {
        return kinderLastName;
    }

    public void setKinderLastName(String kinderLastName) {
        this.kinderLastName = kinderLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof NoteBasic)) return false;
        NoteBasic noteBasic = (NoteBasic) o;
        return id == noteBasic.id &&
                Objects.equals(kinderName, noteBasic.kinderName) &&
                Objects.equals(kinderMiddleName, noteBasic.kinderMiddleName) &&
                Objects.equals(kinderLastName, noteBasic.kinderLastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, kinderName, kinderMiddleName, kinderLastName);
    }
}
