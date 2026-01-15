-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3310
-- Generation Time: Maj 31, 2024 at 06:22 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `buildingsdb`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `apartment`
--

CREATE TABLE `apartment` (
                             `id_apartment` int(11) NOT NULL,
                             `apt_number` int(11) NOT NULL,
                             `level` int(11) NOT NULL,
                             `apt_area` float NOT NULL,
                             `details` text DEFAULT NULL,
                             `id_tenant` int(11) DEFAULT NULL,
                             `id_building` int(11) NOT NULL,
                             `id_manager` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `building`
--

CREATE TABLE `building` (
                            `id_building` int(11) NOT NULL,
                            `address` varchar(100) NOT NULL,
                            `city` varchar(100) NOT NULL,
                            `prowince` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `complaint`
--

CREATE TABLE `complaint` (
                             `id_complaint` int(11) NOT NULL,
                             `title` varchar(64) NOT NULL,
                             `content` text NOT NULL,
                             `date` date NOT NULL,
                             `id_tenant` int(11) NOT NULL,
                             `id_manager` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `executor`
--

CREATE TABLE `executor` (
                            `id_executor` int(11) NOT NULL,
                            `name` varchar(64) NOT NULL,
                            `surname` varchar(64) NOT NULL,
                            `phone` varchar(9) NOT NULL,
                            `birthdate` date NOT NULL,
                            `id_work_type` int(11) NOT NULL,
                            `company_name` varchar(100) DEFAULT NULL,
                            `address` varchar(255) DEFAULT NULL,
                            `nip` varchar(10) DEFAULT NULL,
                            `regon` varchar(9) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `manager`
--

CREATE TABLE `manager` (
                           `id_manager` int(11) NOT NULL,
                           `name` varchar(64) NOT NULL,
                           `surname` varchar(64) NOT NULL,
                           `birthdate` date NOT NULL,
                           `phone` varchar(9) NOT NULL,
                           `residence` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `payment`
--

CREATE TABLE `payment` (
                           `id_payment` int(11) NOT NULL,
                           `amount` int(11) NOT NULL,
                           `payment_date` date NOT NULL,
                           `id_rent` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `renovation`
--

CREATE TABLE `renovation` (
                              `id_renovation` int(11) NOT NULL,
                              `general` tinyint(1) NOT NULL,
                              `id_apartment` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rent`
--

CREATE TABLE `rent` (
                        `id_rent` int(11) NOT NULL,
                        `add_date` date NOT NULL,
                        `start_date` date NOT NULL,
                        `end_date` date NOT NULL,
                        `information` text NOT NULL,
                        `amount` decimal(10,0) NOT NULL,
                        `payed` tinyint(1) NOT NULL,
                        `id_apartment` int(11) NOT NULL,
                        `id_agreement` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rent_agreement`
--

CREATE TABLE `rent_agreement` (
                                  `id_rent_agreement` int(11) NOT NULL,
                                  `agreement_date` date NOT NULL,
                                  `rent_start_date` date NOT NULL,
                                  `rent_end_date` date NOT NULL,
                                  `rent_price` decimal(10,2) NOT NULL,
                                  `id_tenant` int(11) NOT NULL,
                                  `id_apartment` int(11) NOT NULL,
                                  `id_manager` int(11) NOT NULL,
                                  `status` enum('active','expired','terminated','') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `repair`
--

CREATE TABLE `repair` (
                          `id_repair` int(11) NOT NULL,
                          `cost` decimal(10,2) NOT NULL,
                          `start_date` date NOT NULL,
                          `end_date` date NOT NULL,
                          `planned_end_date` date DEFAULT NULL,
                          `planned_start_date` date DEFAULT NULL,
                          `id_repair_com` int(11) NOT NULL,
                          `id_executor` int(11) DEFAULT NULL,
                          `id_manager` int(11) NOT NULL,
                          `status` enum('completed','pending','cancelled','') NOT NULL DEFAULT 'pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `repair_com`
--

CREATE TABLE `repair_com` (
                              `id_repair_com` int(11) NOT NULL,
                              `title` varchar(64) NOT NULL,
                              `description` text NOT NULL,
                              `creation_date` date NOT NULL,
                              `status` enum('completed','pending','cancelled','') CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL DEFAULT 'pending',
                              `id_apartment` int(11) NOT NULL,
                              `id_rent_agreement` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `room`
--

CREATE TABLE `room` (
                        `id_room` int(11) NOT NULL,
                        `room_count` int(11) NOT NULL,
                        `id_apartment` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `room_type`
--

CREATE TABLE `room_type` (
                             `id_room` int(11) NOT NULL,
                             `room` varchar(64) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `tenant`
--

CREATE TABLE `tenant` (
                          `id_tenant` int(11) NOT NULL,
                          `residence` varchar(100) NOT NULL,
                          `name` varchar(64) NOT NULL,
                          `surname` varchar(64) NOT NULL,
                          `birthdate` date NOT NULL,
                          `phone` varchar(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_login`
--

CREATE TABLE `user_login` (
                              `id_user` int(11) NOT NULL,
                              `username` varchar(64) NOT NULL,
                              `password` varchar(255) NOT NULL,
                              `user_type` enum('tenant','manager') NOT NULL,
                              `tenant_user_id` int(11) DEFAULT NULL,
                              `manager_user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `worktype`
--

CREATE TABLE `worktype` (
                            `id_work_type` int(11) NOT NULL,
                            `work_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `apartment`
--
ALTER TABLE `apartment`
    ADD PRIMARY KEY (`id_apartment`),
    ADD KEY `id_tenant` (`id_tenant`),
    ADD KEY `id_manager` (`id_manager`) USING BTREE,
    ADD KEY `id_building` (`id_building`) USING BTREE;

--
-- Indeksy dla tabeli `building`
--
ALTER TABLE `building`
    ADD PRIMARY KEY (`id_building`);

--
-- Indeksy dla tabeli `complaint`
--
ALTER TABLE `complaint`
    ADD PRIMARY KEY (`id_complaint`),
    ADD KEY `id_tenant` (`id_tenant`) USING BTREE,
    ADD KEY `id_manager` (`id_manager`) USING BTREE;

--
-- Indeksy dla tabeli `executor`
--
ALTER TABLE `executor`
    ADD PRIMARY KEY (`id_executor`),
    ADD KEY `id_work_type` (`id_work_type`);

--
-- Indeksy dla tabeli `manager`
--
ALTER TABLE `manager`
    ADD PRIMARY KEY (`id_manager`);

--
-- Indeksy dla tabeli `payment`
--
ALTER TABLE `payment`
    ADD PRIMARY KEY (`id_payment`),
    ADD KEY `id_rent` (`id_rent`) USING BTREE;

--
-- Indeksy dla tabeli `renovation`
--
ALTER TABLE `renovation`
    ADD PRIMARY KEY (`id_renovation`),
    ADD KEY `id_apartment` (`id_apartment`);

--
-- Indeksy dla tabeli `rent`
--
ALTER TABLE `rent`
    ADD PRIMARY KEY (`id_rent`),
    ADD KEY `id_apartment` (`id_apartment`);

--
-- Indeksy dla tabeli `rent_agreement`
--
ALTER TABLE `rent_agreement`
    ADD PRIMARY KEY (`id_rent_agreement`),
    ADD KEY `id_tenant` (`id_tenant`) USING BTREE,
    ADD KEY `id_apartment` (`id_apartment`),
    ADD KEY `id_manager` (`id_manager`);

--
-- Indeksy dla tabeli `repair`
--
ALTER TABLE `repair`
    ADD PRIMARY KEY (`id_repair`),
    ADD KEY `id_repair_com` (`id_repair_com`),
    ADD KEY `id_executor` (`id_executor`),
    ADD KEY `id_manager` (`id_manager`) USING BTREE;

--
-- Indeksy dla tabeli `repair_com`
--
ALTER TABLE `repair_com`
    ADD PRIMARY KEY (`id_repair_com`),
    ADD KEY `id_apartment` (`id_apartment`),
    ADD KEY `id_rent_agreement` (`id_rent_agreement`);

--
-- Indeksy dla tabeli `room`
--
ALTER TABLE `room`
    ADD KEY `id_apartment` (`id_apartment`),
    ADD KEY `id_room` (`id_room`);

--
-- Indeksy dla tabeli `room_type`
--
ALTER TABLE `room_type`
    ADD PRIMARY KEY (`id_room`);

--
-- Indeksy dla tabeli `tenant`
--
ALTER TABLE `tenant`
    ADD PRIMARY KEY (`id_tenant`);

--
-- Indeksy dla tabeli `user_login`
--
ALTER TABLE `user_login`
    ADD PRIMARY KEY (`id_user`),
    ADD UNIQUE KEY `unique_username` (`username`),
    ADD KEY `fk_tenant_user_id` (`tenant_user_id`),
    ADD KEY `fk_manager_user_id` (`manager_user_id`);

--
-- Indeksy dla tabeli `worktype`
--
ALTER TABLE `worktype`
    ADD PRIMARY KEY (`id_work_type`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `apartment`
--
ALTER TABLE `apartment`
    MODIFY `id_apartment` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `building`
--
ALTER TABLE `building`
    MODIFY `id_building` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `complaint`
--
ALTER TABLE `complaint`
    MODIFY `id_complaint` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `executor`
--
ALTER TABLE `executor`
    MODIFY `id_executor` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `manager`
--
ALTER TABLE `manager`
    MODIFY `id_manager` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
    MODIFY `id_payment` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `renovation`
--
ALTER TABLE `renovation`
    MODIFY `id_renovation` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rent`
--
ALTER TABLE `rent`
    MODIFY `id_rent` int(11) NOT NULL AUTO_INCREMENT,
    ADD CONSTRAINT `rent_ibfk_1` FOREIGN KEY (`id_apartment`) REFERENCES `apartment` (`id_apartment`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- AUTO_INCREMENT for table `rent_agreement`
--
ALTER TABLE `rent_agreement`
    MODIFY `id_rent_agreement` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `repair`
--
ALTER TABLE `repair`
    MODIFY `id_repair` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `repair_com`
--
ALTER TABLE `repair_com`
    MODIFY `id_repair_com` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `room_type`
--
ALTER TABLE `room_type`
    MODIFY `id_room` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tenant`
--
ALTER TABLE `tenant`
    MODIFY `id_tenant` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_login`
--
ALTER TABLE `user_login`
    MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `worktype`
--
ALTER TABLE `worktype`
    MODIFY `id_work_type` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `apartment`
--
ALTER TABLE `apartment`
    ADD CONSTRAINT `apartment_ibfk_2` FOREIGN KEY (`id_tenant`) REFERENCES `tenant` (`id_tenant`) ON DELETE NO ACTION ON UPDATE CASCADE,
    ADD CONSTRAINT `apartment_ibfk_4` FOREIGN KEY (`id_building`) REFERENCES `building` (`id_building`) ON DELETE NO ACTION ON UPDATE CASCADE,
    ADD CONSTRAINT `apartment_ibfk_5` FOREIGN KEY (`id_manager`) REFERENCES `manager` (`id_manager`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `complaint`
--
ALTER TABLE `complaint`
    ADD CONSTRAINT `complaint_ibfk_1` FOREIGN KEY (`id_tenant`) REFERENCES `tenant` (`id_tenant`) ON DELETE NO ACTION ON UPDATE CASCADE,
    ADD CONSTRAINT `complaint_ibfk_2` FOREIGN KEY (`id_manager`) REFERENCES `manager` (`id_manager`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `executor`
--
ALTER TABLE `executor`
    ADD CONSTRAINT `executor_ibfk_1` FOREIGN KEY (`id_work_type`) REFERENCES `worktype` (`id_work_type`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
    ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`id_rent`) REFERENCES `rent` (`id_rent`);

--
-- Constraints for table `renovation`
--
ALTER TABLE `renovation`
    ADD CONSTRAINT `renovation_ibfk_1` FOREIGN KEY (`id_apartment`) REFERENCES `apartment` (`id_apartment`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    ADD CONSTRAINT `renovation_ibfk_2` FOREIGN KEY (`id_renovation`) REFERENCES `repair_com` (`id_repair_com`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `rent_agreement`
--
ALTER TABLE `rent_agreement`
    ADD CONSTRAINT `rent_agreement_ibfk_1` FOREIGN KEY (`id_manager`) REFERENCES `manager` (`id_manager`) ON DELETE NO ACTION ON UPDATE CASCADE,
    ADD CONSTRAINT `rent_agreement_ibfk_2` FOREIGN KEY (`id_apartment`) REFERENCES `apartment` (`id_apartment`) ON DELETE NO ACTION ON UPDATE NO ACTION,
    ADD CONSTRAINT `rent_agreement_ibfk_4` FOREIGN KEY (`id_tenant`) REFERENCES `tenant` (`id_tenant`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `repair`
--
ALTER TABLE `repair`
    ADD CONSTRAINT `repair_ibfk_1` FOREIGN KEY (`id_repair_com`) REFERENCES `repair_com` (`id_repair_com`) ON DELETE NO ACTION ON UPDATE CASCADE,
    ADD CONSTRAINT `repair_ibfk_2` FOREIGN KEY (`id_executor`) REFERENCES `executor` (`id_executor`) ON DELETE NO ACTION ON UPDATE CASCADE,
    ADD CONSTRAINT `repair_ibfk_3` FOREIGN KEY (`id_manager`) REFERENCES `manager` (`id_manager`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `repair_com`
--
ALTER TABLE `repair_com`
    ADD CONSTRAINT `repair_com_ibfk_1` FOREIGN KEY (`id_apartment`) REFERENCES `apartment` (`id_apartment`) ON DELETE NO ACTION ON UPDATE NO ACTION;


--
-- Constraints for table `user_login`
--
ALTER TABLE `user_login`
    ADD CONSTRAINT `fk_manager_user_id` FOREIGN KEY (`manager_user_id`) REFERENCES `manager` (`id_manager`) ON DELETE NO ACTION ON UPDATE CASCADE,
    ADD CONSTRAINT `fk_tenant_user_id` FOREIGN KEY (`tenant_user_id`) REFERENCES `tenant` (`id_tenant`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

/***************************** INSERTS HERE *****************************/
/***************************** MANAGERS *****************************/
INSERT INTO DOCKERDB.manager (id_manager, name, surname, birthdate, phone, residence) VALUES (1, 'John', 'Doe', '1987-05-14', 12748520, 'Abby Rd. 1, London');
INSERT INTO DOCKERDB.manager (id_manager, name, surname, birthdate, phone, residence) VALUES (2, 'Martin', 'Boe', '1986-05-15', 21378520, 'Abby Rd. 2, London');
INSERT INTO DOCKERDB.manager (id_manager, name, surname, birthdate, phone, residence) VALUES (3, 'Daniel', 'Loe', '1985-05-09', 42068520, 'Abby Rd. 3, London');

/***************************** TENANTS *****************************/
INSERT INTO DOCKERDB.tenant (id_tenant, residence, name, surname, birthdate, phone) VALUES (1, 'Akademicka 16/3, Gliwice', 'Jan', 'Kowalski', '1999-05-19', 587201502);
INSERT INTO DOCKERDB.tenant (id_tenant, residence, name, surname, birthdate, phone) VALUES (2, 'Akademicka 16/4, Gliwice', 'Krystyna', 'Bogómiła', '1969-06-09', 507092123);
INSERT INTO DOCKERDB.tenant (id_tenant, residence, name, surname, birthdate, phone) VALUES (3, 'Akademicka 17/3, Gliwice', 'Hans', 'Schriber', '1926-09-01', 987456201);

/***************************** USERS *****************************/
INSERT INTO DOCKERDB.user_login (id_user, username, password, user_type, tenant_user_id, manager_user_id) VALUES (1, 'johndoe', '$2a$12$oolAOerEPnL5Gmov68jdAOcf9nDjlYA8KIcMtYlgBpi3canZSJMei', 'manager', null, 1);
INSERT INTO DOCKERDB.user_login (id_user, username, password, user_type, tenant_user_id, manager_user_id) VALUES (2, 'kowalski', '$2a$12$4CGzpxuP9NLoEvYkZjbL3OC7LjBawH/8q2hx2.XnGn3MSwrP5xhKW', 'tenant', 1, null);
INSERT INTO DOCKERDB.user_login (id_user, username, password, user_type, tenant_user_id, manager_user_id) VALUES (3, 'martinboe', '$2a$12$zyO0zGKeV0RKoCggOzCql..OZyqGvlTt1ux5g.4sAsR9ig.pEMv6m', 'manager', null, 2);
INSERT INTO DOCKERDB.user_login (id_user, username, password, user_type, tenant_user_id, manager_user_id) VALUES (4, 'danielloe', '$2a$12$/b/.gvh2yvxwBOPV8dL/.OveVQOF42DwkczDyetTsc.Xb1.9NsSFe', 'manager', null, 3);
INSERT INTO DOCKERDB.user_login (id_user, username, password, user_type, tenant_user_id, manager_user_id) VALUES (5, 'krysia', '$2a$12$CxyRT5o0fwXzgFxeGZRz0.vSu9F3iJMbftiP94QgMsOq/JpA4O10y', 'tenant', 2, null);
INSERT INTO DOCKERDB.user_login (id_user, username, password, user_type, tenant_user_id, manager_user_id) VALUES (6, 'HANS', '$2a$12$H4VU5jzB9hnKAT0Yliuiqegm35CLwDIaJ/lXm3OZklBsjkoCltic.', 'tenant', 3, null);

/***************************** BUILDINGS *****************************/
INSERT INTO DOCKERDB.building (id_building, address, city, prowince) VALUES (1, 'Akademicka 20/7', 'Gliwice', 'Śląsk');
INSERT INTO DOCKERDB.building (id_building, address, city, prowince) VALUES (2, 'Mazowiecka 44', 'Gliwice', 'Śląsk');
INSERT INTO DOCKERDB.building (id_building, address, city, prowince) VALUES (3, 'Rynek 2', 'Gliwice', 'Śląsk');

/***************************** ROOM_TYPE *****************************/
INSERT INTO DOCKERDB.room_type (id_room, room) VALUES (1, 'Living room');
INSERT INTO DOCKERDB.room_type (id_room, room) VALUES (2, 'Kitchen');
INSERT INTO DOCKERDB.room_type (id_room, room) VALUES (3, 'Bathroom');
INSERT INTO DOCKERDB.room_type (id_room, room) VALUES (4, 'Bed room');

/***************************** ROOM *****************************/
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (1, 1, 1);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (2, 1, 1);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (3, 1, 1);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (4, 1, 1);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (1, 1, 2);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (2, 1, 2);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (3, 1, 2);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (4, 1, 2);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (1, 1, 3);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (2, 1, 3);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (3, 1, 3);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (4, 2, 3);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (1, 1, 4);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (2, 1, 4);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (3, 1, 4);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (4, 3, 4);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (1, 1, 5);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (2, 1, 5);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (3, 1, 5);
INSERT INTO DOCKERDB.room (id_room, room_count, id_apartment) VALUES (4, 2, 5);

/***************************** APARTMENTS *****************************/
INSERT INTO DOCKERDB.apartment (id_apartment, apt_number, level, apt_area, details, id_tenant, id_building, id_manager) VALUES (1, 20, 3, 20, 'Skromna kawalerka dla studenta. Blisko uczelni.', 1, 1, 1);
INSERT INTO DOCKERDB.apartment (id_apartment, apt_number, level, apt_area, details, id_tenant, id_building, id_manager) VALUES (2, 22, 3, 25.5, 'Skromna kawalerka dla studenta. Blisko uczelni.', 2, 1, 2);
INSERT INTO DOCKERDB.apartment (id_apartment, apt_number, level, apt_area, details, id_tenant, id_building, id_manager) VALUES (3, 12, 2, 30.43, 'Małe mieszkanko dla dwojga.', 2, 2, 2);
INSERT INTO DOCKERDB.apartment (id_apartment, apt_number, level, apt_area, details, id_tenant, id_building, id_manager) VALUES (4, 58, 8, 58.92, 'Duże mieszkanie dla całej rodziny! Mili sąsiedzi, ładny widok na miasto.', 3, 2, 3);
INSERT INTO DOCKERDB.apartment (id_apartment, apt_number, level, apt_area, details, id_tenant, id_building, id_manager) VALUES (5, 2, 0, 27.7, 'Poza nieznośnymi sąsiadami, wszystko inne jest wpożądku.', 3, 3, 1);

/***************************** RENT_AGREEMENTS *****************************/
INSERT INTO DOCKERDB.rent_agreement (id_rent_agreement, agreement_date, rent_start_date, rent_end_date, rent_price, id_tenant, id_apartment, id_manager, status) VALUES (1, '2020-06-05', '2023-06-30', '2029-06-22', 2000.00, 1, 1, 1, 'active');
INSERT INTO DOCKERDB.rent_agreement (id_rent_agreement, agreement_date, rent_start_date, rent_end_date, rent_price, id_tenant, id_apartment, id_manager, status) VALUES (2, '2020-06-05', '2023-06-30', '2029-06-22', 2000.00, 2, 2, 1, 'active');
INSERT INTO DOCKERDB.rent_agreement (id_rent_agreement, agreement_date, rent_start_date, rent_end_date, rent_price, id_tenant, id_apartment, id_manager, status) VALUES (3, '2020-06-05', '2023-06-30', '2029-06-22', 2000.00, 2, 3, 2, 'active');
INSERT INTO DOCKERDB.rent_agreement (id_rent_agreement, agreement_date, rent_start_date, rent_end_date, rent_price, id_tenant, id_apartment, id_manager, status) VALUES (4, '2020-06-05', '2022-06-30', '2029-06-22', 2000.00, 3, 4, 2, 'active');
INSERT INTO DOCKERDB.rent_agreement (id_rent_agreement, agreement_date, rent_start_date, rent_end_date, rent_price, id_tenant, id_apartment, id_manager, status) VALUES (5, '2020-06-05', '2024-06-30', '2029-06-22', 2000.00, 3, 5, 3, 'active');

/***************************** RENTS *****************************/
