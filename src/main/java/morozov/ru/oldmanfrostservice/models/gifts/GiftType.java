package morozov.ru.oldmanfrostservice.models.gifts;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "gift_type")
public class GiftType {

    @Id
    @Column(name = "type", nullable = false)
    private String typeName;

    public GiftType() {
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftType giftType = (GiftType) o;
        return Objects.equals(typeName, giftType.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName);
    }
}
