CREATE DATABASE warehouse_manager;
use warehouse_manager;

CREATE TABLE products (
    Product_Id char(5) primary key ,
    Product_Name nvarchar(150) not null unique ,
    Manufacturer nvarchar(200) not null,
    Created date default(CURRENT_DATE),
    Batch smallint not null ,
    Quantity int not null default(0),
    Product_Status bit default(1)
);

CREATE TABLE employees(
    Emp_Id char(5) primary key ,
    Emp_Name nvarchar(00) not null,
    Birth_Of_Date date,
    Email varchar(100) not null ,
    Phone varchar(100) not null ,
    Address text not null ,
    Emp_Status smallint not null
);

CREATE TABLE accounts(
    Acc_id int primary key AUTO_INCREMENT,
    User_name varchar(30) not null unique ,
    Password varchar(30) not null ,
    Permission bit default(1),
    Emp_id char(5) not null unique,
    Acc_status bit default(1)
);

CREATE TABLE bills(
    Bill_id bigint primary key AUTO_INCREMENT,
    Bill_Code varchar(10) not null ,
    Bill_Type bit not null ,
    Emp_id_created char(5) not null,
    Created date default(CURRENT_DATE),
    Emp_id_auth char(5) not null,
    Auth_date date default(CURRENT_DATE),
    Bill_Status smallint not null default(0)
);
ALTER TABLE bills
ADD FOREIGN KEY (Emp_id_created) REFERENCES employees(Emp_Id);
ALTER TABLE bills
ADD FOREIGN KEY (Emp_id_auth) REFERENCES employees(Emp_Id);

CREATE TABLE bill_details(
    Bill_Detail_Id bigint primary key AUTO_INCREMENT,
    Bill_Id bigint not null,
    Product_Id char(5) not null,
    Quantity int not null check ( Quantity > 0 ),
    Price float not null check ( Price > 0 )
);
ALTER TABLE bill_details
ADD FOREIGN KEY (Bill_Id) REFERENCES bills(Bill_id);
ALTER TABLE bill_details
ADD FOREIGN KEY (Product_Id) REFERENCES products(Product_Id);