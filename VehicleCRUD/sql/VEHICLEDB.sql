CREATE DATABASE `VEHICLEDB`;
USE VEHICLEDB;

CREATE TABLE `vehicle` (
  `Id` int(11) NOT NULL  ,
  `Year` int(4) NOT NULL,
  `Make` varchar(128) NOT NULL,
  `Model` varchar(45) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Id_UNIQUE` (`Id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1


