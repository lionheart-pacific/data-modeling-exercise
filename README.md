# data-modeling-exercise

Supporting material for the Lionheart Pacific Practices Learning Club. Worked through alongside a presentation.

## How it works

Each module under `implementations/` is a different data-modeling approach. You work through them side by side, comparing how each holds up as the requirements grow at each step. The contract and supporting test fixtures live under `repository-contract/`. To complete a step, edit the implementation and its Flyway migration until the inherited tests pass.

To advance to the next step, change the interface your repository implements and the abstract test your concrete test extends. The new step adds tests on top of the previous one.

`implementations/example/` is a naive Step 1 implementation included to demonstrate the mechanics: Spring's JdbcClient, Flyway migrations, etc.

Use the `Tests` run configuration to run all tests. Requires Docker.

## Steps

1. [From notebook to database](requirements/step-1.md)
2. [How big is the pet?](requirements/step-2.md)
3. [The vet's request](requirements/step-3.md)
4. [Staff of the month](requirements/step-4.md)
5. [The Biscuit problem](requirements/step-5.md)

## Implementations

- [audit-log](implementations/audit-log/README.md) — current state in a main table plus side tables for history.
- [versioned-objects](implementations/versioned-objects/README.md) — every version of an entity as its own row.
