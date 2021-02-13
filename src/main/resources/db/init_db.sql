
/* Fill table banks*/
insert into banks(name, phone_number, bank_type, is_online_available, number_of_departments, address)
values ('PrivatBank', '+380575552233', 'GLOBAL', true, 400, 'Kiev, st. Vesnina 15'),
       ('AlfaBank', '+380572223344', 'GLOBAL', true, 200, 'Kharkiv, st. Gogol 10'),
       ('CreditBank', '+380440995633', 'LOCAL', false, 400, 'Poltava, st. Halamenuka 3');

/* Fill table currencies*/
insert into currencies (name, short_name, purchase, sale, bank_id)
VALUES ('United States Dollar', 'USD', 28.25, 28.70, 1),
       ('United States Dollar', 'USD', 28.25, 28.70, 2),
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
/* Fill table roles*/
insert into roles(id, name) values (1, 'ADMIN');
insert into roles(id, name) values (2, 'MANAGER');
insert into roles(id, name) values (3, 'CONSUMER');

/* Fill table users*/
/* admin - admin*/
insert into users(id, name, password) values (1, 'admin', '$2y$10$EanRcx1G0KIX3bPSfWcf4ORSpqAkrMQP2fPwNhNzwfkcU/nwD9CLS');
/* manager - manager*/
insert into users(id, name, password) values (2, 'manager', '$2y$10$5H9bB2ske/JcD10aSJPcEurPfS.vDLzsv4SHJyDRDOxYUSBirz66y');
/* consumer - consumer*/
insert into users(id, name, password) values (3, 'consumer', '$2y$10$J4OQdQfmRjBwOs9JM1PEFue7O8invUiWBXWDWJsWW6D3Ni3Xn5HDy');

/*ADMIN and MANAGER to admin user*/
insert into users_roles(user_id, role_id) VALUES (1, 1);
insert into users_roles(user_id, role_id) VALUES (1, 2);

/* MANAGER to manager */
insert into users_roles(user_id, role_id) VALUES (2, 2);

/* CONSUMER to consumer*/
insert into users_roles(user_id, role_id) VALUES (3, 3);