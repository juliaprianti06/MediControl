CREATE DATABASE  IF NOT EXISTS `sistema_medicamentos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sistema_medicamentos`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: sistema_medicamentos
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `medicamento`
--

DROP TABLE IF EXISTS `medicamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicamento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `dosagem` varchar(20) NOT NULL,
  `unidade` varchar(10) NOT NULL,
  `forma` varchar(20) NOT NULL,
  `intervalo_uso` int NOT NULL,
  `inicio_tratamento` date NOT NULL,
  `duracao_dias` int DEFAULT NULL,
  `indeterminado` tinyint(1) DEFAULT '0',
  `hora_primeira_dose` time DEFAULT NULL,
  `usuario_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_usuario_id` (`usuario_id`),
  CONSTRAINT `fk_usuario_id` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicamento`
--

LOCK TABLES `medicamento` WRITE;
/*!40000 ALTER TABLE `medicamento` DISABLE KEYS */;
INSERT INTO `medicamento` VALUES (13,'dsaasd','23','mg','Comprimido',5,'2025-06-26',NULL,1,'02:00:00',1),(14,'asdasd','24','mg','Comprimido',1,'2025-06-26',NULL,1,'01:00:00',1),(15,'Rivotril','1','g','Comprimido',6,'2025-06-26',NULL,1,'06:00:00',2),(16,'Gadernal','1000','mg','Comprimido',4,'2025-06-29',NULL,1,'08:00:00',1);
/*!40000 ALTER TABLE `medicamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro`
--

DROP TABLE IF EXISTS `registro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro` (
  `id` int NOT NULL AUTO_INCREMENT,
  `medicamento_id` int DEFAULT NULL,
  `horario` datetime DEFAULT NULL,
  `status` enum('TOMADO','ESQUECIDO','PENDENTE') NOT NULL,
  `usuario_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `medicamento_id` (`medicamento_id`),
  KEY `fk_usuario_registro` (`usuario_id`),
  CONSTRAINT `fk_usuario_registro` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  CONSTRAINT `registro_ibfk_1` FOREIGN KEY (`medicamento_id`) REFERENCES `medicamento` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=525 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro`
--

LOCK TABLES `registro` WRITE;
/*!40000 ALTER TABLE `registro` DISABLE KEYS */;
INSERT INTO `registro` VALUES (468,14,'2025-06-26 01:00:00','TOMADO',1),(469,13,'2025-06-26 02:00:00','TOMADO',1),(470,14,'2025-06-26 02:00:00','ESQUECIDO',1),(471,14,'2025-06-26 03:00:00','TOMADO',1),(472,14,'2025-06-26 04:00:00','TOMADO',1),(473,15,'2025-06-26 18:00:00','TOMADO',2),(474,15,'2025-06-26 12:00:00','ESQUECIDO',2),(475,15,'2025-06-26 06:00:00','TOMADO',2),(476,14,'2025-06-27 01:00:00','ESQUECIDO',1),(477,13,'2025-06-27 02:00:00','ESQUECIDO',1),(478,14,'2025-06-27 02:00:00','ESQUECIDO',1),(479,14,'2025-06-27 03:00:00','ESQUECIDO',1),(480,14,'2025-06-27 04:00:00','ESQUECIDO',1),(481,13,'2025-06-29 02:00:00','TOMADO',1),(482,13,'2025-06-29 07:00:00','ESQUECIDO',1),(483,13,'2025-06-29 12:00:00','ESQUECIDO',1),(484,13,'2025-06-29 17:00:00','ESQUECIDO',1),(485,14,'2025-06-29 01:00:00','TOMADO',1),(486,14,'2025-06-29 02:00:00','TOMADO',1),(487,14,'2025-06-29 03:00:00','ESQUECIDO',1),(488,14,'2025-06-29 04:00:00','ESQUECIDO',1),(489,14,'2025-06-29 05:00:00','ESQUECIDO',1),(490,14,'2025-06-29 06:00:00','ESQUECIDO',1),(491,14,'2025-06-29 07:00:00','ESQUECIDO',1),(492,14,'2025-06-29 08:00:00','ESQUECIDO',1),(493,14,'2025-06-29 09:00:00','ESQUECIDO',1),(494,14,'2025-06-29 10:00:00','ESQUECIDO',1),(495,14,'2025-06-29 11:00:00','ESQUECIDO',1),(496,14,'2025-06-29 12:00:00','ESQUECIDO',1),(497,14,'2025-06-29 13:00:00','ESQUECIDO',1),(498,14,'2025-06-29 14:00:00','ESQUECIDO',1),(499,14,'2025-06-29 15:00:00','ESQUECIDO',1),(500,14,'2025-06-29 16:00:00','ESQUECIDO',1),(501,14,'2025-06-29 17:00:00','ESQUECIDO',1),(502,14,'2025-06-29 18:00:00','ESQUECIDO',1),(503,14,'2025-06-29 19:00:00','ESQUECIDO',1),(504,16,'2025-06-29 08:00:00','ESQUECIDO',1),(505,16,'2025-06-29 12:00:00','ESQUECIDO',1),(506,16,'2025-06-29 16:00:00','ESQUECIDO',1),(507,14,'2025-06-29 20:00:00','ESQUECIDO',1),(508,14,'2025-06-29 21:00:00','ESQUECIDO',1),(509,16,'2025-06-29 20:00:00','ESQUECIDO',1),(510,13,'2025-06-29 22:00:00','ESQUECIDO',1),(511,14,'2025-06-29 22:00:00','ESQUECIDO',1),(512,13,'2025-07-10 02:00:00','ESQUECIDO',1),(513,13,'2025-07-10 07:00:00','ESQUECIDO',1),(514,14,'2025-07-10 01:00:00','ESQUECIDO',1),(515,14,'2025-07-10 02:00:00','ESQUECIDO',1),(516,14,'2025-07-10 03:00:00','ESQUECIDO',1),(517,14,'2025-07-10 04:00:00','ESQUECIDO',1),(518,14,'2025-07-10 05:00:00','ESQUECIDO',1),(519,14,'2025-07-10 06:00:00','ESQUECIDO',1),(520,14,'2025-07-10 07:00:00','ESQUECIDO',1),(521,14,'2025-07-10 08:00:00','ESQUECIDO',1),(522,14,'2025-07-10 09:00:00','ESQUECIDO',1),(523,14,'2025-07-10 10:00:00','ESQUECIDO',1),(524,16,'2025-07-10 08:00:00','ESQUECIDO',1);
/*!40000 ALTER TABLE `registro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `data_nasc` date DEFAULT NULL,
  `senha` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Gustavo Santos Moreira','12991503513','santosmoreiragustavo@gmail.com','2006-02-04','ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad'),(2,'Julia Ferreira','+55 12 98881-0610','juliaprianti06@gmail.com','2005-10-06','2971f57754542415b908a53512f1f35ee3d6a0e23f93abdf9556110ddf540d52');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-15 21:29:29
