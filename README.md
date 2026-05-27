# data-modeling-exercise

Supporting material for the Lionheart Pacific Practices Learning Club. Worked through alongside a presentation.

## How it works

Each module under `implementations/` is a different data-modeling approach. You work through them side by side, comparing how each holds up as the requirements grow at each step. The contract and supporting test fixtures live under `repository-contract/`. To complete a step, edit the implementation and its Flyway migration until the inherited tests pass.

To advance to the next step, change the interface your repository implements and the abstract test your concrete test extends. The new step adds tests on top of the previous one.

`implementations/example/` is a naive Step 1 implementation included to demonstrate the mechanics: Spring's JdbcClient, Flyway migrations, etc.

Use the `Tests` run configuration to run all tests. Requires Docker.
