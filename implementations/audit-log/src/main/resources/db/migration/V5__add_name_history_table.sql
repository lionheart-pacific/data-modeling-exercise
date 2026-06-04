CREATE TABLE name_history
(
    id          BIGSERIAL PRIMARY KEY,
    pet_id      BIGINT    NOT NULL,
    name        TEXT      NOT NULL,
    recorded_at TIMESTAMP NOT NULL,
    actor_id    BIGINT    NOT NULL
)