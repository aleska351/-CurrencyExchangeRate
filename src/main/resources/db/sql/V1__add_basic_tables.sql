CREATE TABLE if not EXISTS banks
(
    id                    SERIAL       NOT NULL
        CONSTRAINT bank_key PRIMARY KEY,
    name                  VARCHAR(255) NOT NULL,
    phone_number          VARCHAR(255) NOT NULL,
    bank_type             VARCHAR(100) NOT NULL,
    is_online_available   BOOLEAN      NOT NULL,
    number_of_departments INTEGER      NOT NULL,
    address               VARCHAR(255) NOT NULL,
    unique (name, phone_number, address)
);

CREATE TABLE if not EXISTS currencies
(
    id         SERIAL
        CONSTRAINT currency_key NOT NULL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    short_name VARCHAR(255) NOT NULL,
    purchase   FLOAT        NOT NULL,
    sale       FLOAT        NOT NULL,
    bank_id    INTEGER      NOT NULL,
    FOREIGN KEY (bank_id) REFERENCES banks (id),
    unique (name, short_name, bank_id)
);