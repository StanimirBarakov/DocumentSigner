-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: document_signer
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
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
 SET character_set_client = utf8mb4 ;
CREATE TABLE `documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `decision_type` varchar(45) NOT NULL,
  `user_id` int(11) NOT NULL,
  `signature` blob,
  `public_key` blob,
  `signed_type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `id_idx` (`user_id`),
  CONSTRAINT `id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (20,'ipc-task.pdf','APPROVED',8,_binary '6p\◊\«\r\‡ ÇM}\·áp\·ô¿w\Ê\”˙\ÏqZˆ(krm≤∑z≥\ÔUäC\∆\√ Õ§:Åâ`IS\'7\\ŒÄ!Zé~Cjív\≈|UÛ!|\ C\—\Ô¸\Ì\⁄ÿá,åMEC\ ˙ˆß!≤À°Øz&\÷Cƒâ¶å2õ#\Íf›ù°dˇ2ı\Á&^`SXâ6À∑ı',_binary '0Åü0\r	*ÜHÜ˜\r\0Åç\00ÅâÅÅ\0\Ô¿«ã,!∫ËØçß	P$£{V\'ÜG¯J\Íj\„ØÚy€±†è\È¨≈Å\‚Q&8\Î\”2<‰ô¨Ç}»Ée™\r+y\n\ÁY¡mÑ7\ÌjìSı\ﬂ%Ñ}\0b\“#\ÿﬁôf,y¯/Ù¡†`y\ﬁA\Ïïs_\0ø5\Œ\n=é•⁄§æ\ƒ~\…P`ÆÒØŸ≥\0','SIGNED'),(24,'b4527998e2d4f73d70a2e1405a9a9183.jpg','UNDECIDED',9,NULL,NULL,'UNSIGNED');
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(500) NOT NULL,
  `email` varchar(45) NOT NULL,
  `user_type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (5,'stavri','$2a$10$syhriRef3M6IMyX1ihQe2.0qXZauOenC2yFVqnYanQXQIdSziwLUG','stavri69@abv.bg',1),(6,'stuno','$2a$10$M9W10DstrKRMC8BaOvInO.PRwYATA8LNEzhqgBFIxBD1DPoB/X00a','stavri68@abv.bg',1),(7,'stuno97','$2a$10$uksfSep/555NwMRYhNqh1OzdYTYpyVqRZt7Jw6XREiuAKcGa7EgeG','stanimir@abv.bg',0),(8,'sasho','$2a$10$WIN9Kixkry2TAfeJ.2sDpuewH.S5Hao.Pxk9fln8/FmkN6vsNaNCq','asash1995@gmail.com',0),(9,'gosho','$2a$10$Wua.rrfJIe8.WpmgDZLyyeomN3ARYllKNt9rLw6Fy2mNaTQpub0W6','goshi@gmail.com',0);
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

-- Dump completed on 2019-07-15 13:33:48
