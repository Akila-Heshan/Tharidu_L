-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for tharidu_iot
CREATE DATABASE IF NOT EXISTS `tharidu_iot` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `tharidu_iot`;

-- Dumping structure for table tharidu_iot.device
CREATE TABLE IF NOT EXISTS `device` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `status` varchar(45) NOT NULL,
  `mode_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_device_mode_idx` (`mode_id`),
  CONSTRAINT `fk_device_mode` FOREIGN KEY (`mode_id`) REFERENCES `mode` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table tharidu_iot.device: ~2 rows (approximately)
INSERT INTO `device` (`id`, `name`, `status`, `mode_id`) VALUES
	(1, 'Light', 'OFF', 3),
	(2, 'Fan', 'OFF', 3);

-- Dumping structure for table tharidu_iot.mode
CREATE TABLE IF NOT EXISTS `mode` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table tharidu_iot.mode: ~3 rows (approximately)
INSERT INTO `mode` (`id`, `name`) VALUES
	(1, 'AUTO'),
	(2, 'ON'),
	(3, 'OFF');

-- Dumping structure for table tharidu_iot.sensor
CREATE TABLE IF NOT EXISTS `sensor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `value` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table tharidu_iot.sensor: ~2 rows (approximately)
INSERT INTO `sensor` (`id`, `name`, `value`) VALUES
	(1, 'temperature', 27),
	(2, 'humidity', 75);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
