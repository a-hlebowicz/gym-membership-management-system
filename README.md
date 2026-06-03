# Gym Membership Management System

application for managing gym memberships, including gyms, membership plans
and members.

## stack

- Java 21
- Spring Boot 4
- Spring Data JPA
- H2
- Maven

## build and run
Requires JDK 21
```
./mvnw spring-boot:run
```

On Windows
```
mvnw.cmd spring-boot:run
```
starts on `http://localhost:8080`. The database is in-memory,
so all data is reset on every restart.

H2 console is available at `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:gymdb`, user: `sa`, empty password).

## API

### create a gym

`POST /api/gyms`

```json
{
  "name": "Gym",
  "address": "street city",
  "phoneNumber": "+48 123 456 789"
}
```

responses:
- `201 Created` with the created gym
- `400 Bad Request` any field blank
- `409 Conflict` gym with the same name already exists

### list all gyms

`GET /api/gyms`

returns `200 OK` with an array of gyms (empty array if none exist).

### create a plan

`POST /api/gyms/{gymId}/plans`

type is one of `BASIC`, `PREMIUM`, `GROUP`.

```json
{
  "name": "Plan",
  "type": "PREMIUM",
  "amount": 100.99,
  "currency": "PLN",
  "durationMonths": 1,
  "maxMembers": 5
}
```

responses:
- `201 Created` with the created plan
- `400 Bad Request` invalid or missing fields
- `404 Not Found` gym does not exist

### list plans of a gym

`GET /api/gyms/{gymId}/plans`

returns `200 OK` with an array of plans (empty array if the gym has none),
or `404 Not Found` if the gym does not exist.

### register a member

`POST /api/plans/{planId}/members`

```json
{
  "name": "Aleksander",
  "surname": "Hlebowicz",
  "email": "aleksanderhlebowicz@gmail.com"
}
```

responses:
- `201 Created` with the created member
- `400 Bad Request` invalid or missing fields
- `404 Not Found` plan does not exist
- `409 Conflict` plan is full (active members reached maxMembers)

### list all members

`GET /api/members`

returns `200 OK` with an array of all members, with plan name, gym name and status.

### cancel a membership

`POST /api/members/{id}/cancel`

responses:
- `200 OK` with the member (status `CANCELLED`)
- `404 Not Found` member does not exist
- `409 Conflict` member is already cancelled