# test-dsl-example

Simple Spring Boot pet clinic application with:

- a small but usable HTTP API for checking in pets, treating the next pet, and reading clinic state
- tests written in **given / when / then** style
- DSL actions/assertions expressed in domain language
- a driver layer that wires DSL assertions to the test technology (`JUnit`)

Run the tests:

```bash
mvn test
```

Run the application:

```bash
mvn spring-boot:run
```

Example API flow:

```bash
curl -i -X POST http://localhost:8080/api/pet-clinic/waiting-pets \
  -H 'Content-Type: application/json' \
  -d '{"ownerName":"Alice","petName":"Fido"}'

curl -i -X POST http://localhost:8080/api/pet-clinic/treat-next

curl -s http://localhost:8080/api/pet-clinic
```
