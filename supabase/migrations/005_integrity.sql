-- Compatibility migration for older BrainGrow V5 databases.
alter table users add column if not exists enabled boolean not null default true;
alter table users add column if not exists age int;
update users set age=18 where age is null;
alter table users alter column age set not null;
update users set role='STUDENT' where role is null or role not in ('STUDENT','PARENT','ADMIN');
alter table users alter column role set default 'STUDENT';
create index if not exists idx_verification_expiry on verification_codes(expires_at);
