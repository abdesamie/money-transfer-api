# Money Transfer Api

Restfull API for money transfer between accounts

Based on Jersey (reference implementation of of jax-rs) , Data layer access insured throght JOOQ

# Technologies:

-Jersey

-Guice

-Jooq

-JUnit

-Mockito

-H2

Application is standalone (plug and play) run upon a lighweight jetty server use H2 for both Prod and Integration test data source
The project is multi-layered:

-Ressources for endpoints

-Services for Business requirements implemantation

-Dao as a data acces layer

I tried to provide as much HTTP status as possible. (200,201,400,404,406,500)


# Services

- GET [http://localhost:8080/api/v1/clients](http://localhost:8080/api/v1/clients)
- POST [http://localhost:8080/api/v1/clients](http://localhost:8080/api/v1/clients)
- PUT [http://localhost:8080/api/v1/clients](http://localhost:8080/api/v1/clients)
- GET [http://localhost:8080/api/v1/clients/1](http://localhost:8080/api/v1/clients/1)
- DELETE [http://localhost:8080/api/v1/clients/1](http://localhost:8080/api/v1/clients/1)
- GET [http://localhost:8080/api/v1/accounts](http://localhost:8080/api/v1/accounts)
- POST [http://localhost:8080/api/v1/accounts](http://localhost:8080/api/v1/accounts)
- PUT [http://localhost:8080/api/v1/accounts](http://localhost:8080/api/v1/accounts)
- PUT [http://localhost:8080/api/v1/accounts/1/deposit/100](http://localhost:8080/api/v1/accounts/1/deposit/100)
- GET [http://localhost:8080/api/v1/accounts/1](http://localhost:8080/api/v1/accounts/1)
- DELETE [http://localhost:8080/api/v1/accounts/1](http://localhost:8080/api/v1/accounts)
- GET [http://localhost:8080/api/v1/transfers](http://localhost:8080/api/v1/transfers)
- POST [http://localhost:8080/api/v1/transfers](http://localhost:8080/api/v1/transfers) to execute payment

# Examples

Clients:


- POST
```json
{
  "firstname": "michael",
  "lastname": "kwakye"
} 
```

- PUT
```json
{
  "id":1
  "firstname": "michael",
  "lastname": "kwakye"
} 
```

Account


- POST


```json
{
      "iban": "GB46BUKB20041538290008",
      "amount": 188100.25
} 
```

- PUT

```json
{
      "id": 1,
      "iban": "GB46BUKB20041538290008",
      "amount": 188100.25
} 
```


Transfer

execution

- POST

```json
{
        "idSender": 1,
        "idReceiver": 2,
        "amount": 200
}
```

# Running:

`mvn clean install`

`mvn exec:java` to run on jetty server

# Testing


`mvn test` for both unit/integration tests

For functional testing: 

Full swagger representation is provided in the project main folder, you could import it to https://editor.swagger.io/

