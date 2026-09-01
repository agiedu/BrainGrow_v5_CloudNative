create table if not exists verification_codes(
 id uuid primary key default gen_random_uuid(),
 email varchar(255) not null,
 code varchar(6) not null,
 purpose varchar(30) not null,
 expires_at timestamp not null
);
create index if not exists idx_verification_email_purpose on verification_codes(email,purpose);
