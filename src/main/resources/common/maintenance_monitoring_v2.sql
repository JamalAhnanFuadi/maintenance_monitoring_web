-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 203.194.114.182    Database: maintenance_monitoring_v2
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audit_authentication`
--

DROP TABLE IF EXISTS `audit_authentication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_authentication` (
  `uid` varchar(50) NOT NULL,
  `tracking_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user` varchar(255) NOT NULL,
  `event` varchar(50) NOT NULL,
  `application` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `result` tinyint NOT NULL,
  `message` text NOT NULL,
  `created_dt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_authentication`
--

LOCK TABLES `audit_authentication` WRITE;
/*!40000 ALTER TABLE `audit_authentication` DISABLE KEYS */;
INSERT INTO `audit_authentication` VALUES ('00b0b1e9-518e-11f0-82b5-00163e624462','f32332d5-0157-4958-8a51-a80bbb25478a','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 06:31:17'),('00face60-53c1-11f0-82b5-00163e624462','117fe840-07d7-4010-ac1d-2ac91834ca7c','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-28 01:41:24'),('01176147-5023-11f0-82b5-00163e624462','e1b1325d-c1e2-44f5-b2e4-0e93d177c1d7','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-23 11:12:52'),('04d5fee1-31a1-11f0-9d19-00163e624462','1a72cf62-e204-431e-a352-7ab814928df0','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-15 15:26:48'),('04ed5e92-fe9e-11ef-81ca-00163e624462','171bbb42-ad05-47f5-88a6-04636f39e31b','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 17:26:50'),('055d5e65-51be-11f0-82b5-00163e624462','4ee2f1c3-e8f3-4d2e-98bc-4414b1094e60','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 12:15:00'),('0a5e2f43-51f4-11f0-82b5-00163e624462','1e2059e9-917f-4411-9b12-263c19bc3b4c','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 18:41:41'),('0c7c7a28-4d30-11f0-82b5-00163e624462','7bf3811e-f8fc-4b70-a608-37b84f1d8476','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-19 17:08:40'),('0ce3ff08-5194-11f0-82b5-00163e624462','7f7658f6-0545-4499-8fc8-1f78e0e05d6c','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 07:14:34'),('107d9bd6-5191-11f0-82b5-00163e624462','cde51563-bd00-46bf-8b91-69bb8721a997','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 06:53:11'),('108d0f25-5397-11f0-82b5-00163e624462','4b497482-1a38-4d4f-883c-cdf18bb2b43d','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-27 20:41:11'),('16c714d6-5026-11f0-82b5-00163e624462','60c8e086-4553-4d81-b176-c4ae84c3d7b2','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-23 11:34:57'),('178e902d-5199-11f0-82b5-00163e624462','650503af-4d7b-4f21-beb8-d876581807ba','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 07:50:40'),('1c021463-0205-11f0-b645-00163e624462','20168f97-38d6-41bc-9a1f-451159d3e5fc','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-16 01:22:20'),('1cc8c757-002b-11f0-950c-00163e624462','43ae08cf-a65e-4fcc-965c-ffc150266831','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-13 16:49:21'),('207f6ec1-4d31-11f0-82b5-00163e624462','9e79c9da-c834-4d69-94fd-a644a68eaa64','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-19 17:16:23'),('259c5425-3693-11f0-9d19-00163e624462','d88d2938-88ae-48f1-ac4e-90398e5d285f','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-21 22:30:04'),('25a6339d-4d23-11f0-82b5-00163e624462','1cc7c463-d5d3-481a-befd-217f2ec0a8f4','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-19 15:36:17'),('27b7372a-31a9-11f0-9d19-00163e624462','2ba44644-89e1-4cb9-94d9-1410731418de','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-15 16:25:02'),('2b894a3b-51ab-11f0-82b5-00163e624462','b061cda8-1854-49e7-a416-9711917a560b','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 10:00:04'),('30591634-51b2-11f0-82b5-00163e624462','49447e8b-23b1-4ce4-a222-88931253ceaa','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 10:50:18'),('31236ff5-fe9a-11ef-afcc-00163e624462','37ce7699-655f-493f-a282-ba77b057aaaa','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 16:59:27'),('31b13b21-0205-11f0-b645-00163e624462','20168f97-38d6-41bc-9a1f-451159d3e5fc','root@mail.com','Logout','Maintenance Monitoring Application',1,'','2025-03-16 01:22:58'),('3282c82b-fe9a-11ef-afcc-00163e624462','37ce7699-655f-493f-a282-ba77b057aaaa','root@mail.com','Logout','Maintenance Monitoring Application',1,'','2025-03-11 16:59:29'),('3451987c-319d-11f0-9d19-00163e624462','d2120db5-78d1-4232-8f4b-2aa5ee99c456','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-15 14:59:29'),('34692b85-0205-11f0-b645-00163e624462','c248b079-e409-4937-913e-56e191473c3e','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-16 01:23:02'),('3646db93-53a6-11f0-82b5-00163e624462','aed5e7df-63ac-4dea-b4db-8f2c9e23c664','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-27 22:29:36'),('38290e4c-51d2-11f0-82b5-00163e624462','9938321a-cb9e-4aab-a3a7-0acf7e541c3e','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 14:39:36'),('3f19f5b0-fea9-11ef-a044-00163e624462','eda03101-0627-4205-95cd-ad46a4d08572','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 18:47:12'),('40589cc7-0203-11f0-9068-00163e624462','26e1b47c-a19b-4546-9796-0634a895a6f4','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-16 01:09:02'),('4106f966-5023-11f0-82b5-00163e624462','47de4832-70a3-45d6-9277-97715c7c8be0','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-23 11:14:40'),('42d72bef-0204-11f0-8764-00163e624462','26e1b47c-a19b-4546-9796-0634a895a6f4','root@mail.com','Logout','Maintenance Monitoring Application',1,'','2025-03-16 01:16:17'),('449fe7d5-01fc-11f0-ba50-00163e624462','e97c32f1-eb87-4e82-bf9e-ec31ce1e04c9','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-16 00:19:03'),('471b2b9d-0204-11f0-8764-00163e624462','6b1619dd-402a-48b6-a580-1f265b17ad0c','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',0,'Login access is not allowed. Please contact administrator for assistance','2025-03-16 01:16:24'),('49d1932d-503f-11f0-82b5-00163e624462','50586d75-cb97-4602-8501-5732eb033ce9','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-23 14:35:20'),('4c2d9c4f-0204-11f0-8764-00163e624462','3c98e3df-3ab1-4bc5-abb6-06c66ab2af1e','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',0,'Login access is not allowed. Please contact administrator for assistance','2025-03-16 01:16:32'),('4c4b1626-369a-11f0-9d19-00163e624462','7aa8a02a-28d5-459e-8f0a-ed073bc4ce8e','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-21 23:21:16'),('4ceeedf3-0205-11f0-b645-00163e624462','c248b079-e409-4937-913e-56e191473c3e','bachtiar.madya.p@gmail.com','Logout','Maintenance Monitoring Application',1,'','2025-03-16 01:23:43'),('4ffd56ec-51f4-11f0-82b5-00163e624462','2e8c8a2b-7abe-4795-9d8b-69c0fd4fe41d','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 18:43:40'),('50ace4ea-0205-11f0-b645-00163e624462','89f1382c-5663-4532-b80e-a2f76447b8e6','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-16 01:23:49'),('50b24bbb-51bb-11f0-82b5-00163e624462','e852749b-c6e9-4366-bf00-ba0e0ded8806','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 11:55:38'),('52d92e35-01ed-11f0-86dc-00163e624462','27c29fad-3cb8-4b54-8411-aa3de8a6ae31','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-15 22:32:05'),('54a30166-31a6-11f0-9d19-00163e624462','df4801ba-83e8-49f5-ab43-391a3f97d1c2','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-15 16:04:49'),('57f455f1-3694-11f0-9d19-00163e624462','6de89a65-4f5b-427d-808e-0232682e1556','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-21 22:38:38'),('598e0e7c-6703-11f0-82b5-00163e624462','c41d9db5-18ec-45be-911d-197c87211427','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-07-22 13:54:12'),('5b7f4709-51d2-11f0-82b5-00163e624462','303a4232-b822-4b09-9d21-d616afa64c8a','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 14:40:35'),('5b912290-518f-11f0-82b5-00163e624462','ba279b00-4985-4df6-ab09-6799978eaf42','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 06:40:58'),('624e4fea-53c2-11f0-82b5-00163e624462','5dbd712b-6c6b-4a03-9d2e-7914230bd51c','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-28 01:51:16'),('62724f7f-51d3-11f0-82b5-00163e624462','6007bc03-6403-4c76-8dc3-b5872dd59148','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 14:47:57'),('6287b357-0047-11f0-a1d6-00163e624462','91120f20-46b8-4e26-b999-63b796e2bc6d','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-13 20:11:44'),('63bb4e21-fe9e-11ef-81ca-00163e624462','fb29cd77-97e2-4490-b88f-c485dff297f0','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 17:29:29'),('63fdc4b9-0209-11f0-adbe-00163e624462','81a0ec95-caae-41fc-bd78-209b941f8a91','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-16 01:52:59'),('6611783e-31af-11f0-9d19-00163e624462','c0a56fd4-571c-401c-adb7-6948080718e8','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-15 17:09:44'),('67dd0b38-1908-11f0-af2a-00163e624462','dcf16395-e1e2-4e31-b06b-afb3ec3be9e9','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-04-14 08:13:52'),('68274c17-51a7-11f0-82b5-00163e624462','a5196ca2-1f33-40af-bf30-e2891942b36c','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 09:33:08'),('6b955205-535b-11f0-82b5-00163e624462','63bba0b6-b5c9-46b9-ba29-3cc580109882','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-27 13:34:20'),('6f19f6ed-01f4-11f0-a232-00163e624462','56fa4370-971e-43d4-ae98-8ecb92b339ad','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-15 23:22:59'),('71310b6b-3693-11f0-9d19-00163e624462','eaa9effe-0229-4707-a8df-931433575844','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-21 22:32:11'),('7221140c-5025-11f0-82b5-00163e624462','2fc00465-d2be-431a-b93c-ef6185f143ec','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-23 11:30:20'),('734982c8-4d2f-11f0-82b5-00163e624462','787885f5-c2ae-40c6-9961-e5879bc6ded6','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-19 17:04:23'),('76d6f84a-fe9c-11ef-ae8f-00163e624462','ece10125-d151-4a0b-b881-56ad38daff37','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 17:15:42'),('772d5489-31a6-11f0-9d19-00163e624462','7f9545d4-5a77-4145-8ce7-16576d663a5f','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-15 16:05:47'),('782f574a-5191-11f0-82b5-00163e624462','29ce2e59-bd4b-4ec0-bf42-7de822778412','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 06:56:06'),('7838319d-4d2c-11f0-82b5-00163e624462','ca1770b5-46b3-428c-be82-8a5cde371a32','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-19 16:43:03'),('7dbe3d67-5398-11f0-82b5-00163e624462','0f9e324d-6417-4245-8679-e0a456d58561','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-27 20:51:24'),('7fe33524-51a8-11f0-82b5-00163e624462','43cfa6f0-a12e-466c-9060-1cba595603ca','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 09:40:57'),('8213bffc-0044-11f0-9d40-00163e624462','29c751f8-6d7e-4fdc-b036-66da91780be8','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-13 19:51:08'),('83c20aa2-01fb-11f0-b06e-00163e624462','4ca53da0-2c92-489f-b4af-754d61039a34','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-16 00:13:40'),('84a537f2-51e1-11f0-82b5-00163e624462','1e10c0a2-a9c3-4dae-83e3-c96900ef9f61','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 16:29:08'),('85f1f654-febd-11ef-a2bb-00163e624462','f3fb55e1-71e6-4984-8a5e-fdf9f74d6198','root@mail.com','Authentication','Maintenance Monitoring Application',0,'Invalid username or password','2025-03-11 21:12:21'),('88c7bd70-febe-11ef-835e-00163e624462','986476f0-da5b-402f-bd42-f3cecae1c1f0','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 21:19:35'),('8b5bb2b7-53ba-11f0-82b5-00163e624462','40614ed0-8d16-4824-828c-364cb7d9b11a','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-28 00:55:09'),('8bbb249f-51e2-11f0-82b5-00163e624462','0b17a221-862c-4cbd-91c7-e34341d38d26','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 16:36:28'),('8bfe40ed-febd-11ef-a2bb-00163e624462','3670d560-0696-49df-bcc8-18bae8f68173','root@mail.com','Authentication','Maintenance Monitoring Application',0,'Invalid username or password','2025-03-11 21:12:31'),('8e0d853b-51b0-11f0-82b5-00163e624462','ef50f1af-1f25-4b52-96f8-c6418e089725','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 10:38:37'),('8e323373-51b3-11f0-82b5-00163e624462','0dc6548b-7fed-481b-b60f-b2d26ebfc25d','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 11:00:05'),('905570ba-51fe-11f0-82b5-00163e624462','b5137f59-d044-498c-b3bd-34dd70f977b6','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 19:56:59'),('90ddc610-fe99-11ef-9886-00163e624462','30ae2aae-1bfd-47a1-96f8-638679323744','root@mail.com','Authentication','Maintenance Monitoring Application',0,'Invalid username or password','2025-03-11 16:54:54'),('92d4ae3f-4d21-11f0-82b5-00163e624462','4a8232da-c930-457d-be65-425350bc841f','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-19 15:25:03'),('92ea8f81-febd-11ef-a2bb-00163e624462','bfd2ecf5-e076-4f0f-9e07-1d1b1319a219','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 21:12:43'),('98b1d955-51f3-11f0-82b5-00163e624462','daff7605-e5c1-42b4-815b-597dbfadcd6c','fuadi.jamal@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 18:38:30'),('98fbdf35-51a2-11f0-82b5-00163e624462','4422356d-f4ea-456c-9e8a-7df6e8e76c70','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 08:58:42'),('9a1cf381-518d-11f0-82b5-00163e624462','18feeff5-6fd5-43f4-9810-e21fa0fe715f','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 06:28:24'),('9ba0c776-5391-11f0-82b5-00163e624462','3f680b46-40e3-4444-8438-7f109090dea6','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-27 20:02:08'),('9bc52b1e-5023-11f0-82b5-00163e624462','45dec968-ea4e-46be-8875-3d060c7fb656','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-23 11:17:11'),('a56b5715-feba-11ef-9d45-00163e624462','7176ad37-fe84-468e-93d7-d783e07c3f99','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 20:51:45'),('a6120fc0-5193-11f0-82b5-00163e624462','94621e4c-eec4-4208-927a-1452cd2a3b24','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 07:11:42'),('a8735e59-518f-11f0-82b5-00163e624462','7108c1dc-9485-43e1-877a-f22d72e6924d','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 06:43:07'),('aa739cb3-51c8-11f0-82b5-00163e624462','c32e7357-cf79-4ee7-8965-27eb63aea3e6','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 13:31:09'),('ab01c8df-0282-11f0-8b41-00163e624462','88d61222-43bf-41c5-86c6-3557dfb2ba20','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-16 16:21:07'),('abafa7a0-51ab-11f0-82b5-00163e624462','fab5c0a4-f299-4cfb-b9dc-bfb7abb8f599','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 10:03:39'),('ac20258c-feae-11ef-876c-00163e624462','be06ef76-79d4-4002-828d-a06ead4a50d2','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 19:26:02'),('ac7d2a50-1908-11f0-af2a-00163e624462','dcf16395-e1e2-4e31-b06b-afb3ec3be9e9','root@mail.com','Logout','Maintenance Monitoring Application',1,'','2025-04-14 08:15:49'),('adaaefdb-368c-11f0-9d19-00163e624462','8ed38b6a-372f-4e6e-a2d0-9cc8e613d256','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-21 21:43:46'),('af0635d9-5226-11f0-82b5-00163e624462','92a9d735-35cd-430a-995d-37bf70d3e48e','fuadi.jamal@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-26 00:44:14'),('b13e128f-51a8-11f0-82b5-00163e624462','7c521362-626d-4ef6-9842-504af4fcf56e','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 09:42:20'),('b388509a-5190-11f0-82b5-00163e624462','63651946-3973-44cf-b5c6-c812b58080fc','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 06:50:36'),('b5bdbad8-01f4-11f0-a232-00163e624462','d1f742e5-6039-4f08-b882-15e42ccdf170','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-15 23:24:57'),('b69f4c20-51e7-11f0-82b5-00163e624462','a06390c9-0ea1-4837-95b3-07c89f938a6c','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 17:13:28'),('b6d53d75-518e-11f0-82b5-00163e624462','d0501040-5b68-4316-b92f-f1a76f24461b','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 06:36:22'),('b95d74c8-1908-11f0-af2a-00163e624462','57186f8d-ac85-41f9-9f98-acfb6b52790e','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-04-14 08:16:10'),('ba3b4a21-51db-11f0-82b5-00163e624462','30061dec-29bd-4078-8b65-c75001104f63','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 15:47:40'),('bc3a66a1-4d2f-11f0-82b5-00163e624462','c7b28d40-7f29-4fa9-a285-63c3b564f11d','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',0,'Login access is not allowed. Please contact administrator for assistance','2025-06-19 17:06:25'),('bcdd6ea7-001e-11f0-8c38-00163e624462','2cf0cae2-002c-4765-a4df-ec51268a5c11','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-13 15:20:46'),('be293ddc-4d2f-11f0-82b5-00163e624462','71340a86-0443-4438-93ba-1d2dd45dcd1e','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',0,'Login access is not allowed. Please contact administrator for assistance','2025-06-19 17:06:29'),('bf430ec7-31af-11f0-9d19-00163e624462','8e000592-21eb-4152-9374-323e4791d17d','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-15 17:12:13'),('c5eb1c7c-51b2-11f0-82b5-00163e624462','3db9625e-4c68-419f-93f7-60c2e09975ce','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 10:54:29'),('c615420f-4d2f-11f0-82b5-00163e624462','5653a531-8154-460f-a5e6-f51a6df82193','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-19 17:06:42'),('c6a63404-002b-11f0-b57e-00163e624462','c00afd7e-6198-41c8-aa86-313854df55a9','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-13 16:54:06'),('c98c570d-53ba-11f0-82b5-00163e624462','9c8b659b-77a9-40fb-b5ec-a9b130e2c03d','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-28 00:56:54'),('cbf9f006-53a5-11f0-82b5-00163e624462','f704f73d-0708-4930-b3b9-b3a33b416996','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-27 22:26:38'),('cc5e59da-1908-11f0-af2a-00163e624462','57186f8d-ac85-41f9-9f98-acfb6b52790e','bachtiar.madya.p@gmail.com','Logout','Maintenance Monitoring Application',1,'','2025-04-14 08:16:42'),('d29c3904-1908-11f0-af2a-00163e624462','b43a48b6-b188-4909-a68b-d3493c4835ae','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-04-14 08:16:52'),('d431beb4-4d30-11f0-82b5-00163e624462','737480a8-7656-4f6e-80f1-16c888f1779c','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-19 17:14:14'),('d4f50b79-003e-11f0-9ffc-00163e624462','77616a23-2c20-442a-84de-bb1601e8ac14','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-13 19:10:30'),('dcb120b9-feac-11ef-8b28-00163e624462','a1e04151-baa8-46df-9a3e-85bc25235195','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 19:13:05'),('dd984613-5193-11f0-82b5-00163e624462','895ccc86-c47f-4e3c-b148-cd599394afb3','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 07:13:15'),('de0a9513-01fa-11f0-b06e-00163e624462','40265d1d-79b2-42ec-8e02-aac51064c520','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-16 00:09:02'),('dfca4970-3689-11f0-9d19-00163e624462','7ec66b4b-276a-4244-a791-ad65599018e0','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-21 21:23:42'),('e072067f-51d0-11f0-82b5-00163e624462','07698b6c-9fed-4a1b-a37a-c684b5462a58','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 14:29:59'),('e2959458-fe99-11ef-9886-00163e624462','b005e61a-9c50-4086-9a1a-6f1ddb8ad603','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 16:57:14'),('e45dcd96-51b1-11f0-82b5-00163e624462','1eccd7fb-c2f8-4ec4-addc-a0280dbbc878','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 10:48:11'),('e514d34a-001e-11f0-8c38-00163e624462','f91abb81-fe23-416d-af1d-22418f98fe37','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-13 15:21:54'),('e547d322-5227-11f0-82b5-00163e624462','fcee4e9e-3945-4012-99fb-21ea88b3829e','fuadi.jamal@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-26 00:52:55'),('e7b5ce6a-feaa-11ef-b993-00163e624462','e99dae1d-2efa-4710-ac17-483b2f5d0ce4','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 18:59:04'),('e82c2fc9-53ae-11f0-82b5-00163e624462','dd47ec08-b653-4455-967d-ff564a8a63fc','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-27 23:31:51'),('e986b348-51fe-11f0-82b5-00163e624462','b8e6346b-886d-4a74-8623-a396261d9057','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 19:59:30'),('ed23184e-518b-11f0-82b5-00163e624462','0780a11e-e14d-44ac-88d0-58250b5c4ac6','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 06:16:25'),('ee3a5814-fe9b-11ef-beda-00163e624462','24bb27ba-daa7-490a-a525-fc863b3f4c8a','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 17:11:53'),('eecc468b-4d2e-11f0-82b5-00163e624462','3f0df772-a9f9-4c6e-a544-615b4671709d','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-19 17:00:41'),('f09e6064-0048-11f0-aae0-00163e624462','0952408e-3720-434d-b923-6455aa9a04b6','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-13 20:22:52'),('f09f3337-51e7-11f0-82b5-00163e624462','91fe845b-13eb-494b-b318-c39e45e58831','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-06-25 17:15:05'),('f656ace2-fe99-11ef-afcc-00163e624462','2f94b88f-e8b4-4abb-93b1-38bcc106374c','root@mail.com','Logout','Maintenance Monitoring Application',1,'','2025-03-11 16:57:48'),('f77043e7-01fb-11f0-ba50-00163e624462','de53956c-0db7-42bb-a765-ff9f1e997f9d','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-16 00:16:54'),('f8a7236d-fea1-11ef-adc0-00163e624462','4403d524-cf40-4b2a-97ae-49171c46b8a3','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 17:55:07'),('f9f402a2-0282-11f0-8b41-00163e624462','ec6449f0-569a-4ba5-8620-9a255adea79e','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-16 16:23:19'),('ff2f63c7-fead-11ef-bdd3-00163e624462','f160f864-7d0d-48d0-93dc-87be0a09d8f6','root@mail.com','Authentication','Maintenance Monitoring Application',1,'','2025-03-11 19:21:12'),('ffdb5ee5-3693-11f0-9d19-00163e624462','c029d5f7-a026-4916-8376-48ef3cf06e38','bachtiar.madya.p@gmail.com','Authentication','Maintenance Monitoring Application',1,'','2025-05-21 22:36:11');
/*!40000 ALTER TABLE `audit_authentication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authentication`
--

DROP TABLE IF EXISTS `authentication`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authentication` (
  `uid` varchar(50) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `password` varchar(255) NOT NULL,
  `login_allowed` tinyint NOT NULL DEFAULT '1',
  `password_last_set` timestamp NULL DEFAULT NULL,
  `last_login_dt` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  CONSTRAINT `authentication_staff_FK` FOREIGN KEY (`uid`) REFERENCES `staff` (`uid`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authentication`
--

LOCK TABLES `authentication` WRITE;
/*!40000 ALTER TABLE `authentication` DISABLE KEYS */;
INSERT INTO `authentication` VALUES ('6ae9c19b-23a9-41b6-a09a-953a4eb52d97','4qghif','4320e9b5aac2435a4bf03d2f49acc0f1acdfe624b478185c16c4c34787242389',1,'2025-03-11 16:59:27','2025-06-19 17:06:42'),('b7cafeeb-e9a3-4d4c-a2a5-7b2962f03540','1j718b','4df93d27e2e2baeae650635df03069b3fe24aab80f0f6cbf91c1a4d28d1ea267',1,NULL,'2025-07-22 13:54:12'),('c7ec1cca-6d22-462f-ba8e-7681a3342a93','btvi97','e701ec153f8fe41cfcffa0172bb444be32207eb1b5989d8db885a4354f9b56b4',1,NULL,'2025-06-26 00:52:55'),('ec43392c-7cbc-489f-9420-283fb6fc72b7','uee5gk','b051cfbb1e2b83d7df42b7e0d1248b8fec205fd1ef69882c6a07db60f6ca4bb6',1,NULL,'2025-06-25 07:48:31'),('f3619883-e99a-43f5-8d95-f6cdf4a180c8','3girqu','f017d250c027638e205e85e7676dc832a86b7c86f82aadbf2fdd591c8df885f1',1,NULL,'2025-06-25 07:48:37');
/*!40000 ALTER TABLE `authentication` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `uid` varchar(50) NOT NULL,
  `display_name` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` text,
  `website` varchar(255) DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('262093b3-5198-11f0-82b5-00163e624462','PT MERCEDES-BENZ INDONESIA','info@mercedes-benz.co.id','02150711799','Sequis Tower Lt 7, \nJl. Jendral Sudirman, Kav 71, Desa/Kelurahan Senayan, \nKec. Kebayoran Baru, Kota Adm. Jakarta Selatan, \nProvinsi DKI Jakarta 12190','https://www.mercedes-benz.co.id/',1,'2025-06-25 14:43:56','2025-06-25 14:44:25'),('451ef432-53bb-11f0-82b5-00163e624462','PT ANOMALI LINTAS TEKNOLOGI','info@anomaliteknologi.co.id','02152960782','Gedung Patra Jasa Lt.17 Jalan Gatot Subroto, Jakarta Selatan','https://www.anomaliteknologi.co.id/',1,'2025-06-28 08:00:23',NULL),('6be37bf4-5197-11f0-82b5-00163e624462','PT RUSWIN INDONESIA','info@ruswin.co.id','02129049888','The Manhattan Square Mid Tower 16th Floor\nJl. TB Simatupang Kav 1-S\nCilandak Timur, Jakarta Selatan 12560','https://www.ruswin.co.id/',1,'2025-06-25 14:38:44',NULL),('e1c1e58c-5197-11f0-82b5-00163e624462','PT BUANA FINANCE TBK','info@buanafi.com','02150806969','Tokopedia Tower - Ciputra World 2\nLt. 38 Unit A-F\nJl. Prof.Dr. Satrio Kav 11\nJakarta Selatan','https://www.buanafinance.co.id/',1,'2025-06-25 14:42:01',NULL);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_pic`
--

DROP TABLE IF EXISTS `customer_pic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_pic` (
  `uid` varchar(50) NOT NULL,
  `display_name` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_pic`
--

LOCK TABLES `customer_pic` WRITE;
/*!40000 ALTER TABLE `customer_pic` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_pic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_type`
--

DROP TABLE IF EXISTS `customer_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_type` (
  `uid` varchar(50) NOT NULL,
  `display_name` varchar(255) NOT NULL,
  `description` text,
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_type`
--

LOCK TABLES `customer_type` WRITE;
/*!40000 ALTER TABLE `customer_type` DISABLE KEYS */;
INSERT INTO `customer_type` VALUES ('52bb28eb-8559-4f77-99aa-14e3ed0b5bbc','End User','A company that directly uses the product or service for its own internal operations.\nThey are the final recipient of the product and do not resell or distribute it further.','2025-05-23 15:32:39',NULL),('d1b242b1-2d97-4085-b808-2ac826e5f22d','Principal','A company that supplies products or services, typically acting as the original source, brand owner, or main provider in a vendor-supplier relationship.','2025-05-23 15:32:39',NULL);
/*!40000 ALTER TABLE `customer_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `uid` varchar(50) NOT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `description` text,
  `create_dt` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES ('2e6ddaee-feae-11ef-bdd3-00163e624462','Administrator',' System Administrator of the application','2025-03-12 02:22:32','2025-05-15 22:03:17'),('6ae86bf0-7733-46df-ae88-30a757b2953f','System Support','People who doing support to the system','2025-03-11 22:17:05','2025-03-14 02:11:16'),('9ad33a62-fea9-11ef-a044-00163e624462','Sales','People who sale the product and or infrastructur devices','2025-03-12 01:49:47','2025-03-13 23:49:44'),('b0b18550-d9b2-4a82-9d91-a1eea1bc69c4','ROOT USER','DO NOT REMOVE THIS DEPARTMENT','2025-03-11 22:17:05','2025-03-12 02:35:08');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_template`
--

DROP TABLE IF EXISTS `email_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `email_template` (
  `uid` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `subject` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `html_content` text NOT NULL,
  `create_dt` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_template`
--

LOCK TABLES `email_template` WRITE;
/*!40000 ALTER TABLE `email_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `email_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `global_setting`
--

DROP TABLE IF EXISTS `global_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `global_setting` (
  `key` varchar(50) NOT NULL,
  `value` varchar(255) NOT NULL,
  `description` text,
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `global_setting`
--

LOCK TABLES `global_setting` WRITE;
/*!40000 ALTER TABLE `global_setting` DISABLE KEYS */;
INSERT INTO `global_setting` VALUES ('MAIL.AUTH.REQUIRED','true',NULL,'2025-06-03 18:45:46',NULL),('MAIL.ENABLE','true',NULL,'2025-06-03 18:45:46',NULL),('MAIL.SMTP.ALIAS','Support',NULL,'2025-06-03 18:46:43',NULL),('MAIL.SMTP.HOST','smtp.gmail.com',NULL,'2025-06-03 18:45:46',NULL),('MAIL.SMTP.PASSWORD','9ltcvq1tq+hW4yO6+ItP+WqmwTmPhVum8AFjnjVDLKhzf5CiVG/hQHbMRIe7kdkA',NULL,'2025-06-03 18:46:44',NULL),('MAIL.SMTP.PORT','587',NULL,'2025-06-03 18:45:46',NULL),('MAIL.SMTP.SENDER','no-reply@tsi.com',NULL,'2025-06-03 18:46:43',NULL),('MAIL.SMTP.STARTTLS.ENABLE','true',NULL,'2025-06-03 18:46:43',NULL),('MAIL.SMTP.USERNAME','tsi.sup.sample@gmail.com',NULL,'2025-06-03 18:46:44',NULL),('OTP.ENABLE','true','OTP Enable','2025-06-03 18:41:45',NULL),('OTP.EXPIRY','5','OTP Expiry (in Minute/s)','2025-06-03 18:41:45',NULL),('OTP.TEST.CODE','123456','Dummy OTP Code','2025-06-03 18:41:45',NULL),('OTP.TEST.ENABLE','true','Dummy OTP Test enable','2025-06-03 18:41:45',NULL),('PASSWORD.RESET.URL','http://203.194.114.182:8080/monitoring/login#reminder',NULL,'2025-06-03 18:50:44',NULL);
/*!40000 ALTER TABLE `global_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `icon`
--

DROP TABLE IF EXISTS `icon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `icon` (
  `uid` varchar(50) NOT NULL,
  `function` varchar(50) DEFAULT NULL,
  `icon` varchar(25) DEFAULT NULL,
  `create_dt` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `icon`
--

LOCK TABLES `icon` WRITE;
/*!40000 ALTER TABLE `icon` DISABLE KEYS */;
INSERT INTO `icon` VALUES ('034d91e7-3c05-40d3-b1f5-f63ce7924068','for project update by user','gi gi-refresh','2025-06-28 04:50:02','2025-06-28 04:58:22'),('80e7fb53-c719-444c-89a4-162cb26ae645','for project update by system','gi gi-fire','2025-06-28 04:58:51',NULL),('fb334975-097c-44da-94d5-3f556cfd988e','for project creation','gi gi-crown','2025-06-28 04:50:02',NULL);
/*!40000 ALTER TABLE `icon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `uid` varchar(50) NOT NULL,
  `display_name` varchar(100) NOT NULL,
  `category_uid` varchar(50) DEFAULT NULL,
  `brand_uid` varchar(50) DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `create_dt` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`),
  KEY `product_product_brand_FK` (`brand_uid`),
  KEY `product_product_category_FK` (`category_uid`),
  CONSTRAINT `product_product_brand_FK` FOREIGN KEY (`brand_uid`) REFERENCES `product_brand` (`uid`) ON DELETE RESTRICT,
  CONSTRAINT `product_product_category_FK` FOREIGN KEY (`category_uid`) REFERENCES `product_category` (`uid`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('111dab87-5196-11f0-82b5-00163e624462','Meraki MS425','7d614d44-5195-11f0-82b5-00163e624462','1d29c623-5195-11f0-82b5-00163e624462','Meraki MS425 32 L3 Cloud-Managed 32x 10G SFP+ Switch',1,'2025-06-25 14:29:02','2025-06-25 14:29:02'),('222dccd3-5196-11f0-82b5-00163e624462','Meraki MS350','7d614d44-5195-11f0-82b5-00163e624462','1d29c623-5195-11f0-82b5-00163e624462','Meraki MS350 48 L3 Stackable Cloud-Managed 48x GigE Switch',1,'2025-06-25 14:29:30','2025-06-25 14:29:30'),('40c7cb95-5196-11f0-82b5-00163e624462','VXRAIL 14G E560 1U1N 1S VSAN ENT H','81dc3736-5195-11f0-82b5-00163e624462','23b4fd89-5195-11f0-82b5-00163e624462','VXRAIL 14G E560 1U1N 1S VSAN ENT H',1,'2025-06-25 14:30:22','2025-06-25 14:30:22'),('65acbb01-5196-11f0-82b5-00163e624462','C9120AX','86367386-5195-11f0-82b5-00163e624462','1d29c623-5195-11f0-82b5-00163e624462','C9120AX Internal 802.11ax 4x4:4 MIMO;IOT;BT5;mGig;USB;RHL',1,'2025-06-25 14:31:24','2025-06-25 14:31:24'),('de3482da-5195-11f0-82b5-00163e624462','FortiGate 1100E','7972e376-5195-11f0-82b5-00163e624462','f8b1c633-5194-11f0-82b5-00163e624462','',1,'2025-06-25 14:27:36','2025-06-25 14:27:36'),('ed0fe3d9-5195-11f0-82b5-00163e624462','Forti Analyzer 300G','7972e376-5195-11f0-82b5-00163e624462','f8b1c633-5194-11f0-82b5-00163e624462','',1,'2025-06-25 14:28:01','2025-06-25 14:28:01');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_brand`
--

DROP TABLE IF EXISTS `product_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_brand` (
  `uid` varchar(50) NOT NULL,
  `display_name` varchar(100) NOT NULL,
  `description` text,
  `create_dt` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_brand`
--

LOCK TABLES `product_brand` WRITE;
/*!40000 ALTER TABLE `product_brand` DISABLE KEYS */;
INSERT INTO `product_brand` VALUES ('1d29c623-5195-11f0-82b5-00163e624462','Cisco','','2025-06-25 14:22:12','2025-06-25 14:22:12'),('23b4fd89-5195-11f0-82b5-00163e624462','Dell EMC','','2025-06-25 14:22:23','2025-06-25 14:22:23'),('2cdf608b-5195-11f0-82b5-00163e624462','Allied Telesis','','2025-06-25 14:22:39','2025-06-25 14:22:39'),('f8b1c633-5194-11f0-82b5-00163e624462','Fortinet','','2025-06-25 14:21:11','2025-06-25 14:21:11');
/*!40000 ALTER TABLE `product_brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_category` (
  `uid` varchar(50) NOT NULL,
  `display_name` varchar(100) NOT NULL,
  `description` text,
  `create_dt` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
INSERT INTO `product_category` VALUES ('6aa6c8d6-5195-11f0-82b5-00163e624462','Power Supply Unit','','2025-06-25 14:24:22','2025-06-25 14:24:39'),('7972e376-5195-11f0-82b5-00163e624462','Firewall / Analyzer','','2025-06-25 14:24:47','2025-06-25 14:24:47'),('7d614d44-5195-11f0-82b5-00163e624462','Network Switch','','2025-06-25 14:24:54','2025-06-25 14:24:54'),('81dc3736-5195-11f0-82b5-00163e624462','Hyperconverged Infrastructure','','2025-06-25 14:25:01','2025-06-25 14:25:01'),('86367386-5195-11f0-82b5-00163e624462','Wireless Access Point','','2025-06-25 14:25:09','2025-06-25 14:25:09'),('8da49cfb-5195-11f0-82b5-00163e624462','Video Conferencing','','2025-06-25 14:25:21','2025-06-25 14:25:21'),('93b3711e-5195-11f0-82b5-00163e624462','Support Subscription','','2025-06-25 14:25:31','2025-06-25 14:25:31'),('a1205845-5195-11f0-82b5-00163e624462','License','','2025-06-25 14:25:54','2025-06-25 14:25:54'),('a54c2a33-5195-11f0-82b5-00163e624462','Router','','2025-06-25 14:26:01','2025-06-25 14:26:01'),('b0ffeb8e-5195-11f0-82b5-00163e624462','Network Module','','2025-06-25 14:26:21','2025-06-25 14:26:21');
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `uid` varchar(50) NOT NULL,
  `display_name` varchar(255) NOT NULL,
  `customer_uid` varchar(50) NOT NULL,
  `sales_order_number` varchar(20) DEFAULT NULL,
  `job_code` varchar(255) DEFAULT NULL,
  `description` text,
  `status` tinyint DEFAULT '1',
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `mark_for_deletion` tinyint NOT NULL DEFAULT '0',
  `mark_for_deletion_dt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `project_customer_FK` (`customer_uid`),
  CONSTRAINT `project_customer_FK` FOREIGN KEY (`customer_uid`) REFERENCES `customer` (`uid`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES ('1f44b29b-6ab7-4024-ad70-ca0910a8dffd','Cisco Meraki Maintenance for Kemenkes 2022 - 2023','451ef432-53bb-11f0-82b5-00163e624462','','','',1,'2025-06-28 08:00:52',NULL,0,NULL),('4a62a5b6-7251-4a88-85f6-a0a074e080e0','Renewal Maintenance Security 2022 - 2023','6be37bf4-5197-11f0-82b5-00163e624462','800800',NULL,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nisl at facilisis tristique, arcu nulla cursus sapien, vel blandit ipsum justo non turpis. Proin vulputate, tortor nec malesuada vestibulum, metus metus interdum elit, ut volutpat arcu dolor at odio. Integer eget risus in nibh iaculis lacinia. In dictum nisi nec purus congue, id dapibus tortor vestibulum. Mauris vitae risus sed lorem efficitur commodo. Donec consequat sem vel lectus laoreet, sed laoreet nunc vehicula. Nulla facilisi. Sed hendrerit risus a augue tristique, ut ullamcorper elit sollicitudin.',1,'2025-06-25 16:13:22','2025-06-28 07:43:23',1,'2025-07-27 20:41:17'),('5ff357ff-3c7d-41ff-be73-7cce49690aef','Renewal Maintenance Security 2022 - 2023','6be37bf4-5197-11f0-82b5-00163e624462','','','',1,'2025-06-28 06:55:13',NULL,0,NULL),('948126e8-9a69-4b2b-a2ca-4bedad337ee1','test','e1c1e58c-5197-11f0-82b5-00163e624462','','','',1,'2025-07-22 20:56:03','2025-07-22 20:59:07',1,'2025-08-21 13:59:07');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_customer_pic`
--

DROP TABLE IF EXISTS `project_customer_pic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_customer_pic` (
  `uid` varchar(50) NOT NULL,
  `project_uid` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `project_customer_pic_project_FK` (`project_uid`),
  CONSTRAINT `project_customer_pic_project_FK` FOREIGN KEY (`project_uid`) REFERENCES `project` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_customer_pic`
--

LOCK TABLES `project_customer_pic` WRITE;
/*!40000 ALTER TABLE `project_customer_pic` DISABLE KEYS */;
INSERT INTO `project_customer_pic` VALUES ('593f553b-274b-433c-9a19-dcf95542b102','4a62a5b6-7251-4a88-85f6-a0a074e080e0','Mei Chan','98420820372','mei.chan@rusin.co.id','2025-06-25 21:18:55',NULL),('d9f6aba6-b2af-484e-ba6b-96d976ce1a97','4a62a5b6-7251-4a88-85f6-a0a074e080e0','David Santoso','01349138139','david@ruswin.co.id','2025-06-25 21:18:55',NULL);
/*!40000 ALTER TABLE `project_customer_pic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_history`
--

DROP TABLE IF EXISTS `project_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_history` (
  `uid` varchar(50) NOT NULL,
  `project_uid` varchar(50) NOT NULL,
  `actor` varchar(255) NOT NULL,
  `event` varchar(50) NOT NULL,
  `message_title` varchar(255) NOT NULL,
  `message_detail` text NOT NULL,
  `icon` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `create_dt` timestamp NOT NULL,
  `request` longtext,
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `project_history_project_FK` (`project_uid`),
  KEY `project_history_icon_FK` (`icon`),
  CONSTRAINT `project_history_icon_FK` FOREIGN KEY (`icon`) REFERENCES `icon` (`uid`) ON DELETE RESTRICT,
  CONSTRAINT `project_history_project_FK` FOREIGN KEY (`project_uid`) REFERENCES `project` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_history`
--

LOCK TABLES `project_history` WRITE;
/*!40000 ALTER TABLE `project_history` DISABLE KEYS */;
INSERT INTO `project_history` VALUES ('2b5240a5-53b2-11f0-82b5-00163e624462','5ff357ff-3c7d-41ff-be73-7cce49690aef','Bachtiar Permadi','CREATE','Project Created','Renewal Maintenance Security 2022 - 2023 has been created','fb334975-097c-44da-94d5-3f556cfd988e','2025-06-28','06:55:13','2025-06-27 23:55:13',NULL),('2e802dcd-073c-4457-92f0-ca990550936f','4a62a5b6-7251-4a88-85f6-a0a074e080e0','Bachtiar Permadi','UPDATE','PIC Updated','Bachtiar Permadi has added as project PIC','034d91e7-3c05-40d3-b1f5-f63ce7924068','2025-06-28','06:44:12','2025-06-27 23:43:57',NULL),('39fc08dc-0352-4479-9cfe-0a0cd6b6470d','4a62a5b6-7251-4a88-85f6-a0a074e080e0','Bachtiar Permadi','UPDATE','PIC Updated','Yosepha Ayumi has added as project PIC','034d91e7-3c05-40d3-b1f5-f63ce7924068','2025-06-26','04:01:50','2025-06-25 21:01:50',NULL),('577fe4b4-53bb-11f0-82b5-00163e624462','1f44b29b-6ab7-4024-ad70-ca0910a8dffd','Bachtiar Permadi','CREATE','Project Created','Cisco Meraki Maintenance for Kemenkes 2022 - 2023 has been created','fb334975-097c-44da-94d5-3f556cfd988e','2025-06-28','08:00:52','2025-06-28 01:00:52','{\r\n  \"uid\" : \"1f44b29b-6ab7-4024-ad70-ca0910a8dffd\",\r\n  \"displayName\" : \"Cisco Meraki Maintenance for Kemenkes 2022 - 2023\",\r\n  \"salesOrderNumber\" : \"\",\r\n  \"jobCode\" : \"\",\r\n  \"description\" : \"\",\r\n  \"status\" : false,\r\n  \"createDt\" : \"2025-06-28 08:00:52\",\r\n  \"customerUid\" : \"451ef432-53bb-11f0-82b5-00163e624462\",\r\n  \"staffPic\" : [ {\r\n    \"projectUid\" : \"1f44b29b-6ab7-4024-ad70-ca0910a8dffd\",\r\n    \"staffUid\" : \"f3619883-e99a-43f5-8d95-f6cdf4a180c8\",\r\n    \"createDt\" : \"2025-06-28 08:00:52\"\r\n  } ],\r\n  \"markForDeletion\" : false\r\n}'),('61ad5f70-9d88-45db-9a9d-8a34c68a35c5','4a62a5b6-7251-4a88-85f6-a0a074e080e0','Bachtiar Permadi','UPDATE','PIC Updated','David Santoso has added as customer PIC','034d91e7-3c05-40d3-b1f5-f63ce7924068','2025-06-26','04:07:46','2025-06-25 21:07:46',NULL),('9722daf1-e99e-41f7-8462-f054e82f8d0c','4a62a5b6-7251-4a88-85f6-a0a074e080e0','Bachtiar Permadi','CREATE','Project Created','Renewal Maintenance Security 2022 - 2023  has been created','fb334975-097c-44da-94d5-3f556cfd988e','2025-06-26','03:47:20','2025-06-25 20:47:20',NULL),('9b41ecdb-6703-11f0-82b5-00163e624462','948126e8-9a69-4b2b-a2ca-4bedad337ee1','Bachtiar Permadi','CREATE','Project Created','test has been created','fb334975-097c-44da-94d5-3f556cfd988e','2025-07-22','20:56:03','2025-07-22 13:56:03','{\r\n  \"uid\" : \"948126e8-9a69-4b2b-a2ca-4bedad337ee1\",\r\n  \"displayName\" : \"test\",\r\n  \"salesOrderNumber\" : \"\",\r\n  \"jobCode\" : \"\",\r\n  \"description\" : \"\",\r\n  \"status\" : false,\r\n  \"createDt\" : \"2025-07-22 20:56:03\",\r\n  \"customerUid\" : \"e1c1e58c-5197-11f0-82b5-00163e624462\",\r\n  \"staffPic\" : [ {\r\n    \"projectUid\" : \"948126e8-9a69-4b2b-a2ca-4bedad337ee1\",\r\n    \"staffUid\" : \"b7cafeeb-e9a3-4d4c-a2a5-7b2962f03540\",\r\n    \"createDt\" : \"2025-07-22 20:56:03\"\r\n  }, {\r\n    \"projectUid\" : \"948126e8-9a69-4b2b-a2ca-4bedad337ee1\",\r\n    \"staffUid\" : \"ec43392c-7cbc-489f-9420-283fb6fc72b7\",\r\n    \"createDt\" : \"2025-07-22 20:56:03\"\r\n  } ],\r\n  \"markForDeletion\" : false\r\n}'),('f7553952-ddd1-4ed9-898a-3bdaa8b8479b','4a62a5b6-7251-4a88-85f6-a0a074e080e0','Bachtiar Permadi','UPDATE','PIC Updated','Mei Chan has added as customer PIC','034d91e7-3c05-40d3-b1f5-f63ce7924068','2025-06-26','04:07:53','2025-06-25 21:07:53',NULL);
/*!40000 ALTER TABLE `project_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_pic`
--

DROP TABLE IF EXISTS `project_pic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_pic` (
  `uid` varchar(50) NOT NULL,
  `project_uid` varchar(50) NOT NULL,
  `staff_uid` varchar(50) NOT NULL,
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `project_pic_project_FK` (`project_uid`),
  KEY `project_pic_staff_FK` (`staff_uid`),
  CONSTRAINT `project_pic_project_FK` FOREIGN KEY (`project_uid`) REFERENCES `project` (`uid`) ON DELETE CASCADE,
  CONSTRAINT `project_pic_staff_FK` FOREIGN KEY (`staff_uid`) REFERENCES `staff` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_pic`
--

LOCK TABLES `project_pic` WRITE;
/*!40000 ALTER TABLE `project_pic` DISABLE KEYS */;
INSERT INTO `project_pic` VALUES ('2b3389bb-53b2-11f0-82b5-00163e624462','5ff357ff-3c7d-41ff-be73-7cce49690aef','f3619883-e99a-43f5-8d95-f6cdf4a180c8','2025-06-28 06:55:13',NULL),('5769905b-53bb-11f0-82b5-00163e624462','1f44b29b-6ab7-4024-ad70-ca0910a8dffd','f3619883-e99a-43f5-8d95-f6cdf4a180c8','2025-06-28 08:00:52',NULL),('9b2d2b49-6703-11f0-82b5-00163e624462','948126e8-9a69-4b2b-a2ca-4bedad337ee1','b7cafeeb-e9a3-4d4c-a2a5-7b2962f03540','2025-07-22 20:56:03',NULL),('9b320f0e-6703-11f0-82b5-00163e624462','948126e8-9a69-4b2b-a2ca-4bedad337ee1','ec43392c-7cbc-489f-9420-283fb6fc72b7','2025-07-22 20:56:03',NULL),('bf71c7f7-7733-4e02-b974-93ced76f3b98','4a62a5b6-7251-4a88-85f6-a0a074e080e0','b7cafeeb-e9a3-4d4c-a2a5-7b2962f03540','2025-06-25 21:16:36',NULL),('faaed092-9709-4be0-8dd4-84e8805ac9ec','4a62a5b6-7251-4a88-85f6-a0a074e080e0','f3619883-e99a-43f5-8d95-f6cdf4a180c8','2025-06-25 21:16:36',NULL);
/*!40000 ALTER TABLE `project_pic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_service_order`
--

DROP TABLE IF EXISTS `project_service_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_service_order` (
  `uid` varchar(50) NOT NULL,
  `project_uid` varchar(50) NOT NULL,
  `contract_number` varchar(50) NOT NULL,
  `service_qty` tinyint DEFAULT '0',
  PRIMARY KEY (`uid`) USING BTREE,
  KEY `project_service_project_FK` (`project_uid`),
  CONSTRAINT `project_service_project_FK` FOREIGN KEY (`project_uid`) REFERENCES `project` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_service_order`
--

LOCK TABLES `project_service_order` WRITE;
/*!40000 ALTER TABLE `project_service_order` DISABLE KEYS */;
INSERT INTO `project_service_order` VALUES ('1d128aab-fc11-43ae-b18f-efd885ed7302','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',0),('23effa25-4a1e-4570-b59b-22e711fb19d9','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',0),('29a83b37-5340-4b72-bc0d-e73e38709113','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',0),('42b0b7f1-46b0-456d-99bc-2fd2c62f47af','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',0),('598c05d5-db48-4dd8-8fe9-8a7e420878ab','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',0),('5f5edfae-8cd6-44ea-bcf5-401443560186','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',0),('673b8975-bb4b-4c83-9b82-9771a084230e','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',0),('7e947aee-2a6d-4b01-b166-dfeb99cbc875','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',0),('8540863f-e277-4178-a634-0c2ba3b4b8ee','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',0),('859dea3f-3c02-4462-b8b1-faf9d4025537','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',0),('85d94a4f-88a1-4437-a532-78392795e443','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',0),('9434e373-4b01-414e-b5ac-08b2f4c83556','4a62a5b6-7251-4a88-85f6-a0a074e080e0','MSC22-0006',2),('f3db8583-d13b-4aac-bfd0-f82e79eb371c','4a62a5b6-7251-4a88-85f6-a0a074e080e0','DWC22-0006',2);
/*!40000 ALTER TABLE `project_service_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_tag`
--

DROP TABLE IF EXISTS `project_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_tag` (
  `uid` varchar(50) NOT NULL,
  `project_uid` varchar(50) NOT NULL,
  `service_order_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `tag` varchar(50) NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `project_tag_project_FK` (`project_uid`),
  KEY `project_tag_project_service_order_FK` (`service_order_uid`),
  CONSTRAINT `project_tag_project_FK` FOREIGN KEY (`project_uid`) REFERENCES `project` (`uid`) ON DELETE CASCADE,
  CONSTRAINT `project_tag_project_service_order_FK` FOREIGN KEY (`service_order_uid`) REFERENCES `project_service_order` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_tag`
--

LOCK TABLES `project_tag` WRITE;
/*!40000 ALTER TABLE `project_tag` DISABLE KEYS */;
INSERT INTO `project_tag` VALUES ('a1b77acb-9f7c-4709-9a73-d6e8cc0b603e','4a62a5b6-7251-4a88-85f6-a0a074e080e0','f3db8583-d13b-4aac-bfd0-f82e79eb371c','active'),('cd9281d0-c63e-40a1-a1ad-36d9782f81e7','4a62a5b6-7251-4a88-85f6-a0a074e080e0','9434e373-4b01-414e-b5ac-08b2f4c83556','maintenance');
/*!40000 ALTER TABLE `project_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_level`
--

DROP TABLE IF EXISTS `service_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_level` (
  `uid` varchar(50) NOT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `description` text,
  `create_dt` datetime DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_level`
--

LOCK TABLES `service_level` WRITE;
/*!40000 ALTER TABLE `service_level` DISABLE KEYS */;
INSERT INTO `service_level` VALUES ('20d880b6-369b-11f0-9d19-00163e624462','Device Warranty','','2025-05-22 06:27:14','2025-06-25 14:15:09'),('26afb942-369b-11f0-9d19-00163e624462','8x5xNBD','Unlimitted MTC Ticket - PM 4x per Year\n','2025-05-22 06:27:24','2025-06-25 14:15:34'),('3ecf5dc9-5194-11f0-82b5-00163e624462','24x7','Unlimitted MTC Ticket','2025-06-25 14:15:59',NULL),('4c668d23-5194-11f0-82b5-00163e624462','License','','2025-06-25 14:16:22',NULL);
/*!40000 ALTER TABLE `service_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `uid` varchar(50) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) DEFAULT NULL,
  `department_uid` varchar(50) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(50) DEFAULT NULL,
  `dob` timestamp NULL DEFAULT NULL,
  `photo_url` timestamp NULL DEFAULT NULL,
  `status` tinyint DEFAULT NULL,
  `create_dt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_dt` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE KEY `staff_unique` (`email`),
  KEY `staff_department_FK` (`department_uid`),
  CONSTRAINT `staff_department_FK` FOREIGN KEY (`department_uid`) REFERENCES `department` (`uid`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES ('6ae9c19b-23a9-41b6-a09a-953a4eb52d97','Root','User','b0b18550-d9b2-4a82-9d91-a1eea1bc69c4','root@mail.com','123456789',NULL,NULL,1,'2025-03-11 22:25:15','2025-03-14 03:28:57'),('b7cafeeb-e9a3-4d4c-a2a5-7b2962f03540','Bachtiar','Permadi','2e6ddaee-feae-11ef-bdd3-00163e624462','bachtiar.madya.p@gmail.com','085747079410','1991-10-12 17:00:00',NULL,1,'2025-03-16 07:17:17','2025-06-20 00:17:54'),('c7ec1cca-6d22-462f-ba8e-7681a3342a93','Jamal Ahnan','Fuadi','6ae86bf0-7733-46df-ae88-30a757b2953f','fuadi.jamal@mail.com','00000000000','2000-06-03 17:00:00',NULL,1,'2025-06-25 14:48:14','2025-06-25 14:48:23'),('ec43392c-7cbc-489f-9420-283fb6fc72b7','Ivan','Adrian','9ad33a62-fea9-11ef-a044-00163e624462','adrian.ivan@mail.com','000000000000','1990-03-10 17:00:00',NULL,1,'2025-06-25 14:47:27','2025-06-25 14:48:31'),('f3619883-e99a-43f5-8d95-f6cdf4a180c8','Yosepha ','Ayumi','9ad33a62-fea9-11ef-a044-00163e624462','ayumi.yosepha@mail.com','00000000000000','1989-06-09 17:00:00',NULL,1,'2025-06-25 14:46:47','2025-06-25 14:48:37');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `view_project_history`
--

DROP TABLE IF EXISTS `view_project_history`;
/*!50001 DROP VIEW IF EXISTS `view_project_history`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `view_project_history` AS SELECT 
 1 AS `uid`,
 1 AS `project_uid`,
 1 AS `projectName`,
 1 AS `actor`,
 1 AS `event`,
 1 AS `message_title`,
 1 AS `message_detail`,
 1 AS `icon`,
 1 AS `updateDate`,
 1 AS `updateTime`,
 1 AS `create_dt`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'maintenance_monitoring_v2'
--

--
-- Final view structure for view `view_project_history`
--

/*!50001 DROP VIEW IF EXISTS `view_project_history`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`goldspice`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `view_project_history` AS select `ph`.`uid` AS `uid`,`ph`.`project_uid` AS `project_uid`,`p`.`display_name` AS `projectName`,`ph`.`actor` AS `actor`,`ph`.`event` AS `event`,`ph`.`message_title` AS `message_title`,`ph`.`message_detail` AS `message_detail`,`i`.`icon` AS `icon`,date_format(`ph`.`date`,'%d %M %Y') AS `updateDate`,date_format(`ph`.`time`,'%h:%i %p') AS `updateTime`,`ph`.`create_dt` AS `create_dt` from ((`project_history` `ph` join `project` `p` on((`p`.`uid` = `ph`.`project_uid`))) join `icon` `i` on((`i`.`uid` = `ph`.`icon`))) order by `ph`.`create_dt` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-23 10:19:31
