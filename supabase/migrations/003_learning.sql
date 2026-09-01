create table if not exists learning_records(
 id uuid primary key default gen_random_uuid(),
 user_id uuid not null references users(id) on delete cascade,
 exercise_id uuid not null references exercises(id) on delete cascade,
 score int not null check(score between 0 and 100),
 created_at timestamp not null default now()
);
create index if not exists idx_learning_user_created on learning_records(user_id,created_at desc);
create index if not exists idx_learning_exercise on learning_records(exercise_id);
