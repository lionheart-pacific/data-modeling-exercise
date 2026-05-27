CREATE TABLE pets (
    id     BIGSERIAL        PRIMARY KEY,
    name   TEXT             NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    status TEXT             NOT NULL DEFAULT 'AVAILABLE' CHECK (status IN ('AVAILABLE', 'ADOPTED'))
);
