-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Sep 20, 2022 at 08:49 PM
-- Server version: 5.7.31
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `archive`
--

DROP TABLE IF EXISTS `archive`;
CREATE TABLE IF NOT EXISTS `archive` (
  `ar_id` int(11) NOT NULL AUTO_INCREMENT,
  `ar_client_id` int(11) NOT NULL,
  `ar_chambre_id` int(11) NOT NULL,
  `ar_date_entry` date NOT NULL,
  `ar_date_exit` date NOT NULL,
  PRIMARY KEY (`ar_id`),
  KEY `FK_ar_chambre_id` (`ar_chambre_id`),
  KEY `FK_ar_client_id` (`ar_client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `archive`
--

INSERT INTO `archive` (`ar_id`, `ar_client_id`, `ar_chambre_id`, `ar_date_entry`, `ar_date_exit`) VALUES
(1, 6, 9, '2022-06-16', '2022-07-30'),
(2, 5, 11, '2022-07-05', '2022-07-15'),
(3, 2, 8, '2022-07-10', '2022-07-20'),
(4, 5, 11, '2022-06-30', '2022-08-30'),
(5, 6, 1, '2022-06-16', '2022-06-20');

--
-- Triggers `archive`
--
DROP TRIGGER IF EXISTS `after_insert_archive`;
DELIMITER $$
CREATE TRIGGER `after_insert_archive` AFTER INSERT ON `archive` FOR EACH ROW BEGIN
UPDATE chambre,archive SET chambre.chambre_etat='Free' WHERE chambre.chambre_id=archive.ar_chambre_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `chambre`
--

DROP TABLE IF EXISTS `chambre`;
CREATE TABLE IF NOT EXISTS `chambre` (
  `chambre_id` int(11) NOT NULL AUTO_INCREMENT,
  `chambre_name` varchar(100) DEFAULT NULL,
  `chambre_image` varchar(100) NOT NULL,
  `chambre_classe` varchar(100) NOT NULL,
  `chambre_etat` varchar(100) NOT NULL,
  `chambre_prix` double NOT NULL,
  PRIMARY KEY (`chambre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `chambre`
--

INSERT INTO `chambre` (`chambre_id`, `chambre_name`, `chambre_image`, `chambre_classe`, `chambre_etat`, `chambre_prix`) VALUES
(1, 'ROOM 1', 'room1.jpg', 'Simple', 'Occupied', 50),
(2, 'ROOM 2', 'room2.jpg', 'VIP', 'Free', 80),
(4, 'ROOM 3', 'room3.jpg', 'VVIP', 'Free', 100),
(5, 'ROOM 4', 'room1.jpg', 'Simple', 'Occupied', 50),
(6, 'ROOM 5', 'room1.jpg', 'Simple', 'Free', 50),
(7, 'ROOM 6', 'room3.jpg', 'VVIP', 'Occupied', 100),
(8, 'ROOM 7', 'room3.jpg', 'VVIP', 'Occupied', 100),
(9, 'ROOM 8', 'room2.jpg', 'VIP', 'Free', 80),
(10, 'ROOM 9', 'room2.jpg', 'VIP', 'Free', 80),
(11, 'ROOM 10', 'room3.jpg', 'VVIP', 'Free', 100),
(12, 'ROOM 11', 'room2.jpg', 'Simple', 'Free', 50);

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `client_id` int(11) NOT NULL AUTO_INCREMENT,
  `client_fname` varchar(100) NOT NULL,
  `client_lname` varchar(100) NOT NULL,
  `client_email` varchar(100) NOT NULL,
  `client_tel` int(11) NOT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`client_id`, `client_fname`, `client_lname`, `client_email`, `client_tel`) VALUES
(1, 'Mugisho', 'Lwango', 'mugi@biu.bi', 76058950),
(2, 'Chris', 'Cedrick', 'chriscedrick4@gmail.com', 62171087),
(4, 'Salim', 'Njopeka', 'snjopeka@biu.bi', 75106683),
(5, 'Kwizera', 'Ibrahim', 'kwizera.ibrahim@gmail.com', 68274651),
(6, 'Klose', 'Janvier', 'klosejr@gmail.com', 62171087),
(7, 'Mugisha', 'Lee Beaugar', 'lee@biu.bi', 6798085);

-- --------------------------------------------------------

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `res_id` int(11) NOT NULL AUTO_INCREMENT,
  `res_client_id` int(11) NOT NULL,
  `res_chambre_id` int(11) NOT NULL,
  `res_date_entry` date NOT NULL,
  `res_date_exit` date NOT NULL,
  PRIMARY KEY (`res_id`),
  KEY `FK_res_client_id` (`res_client_id`),
  KEY `FK_res_chambre_id` (`res_chambre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation` (`res_id`, `res_client_id`, `res_chambre_id`, `res_date_entry`, `res_date_exit`) VALUES
(2, 1, 5, '2022-05-16', '2022-07-20'),
(3, 4, 7, '2022-06-30', '2022-07-30'),
(8, 5, 1, '2022-07-05', '2022-07-20'),
(9, 5, 8, '2022-05-16', '2022-06-20');

--
-- Triggers `reservation`
--
DROP TRIGGER IF EXISTS `after_insert_reservation`;
DELIMITER $$
CREATE TRIGGER `after_insert_reservation` AFTER INSERT ON `reservation` FOR EACH ROW BEGIN
    UPDATE chambre,reservation SET chambre.chambre_etat='Occupied' WHERE chambre.chambre_id=reservation.res_chambre_id;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `util_id` int(11) NOT NULL AUTO_INCREMENT,
  `util_fname` varchar(100) NOT NULL,
  `util_lname` varchar(100) NOT NULL,
  `util_email` varchar(100) NOT NULL,
  `util_role` varchar(100) DEFAULT NULL,
  `util_password` varchar(100) NOT NULL,
  PRIMARY KEY (`util_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`util_id`, `util_fname`, `util_lname`, `util_email`, `util_role`, `util_password`) VALUES
(2, 'Tif', 'Manisha', 'tifmanisha80@gmail.com', 'Administrator', '1234567890');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `archive`
--
ALTER TABLE `archive`
  ADD CONSTRAINT `FK_ar_chambre_id` FOREIGN KEY (`ar_chambre_id`) REFERENCES `chambre` (`chambre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_ar_client_id` FOREIGN KEY (`ar_client_id`) REFERENCES `client` (`client_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `FK_res_chambre_id` FOREIGN KEY (`res_chambre_id`) REFERENCES `chambre` (`chambre_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_res_client_id` FOREIGN KEY (`res_client_id`) REFERENCES `client` (`client_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
