-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Cze 29, 2024 at 08:32 PM
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
-- Database: `jprojekt`
--
CREATE DATABASE IF NOT EXISTS `jprojekt` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `jprojekt`;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `sprzet`
--

CREATE TABLE `sprzet` (
  `id` int(11) NOT NULL,
  `rodzaj` varchar(50) NOT NULL,
  `nazwa` varchar(50) NOT NULL,
  `ilosc` int(11) NOT NULL,
  `kosztzadzien` int(11) NOT NULL,
  `maxczasdni` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sprzet`
--

INSERT INTO `sprzet` (`id`, `rodzaj`, `nazwa`, `ilosc`, `kosztzadzien`, `maxczasdni`) VALUES
(1, 'Sportowy', 'Narty', 9, 60, 4),
(2, 'Kamera', 'Canon', 5, 100, 14),
(3, 'Mikrofon', 'SPC', 10, 50, 14),
(4, 'Projektor', 'Epson', 6, 200, 10),
(5, 'Głośnik', 'JBL', 8, 150, 7),
(6, 'Laptop', 'Dell', 9, 300, 20),
(7, 'Kamera sportowa', 'GoPro', 11, 50, 30),
(8, 'Tablet', 'iPad', 5, 250, 14),
(9, 'Dron', 'DJI', 3, 150, 10),
(10, 'Laptop', 'MacBook', 21, 150, 8),
(12, 'Odkurzacz', 'Karcher', 35, 60, 7),
(13, 'Laptop', 'Huawei', 14, 70, 14),
(14, 'Mop', 'Vileda', 52, 20, 5);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `login` varchar(50) NOT NULL,
  `imie` varchar(50) NOT NULL,
  `nazwisko` varchar(50) NOT NULL,
  `pasword` varchar(50) NOT NULL,
  `isadmin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `login`, `imie`, `nazwisko`, `pasword`, `isadmin`) VALUES
(1, 'admin', 'Jan', 'Kowalski', 'admin123', 1),
(2, 'jacek', 'Jacek', 'Jackowski', 'jacek11', 0),
(3, 'adrian', 'Adrian', 'Andrzejewski', 'adriano', 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `wypozyczenia`
--

CREATE TABLE `wypozyczenia` (
  `id` int(11) NOT NULL,
  `sprzet_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `ilosc` int(11) NOT NULL,
  `dataod` date NOT NULL,
  `datado` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `wypozyczenia`
--

INSERT INTO `wypozyczenia` (`id`, `sprzet_id`, `user_id`, `ilosc`, `dataod`, `datado`) VALUES
(1, 7, 2, 12, '2024-06-29', '2024-07-03'),
(2, 6, 2, 10, '2024-06-29', '2024-07-06'),
(3, 1, 2, 10, '2024-06-29', '2024-07-02'),
(4, 10, 2, 25, '2024-06-29', '2024-07-05'),
(5, 14, 2, 55, '2024-06-29', '2024-07-02'),
(6, 12, 3, 40, '2024-06-29', '2024-07-06');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `sprzet`
--
ALTER TABLE `sprzet`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `wypozyczenia`
--
ALTER TABLE `wypozyczenia`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sprzet_id` (`sprzet_id`),
  ADD KEY `user_id` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `sprzet`
--
ALTER TABLE `sprzet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `wypozyczenia`
--
ALTER TABLE `wypozyczenia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `wypozyczenia`
--
ALTER TABLE `wypozyczenia`
  ADD CONSTRAINT `wypozyczenia_ibfk_1` FOREIGN KEY (`sprzet_id`) REFERENCES `sprzet` (`id`),
  ADD CONSTRAINT `wypozyczenia_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
