# REST Assured API tests

API-автотесты для [JSONPlaceholder](https://jsonplaceholder.typicode.com).

**Стек:** Java 24, Maven, JUnit 5, REST Assured, Jackson, JSON Schema Validator, Allure.

## Что покрыто
- GET / POST / PUT / PATCH / DELETE, позитивные и негативные (404) сценарии
- Параметризованные тесты (`@ParameterizedTest`)
- Валидация ответа по JSON-схеме
- POJO + Builder: сериализация запроса и десериализация ответа
- Цепочка запросов (данные из одного ответа → следующий запрос)
- Общая конфигурация через `RequestSpecification`
- Отчёты Allure

## Структура
```
src/test/java/org/example
├── base/    BaseTest (спецификация запросов), Endpoints (пути)
├── models/  Post, User (POJO)
└── tests/   UsersApiTest, PostsApiTest
src/test/resources/schemas/   JSON-схемы
docs/                         заметки по ручному тестированию в Postman
```

## Запуск
```bash
mvn clean test                              # все тесты
mvn test -Dtest=UsersApiTest                # один класс
mvn test -DbaseUrl=https://other-api.com    # другой хост
```

## Allure-отчёт
```bash
allure serve allure-results
```
