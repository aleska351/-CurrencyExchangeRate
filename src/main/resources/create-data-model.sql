drop table currencies;
/*Create table bank_type*/
CREATE TABLE if not EXISTS bank_type
(
    id   SERIAL       NOT NULL PRIMARY KEY,
    type VARCHAR(100) NOT NULL
);
/* Fill table bank_type*/
INSERT INTO bank_type (type)
VALUES ('GLOBAL');
INSERT INTO bank_type (type)
VALUES ('LOCAL');

/*Create table banks*/
CREATE TABLE if not EXISTS banks
(
    id                    SERIAL       NOT NULL
        CONSTRAINT bank_key PRIMARY KEY,
    name                  VARCHAR(255) NOT NULL,
    phone_number          VARCHAR(255) NOT NULL,
    bank_type             INTEGER      NOT NULL,
    is_online_available   BOOLEAN      NOT NULL,
    number_of_departments INTEGER      NOT NULL,
    address               VARCHAR(255) NOT NULL,
    FOREIGN KEY (bank_type) REFERENCES bank_type (id)
);
/* Fill table banks*/
insert into banks(name, phone_number, bank_type, is_online_available, number_of_departments, address)
values ('PrivatBank', '+380575552233', 1, TRUE, 400, 'Kiev, st. Vesnina 15'),
       ('AlfaBank', '+380572223344', 1, TRUE, 200, 'Kharkiv, st. Gogol 10'),
       ('CreditBank', '+380440995633', 2, FALSE, 400, 'Poltava, st. Halamenuka 3');


/*Create table currencies*/
CREATE TABLE if not EXISTS currencies
(
    id         SERIAL
        CONSTRAINT currency_key NOT NULL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    short_name VARCHAR(255) NOT NULL,
    purchase   MONEY        NOT NULL,
    sale       MONEY        NOT NULL,
    bank_id    INTEGER      NOT NULL,
    FOREIGN KEY (bank_id) REFERENCES banks (id)
);
/* Fill table currencies*/
insert into currencies (name, short_name, purchase, sale, bank_id)
VALUES ('United States Dollar', 'USD', 28.25, 28.70, 1),
       ('United States Dollar', 'USD', 28.25, 28.70, 1),
       ('United States Dollar', 'USD', 28.25, 28.70, 3),
       ('EURO', 'EUR', 33.55, 34.60, 1),
       ('EURO', 'EUR', 33.55, 34.60, 2),
       ('EURO', 'EUR', 33.55, 34.60, 3),
       ('Canadian Dollar', 'CAD', 21.54, 21.78, 1),
       ('Hryvnia', 'UAH', 1, 1, 1),
       ('Hryvnia', 'UAH', 1, 1, 2),
       ('Hryvnia', 'UAH', 1, 1, 3),
       ('British Pound ', 'GBP', 35.73, 37.73, 1),
       ('British Pound ', 'GBP', 35.73, 37.73, 2),
       ('South African Rand ', 'ZAR', 0.56, 0.59, 1);


/* Select data from tables by id */
SELECT *
FROM banks
where id = 1;
SELECT *
FROM currencies
where id = 4;


/* Select all data from tables*/
SELECT *
FROM banks;
SELECT *
FROM currencies;


/* Update current data on table in whole or in part*/
UPDATE banks
SET name                  = 'Agricole',
    phone_number          = +380884443311,
    bank_type             = 2,
    is_online_available   = TRUE,
    number_of_departments =100,
    address               ='Dnipro, street Pavlova 13'
WHERE id = 3;
UPDATE currencies
SET purchase=22.11,
    sale=22.34
WHERE id = 3;

/*Delete data from tables by id */
DELETE
FROM banks
WHERE id = 3;
DELETE
FROM currencies
WHERE id = 6;

/* Delete all data from tables*/
TRUNCATE TABLE banks;
TRUNCATE TABLE currencies;
TRUNCATE TABLE bank_type;

/* Delete all tables */
drop table banks;
drop table currencies;
drop table bank_type;