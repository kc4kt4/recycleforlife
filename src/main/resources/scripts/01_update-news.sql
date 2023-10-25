alter table news
    add column city text;
update news
set city = 'Санкт-Петебург';
alter table news
    alter column city set not null;

alter table news
    add column "type" text;
update news
set "type" = 'EVENT';
alter table news
    alter column "type" set not null;
