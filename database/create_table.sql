create table if not exists books (id serial primary key, title varchar(100) not null, description varchar(500) not null, isbn varchar(100) not null, language varchar(5) not null);
