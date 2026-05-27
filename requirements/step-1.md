# Step 1: From notebook to database

Wagtail Manor has been running for three years out of a converted hardware store off Highway 9. Pets have been tracked in a leather-bound intake book that lives on the front counter. Dana, the director, is tired of the book. It's a fire risk, half the entries are illegible, and last week somebody spilled coffee on the page with all the September arrivals.

She wants a system. Nothing fancy. Each pet has a name and a weight when they come in. Staff weigh new arrivals and update the weight at regular intervals. They need to be able to look up any pet by an identifier.

## Acceptance criteria

- A new pet can be registered with a name and weight, returning an identifier we can refer to it by.
- A pet can be looked up by that identifier.
- A pet's weight can be updated.
- Looking up an identifier that doesn't exist returns nothing rather than failing.
