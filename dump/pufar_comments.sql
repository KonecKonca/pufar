CREATE DATABASE  IF NOT EXISTS `pufar` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pufar`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pufar
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `notification_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `comment` varchar(500) NOT NULL,
  `date` mediumtext,
  PRIMARY KEY (`comment_id`),
  KEY `comments_users_user_id_fk` (`user_id`),
  KEY `comments_notifications_notification_id_fk` (`notification_id`),
  CONSTRAINT `comments_notifications_notification_id_fk` FOREIGN KEY (`notification_id`) REFERENCES `notifications` (`notification_id`),
  CONSTRAINT `comments_users_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (3,3,1,'price is over my mind','1545390205764'),(6,68,3,'first comment','1545390225765'),(7,68,2,'second comment','1545390225964'),(8,68,1,'тачка топ, буду рассматривать','1545591470757'),(9,68,1,'хотя, нет, соси писькук бомжара','1545591493345'),(10,67,1,'лоховский гелик)))','1545591521626'),(11,68,1,'тачка топ, буду брать','1545598608254'),(12,67,1,'это жига, осёл','1545661593691'),(13,21,13,'петя лошара','1545665399835'),(14,8,1,'111111111111111111111','1545737112063'),(15,8,1,'2222222222222222222','1545737115060'),(22,105,1,'1','1545762277203'),(23,105,1,'2','1545762279051'),(24,105,1,'3','1545762281194'),(25,105,1,'1','1545762298319'),(26,105,1,'2','1545762300845'),(27,105,1,'3','1545762302903'),(40,61,1,'2','1545933639784'),(47,66,1,'дааааа','1545937206940'),(48,66,1,'развратница','1545937213920'),(49,105,1,'214dgrgr','1546086719135'),(61,3,1,'2143243','1546531533132');
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-03 19:19:57
