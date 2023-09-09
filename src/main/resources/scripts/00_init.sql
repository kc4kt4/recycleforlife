create table "category"
(
    "id"          bigserial primary key,
    "uuid"        uuid unique not null,
    "name"        text        not null,
    "description" text        not null,
    "parent_id"   bigint,

    foreign key (parent_id) references "category" (id)
);

create table "category_logo"
(
    "id"           bigserial primary key,
    "uuid"         uuid unique not null,
    "category_id"  bigint      not null,
    "image_base64" text        not null,

    foreign key (category_id) references "category" (id)
);

create table "fraction"
(
    "id"           bigserial primary key,
    "uuid"         uuid unique not null,
    "category_id"  bigint      not null,
    "name"         text        not null,
    "description"  text        not null,
    "title"        text        not null,
    "article"      text        not null,
    "image_base64" text        not null,

    foreign key (category_id) references "category" (id)
);

create table "receiving_point"
(
    "id"            bigserial primary key,
    "uuid"          uuid unique not null,
    "name"          text        not null,
    "description"   text,
    "latitude"      numeric     not null,
    "longitude"     numeric     not null,
    "subtitle"      text,
    "email"         text,
    "msisdn"        text,
    "working_hours" jsonb       not null
);

create table "fraction_receiving_point"
(
    "id"                 bigserial primary key,
    "receiving_point_id" bigint not null,
    "fraction_id"        bigint not null,

    foreign key (receiving_point_id) references "receiving_point" (id),
    foreign key (fraction_id) references "fraction" (id),
    unique ("receiving_point_id", "fraction_id")
);

create table "news"
(
    "id"                bigserial primary key,
    "uuid"              uuid unique not null,
    "date"              date        not null,
    "name"              text        not null,
    "title"             text        not null,
    "short_description" text        not null,
    "description"       text        not null,
    "image_base64"      text
);

create table "introduce_info"
(
    "id"           bigserial primary key,
    "uuid"         uuid unique not null,
    "title"        date        not null,
    "subtitle"     text        not null,
    "description"  text        not null,
    "image_base64" text
);

create table "user"
(
    "id"               bigserial primary key,
    "uuid"             uuid unique not null,
    "login"            text        not null,
    "encoded_password" text        not null,
    "name"             text        not null,
    "date_of_birth"    date        not null,
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
