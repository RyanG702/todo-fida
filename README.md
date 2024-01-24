# FIDA Todo List Project

## Project Overview
This project is a todo list app that performs various functions based on given requirements:

- Display a list of todos
- Allow for a todo to be deleted
- Allow for a todo to be completed, along with completion date


## Setup

### Project Specifications
- Java 17 SDK
- JPA
- Maven Wrapper
- H2 In-Memory Database

### Step 1: Clone repository
```bash
git clone git@github.com:RyanG702/todo-fida.git
```

### Step 2: Run project locally
- Ensure that your IDE is using Java 17 JDK
- navigate to the root of the project and run script ./deploy.sh
- Application will run on port `:8080`

### Step 3: Interact with application
- Web App: http://localhost:8080/
- Swagger UI: http://localhost:8080/swagger-ui/index.html#/

### Step 4: Get started
Add some todos by using one of the following options:

**Swagger**
```http
http://localhost:8080/swagger-ui/index.html#/todo-controller/addTodos
```

**Curl**
```curl
curl -X 'POST' \
'http://localhost:8080/api/v1/todos' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '[
"Buy eggs", "Fold laundry", "Call mom", "Lüfte die Wohnung", "Gassi mit dem Hund gehen"
]'
```

**Postman**
```http
POST http://localhost:8080/api/v1/todos
```
```json
// Example payload
[
"Buy eggs", "Fold laundry", "Call mom", "Lüfte die Wohnung", "Gassi mit dem Hund gehen"
]
```


## API Reference

### Todo Controller

#### Create new todo

```http
POST /api/v1/todos
```

```json
// Example payload
[
"Buy eggs", "Fold laundry", "Call mom", "Lüfte die Wohnung", "Gassi mit dem Hund gehen"
]
```

#### Get all todos

```http
GET /api/v1/todos
```

#### Delete a todo

```http
DELETE /api/v1/todo/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `string` | **Required**. Unique identifier of the todo entity. |


#### Update a todo's completed date

```http
PUT /api/v1/todo/{id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `string` | **Required**. Unique identifier of the todo entity. |


## H2 Database Console
The H2 database console provided a client to vew and interact with the data in the back-end.
- H2 Console UI: http://localhost:8080/h2-console
  - Username: `user`
  - Password: _leave empty_


## Design Decisions
Some design decisions were made based on assumptions from reading the project brief:

| Topic | Design Decision                                                                                                                                                                                                                                                            |
| :----- |:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|Data Model|Used Spring JPA data to map the entity|
|Database|Used H2 in-memory database|
|Sanitization|Used Java Jsoup library to sanitize input data on POST data|
|CSS & JS|Used plain, vanilla HTML, CSS, and JavaScript to keep the front-end light-weight. Back-end design and process was the main focus.|
|Containerization|Decided not to require running this app from an image, requiring Docker Utility, for simplified installation and use.|