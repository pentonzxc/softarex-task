create schema softarex_task
    owner to postgres;

create type field_type as enum ('SINGLE_LINE_TEXT', 'MULTI_LINE_TEXT', 'RADIO_BUTTON', 'CHECKBOX', 'COMBOBOX', 'DATE');

alter type field_type owner to postgres;

create table "user"
(
    id                serial
        primary key,
    email             varchar(255),
    first_name        varchar(255),
    is_active         boolean,
    last_name         varchar(255),
    password          varchar(255),
    phone_number      varchar(255),
    verification_code varchar(255)
);

alter table "user"
    owner to postgres;

create table questionnaire_field
(
    id          serial
        primary key,
    is_active   boolean,
    is_required boolean,
    label       varchar(255),
    options     varchar(255),
    type        varchar(255),
    user_id     integer
        constraint fklvdew3tux1wpb8xvjg1w4rp4s
            references "user"
);

alter table questionnaire_field
    owner to postgres;

create table questionnaire_response
(
    id      serial
        primary key,
    data    varchar(5000),
    user_id integer
        constraint fkrg0chbaq7d6qvn3fuuxf28exu
            references "user"
);

alter table questionnaire_response
    owner to postgres;

create table user_password_change
(
    id                serial
        primary key,
    password          varchar(255),
    verification_code varchar(255),
    user_id           integer
        constraint fklgp12nxfjts8j0xqtcpaar2ad
            references "user"
);

alter table user_password_change
    owner to postgres;

