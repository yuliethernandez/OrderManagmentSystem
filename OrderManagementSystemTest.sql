DROP DATABASE IF EXISTS OrderManagementSystemTest;
CREATE DATABASE OrderManagementSystemTest;
USE OrderManagementSystemTest;

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