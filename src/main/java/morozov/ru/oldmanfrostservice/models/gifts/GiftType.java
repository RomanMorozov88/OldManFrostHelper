package morozov.ru.oldmanfrostservice.models.gifts;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "gift_type")
public class GiftType {

    @Id
    private String type;

    public GiftType() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftType giftType = (GiftType) o;
        return Objects.equals(type, giftType.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
