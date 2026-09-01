create table if not exists exercises(
 id uuid primary key default gen_random_uuid(),
 title varchar(160) not null,
 type varchar(50) not null,
 difficulty varchar(30) not null,
 question text not null,
 answer text not null
);
create index if not exists idx_exercises_type on exercises(type);
insert into exercises(title,type,difficulty,question,answer)
select * from (values
 ('数字规律','logic','easy','2, 4, 6, 8, ?','10'),
 ('数字规律','logic','medium','3, 6, 12, 24, ?','48'),
 ('字母记忆','memory','easy','请记住：APPLE - RIVER - BLUE。完成后输入你记住的第三个词。','BLUE'),
 ('数字记忆','memory','medium','请记住数字 7 - 2 - 9 - 4。完成后输入最后一个数字。','4')
) as seed(title,type,difficulty,question,answer)
where not exists (select 1 from exercises);
