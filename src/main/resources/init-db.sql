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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-08  2:16:15

-- Populate the users table
INSERT INTO `users` (`username`, `email`, `full_name`, `password`) VALUES
                                                                       ('user1', 'user1@example.com', 'User One', 'password1'),
                                                                       ('user2', 'user2@example.com', 'User Two', 'password2');

-- Populate the categories table
INSERT INTO `categories` (`category_name`) VALUES
                                               ('Science'),
                                               ('Mathematics');

-- Populate the quizzes table (assuming 'username' refers to a user that creates the quiz)
INSERT INTO `quizzes` (`quiz_id`, `description`, `image_link`, `title`, `username`) VALUES
                                                                                        (1, 'A science quiz about general knowledge in biology.', NULL, 'Biology Basics', 'user1'),
                                                                                        (2, 'Math quiz covering basic algebra.', NULL, 'Algebra 101', 'user2');

-- Populate the categories_quizzes junction table to associate categories with quizzes
INSERT INTO `categories_quizzes` (`category_entity_category_name`, `quizzes_quiz_id`) VALUES
                                                                                          ('Science', 1),
                                                                                          ('Mathematics', 2);

-- Assuming there are more tables that require initial data,
-- similar INSERT statements can be added here following the same pattern.
