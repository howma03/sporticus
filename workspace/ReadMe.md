# Getting code

git clone https://github.com/howma03/sporticus.git

# Configuring Intellij

Create new empty project is intellij

file->projectStructure

modules->ImportModule

Import sporticus\workspace\build.gradle

# Install MySQL

https://dev.mysql.com/downloads/mysql/

# Database

CREATE DATABASE sporticus;

CREATE USER 'admin1'@'localhost' IDENTIFIED BY 'S0uthern';

GRANT ALL PRIVILEGES ON sporticus.* TO 'admin1'@'localhost';

# Create DB

On first run set the create database property in the default-application.properties file

spring.jpa.hibernate.ddl-auto=create

Subsequently set it to update

spring.jpa.hibernate.ddl-auto=update


# Test Data

insert into user (id, created, email, enabled, first_name, is_admin, last_name, password) 
values ( 1, '2017/11/08', 'test@sporticus.com', true, 'test', true, 'last', '$2a$10$nqVxG8EUTLx4.RZp4d32XudHeXWn5esUe.cjxwB2Rcl1cNUKBEf4C');
