CREATE TABLE weight_history
(
    id          BIGSERIAL PRIMARY KEY,
    pet_id      BIGINT        NOT NULL,
    weight      DECIMAL(5, 2) NOT NULL,
    recorded_at TIMESTAMP     NOT NULL
)