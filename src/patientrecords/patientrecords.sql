SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE IF NOT EXISTS `patient` (
    `id` int(10) NOT NULL,
    `firstname` varchar(50) DEFAULT NULL,
    `lastname` varchar(50) DEFAULT NULL,
    `sex` char(1) NOT NULL,
    `age` int(3) NOT NULL,
    `floor` int(3) NOT NULL,
    `room` int(4) NOT NULL,
    `fee` decimal(10, 2) DEFAULT NULL,
    `insurance` decimal(10, 2) DEFAULT NULL
)ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

INSERT INTO `patient` (`id`, `firstname`, `lastname`, `sex`, `age`, `floor`, `room`, `fee`, `insurance`) VALUES
(1, 'SARA', 'BROWN', 'F', 24, 2, 201, 3500.00, 2500.00),
(2, 'ASHLEY', 'JONES', 'X', 16, 3, 305, 1500.00, 1500.00),
(3, 'ROBERT', 'ANDERSON', 'M', 34, 1, 110, 585.00, 125.00),
(4, 'MARCUS', 'STUART', 'M', 75, 3, 301, 6570.00, 6500.00),
(5, 'PAIGE', 'CORTESS', 'F', 32, 2, 207, 12000.00, 10000); 

ALTER TABLE `patient`
 ADD PRIMARY KEY (`id`);

 ALTER TABLE `patient`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;