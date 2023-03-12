-- --------------------------------------------------------
-- 호스트:                          i8A810.p.ssafy.io
-- 서버 버전:                        10.10.2-MariaDB-1:10.10.2+maria~ubu2204 - mariadb.org binary distribution
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- bundlerdev 데이터베이스 구조 내보내기
DROP DATABASE IF EXISTS `mydata`;
CREATE DATABASE IF NOT EXISTS `mydata` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `mydata`;
-- 테이블 bundlerdev.Area 구조 내보내기
DROP TABLE IF EXISTS `Area`;
CREATE TABLE IF NOT EXISTS `Area` (
  `userId` bigint(20) NOT NULL,
  `job` varchar(255) DEFAULT NULL,
  `skill` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.BUNDLES 구조 내보내기
DROP TABLE IF EXISTS `BUNDLES`;
CREATE TABLE IF NOT EXISTS `BUNDLES` (
  `bundle_id` bigint(20) NOT NULL,
  `bundle_thumbnail` varchar(255) DEFAULT NULL,
  `bundle_thumbnail_text` varchar(255) DEFAULT NULL,
  `is_bundle_public` bit(1) DEFAULT NULL,
  `is_bundle_default` bit(1) DEFAULT NULL,
  `is_bundle_private` bit(1) DEFAULT NULL,
  `bundle_thumbnail_file_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bundle_id`),
  CONSTRAINT `FKj1jf4qwree7timny23qswmc48` FOREIGN KEY (`bundle_id`) REFERENCES `FEEDS` (`feed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.CARDS 구조 내보내기
DROP TABLE IF EXISTS `CARDS`;
CREATE TABLE IF NOT EXISTS `CARDS` (
  `card_commentary` varchar(255) DEFAULT NULL,
  `card_description` varchar(255) DEFAULT NULL,
  `card_id` bigint(20) NOT NULL,
  `card_scrap_cnt` int(11) DEFAULT NULL,
  `card_type` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`card_id`),
  KEY `FKfvp1ebowuparc3320yo7khgbh` (`category_id`),
  CONSTRAINT `FK1syrlhua1q72umkw6kg63f8sj` FOREIGN KEY (`card_id`) REFERENCES `FEEDS` (`feed_id`),
  CONSTRAINT `FKfvp1ebowuparc3320yo7khgbh` FOREIGN KEY (`category_id`) REFERENCES `CATEGORIES` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.CARD_BUNDLE 구조 내보내기
DROP TABLE IF EXISTS `CARD_BUNDLE`;
CREATE TABLE IF NOT EXISTS `CARD_BUNDLE` (
  `card_bundle_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bundle_id` bigint(20) DEFAULT NULL,
  `card_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`card_bundle_id`),
  UNIQUE KEY `UKho092nel24059ips13kpf59tw` (`bundle_id`,`card_id`),
  KEY `FKl026emcgxjah52u30qvjor2p2` (`card_id`),
  CONSTRAINT `FKl026emcgxjah52u30qvjor2p2` FOREIGN KEY (`card_id`) REFERENCES `CARDS` (`card_id`),
  CONSTRAINT `FKthwidjjkbs1u4ab48bjweg2ya` FOREIGN KEY (`bundle_id`) REFERENCES `BUNDLES` (`bundle_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.CATEGORIES 구조 내보내기
DROP TABLE IF EXISTS `CATEGORIES`;
CREATE TABLE IF NOT EXISTS `CATEGORIES` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  `category_parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`category_id`),
  KEY `FK1bxaocr2cptl8kp6oef9q0xyo` (`category_parent_id`),
  CONSTRAINT `FK1bxaocr2cptl8kp6oef9q0xyo` FOREIGN KEY (`category_parent_id`) REFERENCES `CATEGORIES` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.COMMENTS 구조 내보내기
DROP TABLE IF EXISTS `COMMENTS`;
CREATE TABLE IF NOT EXISTS `COMMENTS` (
  `comment_id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `comment_content` varchar(255) DEFAULT NULL,
  `feed_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  UNIQUE KEY `UK1ew5bmd32qitf2aujmmoixc00` (`feed_id`,`user_id`),
  KEY `FKrovr02lwqcjrqv4344m0do99s` (`user_id`),
  CONSTRAINT `FKqlvod0uujw6995khtp9c75rus` FOREIGN KEY (`feed_id`) REFERENCES `FEEDS` (`feed_id`),
  CONSTRAINT `FKrovr02lwqcjrqv4344m0do99s` FOREIGN KEY (`user_id`) REFERENCES `USERS` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.FEEDS 구조 내보내기
DROP TABLE IF EXISTS `FEEDS`;
CREATE TABLE IF NOT EXISTS `FEEDS` (
  `feed_type` varchar(31) NOT NULL,
  `feed_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `feed_comment_cnt` int(11) DEFAULT NULL,
  `feed_content` varchar(255) DEFAULT NULL,
  `feed_like_cnt` int(11) DEFAULT NULL,
  `feed_title` varchar(255) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`feed_id`),
  KEY `FK43ap4wc5wymd4tlcv0t6s9r62` (`user_id`),
  CONSTRAINT `FK43ap4wc5wymd4tlcv0t6s9r62` FOREIGN KEY (`user_id`) REFERENCES `USERS` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.FEED_CATEGORY 구조 내보내기
DROP TABLE IF EXISTS `FEED_CATEGORY`;
CREATE TABLE IF NOT EXISTS `FEED_CATEGORY` (
  `feed_category_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `feed_id` bigint(20) DEFAULT NULL,
  `target_category_id` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`feed_category_id`),
  UNIQUE KEY `UKeut8d8m2l1pp57ltipspl24y4` (`feed_id`,`category_id`),
  KEY `FK5kpvfrycj5rsq1b91sfyyq2k1` (`category_id`),
  CONSTRAINT `FK5kpvfrycj5rsq1b91sfyyq2k1` FOREIGN KEY (`category_id`) REFERENCES `CATEGORIES` (`category_id`),
  CONSTRAINT `FKm3rhebl1e914rsijmjtixcons` FOREIGN KEY (`feed_id`) REFERENCES `FEEDS` (`feed_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.FEED_LIKE 구조 내보내기
DROP TABLE IF EXISTS `FEED_LIKE`;
CREATE TABLE IF NOT EXISTS `FEED_LIKE` (
  `feed_like_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `feed_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`feed_like_id`),
  UNIQUE KEY `UKaysuc5uncaryrq8fb3kifuma6` (`feed_id`,`user_id`),
  KEY `FK89lyx90wyhcwhmyo8mjjs8ljj` (`user_id`),
  CONSTRAINT `FK89lyx90wyhcwhmyo8mjjs8ljj` FOREIGN KEY (`user_id`) REFERENCES `USERS` (`user_id`),
  CONSTRAINT `FKsig4djp0tqev83t6tnr9m9qa7` FOREIGN KEY (`feed_id`) REFERENCES `FEEDS` (`feed_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.FOLLOWS 구조 내보내기
DROP TABLE IF EXISTS `FOLLOWS`;
CREATE TABLE IF NOT EXISTS `FOLLOWS` (
  `follow_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `follow_from_id` bigint(20) DEFAULT NULL,
  `follow_to_id` bigint(20) DEFAULT NULL,
  `followBackId` int(11) DEFAULT NULL,
  PRIMARY KEY (`follow_id`),
  UNIQUE KEY `UKk5lvimqclgn2x93miv9x31ij9` (`follow_to_id`,`follow_from_id`),
  KEY `FKrsjsui5fvc9tlnff758afvo2b` (`follow_from_id`),
  CONSTRAINT `FKh2et1us138euj9ib2ak48maad` FOREIGN KEY (`follow_to_id`) REFERENCES `USERS` (`user_id`),
  CONSTRAINT `FKrsjsui5fvc9tlnff758afvo2b` FOREIGN KEY (`follow_from_id`) REFERENCES `USERS` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.LINKS 구조 내보내기
DROP TABLE IF EXISTS `LINKS`;
CREATE TABLE IF NOT EXISTS `LINKS` (
  `link_description` varchar(255) DEFAULT NULL,
  `link_id` bigint(20) NOT NULL,
  `link_image` varchar(255) DEFAULT NULL,
  `link_title` varchar(255) DEFAULT NULL,
  `link_url` varchar(255) NOT NULL,
  PRIMARY KEY (`link_id`),
  CONSTRAINT `FKldeo3tl7elyr8vnhbuhr7oiu8` FOREIGN KEY (`link_id`) REFERENCES `CARDS` (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.USERS 구조 내보내기
DROP TABLE IF EXISTS `USERS`;
CREATE TABLE IF NOT EXISTS `USERS` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `follower_cnt` int(11) DEFAULT NULL,
  `following_cnt` int(11) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT b'0',
  `user_email` varchar(255) NOT NULL,
  `user_introduction` varchar(255) DEFAULT NULL,
  `user_nickname` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_profile_image` varchar(255) DEFAULT NULL,
  `user_role` varchar(255) DEFAULT NULL,
  `github_url` varchar(255) DEFAULT NULL,
  `provider_email` varchar(255) DEFAULT NULL,
  `provider_id` varchar(255) DEFAULT NULL,
  `provider_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_8606yjkhicwxopqg5vditgr71` (`user_email`),
  UNIQUE KEY `UK_lk7pqotfq7t8tkm98dw3d259` (`user_nickname`),
  UNIQUE KEY `UKtldrioqws7dwdl022jof3l6fk` (`provider_type`,`provider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.USER_CARD_HIT 구조 내보내기
DROP TABLE IF EXISTS `USER_CARD_HIT`;
CREATE TABLE IF NOT EXISTS `USER_CARD_HIT` (
  `user_card_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_submit` varchar(255) DEFAULT NULL,
  `card_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_card_id`),
  UNIQUE KEY `UK6793fkfsclloa7s6t7alcqtu1` (`user_id`,`card_id`),
  KEY `FKpys9gtdfilw2i13plkcema4rt` (`card_id`),
  CONSTRAINT `FKcv0u1i8kmx4dchxcwluwi3j1v` FOREIGN KEY (`user_id`) REFERENCES `USERS` (`user_id`),
  CONSTRAINT `FKpys9gtdfilw2i13plkcema4rt` FOREIGN KEY (`card_id`) REFERENCES `CARDS` (`card_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 bundlerdev.USER_REFRESH_TOKEN 구조 내보내기
DROP TABLE IF EXISTS `USER_REFRESH_TOKEN`;
CREATE TABLE IF NOT EXISTS `USER_REFRESH_TOKEN` (
  `refresh_token_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `REFRESH_TOKEN` varchar(256) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`refresh_token_id`),
  KEY `FKs6gx8nvtn7fggutqnnbqq4wbd` (`user_id`),
  CONSTRAINT `FKs6gx8nvtn7fggutqnnbqq4wbd` FOREIGN KEY (`user_id`) REFERENCES `USERS` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (1, '알고리즘', NULL);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (2, 'CS', NULL);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (3, '직무', NULL);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (4, '언어', NULL);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (5, '기타', NULL);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (6, '그래프탐색(DFS/BFS/MST..) / 트리 / 힙', 1);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (7, '스택 / 큐 / 정렬', 1);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (8, '완전 탐색 / 이분 탐색', 1);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (9, '탐욕 / 동적 계획법 / 해시', 1);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (10, '기타', 1);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (11, '수학', 2);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (12, '컴퓨터 구조', 2);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (13, '운영체제', 2);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (14, '자료구조', 2);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (15, '네트워크', 2);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (16, '데이터베이스', 2);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (17, '기타', 2);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (18, '면접', 3);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (19, '백엔드', 3);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (20, '프론트엔드', 3);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (21, '인프라', 3);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (22, 'DBA', 3);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (23, '기타', 3);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (24, 'C / C++', 4);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (25, 'Python', 4);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (26, 'JAVA', 4);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (27, 'Kotlin', 4);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (28, 'JS / HTML / CSS', 4);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (29, 'SQL', 4);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (30, '기타', 4);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (31, '기업 분석', 5);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (32, 'IT기사 / 트렌드', 5);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (33, '자격증', 5);
INSERT INTO `CATEGORIES` (`category_id`, `category_name`, `category_parent_id`) VALUES (34, '기타', 5);

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
