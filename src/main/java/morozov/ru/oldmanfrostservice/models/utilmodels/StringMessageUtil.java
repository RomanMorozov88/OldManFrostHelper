package morozov.ru.oldmanfrostservice.models.utilmodels;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
