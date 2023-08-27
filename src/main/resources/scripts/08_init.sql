create table "user"
(
    "id"               bigserial primary key,
    "uuid"             uuid unique not null,
    "login"            text        not null,
    "encoded_password" text        not null,
    "name"             text        not null,
    "age"              int         not null,
    "sex"              text        not null
);

create table "refresh_token"
(
    "id"             bigserial primary key,
    "user_id"        bigint                      not null,
    "refresh_token"  text unique                 not null,
    "created_at"     timestamp without time zone not null,
    "expired_at"     timestamp without time zone not null,
    "deactivated_at" timestamp without time zone,

    FOREIGN KEY (user_id) REFERENCES "user" (id)
);
