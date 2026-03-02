CREATE USER 'admin'@'localhost' IDENTIFIED BY '123';
GRANT ALL PRIVILEGES ON talenthub.* TO 'admin'@'localhost';
FLUSH PRIVILEGES;
use talenthub;

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       password VARCHAR(100) NOT NULL,
                       creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE techs (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(30) NOT NULL
);

CREATE TABLE positions (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(30) NOT NULL
);

CREATE TABLE crawlerlogs (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             title VARCHAR(100) NOT NULL,
                             tech_id INT NOT NULL,
                             position_id INT,
                             city_name VARCHAR(100),
                             uf_name VARCHAR(50),
                             uf_abrev VARCHAR(2),
                             tech_level VARCHAR(30),
                             hiring_type VARCHAR(15),
                             salary_range DECIMAL(10,2),
                             benefits TEXT,
                             requirements TEXT,
                             company_name VARCHAR(100) NOT NULL,
                             posting_link VARCHAR(250) NOT NULL,
                             email_contact VARCHAR(100),
                             phone_contact VARCHAR(20),
                             name_contact VARCHAR(100),
                             work_mode ENUM('REMOTE', 'HYBRID', 'ON_SITE'),
                             creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             creation_user ENUM('CRAWLER', 'API'),
                             plataform varchar(30) NOT NULL,
                             FOREIGN KEY (tech_id) REFERENCES techs(id),
                             FOREIGN KEY (position_id) REFERENCES positions(id)
);

CREATE TABLE customers (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(150) NOT NULL UNIQUE,
                           password VARCHAR(100) NOT NULL,
                           photo MEDIUMBLOB,
                           birthdate DATE,
                           creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE customerscrawlers (
                                   customer_id INT NOT NULL,
                                   crawler_id INT NOT NULL,
                                   creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (customer_id, crawler_id),
                                   FOREIGN KEY (customer_id) REFERENCES customers(id),
                                   FOREIGN KEY (crawler_id) REFERENCES crawlerlogs(id)
);

CREATE TABLE token (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       token VARCHAR(250) NOT NULL
);