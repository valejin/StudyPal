-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: studypal
-- ------------------------------------------------------
-- Server version	8.3.0

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
-- Table structure for table `richieste`
--

DROP TABLE IF EXISTS `richieste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `richieste` (
  `idrichieste` int NOT NULL AUTO_INCREMENT,
  `emailTutor` varchar(45) NOT NULL,
  `emailStudente` varchar(45) NOT NULL,
  `materia` varchar(45) NOT NULL,
  `modLezione` tinyint DEFAULT NULL,
  `tariffa` int DEFAULT NULL,
  `giorni` varchar(100) DEFAULT NULL,
  `note` varchar(250) DEFAULT NULL,
  `stato` int NOT NULL DEFAULT '0',
  `recensione` int DEFAULT NULL,
  `nomeTutor` varchar(45) DEFAULT NULL,
  `cognomeTutor` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idrichieste`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `richieste`
--

LOCK TABLES `richieste` WRITE;
/*!40000 ALTER TABLE `richieste` DISABLE KEYS */;
INSERT INTO `richieste` VALUES (2,'eli@gmail.com','es','inglese',0,26,NULL,'Ciao! Vorrei prenotare ripetizioni di inglese per la domenica pomeriggio.',2,NULL,'Elisa','Marzioli'),(7,'elitutor@gmail.com','es','informatica',0,40,'','ciao voglio prendere ripetizioni perdomenica grazie',2,NULL,'Eli','Marzi'),(20,'viola@gmail.com','elisa@gmail.com','matematica',0,16,'','Buonasera, Viola.\nSarebbe disponibile per ripetizioni dik matematica il prossimo weekend?',1,NULL,NULL,NULL),(24,'elitutor@gmail.com','es','analisi',0,40,'','Ciao!!! mercoledì ci sei?',1,2,'Elisabetta','Marzioletta'),(28,'valetutor@gmail.com','elisa@gmail.com','Analisi',0,36,'','Ciao!',1,NULL,'Valentina','Jin'),(29,'valetutor@gmail.com','elisa@gmail.com','Analisi',0,36,NULL,'Salve, vorrei richiedere una ripetizione di analisi per martedì pomeriggio, dalle 13 alle 14.',1,NULL,'Valentina','Jin'),(31,'viola@gmail.com','elisa@gmail.com','latino',0,16,'','Ciao Viola, vorrei prenotare una ripetizione per martedì 11/06 alle 17',0,NULL,'Viola','Marzioli'),(79,'et','es','latino',0,28,'null','',1,NULL,'Elisa','Marzioli'),(81,'Angelo@gmail.com','es','ingegneria',0,26,'null','ciao :)',1,5,'Angelo','Romano'),(84,'et','es','latino',0,28,'','Giovedì prossimo va bene?',1,NULL,'Elisa','Marzioli'),(85,'et','es','latino',0,28,'null','giovedì pomeriggio alle 14:00',1,NULL,'Elisa','Marzioli'),(86,'et','es','latino',0,28,'','giovedì alle 15:00',1,NULL,'Elisa','Marzioli'),(87,'et','es','inglese',0,28,'null','hey',1,NULL,'Elisa','Marzioli'),(88,'et','es','latino',0,28,'null','prova',1,NULL,'Elisa','Marzioli'),(89,'et','es','inglese',0,28,'null','prova prova',1,NULL,'Elisa','Marzioli'),(90,'et','es','latino',0,28,'null','',1,NULL,'Elisa','Marzioli'),(91,'et','es','inglese',0,28,'null','1',1,NULL,'Elisa','Marzioli'),(92,'et','es','latino',0,28,'null','2',1,NULL,'Elisa','Marzioli'),(93,'et','es','inglese',0,28,'null','3',1,NULL,'Elisa','Marzioli'),(97,'et','es','inglese',0,28,'null','',1,NULL,'Elisa','Marzioli'),(98,'et','es','inglese',0,36,'null','khbjv',1,NULL,'Elisa','Marzioli'),(99,'et','es','inglese',0,36,'null','kbh.j-ò-ou',1,2,'Elisa','Marzioli'),(102,'valetutor@gmail.com','studente@mail.com','Analisi 1',1,36,'Mercoledì','Mercoledì alle 17, due ore',1,3,'Valentina','Jin'),(103,'valetutor@gmail.com','studente@mail.com','Analisi 1',0,36,'Mercoledì','dalle 17 alle 18',0,NULL,'Valentina','Jin'),(118,'eli@tutor.com','frank@gmail.com','Analisi 1',0,28,'Lunedì','mattina dalle 11 alle 12',1,NULL,'Elisa','Marzioli'),(121,'eli@tutor.com','ema@gmail.com','Inglese',0,28,'Mercoledì','mercoledì dalle 17 alle 18',1,NULL,'Elisa','Marzioli'),(123,'eli@tutor.com','vale@studente.com','Analisi 1',0,27,'Mercoledì','ciao',2,NULL,'Elisa','Marzioli'),(124,'eli@tutor.com','vale@studente.com','Analisi 1',0,28,'Mercoledì','17:00',0,NULL,'Elisa','Marzioli');
/*!40000 ALTER TABLE `richieste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutor`
--

DROP TABLE IF EXISTS `tutor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutor` (
  `email` varchar(45) NOT NULL,
  `tariffa` int DEFAULT NULL,
  `luogo` varchar(45) DEFAULT NULL,
  `materie` varchar(100) DEFAULT NULL,
  `inPresenza` tinyint DEFAULT NULL,
  `webCam` tinyint DEFAULT NULL,
  `giorni` varchar(100) DEFAULT NULL,
  `nome` varchar(45) DEFAULT NULL,
  `cognome` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`email`),
  CONSTRAINT `email` FOREIGN KEY (`email`) REFERENCES `utente` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutor`
--

LOCK TABLES `tutor` WRITE;
/*!40000 ALTER TABLE `tutor` DISABLE KEYS */;
INSERT INTO `tutor` VALUES ('aaa@tutor.com',NULL,NULL,NULL,NULL,NULL,NULL,'ELisa ','Marzioli'),('Angelo@gmail.com',26,'Roma','Ingegneria del software',1,1,'Lunedì, Mercoledì, Venerdì','Angelo','Romano'),('eli@gmail.com',31,'Roma','Inglese, Informatica',1,1,'Mercoledì, Sabato, Domenica','Elisa','Marzioli'),('eli@tutor.com',28,'Roma','Analisi 1, Inglese',1,1,'Lunedì, Martedì, Mercoledì, Venerdì, Sabato, Domenica','Elisa','Marzioli'),('testUser@gmail.com',29,'Milano','Analisi 1, Fisica 1',1,0,'Lunedì, Martedì','testUserNome','testUserCognome'),('testUser1721406652669@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,'testUser1721406652669','testUser1721406652669'),('valetutor@gmail.com',36,'Roma','Analisi 1, Ricerca Operativa',1,0,'Lunedì, Mercoledì, Venerdì','Valentina','Jin'),('viola@gmail.com',16,'Roma','Analisi 1, Matematica, latino',1,0,'Martedì, Mercoledì, Giovedì, Sabato','Viola','Marzioli');
/*!40000 ALTER TABLE `tutor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `email` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `isTutor` tinyint DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('a','sofia','tosti','ciao2002',0),('aaa@tutor.com','ELisa ','Marzioli','eli',1),('aliverniniangelica@gmail.com','Angelica','Alivernini','Angelicagmail592',0),('Angelo@gmail.com','Angelo','Romano','angelo123',1),('catia@gmail.com','Catia','Capriotti','catia',0),('eli@gmail.com','Elisa','Marzioli','tutor',1),('eli@studente.com','Elisa','Marzioli','elisa',0),('eli@tutor.com','Elisa','Marzioli','1111',1),('elisa.santonocito03@gmail.com','Elisa','Santonocito','Simbipooh24!',1),('elisa@gmail.com','Elisa','Marzioli','eli',0),('elistudente@gmail.com','Elisa','Marzioli','e',0),('ema@gmail.com','Ema','Nuela','ema',0),('es','elisa','marzioli','es',0),('frank@gmail.com','Frank','Legrottaglie','frank',0),('ila@studente.com','Ilaria','Giustozzi','1111',0),('leo@gmail.com','leo','man','l',0),('ludovica@gmail.com','llll','bbbb','caramelle',0),('maxmarzioli@gmail.com','Max','Marzioli','max',1),('sluca26@gmail.com','Luca','Santonocito','3416Bkt26',1),('studente@gmail.com','Elisa','Marzioli','s',0),('studente@mail.com','stud','stud','stud',0),('test@email.com','test','test','test',0),('testUser@gmail.com','testUserNome','testUserCognome','testUser',1),('testUser1721406652669@gmail.com','testUser1721406652669','testUser1721406652669','testUser1721406652669',1),('vale@studente.com','Valentina','Jin','valej',0),('valetutor@gmail.com','Valentina','Jin','vj',1),('viola@gmail.com','Viola','Marzioli','vio',1),('violamarzioli@gmail.com','viola','marzioli','ciaociaobravaeli',1);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-19 18:39:58
