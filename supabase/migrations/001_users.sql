create extension if not exists pgcrypto;
create table if not exists users(
 id uuid primary key default gen_random_uuid(),
 email varchar(255) unique not null,
 password_hash text not null,
 age int not null check(age between 7 and 18),
 role varchar(20) not null default 'STUDENT' check(role in ('STUDENT','PARENT','ADMIN')),
 enabled boolean not null default true,
 created_at timestamp not null default now()
);
create index if not exists idx_users_role on users(role);
create index if not exists idx_users_enabled on users(enabled);
