
# Логин - регистър система

## Описание на проекта:
Проектът е Spring Web приложение, който имплементира логин - регистер система с опции за манипулации върху потребителите. В него се включват: REST API, Spring Security, MySQL, интеграция на Gmail и Flyway за миграции.

## Системни изисквания:
Java 17+
MySQL 8.0+
Maven
Gmail

## Команди за стартиране:
mvn clean install - инсталиране на зависимостите
mvn spring-boot:run - стартирай приложението

## Листа с ползвани депендансита:
io.jsonwebtoken (JWT)
com.fasterxml.jackson.dataformat.xml (XML)
org.modelmapper (Model Mapper)
org.flywaydb (Flyway MySQL)
spring-boot-starter (Web, JPA, Security, Mail, Validation)
springdoc-openapi-starter-webmvc-ui (OpenAPI)
mysql-connector-j (MySQL Connector)

## Как да достъпим информация чрез Spring OpenAPI:
След стартиране на приложението, трябва да заредите /swagger-ui.html или /v3/api-docs в браузъра за достъп до документацията на API-то.

## Други настройки
При стартиране на проекта трябва да се прегледат файловете application.properties, в които може да подмените настройките на проекта. Важно е да се отбеле, че трябва да имате системни променливи във Вашата операционна система с имена като в application.properties файловете със съответните стойности.