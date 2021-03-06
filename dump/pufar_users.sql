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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(25) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `ban_status` tinyint(1) DEFAULT '0',
  `number_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `users_login_uindex` (`login`),
  KEY `status` (`status`),
  KEY `users_numbers_number_id_fk` (`number_id`),
  CONSTRAINT `status` FOREIGN KEY (`status`) REFERENCES `statuses` (`status_id`),
  CONSTRAINT `users_numbers_number_id_fk` FOREIGN KEY (`number_id`) REFERENCES `numbers` (`number_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'andrei','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B',3,0,1),(2,'vasil','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B',1,0,NULL),(3,'petia','15 09 20 CC ED C3 4D 24 03 1C DD 37 11 E4 33 10',1,0,1),(4,'konecKonca','41 95 E4 03 44 44 CA FE 4F A9 A6 E1 3A 01 B2 52',2,0,NULL),(9,'23434','31 41 C8 39 8A 2D 02 F7 8C B4 B0 53 3B 13 83 49 ',1,0,NULL),(11,'andrei_ert','32 74 A6 9A 48 5D 45 5E DA C6 0A C6 67 5B 2E A9 ',1,0,NULL),(12,'юджин217','41 95 E4 03 44 44 CA FE 4F A9 A6 E1 3A 01 B2 52 ',1,0,NULL),(13,'южаюжа','41 95 E4 03 44 44 CA FE 4F A9 A6 E1 3A 01 B2 52 ',1,0,4),(14,'ergrупупупн','96 E7 92 18 96 5E B7 2C 92 A5 49 DD 5A 33 01 12 ',1,0,NULL),(15,'qwewe','EF E6 39 81 27 92 8F 1B 2E 9E F3 20 7F B8 26 63 ',1,0,NULL),(16,'йцуйцу','21 27 DA 04 4D 2F 11 43 F6 07 D1 05 EA EC 83 64 ',1,0,NULL),(17,'grgrgr','6C 8E 44 8F 6F A9 DE D8 EB 6A 81 34 D5 E6 3C 79 ',1,0,NULL),(18,'234346','E7 24 E5 AD 0D 0D F6 25 74 42 39 EF 1C 60 E5 E8 ',1,0,NULL),(19,'111111','96 E7 92 18 96 5E B7 2C 92 A5 49 DD 5A 33 01 12',3,0,NULL),(20,'petusha','5E B1 07 1D F7 5D 60 E3 E4 A6 47 95 2F 42 7A 93 ',1,1,NULL),(21,'andreix','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B ',1,0,NULL),(22,'andrei1','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B ',1,1,NULL),(23,'xxxxx','FB 0E 22 C7 9A C7 56 79 E9 88 1E 6B A1 83 B3 54 ',1,0,NULL),(24,'testtest','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B ',1,0,8),(25,'testtestt','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B ',1,0,NULL),(32,'andrei22','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B ',1,0,NULL),(33,'andrei33','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B ',1,0,NULL),(34,'andrei23','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B ',1,0,11),(35,'andrei3233','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B ',1,0,NULL),(36,'andrei15','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B ',1,0,NULL),(37,'andrei<SCRIPT>','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B',1,0,NULL),(39,'щжщ0ж','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B',1,0,NULL),(40,'andreiefefefefefe','82 7C CB 0E EA 8A 70 6C 4C 34 A1 68 91 F8 4E 7B',1,0,NULL),(42,'22222','3D 21 72 41 8C E3 05 C7 D1 6D 4B 05 59 7C 6A 59',1,0,NULL),(43,'33333','B7 BC 2A 2F 5B B6 D5 21 E6 4C 89 74 C1 43 E9 A0',1,0,12),(44,'44444','79 B7 CD CD 14 DB 14 E9 CB 49 8F 17 93 81 7D 69',1,0,NULL),(45,'55555','C5 FE 25 89 6E 49 DD FE 99 6D B7 50 8C F0 05 34',3,0,NULL),(46,'66666','AE 8B 5A A2 6A 3A E3 16 12 EE C1 D1 F6 FF BC E9',1,0,NULL),(47,'77777','22 A4 D9 B0 4F E9 5C 98 93 B4 1E 2F DE 83 A4 27',1,0,NULL),(48,'88888','1C 39 5A 8D CE 13 58 49 BD 73 C6 DB A3 B5 48 09',1,0,NULL),(49,'99999','D3 EB 9A 92 33 E5 29 48 74 0D 7E B8 C3 06 2D 14',1,0,NULL),(50,'qqqqq','43 75 99 F1 EA 35 14 F8 96 9F 16 1A 66 06 CE 18',1,0,NULL),(51,'wwwww','DF 48 34 02 B9 BF EB 23 47 17 A3 2C 6E 86 28 0E',1,0,NULL),(52,'eeeee','86 87 1B 9B 1A B3 3B 08 34 D4 55 C5 40 D8 2E 89',1,0,NULL),(53,'rrrrr','6B 76 B5 B5 45 67 EC 00 08 28 7D 11 A2 E9 E2 2A',1,0,NULL),(54,'ttttt','6C EE 46 18 FC 49 60 D1 84 EB 7E FB D0 AA 27 B5',1,0,NULL),(55,'yyyyy','C5 77 7C 09 1D F7 72 FF 2B 2D FF CB 43 D9 79 4C',1,0,NULL),(56,'uuuuu','3C 9A A2 81 CE D9 22 94 F2 59 C0 C5 55 20 B2 CF',1,0,NULL),(57,'iiiii','07 75 D0 BF BE C1 08 DE 4A 82 71 E3 4F 95 47 CB',1,0,NULL),(58,'ooooo','45 29 83 08 DB EC 5C 07 57 E6 F7 51 D0 FB 2A 29',1,0,NULL),(59,'ppppp','A7 C4 71 CF D3 C4 2D C6 D6 A8 55 2A C2 C0 A2 2C',1,0,NULL),(60,'aaaaa','59 4F 80 3B 38 0A 41 39 6E D6 3D CA 39 50 35 42',1,0,NULL),(61,'sssss','2D 02 E6 69 73 1C BA DE 6A 64 B5 8D 60 2C F2 A4',1,0,NULL),(62,'ddddd','50 F8 4D AF 3A 6D FD 6A 9F 20 C9 F8 EF 42 89 42',1,0,13),(63,'fffff','A9 8F 6F 64 E6 CD FA C2 2A B2 FF D1 5A 72 41 E3',1,0,NULL),(64,'ggggg','9A 0F E2 7C 8B CC 9A AD 51 ED A5 5E 1B 73 5E B5',1,0,NULL),(65,'hhhhh','C1 18 1A AC F6 46 B9 7F 0A 0A 78 2D B3 51 A4 05',1,0,NULL),(66,'jjjjj','3E 55 51 7A EA 64 F6 D3 68 EF 83 64 59 F1 51 82',1,0,NULL),(67,'kkkkk','F2 5B 82 58 B6 F0 86 5C 46 0C E3 10 7D 6F 01 16',1,0,NULL),(68,'lllll','11 5C B5 3D EE A1 F4 07 B6 A4 D3 51 3F C4 92 B0',1,0,NULL),(69,'zzzzz','95 EB C3 C7 B3 B9 F1 D2 C4 0F EC 14 41 5D 3C B8',1,0,NULL),(70,'ccccc','67 C7 62 27 6B CE D0 9E E4 DF 0E D5 37 D1 64 EA',1,0,NULL),(71,'vvvvv','10 39 35 FB 41 4D 69 3B A3 A5 F0 1A 9D 93 99 D3',1,0,NULL),(72,'bbbbb','A2 10 75 A3 6E ED DD 08 4E 17 61 1A 23 8C 71 01',1,0,NULL),(73,'nnnnn','04 12 60 22 82 FA 12 21 FF 21 19 94 89 5A A4 D2',1,0,NULL),(74,'mmmmm','D9 30 8F 32 F8 C6 CF 37 0C A5 AA AE AF C0 D4 9B',1,0,NULL),(75,'яяяяя','50 F2 0E A3 70 A1 13 0F 64 40 9E 52 11 54 AA 68',1,0,NULL),(76,'ччччч','8B E3 7A 69 2A C4 1D 5C 92 75 4F AA E3 1A 49 F3',1,0,NULL),(77,'ссссс','27 54 52 3A 41 7E DA 50 F2 24 51 A7 C8 A4 CC 87',1,0,NULL),(78,'ммммм','96 E4 A9 F7 A1 9D 4D A8 30 BF DC AB EC 67 33 94',1,0,NULL),(79,'иииии','63 0E 0E E1 30 4A 0F BF 58 DC CE 27 30 A4 E8 98',1,0,NULL),(80,'ттттт','7D 47 22 28 B9 EA 3B F7 FB C1 ED 83 B9 54 61 D8',1,0,NULL),(81,'ььььь','C5 85 02 FC E9 A8 E2 6B 5E 37 B2 10 6E 5A C3 6B',1,0,NULL),(82,'ююююю','60 2F EA 26 45 06 C4 EF 7B C6 D7 CE E7 68 A3 C1',1,0,NULL),(83,'1111q','46 CD 28 F3 72 0F 9D B4 71 AF 84 89 1F E2 31 5A',1,0,NULL),(84,'2222q','5D B6 0C 3A 76 7A 3B 5D 19 CE 22 F5 1C 0C 37 4A',1,0,NULL),(85,'12312','B0 BA EE 9D 27 9D 34 FA 1D FD 71 AA DB 90 8C 3F',1,0,14);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-08 13:53:25
