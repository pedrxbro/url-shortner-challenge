# 📎 URL Shortener

Um encurtador de URLs desenvolvido em **Java + Spring Boot**, utilizando **MongoDB** para persistência e containerizado via **Docker**.
Ele gera um código curto para uma URL longa e redireciona para ela quando acessado.

---

## 🚀 Tecnologias usadas

* Java 17+
* Spring Boot
* Spring Data MongoDB
* MongoDB
* Docker e Docker Compose
* Apache Commons Lang (`RandomStringUtils`)

---

## 📂 Estrutura do projeto

```
.
├── docker/
│   └── docker-compose.yml
├── src/main/java/com/univali/urlshortner/
│   ├── controller/
│   │   └── UrlShortnerController.java
│   ├── dto/
│   │   └── UrlRequestDTO.java
│   ├── entity/
│   │   └── UrlShortnerEntity.java
│   ├── repository/
│   │   └── UrlShortnerRepository.java
│   ├── service/
│   │   └── UrlShortnerService.java
│   └── UrlShortnerApplication.java
└── resources/
    ├── static/
    ├── templates/
    └── application.properties
```

---

## 📄 Camadas

* **Controller**: expõe os endpoints REST para criar e redirecionar URLs.
* **DTO**: objeto para receber a URL original enviada pelo cliente.
* **Entity**: representação da URL no banco MongoDB com data de expiração.
* **Repository**: interface para persistência (Spring Data MongoDB).
* **Service**: regra de negócios para encurtar e redirecionar URLs.

---

## 🐳 Rodando com Docker

O projeto usa MongoDB em um container.

### Pré-requisitos

* Docker
* Docker Compose
* JDK 17+
* Maven

### Passos

1️⃣ Suba o banco MongoDB:

```bash
docker-compose up -d
```

O `docker/docker-compose.yml` já define usuário e senha para o banco:

```yaml
services:
  mongodb:
    image: mongo
    ports:
      - "27017:27017"
    environment:
      - MONGO_INITDB_ROOT_USERNAME=admin
      - MONGO_INITDB_ROOT_PASSWORD=123
```

2️⃣ Rode a aplicação Spring Boot:

```bash
./mvnw spring-boot:run
```


---

## 🔗 Endpoints

| Método | Rota           | Descrição                       |
| ------ | -------------- | ------------------------------- |
| POST   | `/shorten-url` | Cria uma URL encurtada          |
| GET    | `/{id}`        | Redireciona para a URL original |

### Exemplo: encurtar uma URL

**Requisição**

```http
POST /shorten-url
Content-Type: application/json

{
  "url": "https://www.google.com"
}
```

**Resposta**

```json
"http://localhost:8080/gApzi6"
```

### Exemplo: redirecionar

Acessando no navegador:

```
http://localhost:8080/gApzi6
```
O sistema consulta o banco de dados para encontrar a URL original associada a esse ID.

Retorna `302 Found` e redireciona para a URL original.

---

## ⚙️ Configurações

No `src/main/resources/application.properties` você deve configurar a conexão com o MongoDB.
Certifique-se de que as credenciais batem com as do `docker-compose.yml` (admin/123) e que a porta é 27017.

Exemplo mínimo:

```properties
spring.data.mongodb.uri=mongodb://admin:123@localhost:27017/admin
```

---

## 👨‍💻 Desenvolvimento

Esse projeto foi desenvolvido para fins de aprendizado, explorando:

* Criação de APIs REST com Spring Boot.
* Boas práticas com DTOs e Services.
* Persistência com MongoDB.
* Redirecionamentos HTTP.
* Expiração de documentos com `@Indexed(expireAfterSeconds)`.

---

## 📜 Sobre o tempo de expiração

Cada URL encurtada expira em **1 minuto** após ser criada.
Esse comportamento está definido em `UrlShortnerService`:

```java
LocalDateTime.now().plusMinutes(1)
```

e na entidade:

```java
@Indexed(expireAfterSeconds = 0)
private LocalDateTime expiresAt;
```

---

