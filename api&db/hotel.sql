/*
SQLyog Community v12.02 (32 bit)
MySQL - 5.7.24 : Database - hotel
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hotel` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `hotel`;

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `Category_ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `image` varchar(300) NOT NULL,
  PRIMARY KEY (`Category_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `category` */

insert  into `category`(`Category_ID`,`name`,`image`) values (1,'Indian','images/indian/indian.jpg'),(2,'Chinese','images/chinese/chinese.jpg'),(3,'Italian','images/italian/italian.jpg'),(4,'Mexican','images/mexican/mexican.png');

/*Table structure for table `chef_details` */

DROP TABLE IF EXISTS `chef_details`;

CREATE TABLE `chef_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(500) NOT NULL,
  `name` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone_num` bigint(20) DEFAULT NULL,
  `fcm_key` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_num` (`phone_num`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `chef_details` */

insert  into `chef_details`(`id`,`email`,`name`,`password`,`phone_num`,`fcm_key`) values (2,'chef1@gmail.com','chef1','03b64b2e277aabadf6e1f498c26745c6',6356913000,NULL),(3,'chef2@gmail.com','chef2','fc1349979e7184f376a62afc68cac381',333,NULL),(4,'chef3@gmail.com','chef3','f7e213cf43c3e88c2c43b3655e070a06',444,NULL);

/*Table structure for table `chef_status` */

DROP TABLE IF EXISTS `chef_status`;

CREATE TABLE `chef_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chef_id` int(11) NOT NULL,
  `status` varchar(100) NOT NULL DEFAULT 'Available',
  PRIMARY KEY (`id`),
  UNIQUE KEY `chef_id` (`chef_id`),
  CONSTRAINT `chef_id` FOREIGN KEY (`chef_id`) REFERENCES `chef_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `chef_status` */

insert  into `chef_status`(`id`,`chef_id`,`status`) values (2,2,'Available'),(3,3,'Available'),(4,4,'Available');

/*Table structure for table `dummy_order_details` */

DROP TABLE IF EXISTS `dummy_order_details`;

CREATE TABLE `dummy_order_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `dummy_order_details` */

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(100) NOT NULL,
  `img` varchar(300) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `sub_cat_id` int(11) NOT NULL,
  `qnty` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `sub_cat_id` (`sub_cat_id`),
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`sub_cat_id`) REFERENCES `sub_category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `items` */

insert  into `items`(`id`,`name`,`description`,`img`,`price`,`sub_cat_id`,`qnty`) values (1,'Naan','chapati','images/indian/chapati/naan.png','10.00',1,0),(2,'Paneer tikka','sabji','images/indian/sabji/paneer_tikka.png','45.00',3,0),(3,'Dry manchurian','manchurian','images/chinese/manchurian/dry_manchurian.png','90.00',2,0),(4,'Margherita','pizza','images/italian/pizza/margherita.png','99.00',4,0),(5,'Red sauce Pasta','pasta','images/mexican/pasta/red_sauce_pasta.png','70.00',5,0),(6,'White sauce','pasta','images/mexican/pasta/white_sauce_pasta.png','90.50',5,0);

/*Table structure for table `ordereddetails` */

DROP TABLE IF EXISTS `ordereddetails`;

CREATE TABLE `ordereddetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `item_qnty` int(11) NOT NULL,
  `itemNotes` varchar(200) DEFAULT NULL,
  `chef_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_id` (`item_id`),
  KEY `table_id` (`table_id`),
  KEY `order_chef_id` (`chef_id`),
  CONSTRAINT `item_id` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_chef_id` FOREIGN KEY (`chef_id`) REFERENCES `chef_details` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `table_id` FOREIGN KEY (`table_id`) REFERENCES `tab_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `ordereddetails` */

insert  into `ordereddetails`(`id`,`table_id`,`item_id`,`item_qnty`,`itemNotes`,`chef_id`) values (2,1,1,1,'anything u can add here!',NULL);

/*Table structure for table `sub_category` */

DROP TABLE IF EXISTS `sub_category`;

CREATE TABLE `sub_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `image` varchar(300) NOT NULL,
  `cat_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cat_id` (`cat_id`),
  CONSTRAINT `sub_category_ibfk_1` FOREIGN KEY (`cat_id`) REFERENCES `category` (`Category_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `sub_category` */

insert  into `sub_category`(`id`,`name`,`image`,`cat_id`) values (1,'Chapati','images/indian/chapati.jpg',1),(2,'Manchurian','images/chinese/manchurian.jpg',2),(3,'Sabji','images/indian/sabji.jpg',1),(4,'Pizza','images/italian/pizza.jpg',3),(5,'Pasta','images/mexican/pasta.jpg',4);

/*Table structure for table `tab_status` */

DROP TABLE IF EXISTS `tab_status`;

CREATE TABLE `tab_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `tab_status` */

insert  into `tab_status`(`id`,`name`,`status`) values (1,'First','Reserved'),(2,'Second','Available'),(3,'Third','Available'),(4,'Fourth','Available'),(5,'Fifth','Available');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
