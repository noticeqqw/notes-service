# Notes Service

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring_Security-6.x-brightgreen.svg)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JWT-jjwt_0.12.3-black.svg)](https://github.com/jwtk/jjwt)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED.svg)](https://www.docker.com/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![Lombok](https://img.shields.io/badge/Lombok-latest-red.svg)](https://projectlombok.org/)

REST API сервис для управления заметками с JWT-авторизацией.



## Стек

- Java 21
- Spring Boot 4.0
- Spring Security + JWT (jjwt 0.12.3)
- Spring Data JPA / Hibernate
- PostgreSQL 16
- Docker + Docker Compose
- Maven + Lombok

## Быстрый старт

### Запуск через Docker (рекомендуется)

```bash
docker-compose up --build
```

Приложение будет доступно на `http://localhost:8080`.
PostgreSQL будет доступен на `localhost:5432`.

### Запуск локально

1. Запустить базу данных:

```bash
docker-compose up postgres -d
```

2. Запустить приложение:

```bash
./mvnw spring-boot:run
```

## API

### Аутентификация

| Метод | URL | Описание |
|-------|-----|----------|
| POST | `/api/auth/register` | Регистрация нового пользователя |
| POST | `/api/auth/login` | Вход, возвращает JWT токен |

#### Регистрация

```
POST /api/auth/register
Content-Type: application/json

{
  "username": "alice",
  "email": "alice@example.com",
  "password": "secret123"
}
```

Ответ:

```json
{
  "token": "<jwt>"
}
```

#### Вход

```
POST /api/auth/login
Content-Type: application/json

{
  "username": "alice",
  "password": "secret123"
}
```

### Заметки

Все эндпоинты требуют заголовок:

```
Authorization: Bearer <jwt>
```

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/api/notes` | Получить все свои заметки |
| GET | `/api/notes?tag=work` | Фильтр по тегу |
| POST | `/api/notes` | Создать заметку |
| PUT | `/api/notes/{id}` | Обновить заметку |
| DELETE | `/api/notes/{id}` | Удалить заметку |
| POST | `/api/notes/{id}/tags?name=work` | Добавить тег к заметке |

#### Создать заметку

```
POST /api/notes
Authorization: Bearer <jwt>
Content-Type: application/json

{
  "title": "Моя заметка",
  "content": "Текст заметки"
}
```

#### Ответ (заметка)

```json
{
  "id": 1,
  "title": "Моя заметка",
  "content": "Текст заметки",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00",
  "tags": ["work", "important"]
}
```

## Структура проекта

```
src/main/java/noticeqqw/project/main/
├── config/
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   └── NoteController.java
├── dto/
│   ├── auth/
│   │   ├── RegisterRequest.java
│   │   ├── LoginRequest.java
│   │   └── JwtResponse.java
│   └── note/
│       ├── NoteRequest.java
│       └── NoteResponse.java
├── entity/
│   ├── User.java
│   ├── Note.java
│   └── Tag.java
├── repository/
│   ├── UserRepository.java
│   ├── NoteRepository.java
│   └── TagRepository.java
├── security/
│   ├── JwtUtils.java
│   ├── JwtAuthFilter.java
│   └── UserDetailsServiceImpl.java
├── service/
│   ├── AuthService.java
│   └── NoteService.java
└── MainApplication.java
```

## Конфигурация

Переменные окружения (при запуске без Docker задаются в `application.properties`):

| Переменная | Описание | По умолчанию |
|------------|----------|--------------|
| `SPRING_DATASOURCE_URL` | URL базы данных | `jdbc:postgresql://localhost:5432/notesdb` |
| `SPRING_DATASOURCE_USERNAME` | Пользователь БД | `notes_user` |
| `SPRING_DATASOURCE_PASSWORD` | Пароль БД | `notes_pass` |
| `JWT_SECRET` | Секретный ключ для подписи токенов | — |
| `JWT_EXPIRATION` | Время жизни токена в миллисекундах | `86400000` (24ч) |
