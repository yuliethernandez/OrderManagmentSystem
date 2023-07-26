DROP DATABASE IF EXISTS OrderManagementSystem;
CREATE DATABASE OrderManagementSystem;
USE OrderManagementSystem;
DROP TABLE IF EXISTS supplier;
CREATE TABLE supplier(
	supplierId INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phoneNumber CHAR(12) NOT NULL,
    email VARCHAR(120) NOT NULL,
    details VARCHAR(255)
);

DROP TABLE IF EXISTS product;
CREATE TABLE product(
	productId INT PRIMARY KEY AUTO_INCREMENT,
	description VARCHAR(255), 
    name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    tax BOOLEAN DEFAULT TRUE,
    price DECIMAL(8,2) NOT NULL   
);

DROP TABLE IF EXISTS productSupplier;
CREATE TABLE productSupplier(
	productId INT NOT NULL,
    supplierId INT NOT NULL,
    primary key (supplierId, productId),
    foreign key fk_Product(productId) REFERENCES product(productId),
	foreign key fk_Supplier(supplierId) REFERENCES supplier(supplierId)
);

DROP TABLE IF EXISTS customer;
CREATE TABLE customer(
	customerId INT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(150) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    zipCode CHAR(7) NOT NULL,
    phoneNumber CHAR(12),
    email VARCHAR(120),
    gstNumber CHAR(9) NOT NULL,
    gstExtension VARCHAR(7) NOT NULL
);

DROP TABLE IF EXISTS orderCustomer;
CREATE TABLE orderCustomer(
	orderId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	details VARCHAR(250),
    total DECIMAL(8,2) NOT NULL,
    quantity INT NOT NULL,
    dateOrder DATE NOT NULL,
    customerId INT NOT NULL,
    FOREIGN KEY fk_Customer(customerId) REFERENCES customer(customerId)
);

DROP TABLE IF EXISTS productOrder;
CREATE TABLE productOrder(
	productId INT NOT NULL,
    orderId INT NOT NULL,
    primary key (productId, orderId),
    foreign key fk_Product(productId) REFERENCES product(productId),
	foreign key fk_Order(orderId) REFERENCES ordercustomer(orderId)
);

DROP TABLE IF EXISTS invoice;
CREATE TABLE invoice(
	invoiceId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    shipDate DATE NOT NULL,
    dueDate DATE NOT NULL,
    terms VARCHAR(120),
    saleRepName VARCHAR(150),
    hstTax DECIMAL(8,2) NOT NULL,
    subtotal DECIMAL(8,2) NOT NULL,
    shippingHandling DECIMAL(8,2) NOT NULL,
    notes VARCHAR(250),
    orderId INT NOT NULL,
    FOREIGN KEY fk_Order(orderId) REFERENCES orderCustomer(orderId)
);

USE OrderManagementSystem;
describe customer;
describe orderCustomer;
describe Invoice;
describe product;
describe supplier; 

INSERT INTO customer(name, address, city, zipCode, phoneNumber, email, gstNumber, gstExtension) VALUES 
('La Jeuneuse', 'Saint-norbert street', 'Montreal', 'H3C R5T', '428-245-8973', 'lajeuneuse@gmail.com', '859647263', 'RT 0002'),
('Bacardi', 'Dudemaine street', 'Montreal', 'H3C S7D', '438-579-4112', 'bacardi@gmail.com', '859647263', 'RT 0002'),
('Hola Summer', 'Theodore street', 'Montreal', 'H3C D1R', '428-245-9147', 'holasummer@gmail.com', '859647263', 'RT 0001'),
('Bomban', 'Dudemaine street', 'Montreal', 'H7C R5T', '838-325-3564', 'bomban@gmail.com', '859647263', 'RT 0002'),
('Super C', 'Theodore street', 'Montreal', 'H8U YGT', '838-124-5421', 'superc@hotmail.com', '859647263', 'RT 0001');

INSERT INTO product(`description`, `name`, quantity, tax, price) VALUES 
('Full chocolat cake', 'Chocolat Cake', '10', true, '10.25'),
('Chocolat cake with caramel', 'Caramel Drip Cake', '15', true, '12.40'),
('Best full chocolat cake', 'Ferrero Rocher Cake', '12', true, '15.99'),
('Coffy and chocolat cake', 'Sticky Toffy Cake', '20', true, '9.99'),
('Icecream cake', 'Hommenade Icecream Cake', '20', true, '9.99');

INSERT INTO ordercustomer(details, total, quantity, dateOrder, customerId) VALUES 
('details of order 1', '25.00', '2', '2023-07-14', '1'),
('details of order 1', '35.45', '2', '2023-07-14', '1'),
('details of order 2', '50.00', '3', '2023-05-20', '2'),
('details of order 2', '25.15', '3', '2023-05-20', '2'),
('details of order 2', '47.99', '3', '2023-05-20', '2'),
('details of order 3', '99.25', '1', '2023-05-21', '3');

insert into invoice (shipDate, dueDate, terms, saleRepName, hstTax, subtotal, shippingHandling, notes, orderId) values 
	('2023-07-14', '2022-01-01', 'Term 1', 'Jhon Smith', '10', '15', '5', 'The products must be before 4 pm in the Bakery', '1'),
    ('2023-05-20', '2022-02-01', 'Term 1', 'Leo Grajan', '10', '25', '5', 'The product must be very well cook', '2'),
    ('2023-05-21', '2022-01-01', 'Term 1', 'Joseph ', '10', '15', '5', 'No notes', '3');


DROP TABLE IF EXISTS supplier;
INSERT INTO supplier(`name`, address, phoneNumber, email, details) VALUES 
('Super C', 'San Martin Ave', '415-587-9658', 'superc@hotmail.com', 'No details'),
('Costco', 'San Notre Dame', '425-874-5162', 'costco', 'Products in big amount'),
('Maxi', 'San Theodore', '415-852-8462', 'maxi@gmail.com', 'Just Sugar');

INSERT INTO productsupplier(productId, supplierId) VALUES 
('1', '1'),
('1', '2'),
('2', '2'),
('2', '1'),
('2', '3'),
('3', '3');

INSERT INTO productorder (productId, orderId) VALUES 
('1', '1'),
('1', '2'),
('2', '1'),
('2', '2'),
('3', '3'),
('3', '1');

SELECT * FROM product;
SELECT * FROM invoice;
SELECT * FROM customer;
select * from productSupplier;
select * from ordercustomer;
select * from productorder;

DELETE FROM productorder WHERE orderId = 2;

INSERT INTO productorder (productId, orderId) VALUES 
(1, 2);

select distinct *
from productorder 
inner join product 
on product.productId = productorder.productId
where orderId = 2;

SELECT c.* 
FROM customer c 
INNER JOIN ordercustomer o 
ON c.customerId = o.customerId 
WHERE orderId = 1;

SELECT p.* 
FROM product p 
INNER JOIN productorder po 
ON p.productId = po.productId 
WHERE orderId = 2;

SELECT p.* FROM product p 
INNER JOIN productSupplier ps 
ON p.productId = ps.productId 
WHERE supplierId = 1;

DELETE FROM productorder WHERE orderId = 3;
DELETE FROM invoice WHERE orderId = 3;
DELETE FROM ordercustomer WHERE orderId = 3;

SELECT * FROM invoice WHERE invoiceId = 1;