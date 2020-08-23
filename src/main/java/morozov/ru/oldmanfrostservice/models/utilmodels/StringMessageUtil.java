package morozov.ru.oldmanfrostservice.models.utilmodels;

public class StringMessageUtil implements MessageUtil<String> {

    private String msg;

    public StringMessageUtil() {
    }

    @Override
    public void setData(String input) {
        this.msg = input;
    }

    @Override
    public String getData() {
        return this.msg;
    }
}
