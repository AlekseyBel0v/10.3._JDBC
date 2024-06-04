create schema netology;

create table netology.CUSTOMERS
(
    id           serial primary key,
    name         varchar(255),
    surname      varchar(255),
    age          int check ( age > 0 and age < 150),
    phone_number varchar
);

create table netology.ORDERS
(
    id           serial primary key,
    date         varchar(50),
    customer_id  int references netology.CUSTOMERS (id),
    product_name varchar(255),
    amount       int check ( amount > 0 )
);