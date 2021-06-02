-- MariaDB dump 10.17  Distrib 10.4.14-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: eblo_api_psm_auth
-- ------------------------------------------------------
-- Server version	10.4.14-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_auth`
--

DROP TABLE IF EXISTS `tb_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_auth` (
  `tba_id` int(11) NOT NULL AUTO_INCREMENT,
  `tba_create_date` datetime DEFAULT NULL,
  `tba_create_id` int(11) DEFAULT NULL,
  `tba_update_date` datetime DEFAULT NULL,
  `tba_update_id` int(11) DEFAULT NULL,
  `tba_email` varchar(255) DEFAULT NULL,
  `tba_member_id` varchar(255) DEFAULT NULL,
  `tba_id_login` varchar(255) DEFAULT NULL,
  `tba_password` varchar(32) DEFAULT NULL,
  `tba_status` varchar(20) DEFAULT NULL,
  `tba_token_salt` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`tba_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_auth`
--

LOCK TABLES `tb_auth` WRITE;
/*!40000 ALTER TABLE `tb_auth` DISABLE KEYS */;
INSERT INTO `tb_auth` VALUES (1,'2019-09-03 15:43:36',0,'2020-07-30 10:20:33',0,'achmad.amri@gmail.com','999991','999991','202cb962ac59075b964b07152d234b70','active','jnsr8q8t7dzgqv8qqmkfsffc5mqdn5fec4af');
/*!40000 ALTER TABLE `tb_auth` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-02 15:57:20
