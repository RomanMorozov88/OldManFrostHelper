package morozov.ru.oldmanfrostservice.models.utilmodels;

/**
 * Класс, описывающий письмо к деду Морозу.
 */
public class Letter {

    private String name;
    private String middleName;
    private String lastName;
    private String giftType;

    public Letter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGiftType() {
        return giftType;
    }

    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }
}