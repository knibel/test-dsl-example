# test-dsl-example

Simple Spring Boot pet-clinic style example showing a custom test DSL:

- tests are written in **given / when / then** style
- DSL actions/assertions use domain language (waiting pets, treated pets)
- a driver layer wires DSL assertions to the test technology (`JUnit`)
- outcomes are checked via dataset state changes, not component-to-component calls

Run:

```bash
mvn test
```
