CREATE TABLE if not EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    name     varchar(20),
    password varchar(100)
);

CREATE TABLE if not EXISTS roles
(
    id   BIGSERIAL PRIMARY KEY,
    name varchar(20)
);

CREATE TABLE if not EXISTS users_roles
(
    user_id BIGINT
        CONSTRAINT users_roles_users_id_fk
            references users,
    role_id BIGINT
        CONSTRAINT users_roles_roles_id_fk
            references roles
);