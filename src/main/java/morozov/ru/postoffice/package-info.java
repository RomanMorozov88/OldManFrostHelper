/**
 * Почтовый "Сервис".
 * Т.к. возможна ситуация, когда нужный подарок\подарки на складе отсутствуют,
 * в сервисе деда будет организовываться очередь ожидающих- именно список этих ожидающих
 * будет отправлен сюда для рассылки подарков по мере их поступления на склад
 * (подразумевается, что внутри этот сервис работает так же с сервисом kinderservice
 * и вообще занимается всякой почтовой магией, но нам этот тут не нужно, так что
 * он будет просто принимать список и ответом сообщать некое сообщение с текстом, что всё ок)
 */
package morozov.ru.postoffice;