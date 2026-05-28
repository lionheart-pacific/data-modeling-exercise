CREATE TABLE pets
(
    id         BIGSERIAL PRIMARY KEY,
    pet_id     BIGSERIAL NOT NULL,
    name       TEXT      NOT NULL,
    status     TEXT      NOT NULL DEFAULT 'AVAILABLE' CHECK (status IN ('AVAILABLE', 'ADOPTED')),
    valid_from TIMESTAMP NOT NULL,
    valid_to   TIMESTAMP
)