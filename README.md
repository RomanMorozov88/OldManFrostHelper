# OldManFrostHelper

Описание:
```
Система учета подарков для Деда Мороза.


Каждый год Дед Мороз отвозит кучу подарков детям в разные города. Заблаговременно до наступления нового года 
Дед Мороз читает письма, которые прислали ему дети и заготавливает сани с подарками. Так как все дети хотят 
разные игрушки, лесные зверюшки - верные помощники Деда Мороза - делают подарки разных типов. 
При прочтении письма Дед Мороз обращает внимание на пожелание ребенка, после чего, в случае, 
если поведение за год было хорошим, он отправляет ребенку подарок запрошенного типа.


Дед мороз решил вести статистику подарков, а заодно и облегчить работу себе и зверятам, 
автоматизировав их производство. По его задумке, программное обеспечение должно 
автоматически отправлять заказы на предприятие, которое производит подарки типа M, 
если количество подарков данного типа на складе стало меньше, чем N. 


Для решения поставленной задачи он издал федеральный указ “О разработке информационной системы”, 
которая будет обладать следующими основными функциональными возможностями:
1. Выполнять учет доступных подарков различного типа на складе
2. Предоставлять возможность автоматической обработки писем от детей по внешнему запросу:
   1. Уменьшать количество подарков запрошенного типа на складе
   2. Сохранять данные в хранилище об отправленном подарке для конкретного человека.
3. Отправлять заявку на производство подарков типа N, если количество подарков данного типа на складе стало меньше, чем Q.


Нефункциональные требования к ИС:
1. Должна принимать и отправлять запросы, используя REST API.
2. Должна быть написана на языке программирования Java версии 8+.
3. Должна использовать фреймворк Spring (web, data), библиотеки Hibernate.
4. Должна использовать in memory БД H2 для хранения данных.




Сценарии использования:
Сценарий 1:
1. Потребитель вызывает REST API метод ИС, передавая данные письма в json формате (ФИО ребенка, тип подарка)
2. Система запрашивает данные о поведении ребенка за год у внешнего сервиса (на первое время предлагается использовать заглушку, 
которая локально определяет поведение случайным образом, используя равномерное распределение)
3. В случае, если поведение было неудовлетворительным, система возвращает потребителю ошибку и прекращает обработку запроса.
4. Система выделяет подарок со склада для ребенка и логирует выделение в БД сервиса.
Сценарий 2:
1. Если количество подарков типа N стало меньше, чем Q, то система отправляет заказ на производство подарков 
соответствующего типа для пополнения запасов на складе. Для отправки система использует REST API.
Сценарий 3:
1. Когда подарки были произведены, внешняя система запрашивает разрабатываемую ИС используя REST API, чтобы уведомить, 
что количество доступных подарков увеличилось.
```

При запуске создаются таблицы и в них записываются значения(см.morozov.ru.util.DataInit).  
Количество подврков записывается Q - 2 где Q - контрольная точка для проверки Инспектором.  
(см. morozov.ru.oldmanfrostservice.services.Inspector)  
Инспектор запускается через минуту и проводит проверку склада каждые 5 минут.  
Инспектор формирует (при необходимости) заказы для фабрики.

frost.jar - исполняемый фаил.

http://localhost:8080/h2-console - H2 БД.
http://localhost:8080/v2/api-docs - документация запросов от Swagger.

Запросы: POST /frost/letter Приниает:  
{  
"name" : String,  
"middleName" : String,  
"lastName" : String,   
"giftType" : String  
}  
Возвращает: При успехе:  
{  
NoteOfDone  
}  
При плохом поведении:  
(редирект на frost/bad)  
{  
StringMessageUtil  
}  
При отстутствии подарка на складе:  
(редирект на frost/wait)  
(И создаёт запись в таблице с ожидающими(NoteOfWaiting))  
{  
StringMessageUtil  
}  
При отсутствии нужного типа подарка:  
(редирект на frost/unknown)  
{  
StringMessageUtil  
}  
  
 GET /frost/exit Просто на всякий случай- выключение приложения.
