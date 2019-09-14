# Money transfer api

Restfull API for money transfer between accounts

Based on Jersey (reference implementation of of jax-rs) , Data layer access insured throght JOOQ

# Technologies:

-Jersey

-Jooq

-JUnit

-Mockito

-H2

Application run upon a lighweight jetty server use H2 for both Prod and Integration test data source
The project is multi-layered:

-Ressources for endpoints

-Services for Business requirements implemantation

-Dao as a data acces layer

I tried to provide as much HTTP status as possible.

# Running:

`mvn clean install`

`mvn test` for both unit/integration tests

`mvn exec:java` to run on jetty server

For functional testing: 

Full swagger representation is provided in the project main folder, you could import it to https://editor.swagger.io/

