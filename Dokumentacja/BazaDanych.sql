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
  `id_rent` int(11) DEFAULT NULL,
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
  `payed` tinyint(1) NOT NULL
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
  ADD KEY `id_rent` (`id_rent`),
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
  ADD PRIMARY KEY (`id_rent`);

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
  ADD PRIMARY KEY (`id_room`,`id_apartment`),
  ADD KEY `id_apartment` (`id_apartment`),
  ADD KEY `id_room` (`id_room`);

--
-- Indeksy dla tabeli `room_type`
--
ALTER TABLE `room_type`
  ADD PRIMARY KEY (`id_room`),
  ADD UNIQUE KEY `room` (`room`);

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
  MODIFY `id_rent` int(11) NOT NULL AUTO_INCREMENT;

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
  ADD CONSTRAINT `apartment_ibfk_1` FOREIGN KEY (`id_rent`) REFERENCES `rent` (`id_rent`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `apartment_ibfk_2` FOREIGN KEY (`id_tenant`) REFERENCES `tenant` (`id_tenant`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `apartment_ibfk_4` FOREIGN KEY (`id_building`) REFERENCES `building` (`id_building`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `apartment_ibfk_5` FOREIGN KEY (`id_manager`) REFERENCES `manager` (`id_manager`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `apartment_ibfk_6` FOREIGN KEY (`id_apartment`) REFERENCES `room` (`id_apartment`) ON DELETE NO ACTION ON UPDATE CASCADE;

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
  ADD CONSTRAINT `renovation_ibfk_1` FOREIGN KEY (`id_apartment`) REFERENCES `apartment` (`id_apartment`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `renovation_ibfk_2` FOREIGN KEY (`id_renovation`) REFERENCES `repair_com` (`id_repair_com`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `rent_agreement`
--
ALTER TABLE `rent_agreement`
  ADD CONSTRAINT `rent_agreement_ibfk_1` FOREIGN KEY (`id_manager`) REFERENCES `manager` (`id_manager`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `rent_agreement_ibfk_2` FOREIGN KEY (`id_apartment`) REFERENCES `apartment` (`id_apartment`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `rent_agreement_ibfk_4` FOREIGN KEY (`id_tenant`) REFERENCES `tenant` (`id_tenant`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `rent_agreement_ibfk_5` FOREIGN KEY (`id_rent_agreement`) REFERENCES `repair_com` (`id_rent_agreement`) ON DELETE NO ACTION ON UPDATE CASCADE;

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
  ADD CONSTRAINT `repair_com_ibfk_1` FOREIGN KEY (`id_apartment`) REFERENCES `apartment` (`id_apartment`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `room_ibfk_1` FOREIGN KEY (`id_room`) REFERENCES `room_type` (`id_room`) ON DELETE NO ACTION ON UPDATE CASCADE;

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
