package morozov.ru.oldmanfrostservice.models.utilmodels;

/**
 * Интерфейс для создания простых ответов вида {name : value}
 * @param <T>
 */
public interface MessageUtil<T> {
    public void setData(T input);
}
