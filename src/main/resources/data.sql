----insert for contact
INSERT INTO contact (home_phone, mobile_phone, email, is_deleted) VALUES
('+38161234567', '+381658765432', 'marko@gmail.com', false),
('+38162345678', '+381679876543', 'ana@gmail.com', false),
('+38163456789', '+381689098765', 'petar@gmail.com.com', false),
('+38164567890', '+381690123456', 'milan@gmail.com', false),
('+38164567891', '+381690123451', 'ana5@gmail.com', false),
('+38164567892', '+381690123452', 'ana6@gmail.com', false),
('+38164567893', '+381690123453', 'ana7@gmail.com', false),
('+38164567894', '+381690123454', 'ana8@gmail.com', false);

--insert for person
INSERT INTO person (
    first_name, last_name, jmbg, birth_date, image, is_deleted, gender, marital_status, contact_id
) VALUES
('Marko', 'Marković', '1505980562311', '1980-05-15 00:00:00', 'image_1_1505980.jpg', false, 'FEMALE', 'OTHER', 1),
('Ana', 'Petrovic', '2007990452314', '1990-07-20 00:00:00', 'image_2_2007980.jpg', false, 'FEMALE', 'DIVORCED', 2),
('Jovan', 'Maric', '3003975468620', '1975-03-30 00:00:00', 'image_3_3003975.jpg', false, 'MALE', 'OTHER', 3),
('Milan', 'Jovanovic', '2511985365048', '1985-11-25 00:00:00', 'image_4_2511985.jpg', false, 'MALE', 'OTHER', 4),
('Ana', 'Jovanovic', '2511985365041', '1985-11-25 00:00:00', 'image_4_2511985.jpg', false, 'MALE', 'OTHER', 5),
('Ana', 'Jovanovic', '2511985365043', '1985-11-25 00:00:00', 'image_4_2511985.jpg', false, 'MALE', 'OTHER', 6),
('Ana', 'Jovanovic', '2511985365044', '1985-11-25 00:00:00', 'image_4_2511985.jpg', false, 'MALE', 'OTHER', 7),
('Ana', 'Jovanovic', '2511985365045', '1985-11-25 00:00:00', 'image_4_2511985.jpg', false, 'MALE', 'OTHER', 8);

--insert for country
INSERT INTO country (name, abbreviation, created_at, updated_at, icon, is_deleted) VALUES
('Srbija', 'RS', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 'country_icon_1749555361244.png', false),
('Crna Gora', 'ME', '2024-01-02 11:00:00', '2024-01-02 11:00:00', 'country_icon_1749555424933.png', false),
('Bosna i Hercegovina', 'BA', '2024-01-03 12:00:00', '2024-01-03 12:00:00', 'country_icon_1749555444071.png', false),
('Hrvatska', 'HR', '2024-01-04 13:00:00', '2024-01-04 13:00:00', 'country_icon_1749555467602.png', false);

--insert for city
INSERT INTO city (name, is_deleted, country_id) VALUES
('Beograd', false, 1),
('Podgorica', false, 2),
('Sarajevo', false, 3),
('Dubrovnik',false,4);

--insert for zip
INSERT INTO zip (zip_number, is_deleted, city_id) VALUES
(11000, 0, 1),
(81101, 0, 2),
(71000, 0, 3),
(20000, 0, 4);

--insert for address
INSERT INTO address (street, street_num, is_deleted, city_id, zip_id) VALUES
('Kralja Petra', '12', 0, 1, 1),
('Nemanjićeva', '34', 0, 2, 2),
('Vuka Karadžića', '56', 0, 3, 3),
('Slavonska', '78', 0, 4, 4);

--insert for user
--password for all users is Password123!
INSERT INTO user (id, email, username, password, is_enabled, is_active, address_id, user_role, icon)
VALUES
(1,'pera@example.com', 'user1','$2a$10$SVYagMdgV9HZ4b9xESOK0OCITiIvqo1oW9UFEkyyo1PFS7U82NAVK', 1, 1, 1, 'ADMINISTRATOR', 'image_1_1505980.png'),
(2,'maja@example.com', 'user2', '$2a$10$SVYagMdgV9HZ4b9xESOK0OCITiIvqo1oW9UFEkyyo1PFS7U82NAVK', 1, 1, 2, 'DRIVER', 'image_1_1505980.png'),
(3,'jovan@example.com', 'user3', '$2a$10$SVYagMdgV9HZ4b9xESOK0OCITiIvqo1oW9UFEkyyo1PFS7U82NAVK', 1, 1, 3, 'SUBSCRIBER', 'image_1_1505980.png'),
(4,'ana4@example.com', 'user4', '$2a$10$SVYagMdgV9HZ4b9xESOK0OCITiIvqo1oW9UFEkyyo1PFS7U82NAVK', 1, 0, 4, 'MANAGER', 'image_1_1505980.png'),
(5,'ana5@example.com', 'user5', '$2a$10$SVYagMdgV9HZ4b9xESOK0OCITiIvqo1oW9UFEkyyo1PFS7U82NAVK', 1, 0, 4, 'CLAIMS_ADJUSTER', 'image_1_1505980.png'),
(6,'ana6@example.com', 'user6', '$2a$10$SVYagMdgV9HZ4b9xESOK0OCITiIvqo1oW9UFEkyyo1PFS7U82NAVK', 1, 0, 4, 'SALES_AGENT', 'image_1_1505980.png'),
(7,'ana7@example.com', 'user7', '$2a$10$SVYagMdgV9HZ4b9xESOK0OCITiIvqo1oW9UFEkyyo1PFS7U82NAVK', 1, 0, 4, 'CUSTOMER_SERVICE_REPRESENTATIVE', 'image_1_1505980.png'),
(8,'ana8@example.com', 'user8', '$2a$10$SVYagMdgV9HZ4b9xESOK0OCITiIvqo1oW9UFEkyyo1PFS7U82NAVK', 1, 0, 4, 'CLAIM_HANDLER', 'image_1_1505980.png');



--insert for subscriber role
INSERT INTO subscriber_role (name, is_deleted) VALUES
('Petar', FALSE);


--insert for subscriber
INSERT INTO subscriber (id, subscriber_role_id) VALUES
(3,1);


--insert for task
INSERT INTO task (name, status, assign_date, is_deleted, user_id) VALUES
('Task 1', '0', '2024-01-10 09:00:00', false, 1),
('Task 2', '1', '2024-01-11 10:30:00', false, 2),
('Task 3', '2', '2024-01-12 14:00:00', false, 3),
('Task 4', '3', '2024-01-13 08:45:00', true, 4);


--insert for risk
INSERT INTO risk (description, is_deleted) VALUES
('Opis 1', FALSE),
('Opis 2', FALSE),
('Opis 3', FALSE),
('Opis 4', FALSE);

--insert for Driver
INSERT INTO driver (id,licence_num, licence_obtained, years_insured)VALUES
(1, 'LIC12345', '2020-05-10', 3),
(2, 'LIC67890', '2018-11-20', 5),
(3, 'LIC54321', '2021-03-15', 2),
(4, 'LIC98765', '2019-07-25', 4);

--insert for driver risk
INSERT INTO driver_risk (driver_id, risk_id) VALUES
(1, 1),
(1, 2),
(2, 2),
(2, 3),
(3, 1),
(3, 3),
(4, 2),
(4, 4);