CREATE DATABASE  IF NOT EXISTS `cinema` /*!40100 DEFAULT CHARACTER SET utf8mb4*/;
USE `cinema`;
-- MySQL dump 10.13  Distrib 8.0.14, for Win64 (x86_64)
--
-- Host: localhost    Database: cinema
-- ------------------------------------------------------
-- Server version	8.0.14

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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(45) NOT NULL,
  `a_description` varchar(255) NOT NULL,
  `end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `coupon_id` int(11) DEFAULT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (1,'春季外卖节','春季外卖节','2019-04-23 17:55:59',5,'2019-04-20 17:55:59'),(2,'秋季外卖节','秋季外卖节','2019-04-23 17:55:59',6,'2019-04-20 17:55:59'),(3,'老年人活动','老年人才看的活动','2019-04-26 16:00:00',8,'2019-04-20 16:00:00'),(7,'辉哥大酬宾','辉哥大发慈悲举办的活动','2019-06-28 16:00:00',11,'2019-06-10 16:17:34'),(8,'刘涛的活动','刘涛良心发现决定发布活动','2019-06-29 16:00:00',12,'2019-06-05 16:00:00');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_movie`
--

DROP TABLE IF EXISTS `activity_movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `activity_movie` (
  `activity_id` int(11) DEFAULT NULL,
  `movie_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_movie`
--

LOCK TABLES `activity_movie` WRITE;
/*!40000 ALTER TABLE `activity_movie` DISABLE KEYS */;
INSERT INTO `activity_movie` VALUES (2,10),(2,11),(2,16),(7,10),(7,11),(8,13),(8,10);
/*!40000 ALTER TABLE `activity_movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `charge_record`
--

DROP TABLE IF EXISTS `charge_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `charge_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `time` datetime(3) NOT NULL,
  `amount` double NOT NULL,
  `balance` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `charge_record`
--

