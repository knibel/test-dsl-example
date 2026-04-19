# test-dsl-example

Simple pet-clinic style example showing a custom test DSL:

- tests are written in **given / when / then** style
- DSL actions/assertions use domain language (waiting pets, treated pets)
- a driver layer wires DSL assertions to the test technology (`unittest`)

Run:

```bash
python -m unittest discover -v
```
