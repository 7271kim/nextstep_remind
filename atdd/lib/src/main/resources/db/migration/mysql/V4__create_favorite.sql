DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `favorite` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_date` datetime(6) DEFAULT NULL,
    `modified_date` datetime(6) DEFAULT NULL,
    `member_id` bigint(20) DEFAULT NULL,
    `source_id` bigint(20) DEFAULT NULL,
    `target_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `FK18bw17tb86fh1igov96adasws` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
    CONSTRAINT `FKp3rr9ynngxiyt6wiqupplje2t` FOREIGN KEY (`source_id`) REFERENCES `station` (`id`),
    CONSTRAINT `FKgmw9gwsvibtqwbm4vrarb7e8v` FOREIGN KEY (`target_id`) REFERENCES `station` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=728 DEFAULT CHARSET=utf8mb4;