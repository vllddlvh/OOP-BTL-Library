-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: library_2nd_edition
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documents` (
  `ID` varchar(255) NOT NULL,
  `Title` varchar(255) NOT NULL,
  `Author` varchar(255) NOT NULL,
  `Publisher` varchar(255) NOT NULL,
  `Publication_year` int NOT NULL,
  `Category` varchar(255) NOT NULL,
  `language` varchar(255) NOT NULL,
  `Summary` text NOT NULL,
  `File_image` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES ('1','Harry Potter and the Sorcerer\'s Stone','J.K. Rowling','Bloomsbury',1997,'Fantasy','English','The story of a young wizard, Harry Potter, as he begins his magical education.','hp1.jpg'),('2','To Kill a Mockingbird','Harper Lee','J.B. Lippincott',1960,'Fiction','English','A novel about racism and injustice in the deep South, told through the eyes of Scout Finch.','mockingbird.jpg'),('3','The Alchemist','Paulo Coelho','HarperOne',1988,'Adventure','Portuguese','A journey of a shepherd boy in search of his personal legend and treasures.','alchemist.jpg'),('4','Chí Phèo','Nam Cao','Tân Dân',1941,'Literature','Vietnamese','A tragic story of a poor villager who struggles against social injustices.','chipheo.jpg'),('5','Sapiens: A Brief History of Humankind','Yuval Noah Harari','Harper',2011,'History','English','A comprehensive history of human civilization from the Stone Age to the present.','sapiens.jpg');
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `ID` varchar(10) NOT NULL,
  `password` varchar(30) DEFAULT NULL,
  `Role` enum('Staff','Member') DEFAULT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `login_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('1','1','Member'),('1000','1000','Member'),('1001','1001','Member'),('1401','1401','Staff'),('2','2','Member'),('2302','2302','Member'),('23021','23021','Member'),('23021686','23021686','Member'),('23021750','23021750','Member'),('23021752','23021752','Member'),('3','3','Member'),('U001','U001','Staff'),('U002','U002','Staff'),('U003','U003','Member'),('U004','U004','Member'),('U005','U005','Member');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `ID` varchar(10) NOT NULL,
  `firstName` varchar(10) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `contact` varchar(50) NOT NULL,
  `dateOfBirth` date NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `member_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES ('1','Tran','Dan','Quangminh','2005-06-05'),('2','Bỏ','Mình','sadaa','2005-09-02'),('23021','long vu','long ','đa@gmail.com','2005-05-05'),('3','Bực','Mình','bm@mgiaasd','2005-09-02'),('U003','John','Doe','john.doe@example.com','1995-06-15'),('U004','Jane','Smith','jane.smith@example.com','1997-09-30'),('U005','Emily','Johnson','emily.johnson@example.com','1996-12-22');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request` (
  `requestID` varchar(15) NOT NULL,
  `userID` varchar(10) DEFAULT NULL,
  `documentID` varchar(255) DEFAULT NULL,
  `quantityBorrow` int DEFAULT NULL,
  `borrowDate` date NOT NULL,
  `returnDate` date DEFAULT NULL,
  PRIMARY KEY (`requestID`),
  KEY `userID` (`userID`),
  KEY `fk_request_documents` (`documentID`),
  CONSTRAINT `fk_request_documents` FOREIGN KEY (`documentID`) REFERENCES `documents` (`ID`) ON DELETE CASCADE,
  CONSTRAINT `request_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `ID` varchar(10) NOT NULL,
  `firstName` varchar(10) NOT NULL,
  `lastName` varchar(20) NOT NULL,
  `contact` varchar(50) NOT NULL,
  `jobTitle` varchar(20) DEFAULT NULL,
  `IntroducerID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IntroducerID` (`IntroducerID`),
  CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `staff_ibfk_2` FOREIGN KEY (`IntroducerID`) REFERENCES `staff` (`ID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES ('1401','Sonw','Ngx','NgxS@ops.com','Special Agent',NULL),('U001','Robert','Brown','robert.brown@example.com','Librarian',NULL),('U002','Alice','Davis','alice.davis@example.com','Assistant Librarian','U001');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `ID` varchar(10) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('1'),('1000'),('1001'),('1401'),('2'),('2302'),('23021'),('23021686'),('23021750'),('23021752'),('3'),('U001'),('U002'),('U003'),('U004'),('U005');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-26  1:19:05
