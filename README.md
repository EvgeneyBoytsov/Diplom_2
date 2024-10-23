# Diplom_2
# Задание 
Необходимо протестировать ручки API для Stellar Burgers: 
Создание пользователя, 
Логин пользователя, 
Изменение данных пользователя
Создание заказа, 
Получение заказов конкретного пользователя.

## Написаны тесты на проверку:
1. Создание уникального пользователя
2. Создание пользователя, который уже зарегистрирован
3. Создание пользователя и не заполнить одно из обязательных полей
4. Авторизация пользователя под существующим пользователем
5. Авторизация пользователя с неверным логином и паролем
6. Изменение данных пользователя авторизированного пользователя
7. Изменение данных пользователя не авторизированного пользователя
8. Создание заказа авторизированным пользователем
9. Создание заказа не авторизированным пользователем
10. Создание заказа с ингредиентами
11. Создание заказа без ингредиентов
12. Создание заказа с неверным хешем ингредиентов
13. Получение заказов авторизированного пользователя
14. Получение заказов не авторизированного пользователя

## Создан allure отчёт