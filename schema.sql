CREATE DATABASE IF NOT EXISTS bankdb;
USE bankdb;

CREATE TABLE IF NOT EXISTS users (
    userid   VARCHAR(30) PRIMARY KEY,
    password VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS customers (
    acno        INT PRIMARY KEY,
    cname       VARCHAR(50) NOT NULL,
    email       VARCHAR(80),
    accounttype VARCHAR(20),
    bal         DOUBLE NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS transactions (
    tid              INT AUTO_INCREMENT PRIMARY KEY,
    transaction_type CHAR(1) NOT NULL,
    acno             INT NOT NULL,
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    tamt             DOUBLE,
    FOREIGN KEY (acno) REFERENCES customers(acno) ON DELETE CASCADE
);

INSERT IGNORE INTO users VALUES ('admin', 'admin123');