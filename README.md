### Hexlet tests and linter status:
[![Actions Status](https://github.com/RomanKharkin/java-project-99/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/RomanKharkin/java-project-99/actions)

[![Maintainability](https://api.codeclimate.com/v1/badges/11584470712929832f75/maintainability)](https://codeclimate.com/github/RomanKharkin/java-project-99/maintainability)

[![Test Coverage](https://api.codeclimate.com/v1/badges/11584470712929832f75/test_coverage)](https://codeclimate.com/github/RomanKharkin/java-project-99/test_coverage)

### Link on working app:
[Render.com](https://java-project-99-n1t9.onrender.com)

## Description:

Task manager application written with Spring Boot, Spring Security and some other technologies (see a list of used technologies below).

The application provides functionality to register new users and, for authenticated users only, to create new:

1) Tasks: main entities of the app. They contain name, description, different unique labels and statuses. Task always shows who its creator is;
2) Labels: unique entities that can be added to any tasks to help picking them out. For example, a user can create such labels as "bag", "urgent", "design" etc. Those labels help an executor to better organise his/her work;
3) Task statuses: entities that help to organise and control task execution through all stages of workflow process.

The app also supports task filtration.

## Requirements:

* JDK 20
* Gradle 8.5 (or above)
* GNU Make

## The main technologies used in the app:

* Spring Boot, Spring Data JPA and Spring Security;
* JSON Web Tokens (JWT);
* PostgreSQL and H2 Database;
* Liquibase (for database migrations);
* SpringDoc OpenAPI;
* Lombok for removing some parts of boilerplate code;
* MockWebServer (for tests);
* Dependencies are managed by Gradle (Groovy);
* Others

Attention: fronted part of the project was provided by [Hexlet](https://ru.hexlet.io).

## Run the app

``` zsh
make test
```

Run with development settings
``` zsh
make start
```

Run production version
```zsh
make start-prod
```
Default url: http://localhost:8080
