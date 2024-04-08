-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: localhost    Database: db-1
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
-- Table structure for table `attempt_id_seq`
--

DROP TABLE IF EXISTS `attempt_id_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attempt_id_seq` (
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attempt_id_seq`
--

LOCK TABLES `attempt_id_seq` WRITE;
/*!40000 ALTER TABLE `attempt_id_seq` DISABLE KEYS */;
INSERT INTO `attempt_id_seq` VALUES (101);
/*!40000 ALTER TABLE `attempt_id_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
                              `category_name` varchar(255) NOT NULL,
                              PRIMARY KEY (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES ('Art'),('Geography'),('History'),('Literature'),('Mathematics'),('Music'),('Pop Culture'),('Science'),('Sports'),('Technology');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories_quizzes`
--

DROP TABLE IF EXISTS `categories_quizzes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories_quizzes` (
                                      `category_entity_category_name` varchar(255) NOT NULL,
                                      `quizzes_quiz_id` bigint NOT NULL,
                                      PRIMARY KEY (`category_entity_category_name`,`quizzes_quiz_id`),
                                      KEY `FK88fyfmek1cu9f9sbng20ehwc8` (`quizzes_quiz_id`),
                                      CONSTRAINT `FK88fyfmek1cu9f9sbng20ehwc8` FOREIGN KEY (`quizzes_quiz_id`) REFERENCES `quizzes` (`quiz_id`),
                                      CONSTRAINT `FKjiwc0v1ydkbtasc3nhjveiai7` FOREIGN KEY (`category_entity_category_name`) REFERENCES `categories` (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories_quizzes`
--

LOCK TABLES `categories_quizzes` WRITE;
/*!40000 ALTER TABLE `categories_quizzes` DISABLE KEYS */;
/*!40000 ALTER TABLE `categories_quizzes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_id_seq`
--

DROP TABLE IF EXISTS `question_id_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_id_seq` (
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_id_seq`
--

LOCK TABLES `question_id_seq` WRITE;
/*!40000 ALTER TABLE `question_id_seq` DISABLE KEYS */;
INSERT INTO `question_id_seq` VALUES (151);
/*!40000 ALTER TABLE `question_id_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
                             `question_id` bigint NOT NULL,
                             `alternatives` longtext,
                             `image_link` varchar(255) DEFAULT NULL,
                             `label` varchar(255) DEFAULT NULL,
                             `position` smallint DEFAULT NULL,
                             `type` tinyint DEFAULT NULL,
                             `quiz_id` bigint DEFAULT NULL,
                             PRIMARY KEY (`question_id`),
                             KEY `FKn3gvco4b0kewxc0bywf1igfms` (`quiz_id`),
                             CONSTRAINT `FKn3gvco4b0kewxc0bywf1igfms` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`quiz_id`),
                             CONSTRAINT `questions_chk_1` CHECK ((`type` between 0 and 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'[QuestionAnswersDto(answer=Code!, isCorrect=true), QuestionAnswersDto(answer=Not Code :/, isCorrect=false)]',NULL,'What is the most fun thing to do?',NULL,NULL,NULL),(2,'[QuestionAnswersDto(answer=Trondheim, isCorrect=true), QuestionAnswersDto(answer=Oslo, isCorrect=false), QuestionAnswersDto(answer=Drammen, isCorrect=false)]',NULL,'Where is NTNU Gløshaugem?',NULL,NULL,NULL),(3,'[{\"answer\":\"Code!\",\"isCorrect\":true},{\"answer\":\"Not Code :/\",\"isCorrect\":false}]',NULL,'What is the most fun thing to do?',NULL,NULL,1),(4,'[{\"answer\":\"Trondheim\",\"isCorrect\":true},{\"answer\":\"Oslo\",\"isCorrect\":false},{\"answer\":\"Drammen\",\"isCorrect\":false}]',NULL,'Where is NTNU Gløshaugem?',NULL,NULL,1),(52,'[QuestionAnswersDto(answer=2, isCorrect=true), QuestionAnswersDto(answer=4, isCorrect=false), QuestionAnswersDto(answer=6, isCorrect=false)]',NULL,'1+1',NULL,NULL,NULL),(53,'[QuestionAnswersDto(answer=5, isCorrect=null), QuestionAnswersDto(answer=3, isCorrect=false), QuestionAnswersDto(answer=6, isCorrect=true)]',NULL,'3+3',NULL,NULL,NULL),(54,'[{\"answer\":\"2\",\"isCorrect\":true},{\"answer\":\"4\",\"isCorrect\":false},{\"answer\":\"6\",\"isCorrect\":false}]',NULL,'1+1',NULL,NULL,2),(55,'[{\"answer\":\"5\",\"isCorrect\":null},{\"answer\":\"3\",\"isCorrect\":false},{\"answer\":\"6\",\"isCorrect\":true}]',NULL,'3+3',NULL,NULL,2);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_attempt_entity`
--

DROP TABLE IF EXISTS `quiz_attempt_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_attempt_entity` (
                                       `attempt_id` bigint NOT NULL,
                                       `score` int DEFAULT NULL,
                                       `time_used` int DEFAULT NULL,
                                       `quiz_quiz_id` bigint DEFAULT NULL,
                                       `user_username` varchar(255) DEFAULT NULL,
                                       PRIMARY KEY (`attempt_id`),
                                       KEY `FKe5nnxlr2syyu98ojs5ucje3o3` (`quiz_quiz_id`),
                                       KEY `FK8m607c8gipi8c51k6rk1xrind` (`user_username`),
                                       CONSTRAINT `FK8m607c8gipi8c51k6rk1xrind` FOREIGN KEY (`user_username`) REFERENCES `users` (`username`),
                                       CONSTRAINT `FKe5nnxlr2syyu98ojs5ucje3o3` FOREIGN KEY (`quiz_quiz_id`) REFERENCES `quizzes` (`quiz_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_attempt_entity`
--

LOCK TABLES `quiz_attempt_entity` WRITE;
/*!40000 ALTER TABLE `quiz_attempt_entity` DISABLE KEYS */;
INSERT INTO `quiz_attempt_entity` VALUES (1,2,NULL,1,'henrik'),(2,2,NULL,2,'henrik');
/*!40000 ALTER TABLE `quiz_attempt_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quiz_id_seq`
--

DROP TABLE IF EXISTS `quiz_id_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_id_seq` (
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quiz_id_seq`
--

LOCK TABLES `quiz_id_seq` WRITE;
/*!40000 ALTER TABLE `quiz_id_seq` DISABLE KEYS */;
INSERT INTO `quiz_id_seq` VALUES (101);
/*!40000 ALTER TABLE `quiz_id_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizzes`
--

DROP TABLE IF EXISTS `quizzes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizzes` (
                           `quiz_id` bigint NOT NULL,
                           `description` varchar(255) DEFAULT NULL,
                           `image_link` varchar(255) DEFAULT NULL,
                           `title` varchar(255) DEFAULT NULL,
                           `username` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`quiz_id`),
                           KEY `FK341beywgyl39plgar95elvq04` (`username`),
                           CONSTRAINT `FK341beywgyl39plgar95elvq04` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizzes`
--

LOCK TABLES `quizzes` WRITE;
/*!40000 ALTER TABLE `quizzes` DISABLE KEYS */;
INSERT INTO `quizzes` VALUES (1,'Fun quiz!','https://assets.teenvogue.com/photos/630e3461281b8485f604d293/16:9/w_2560%2Cc_limit/GettyImages-1083856216.jpg','Fun Quiz!','henrik'),(2,'Math Quiz','https://miro.medium.com/v2/resize:fit:1400/1*L76A5gL6176UbMgn7q4Ybg.jpeg','Math Quiz','frikk');
/*!40000 ALTER TABLE `quizzes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizzes_categories`
--

DROP TABLE IF EXISTS `quizzes_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizzes_categories` (
                                      `quiz_entity_quiz_id` bigint NOT NULL,
                                      `categories_category_name` varchar(255) NOT NULL,
                                      PRIMARY KEY (`quiz_entity_quiz_id`,`categories_category_name`),
                                      KEY `FKcrshdrgulipwsh52mqhdude11` (`categories_category_name`),
                                      CONSTRAINT `FK24r8rc7xsb5b3wqs5h7uhbxfn` FOREIGN KEY (`quiz_entity_quiz_id`) REFERENCES `quizzes` (`quiz_id`),
                                      CONSTRAINT `FKcrshdrgulipwsh52mqhdude11` FOREIGN KEY (`categories_category_name`) REFERENCES `categories` (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizzes_categories`
--

LOCK TABLES `quizzes_categories` WRITE;
/*!40000 ALTER TABLE `quizzes_categories` DISABLE KEYS */;
/*!40000 ALTER TABLE `quizzes_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizzes_collaborators`
--

DROP TABLE IF EXISTS `quizzes_collaborators`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizzes_collaborators` (
                                         `quiz_entity_quiz_id` bigint NOT NULL,
                                         `collaborators_username` varchar(255) NOT NULL,
                                         KEY `FKnnp0fh0v473r09cf0cmmjhgbj` (`collaborators_username`),
                                         KEY `FKc0gdhghyyu18bnc6txlsb1xa1` (`quiz_entity_quiz_id`),
                                         CONSTRAINT `FKc0gdhghyyu18bnc6txlsb1xa1` FOREIGN KEY (`quiz_entity_quiz_id`) REFERENCES `quizzes` (`quiz_id`),
                                         CONSTRAINT `FKnnp0fh0v473r09cf0cmmjhgbj` FOREIGN KEY (`collaborators_username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizzes_collaborators`
--

LOCK TABLES `quizzes_collaborators` WRITE;
/*!40000 ALTER TABLE `quizzes_collaborators` DISABLE KEYS */;
INSERT INTO `quizzes_collaborators` VALUES (2,'henrik');
/*!40000 ALTER TABLE `quizzes_collaborators` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `username` varchar(255) NOT NULL,
                         `email` varchar(255) DEFAULT NULL,
                         `full_name` varchar(255) DEFAULT NULL,
                         `password` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('frikk','frikk@mail.com','frikk','$2a$10$1AHRmjNe3bWU2MV3jQrdeuZm2MAQZprwdlN4TLGINBCYW32oM.tA2'),('henrik','henrik@mail.com','Henrik','$2a$10$esrqiQJgpGGdPXRGr2sspOLtzM2YYAWgV/UNoGTDcUe/HmTwkV90u');
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

-- Dump completed on 2024-04-08  4:30:35
