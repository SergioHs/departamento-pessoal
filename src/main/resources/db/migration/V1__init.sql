create table company (
    id char(36) not null,
    name varchar(150) not null,
    document varchar(20),
    active bit not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    version bigint not null,
    primary key (id)
);

create index idx_company_name on company (name);

create table user_account (
    id char(36) not null,
    company_id char(36) not null,
    email varchar(180) not null,
    password_hash varchar(100) not null,
    active bit not null,
    token_version bigint not null,
    created_at timestamp(6) not null,
    updated_at timestamp(6) not null,
    version bigint not null,
    primary key (id),
    constraint uk_user_account_email unique (email),
    constraint fk_user_account_company foreign key (company_id) references company (id)
);

create index idx_user_account_company_id on user_account (company_id);
create index idx_user_account_email on user_account (email);

create table user_account_role (
    user_account_id char(36) not null,
    role varchar(30) not null,
    primary key (user_account_id, role),
    constraint fk_user_account_role_user foreign key (user_account_id) references user_account (id)
);
