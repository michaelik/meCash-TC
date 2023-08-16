## Author
#### Name: Ikechukwu Michael, Email: mikeikechi3@gmail.com
## Introduction🖖
My solution to [meCash](https://www.musala.com) technical assessment.

### Specification

- Users are able to sign up using their email and password.
- On successful profile creation, they are auto (randomly) assigned an account number with a balance in either currency A or B
- Users are able to log in using a username and password
- Users are able to transfer money from their account to another account regardless of the currencies
- Users are able to get their account balance and transaction history
- There are only 2 currencies, currency A & B, which exchange rate A → B: 1.3455

---

### Step One - Tools and Technologies used 🎼

- Spring Boot(Latest Version)
- Spring Data JPA
- Lombok Library
- JDK 18
- Embedded Tomcat
- Mysql Database(Mysql Workbench)
- Maven
- Java IDE (IntelliJ)
- Postman Client

---

### Step Two - Steps to Run the project Locally ⚙️

[MySQL Workbench](https://www.mysql.com/products/workbench) was used to run the database locally. Navigate to the project application.properties file and modify the database credential per your database server requirement such as `username` and `password`
```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/maCash?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    username: #Database-username
    password: #Database-password
```
## Installation

* Clone this repo:

```bash
git clone https://github.com/michaelik/Financial-Based-REST-Service-APIs.git
```
* Navigate to the root directory of the project.
* Build the application
```bash
./mvnw clean install
```
* Run the application
```bash
./mvnw spring-boot:run
```
---

## Usage 🧨

>REST APIs for Drone Service Resource

| HTTP METHOD |               ROUTE                | STATUS CODE |              DESCRIPTION              |
|:------------|:----------------------------------:|:------------|:-------------------------------------:|
| POST        |        `/api/auth/register`        | 201         |            Create new user            |
| POST        |         `/api/auth/login`          | 200         | Login into your newly created account |
| GET         |     `/api/v1/user-detail/{id}`     | 200         |          Read account detail          |
| GET         |   `/api/v1/account-balance/{id}`   | 200         |         Read account balance          |
| POST        |      `/api/v1/transfer/{id}`       | 200         |             Make transfer             |
| GET         | `/api/v1/transaction-history/{id}` | 200         |       Read transaction history        |
| POST        |    `/api/v1/fund-account/{id}`     | 200         |    Fund your newly created account    |

---

### The Client should be able to:

**SignUp**

The payload will have the following fields:

- `name`:
- `email`:
- `password`
- `age`:
- `gender`:
- `accountCurrency`:

Request

```
curl -X POST http://localhost:8080/api/auth/register \
-H 'Content-type: application/json' \
-d '{
    "name": "Ikechukwu Michael",
    "email": "mikeikechi3@gmail.com",
    "password": "12345",
    "age": 20,
    "gender": "M"
    "accountCurrency": A
}'
```
**SignIn**

The payload will have the following field:
- `username`:
- `password`:

Request

```
curl -X POST http://localhost:8080/api/auth/register \
-H 'Content-type: application/json' \
-d '{
    "username": "mikeikechi3@gmail.com",
    "password": "12345"
}'
```

Response

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6Im1pa2Vpa2VjaGkzQGdtYWlsLmNvbSIsImlhdCI6MTY5MjIwOTQ2MiwiZXhwIjoxNjkzNTA1NDYyfQ.tWbASUsAzxtOjVCGWB9_dNn6qmm35IATzoNT9QQmmUY",
  "id": 1,
  "name": "michael ikechukwu",
  "email": "mikeikechi3@gmail.com",
  "gender": "M",
  "age": 20,
  "roles": [
    "ROLE_USER"
  ],
  "username": "mikeikechi3@gmail.com"
  }
}
```
**Read Account Detail**

Request

```
curl -X GET http://localhost:8080/api/v1/user-detail/1 \
-H 'Content-type: application/json' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6Im1pa2Vpa2VjaGkzQGdtYWlsLmNvbSIsImlhdCI6MTY5MjIwOTQ2MiwiZXhwIjoxNjkzNTA1NDYyfQ.tWbASUsAzxtOjVCGWB9_dNn6qmm35IATzoNT9QQmmUY' \
```

Response

```json
{
  "id": 1,
  "name": "michael ikechukwu",
  "email": "mikeikechi3@gmail.com",
  "age": 20,
  "gender": "M",
  "accountNumber": "4613455672",
  "accountCurrency": "B"
}
```
**Read Account Balance**

Request

```
curl -X GET http://localhost:8080/api/v1/account-balance/1 \
-H 'Content-type: application/json' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6Im1pa2Vpa2VjaGkzQGdtYWlsLmNvbSIsImlhdCI6MTY5MjIwOTQ2MiwiZXhwIjoxNjkzNTA1NDYyfQ.tWbASUsAzxtOjVCGWB9_dNn6qmm35IATzoNT9QQmmUY' \
```

Response

```json
{
  "accountName": "michael ikechukwu",
  "accountNumber": "4613455672",
  "totalAccountBalance": "B0.00"
}
```
**Make Transfer**

The url will have the following request:
- `accountNumber`:
- `amount`:

Request

```
curl -X POST http://localhost:8080/api/v1/transfer/1 \
-H 'Content-type: application/json' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6Im1pa2Vpa2VjaGkzQGdtYWlsLmNvbSIsImlhdCI6MTY5MjIwOTQ2MiwiZXhwIjoxNjkzNTA1NDYyfQ.tWbASUsAzxtOjVCGWB9_dNn6qmm35IATzoNT9QQmmUY' \
-d '{
    "accountNumber": "{input valid acct}",
    "amount": "50"
}'
```
**Read Transaction History**

Request

```
curl -X GET http://localhost:8080/api/v1/transaction-history/1
-H 'Content-type: application/json' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6Im1pa2Vpa2VjaGkzQGdtYWlsLmNvbSIsImlhdCI6MTY5MjIwOTQ2MiwiZXhwIjoxNjkzNTA1NDYyfQ.tWbASUsAzxtOjVCGWB9_dNn6qmm35IATzoNT9QQmmUY' \
```
**Fund Account**

The url will have the following request:
- `amount`:

Request

```
curl -X POST http://localhost:8080/api/v1/fund-account/1 \
-H 'Content-type: application/json' \
-H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sInN1YiI6Im1pa2Vpa2VjaGkzQGdtYWlsLmNvbSIsImlhdCI6MTY5MjIwOTQ2MiwiZXhwIjoxNjkzNTA1NDYyfQ.tWbASUsAzxtOjVCGWB9_dNn6qmm35IATzoNT9QQmmUY' \
-d '{
    "amount": "200"
}'
```

Response

```json
{
  "createdAt": "2023-08-16T19:26:22.4292087",
  "status": "Success"
}
```