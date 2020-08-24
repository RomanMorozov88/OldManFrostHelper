package morozov.ru.oldmanfrostservice.models.utilmodels;

public class KinderInfo {

    private String kinderName;
    private Boolean isGood;

    public KinderInfo() {
    }

    public String getKinderName() {
        return kinderName;
    }

    public void setKinderName(String kinderName) {
        this.kinderName = kinderName;
    }

    public Boolean isGood() {
        return isGood;
    }

    public void setGood(Boolean good) {
        this.isGood = good;
    }
}