DROP TABLE IF EXISTS db.SPRING_SESSION_ATTRIBUTES;
DROP TABLE IF EXISTS db.SPRING_SESSION;
DROP TABLE IF EXISTS db.authorities;
DROP TABLE IF EXISTS db.users;

create table if not exists db.users(
	username varchar(50) not null primary key,
	password varchar(500) not null,
	enabled boolean not null
);

create table if not exists db.authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);
