SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS driver_risk, risk, driver, task, subscriber, subscriber_role, user, address, zip, city, country, person, contact;

CREATE TABLE contact (
    id INT AUTO_INCREMENT PRIMARY KEY,
    home_phone VARCHAR(20),
    mobile_phone VARCHAR(20),
    email VARCHAR(50),
    is_deleted BOOLEAN
);

INSERT INTO contact (home_phone, mobile_phone, email, is_deleted) VALUES
('+38161234567', '+381658765432', 'marko@gmail.com', false),
('+38162345678', '+381679876543', 'ana@gmail.com', false),
('+38163456789', '+381689098765', 'petar@gmail.com', false),
('+38164567890', '+381690123456', 'milan@gmail.com', false);

CREATE TABLE person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    jmbg VARCHAR(13),
    birth_date DATETIME,
    image VARCHAR(100),
    is_deleted BOOLEAN,
    gender VARCHAR(1),
    marital_status VARCHAR(1),
    contact_id INT,
    FOREIGN KEY (contact_id) REFERENCES contact(id)
);

INSERT INTO person (first_name, last_name, jmbg, birth_date, image, is_deleted, gender, marital_status, contact_id) VALUES
('Marko', 'Marković', '1505980562311', '1980-05-15 00:00:00', 'image_1_1505980.jpg', false, '0', '0', 1),
('Ana', 'Petrovic', '2007990452314', '1990-07-20 00:00:00', 'image_2_2007980.jpg', false, '1', '1', 2),
('Petar', 'Maric', '3003975468620', '1975-03-30 00:00:00', 'image_3_3003975.jpg', false, '0', '2', 3),
('Milan', 'Jovanovic', '2511985365048', '1985-11-25 00:00:00', 'image_4_2511985.jpg', false, '0', '0', 4);

CREATE TABLE country (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    abbreviation VARCHAR(5),
    created_at DATETIME,
    updated_at DATETIME,
    icon VARCHAR(50),
    is_deleted BOOLEAN
);

INSERT INTO country (name, abbreviation, created_at, updated_at, icon, is_deleted) VALUES
('Srbija', 'RS', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 'srb.png', false),
('Crna Gora', 'ME', '2024-01-02 11:00:00', '2024-01-02 11:00:00', 'cg.png', false),
('Bosna i Hercegovina', 'BA', '2024-01-03 12:00:00', '2024-01-03 12:00:00', 'bih.png', false),
('Hrvatska', 'HR', '2024-01-04 13:00:00', '2024-01-04 13:00:00', 'hr.png', false);

CREATE TABLE city (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    is_deleted BOOLEAN,
    country_id INT,
    FOREIGN KEY (country_id) REFERENCES country(id)
);

INSERT INTO city (name, is_deleted, country_id) VALUES
('Beograd', false, 1),
('Podgorica', false, 2),
('Sarajevo', false, 3),
('Dubrovnik', false, 4);

CREATE TABLE zip (
    id INT AUTO_INCREMENT PRIMARY KEY,
    zip_number INT,
    is_deleted BOOLEAN,
    city_id INT,
    FOREIGN KEY (city_id) REFERENCES city(id)
);

INSERT INTO zip (zip_number, is_deleted, city_id) VALUES
(11000, 0, 1),
(81101, 0, 2),
(71000, 0, 3),
(20000, 0, 4);

CREATE TABLE address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(50),
    street_num VARCHAR(10),
    is_deleted BOOLEAN,
    city_id INT,
    zip_id INT,
    FOREIGN KEY (city_id) REFERENCES city(id),
    FOREIGN KEY (zip_id) REFERENCES zip(id)
);

INSERT INTO address (street, street_num, is_deleted, city_id, zip_id) VALUES
('Kralja Petra', '12', 0, 1, 1),
('Nemanjićeva', '34', 0, 2, 2),
('Vuka Karadžića', '56', 0, 3, 3),
('Slavonska', '78', 0, 4, 4);

CREATE TABLE user (
    id INT PRIMARY KEY,
    username VARCHAR(20),
    email VARCHAR(50),
    password VARCHAR(50),
    is_enabled BOOLEAN,
    is_active BOOLEAN,
    address_id INT,
    user_role VARCHAR(10),
    FOREIGN KEY (address_id) REFERENCES address(id)
);

INSERT INTO user (id, email, username, password, is_enabled, is_active, address_id, user_role) VALUES
(1,'pera@example.com', 'user1','pass123', 1, 1, 1, '1'),
(2,'maja@example.com', 'user2', 'pass456', 1, 1, 2, '1'),
(3,'jovan@example.com', 'user3', 'pass789', 1, 1, 3, '2'),
(4,'ana@example.com', 'user4', 'pass000', 1, 0, 4, '3');

CREATE TABLE subscriber_role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    is_deleted BOOLEAN
);

INSERT INTO subscriber_role (name, is_deleted) VALUES
('Ana', FALSE),
('Petar', FALSE),
('Marko', FALSE),
('Milan', FALSE);

CREATE TABLE subscriber (
    id INT PRIMARY KEY,
    subscriber_role_id INT,
    FOREIGN KEY (subscriber_role_id) REFERENCES subscriber_role(id)
);

INSERT INTO subscriber (id, subscriber_role_id) VALUES
(1,1),
(2,2),
(3,3),
(4,4);

CREATE TABLE task (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    status VARCHAR(10),
    assign_date DATETIME,
    is_deleted BOOLEAN,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO task (name, status, assign_date, is_deleted, user_id) VALUES
('Task 1', '0', '2024-01-10 09:00:00', false, 1),
('Task 2', '1', '2024-01-11 10:30:00', false, 2),
('Task 3', '2', '2024-01-12 14:00:00', false, 3),
('Task 4', '3', '2024-01-13 08:45:00', true, 4);

CREATE TABLE risk (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(100),
    is_deleted BOOLEAN
);

INSERT INTO risk (description, is_deleted) VALUES
('Opis 1', FALSE),
('Opis 2', FALSE),
('Opis 3', FALSE),
('Opis 4', FALSE);

CREATE TABLE driver (
    id INT AUTO_INCREMENT PRIMARY KEY,
    licence_num VARCHAR(20),
    licence_obtained DATE,
    years_insured INT
);

INSERT INTO driver (id, licence_num, licence_obtained, years_insured) VALUES
(1, 'LIC12345', '2020-05-10', 3),
(2, 'LIC67890', '2018-11-20', 5),
(3, 'LIC54321', '2021-03-15', 2),
(4, 'LIC98765', '2019-07-25', 4);

CREATE TABLE driver_risk (
    driver_id INT,
    risk_id INT,
    PRIMARY KEY (driver_id, risk_id),
    FOREIGN KEY (driver_id) REFERENCES driver(id),
    FOREIGN KEY (risk_id) REFERENCES risk(id)
);

INSERT INTO driver_risk (driver_id, risk_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(2, 3),
(3, 1),
(3, 3),
(4, 2),
(4, 4);

SET FOREIGN_KEY_CHECKS=1;