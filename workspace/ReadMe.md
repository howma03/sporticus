Database

CREATE USER 'admin1'@'localhost' IDENTIFIED BY 'S0uthern';

GRANT ALL PRIVILEGES ON sporticus.* TO 'admin1'@'localhost';

insert into user (id, created, email, enabled, first_name, is_admin, last_name, password) 
values ( 1, '2017/11/08', 'test@sporticus.com', true, 'test', true, 'last', 'password');
