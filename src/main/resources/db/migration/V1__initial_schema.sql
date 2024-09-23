/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;



CREATE TABLE IF NOT EXISTS `authorities` (
                                             `id` varchar(255) NOT NULL,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKnb3atvjf9ov5d0egnuk47o5e` (`name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `roles` (
                                       `id` varchar(255) NOT NULL,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `role_authorities` (
                                                  `role_id` varchar(255) NOT NULL,
    `authority_id` varchar(255) NOT NULL,
    KEY `FKpmbd5d32ddnedap7prbfhwuy2` (`authority_id`),
    KEY `FKffl6vh23qrnrld2sxfhtjhbkm` (`role_id`),
    CONSTRAINT `FKffl6vh23qrnrld2sxfhtjhbkm` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
    CONSTRAINT `FKpmbd5d32ddnedap7prbfhwuy2` FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `users` (
                                       `id` varchar(255) NOT NULL,
    `account_non_expired` bit(1) DEFAULT NULL,
    `account_non_locked` bit(1) DEFAULT NULL,
    `activation_link` varchar(255) DEFAULT NULL,
    `birthday` date NOT NULL DEFAULT '2000-01-01',
    `credentials_non_expired` bit(1) DEFAULT NULL,
    `email` varchar(100) NOT NULL,
    `enabled` bit(1) DEFAULT NULL,
    `first_name` varchar(255) NOT NULL,
    `last_name` varchar(255) NOT NULL,
    `maximum_activation_link_time` datetime(6) DEFAULT NULL,
    `maximum_password_reset_time` datetime(6) DEFAULT NULL,
    `password` varchar(100) DEFAULT NULL,
    `phone_number` varchar(255) NOT NULL,
    `unique_string_for_password_reset` varchar(255) DEFAULT NULL,
    `username` varchar(50) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
    UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `user_roles` (
                                            `user_id` varchar(255) NOT NULL,
    `role_id` varchar(255) NOT NULL,
    KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
    KEY `FKhfh9dx7w3ubf1co1vdev94g3f` (`user_id`),
    CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
    CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
