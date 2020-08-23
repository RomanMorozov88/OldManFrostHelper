package morozov.ru.oldmanfrostservice.models.utilmodels;

/**
 * Интерфейс для создания простых ответов вида {name : value}
 * @param <T>
 */
public interface MessageUtil<T> {
    void setData(T input);
    T getData();

}