LOCK TABLES `charge_record` WRITE;
/*!40000 ALTER TABLE `charge_record` DISABLE KEYS */;
INSERT INTO `charge_record` VALUES (1,16,'2019-06-11 11:50:39.000',11,3431);
/*!40000 ALTER TABLE `charge_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `target_amount` float DEFAULT NULL,
  `discount_amount` float DEFAULT NULL,
  `start_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (1,'测试优惠券','春季电影节',20,5,'2019-04-20 17:47:54','2019-04-23 17:47:59'),(5,'测试优惠券','品质联盟',30,4,'2019-04-20 21:14:46','2019-04-24 21:14:51'),(6,'春节电影节优惠券','电影节优惠券',50,10,'2019-04-20 21:15:11','2019-04-21 21:14:56'),(8,'测试优惠券','测试用优惠券',100,50,'2019-06-12 11:48:18','2019-04-26 16:00:00'),(11,'辉哥的优惠券','辉哥的优惠券',20,10,'2019-06-12 11:46:47','2019-06-28 16:00:00'),(12,'刘涛的优惠券','刘涛的优惠券',50,30,'2019-06-05 16:00:00','2019-06-29 16:00:00');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon_user`
--

DROP TABLE IF EXISTS `coupon_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `coupon_user` (
  `coupon_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon_user`
--

LOCK TABLES `coupon_user` WRITE;
/*!40000 ALTER TABLE `coupon_user` DISABLE KEYS */;
INSERT INTO `coupon_user` VALUES (11,16);
/*!40000 ALTER TABLE `coupon_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hall`
--

DROP TABLE IF EXISTS `hall`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `column` int(11) DEFAULT NULL,
  `row` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hall`
--

LOCK TABLES `hall` WRITE;
/*!40000 ALTER TABLE `hall` DISABLE KEYS */;
INSERT INTO `hall` VALUES (1,'刘涛的后宫',10,5),(2,'许竣博的后花园',12,8);
/*!40000 ALTER TABLE `hall` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poster_url` varchar(255) DEFAULT NULL,
  `director` varchar(255) DEFAULT NULL,
  `screen_writer` varchar(255) DEFAULT NULL,
  `starring` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `length` int(11) NOT NULL,
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `name` varchar(255) NOT NULL,
  `description` text,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (10,'http://n.sinaimg.cn/translate/640/w600h840/20190312/ampL-hufnxfm4278816.jpg','大森贵弘 /伊藤秀樹','','神谷浩史 /井上和彦 /高良健吾 /小林沙苗 /泽城美雪','动画',NULL,NULL,120,'2019-04-14 06:54:31','夏目友人帐','在人与妖怪之间过着忙碌日子的夏目，偶然与以前的同学结城重逢，由此回忆起了被妖怪缠身的苦涩记忆。此时，夏目认识了在归还名字的妖怪记忆中出现的女性·津村容莉枝。和玲子相识的她，现在和独子椋雄一同过着平稳的生活。夏目通过与他们的交流，心境也变得平和。但这对母子居住的城镇，却似乎潜伏着神秘的妖怪。在调查此事归来后，寄生于猫咪老师身体的\"妖之种\"，在藤原家的庭院中，一夜之间就长成树结出果实。而吃掉了与自己形状相似果实的猫咪老师，竟然分裂成了3个',0),(11,'https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=113efc83dc09b3deffb2ec3aadd607e4/71cf3bc79f3df8dc9670d47ac011728b4610288f.jpg','安娜·波顿',NULL,'布利·拉尔森','动作/冒险/科幻',NULL,NULL,120,'2019-06-12 12:09:31','惊奇队长','漫画中的初代惊奇女士曾经是一名美国空军均情报局探员，暗恋惊奇先生。。。',0),(12,'https://gss3.bdstatic.com/7Po3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=859c3f1663224f4a5799741531ccf76f/c83d70cf3bc79f3dcfbbad00b4a1cd11728b29a3.jpg','吾峠呼世晴',NULL,'花江夏树','动作','日本','日语',120,'2019-06-12 12:00:37','鬼灭之刃','大正时期，日本。心地善良的卖炭少年·炭治郎，有一天他的家人被鬼杀死了。而唯一幸存下来的妹妹——祢豆子变成了鬼。被绝望的现实打垮的炭治郎，为了寻找让妹妹变回人类的方法，决心朝着“鬼杀队”的道路前进。人与鬼交织的悲哀的兄妹的故事，现在开始！',0),(13,'https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=3cd0e782144c510faec4e51c58624210/7c1ed21b0ef41bd516fad1be5fda81cb38db3dca.jpg','谏山创','','艾伦·耶格尔','动作/冒险','日本','日语',120,'2019-06-12 12:05:00','进击的巨人','107年前（743年），世界上突然出现了人类的天敌“巨人”。面临着生存危机而残存下来的人类逃到了一个地方，盖起了三重巨大的城墙。人们在这隔绝的环境里享受了一百多年的和平，直到艾伦·耶格尔十岁那年，60米高的“超大型巨人”突然出现，以压倒性的力量破坏城门，其后瞬间消失，凶残的巨人们成群的冲进墙内捕食人类',0),(15,'http://ww2.sinaimg.cn/large/85ccde71gw1fasl4d6scig20bx0c8adb.jpg','刘涛','周际宇','江辉','学习','大陆','汉语',111,'2019-06-12 12:15:25','进击的bug','刘涛为您展示如何从删库到跑路',0),(16,'https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike272%2C5%2C5%2C272%2C90/sign=f0909143d4b44aed4d43b6b6d275ec64/10dfa9ec8a1363271458503a9f8fa0ec08fac706.jpg','林孝谦','','陈意涵','爱情','大陆',NULL,123,'2019-06-12 12:09:31','比悲伤更悲伤的故事','唱片制作人张哲凯(刘以豪)和王牌作词人宋媛媛(陈意涵)相依为命，两人自幼身世坎坷只有彼此为伴，他们是亲人、是朋友，也彷佛是命中注定的另一半。父亲罹患遗传重症而被母亲抛弃的哲凯，深怕自己随时会发病不久人世，始终没有跨出友谊的界线对媛媛展露爱意。眼见哲凯的病情加重，他暗自决定用剩余的生命完成他们之间的终曲，再为媛媛找个可以托付一生的好男人。这时，事业有 成温柔体贴的医生(张书豪)适时的出现让他成为照顾媛媛的最佳人选，二人按部就班发展着关系。一切看似都在哲凯的计划下进行。然而，故事远比这里所写更要悲伤......',1);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_like`
--

DROP TABLE IF EXISTS `movie_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `movie_like` (
  `movie_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `like_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`movie_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_like`
--

LOCK TABLES `movie_like` WRITE;
/*!40000 ALTER TABLE `movie_like` DISABLE KEYS */;
INSERT INTO `movie_like` VALUES (10,12,'2019-03-25 02:40:19'),(11,1,'2019-03-22 09:38:12'),(11,2,'2019-03-23 09:38:12'),(11,3,'2019-03-22 08:38:12'),(12,1,'2019-03-23 09:48:46'),(12,3,'2019-03-25 06:36:22'),(14,1,'2019-03-23 09:38:12'),(16,12,'2019-03-23 15:27:48');
/*!40000 ALTER TABLE `movie_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refund_strategy`
--

DROP TABLE IF EXISTS `refund_strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `refund_strategy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hours_before_end` int(11) NOT NULL,
  `rate` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refund_strategy`
--

LOCK TABLES `refund_strategy` WRITE;
/*!40000 ALTER TABLE `refund_strategy` DISABLE KEYS */;
INSERT INTO `refund_strategy` VALUES (1,2,0.5),(2,3,0.75);
/*!40000 ALTER TABLE `refund_strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hall_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `start_time` timestamp NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL,
  `fare` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (20,1,12,'2019-04-13 17:00:00','2019-04-13 18:00:00',20.5),(21,1,10,'2019-04-11 12:00:00','2019-04-11 13:00:00',90),(27,1,11,'2019-04-17 18:01:00','2019-04-17 20:01:00',20.5),(28,1,11,'2019-04-19 16:00:00','2019-04-19 18:00:00',20.5),(30,1,11,'2019-04-18 18:01:00','2019-04-18 20:01:00',20.5),(31,1,11,'2019-04-12 16:00:00','2019-04-12 18:00:00',20.5),(32,1,11,'2019-04-12 20:00:00','2019-04-12 22:00:00',20.5),(37,1,11,'2019-04-15 00:00:00','2019-04-15 02:00:00',20.5),(38,1,11,'2019-04-14 17:00:00','2019-04-14 19:00:00',20.5),(40,1,10,'2019-04-10 16:00:00','2019-04-10 18:00:00',20.5),(41,1,11,'2019-04-10 19:00:00','2019-04-10 21:00:00',20.5),(42,1,11,'2019-04-10 22:00:00','2019-04-11 00:00:00',20.5),(43,1,10,'2019-04-11 01:00:00','2019-04-11 03:00:00',20.5),(44,2,10,'2019-04-11 01:00:00','2019-04-11 03:00:00',20.5),(45,2,10,'2019-04-10 22:00:00','2019-04-11 00:00:00',20.5),(46,2,11,'2019-04-10 19:00:00','2019-04-10 21:00:00',20.5),(47,2,11,'2019-04-10 16:00:00','2019-04-10 18:00:00',20.5),(48,2,10,'2019-04-11 13:00:00','2019-04-11 15:59:00',20.5),(50,1,10,'2019-04-15 16:00:00','2019-04-15 19:00:00',2),(51,1,10,'2019-04-17 05:00:00','2019-04-17 07:00:00',9),(52,1,10,'2019-04-18 05:00:00','2019-04-18 07:00:00',9),(53,1,16,'2019-04-19 07:00:00','2019-04-19 10:00:00',9),(54,1,16,'2019-04-16 19:00:00','2019-04-16 22:00:00',9),(55,1,15,'2019-04-17 23:00:00','2019-04-18 01:00:00',9),(56,2,10,'2019-04-19 13:00:00','2019-04-19 15:59:00',20.5),(57,2,10,'2019-04-20 13:00:00','2019-04-20 15:59:00',20.5),(58,2,10,'2019-04-21 13:00:00','2019-04-21 15:59:00',20.5),(61,1,13,'2019-04-20 11:00:00','2019-04-20 13:00:00',25),(62,1,11,'2019-04-20 08:00:00','2019-04-20 10:00:00',25),(63,2,15,'2019-04-20 16:01:30','2019-04-21 05:30:00',30),(64,1,16,'2019-04-22 02:00:00','2019-04-22 05:30:00',30),(65,1,10,'2019-04-23 02:00:00','2019-04-23 05:30:00',30),(66,2,13,'2019-04-21 07:31:29','2019-04-16 15:59:00',20.5),(67,2,10,'2019-04-25 13:00:00','2019-04-25 15:59:00',20.5),(68,2,10,'2019-04-26 13:00:00','2019-04-26 15:59:00',20.5),(69,1,10,'2019-06-09 12:00:00','2019-06-09 15:59:00',50),(70,2,10,'2019-06-09 12:00:00','2019-06-09 15:59:00',60),(71,1,10,'2019-06-10 13:00:00','2019-06-10 15:59:00',100),(72,1,10,'2019-06-12 12:00:00','2019-06-12 15:59:00',100),(73,2,12,'2019-06-12 12:00:00','2019-06-12 15:59:00',100);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ticket` (
  `user_id` int(11) DEFAULT NULL,
  `schedule_id` int(11) DEFAULT NULL,
  `column_index` int(11) DEFAULT NULL,
  `row_index` int(11) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `pay_amount` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=180 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (16,73,11,4,1,144,'2019-06-10 16:33:01',90),(16,73,1,4,3,145,'2019-06-10 16:34:35',90),(16,73,9,5,1,146,'2019-06-10 16:41:11',90),(16,73,3,6,3,147,'2019-06-10 16:46:52',90);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `identity` varchar(20) DEFAULT '观众',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uindex` (`id`),
  UNIQUE KEY `user_username_uindex` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'root','123456','管理员'),(2,'employee','123456','员工'),(3,'test','123456','观众'),(5,'test1','123456','观众'),(7,'test121','123456','观众'),(8,'root2','123456','员工'),(10,'roottt','123123','观众'),(12,'zhourui','123456','观众'),(13,'abc123','abc123','观众'),(15,'dd','123','观众'),(16,'814775538','814775538','观众');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `view`
--

DROP TABLE IF EXISTS `view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `view` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `view`
--

LOCK TABLES `view` WRITE;
/*!40000 ALTER TABLE `view` DISABLE KEYS */;
INSERT INTO `view` VALUES (1,6);
/*!40000 ALTER TABLE `view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_card`
--

DROP TABLE IF EXISTS `vip_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vip_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `balance` float DEFAULT NULL,
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vip_card_user_id_uindex` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_card`
--

LOCK TABLES `vip_card` WRITE;
/*!40000 ALTER TABLE `vip_card` DISABLE KEYS */;
INSERT INTO `vip_card` VALUES (1,15,375,'2019-04-21 13:54:38'),(2,12,660,'2019-04-17 18:47:42'),(8,16,3521,'2019-06-12 11:53:30');
/*!40000 ALTER TABLE `vip_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vip_promotion`
--

DROP TABLE IF EXISTS `vip_promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `vip_promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `standard` double NOT NULL,
  `minus` double NOT NULL,
  `start_time` date NOT NULL,
  `end_time` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vip_promotion`
--

LOCK TABLES `vip_promotion` WRITE;
/*!40000 ALTER TABLE `vip_promotion` DISABLE KEYS */;
INSERT INTO `vip_promotion` VALUES (1,200,30,'2019-05-10','2019-06-30');
/*!40000 ALTER TABLE `vip_promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'cinema'
--

--
-- Dumping routines for database 'cinema'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-12 20:29:11
