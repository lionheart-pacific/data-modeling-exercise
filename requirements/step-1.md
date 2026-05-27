# Step 1: From notebook to database

Wagtail Manor has been running for three years out of a converted hardware store off Highway 9. There's a leather-bound logbook on the front counter that Dana and her staff use to track who's in and who's out — every pet that comes in gets a line, every adoption gets a check next to the name. The real reason the book exists is so that anyone walking through the door knows, at a glance, which pets are still here and which have been adopted out.

Dana is tired of the book. It's a fire risk, half the entries are illegible, and last week somebody spilled coffee on the page with all the September arrivals. She wants a digital version. New arrivals come in as available; when they're adopted, staff mark them adopted. Pets are sometimes returned, in which case they're marked available again.

## Acceptance criteria

- A new pet can be registered with a name, returning an identifier we can refer to it by.
- New pets start as available.
- A pet's status can be changed between available and adopted, and back if needed.
- A pet can be looked up by its identifier — the lookup shows the current name and status.
- Looking up an identifier that doesn't exist returns nothing rather than failing.
