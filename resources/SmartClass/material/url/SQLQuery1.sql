--create the database
create database Java2
go
--Use The database
use Java2
go
--create table
create table Users(
userid varchar(20),
userpass varchar(20))
go
--Insert values in the table
insert into Users values('Wolf','wol123')
insert into Users values('Star','sta123')
insert into Users values('Coly','col123')
go
--show the data
select * from Users
go

create database NIIT
use NIIT

create table Employee(
ID varchar(20),
Name varchar(20),
Salary varchar(20),
Department varchar(20)
)
insert into Employee values('1','J','10000','software')
insert into Employee values('2','A','1000','software')
insert into Employee values('3','B','100','software')
insert into Employee values('4','C','10','software')
insert into Employee values('5','D','1','software')

select * from Employee