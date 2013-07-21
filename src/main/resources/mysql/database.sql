SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------------- --
--         CREATE TABLE `ROLE`         --
-- ----------------------------------- --
DROP TABLE IF EXISTS `ROLE`;  
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `ROLE` (
	`id` int NOT NULL auto_increment,
	`role` varchar(16) NOT NULL,
  `creator` int,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`),
  CONSTRAINT `fk_role_creator`
      FOREIGN KEY (`creator`) REFERENCES `USER` (`id`)
      ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

-- ----------------------------------- --
--         CREATE TABLE `USER`         --
-- ----------------------------------- --
DROP TABLE IF EXISTS `USER`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `USER` (
  `id` int NOT NULL auto_increment,
  `lanId` varchar(16) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT TRUE,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

-- ----------------------------------- --
--       CREATE TABLE `USER_ROLE`      --
-- ----------------------------------- --
DROP TABLE IF EXISTS `USER_ROLE`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `USER_ROLE` (
	`roleId` int NOT NULL,
	`userId` int NOT NULL,
	CONSTRAINT `fk_userrole_roleId`
      FOREIGN KEY (`roleId`) REFERENCES `ROLE` (`id`)
      ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_userrole_userId`
      FOREIGN KEY (`userId`) REFERENCES `USER` (`id`)
      ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

-- ----------------------------------- --
--        CREATE TABLE `KEYWORD`       --
-- ----------------------------------- --
DROP TABLE IF EXISTS `KEYWORD`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `KEYWORD` (
  `id` int NOT NULL auto_increment,
  `keyword` varchar(64) NOT NULL,
  `creator` int,
  `date` datetime NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`),
  CONSTRAINT `fk_keyword_creator`
      FOREIGN KEY (`creator`) REFERENCES `USER` (`id`)
      ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

-- ----------------------------------- --
--         CREATE TABLE `DEF`          --
-- ----------------------------------- --
DROP TABLE IF EXISTS `DEF`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `DEF` (
  `id` varchar(36) NOT NULL,
  `uploader` int NOT NULL,
  `uploaded` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT FALSE,
  PRIMARY KEY  (`id`),
  CONSTRAINT `fk_definition_userId`
    FOREIGN KEY (`uploader`) REFERENCES `USER` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

-- DROP TRIGGER IF EXISTS `gen_id_def`;
-- DELIMITER #
-- CREATE TRIGGER `gen_id_def`
--   BEFORE INSERT ON DEF
--   FOR EACH ROW
--   BEGIN
--     IF new.id IS NULL THEN
--       SET new.id = uuid();
--     END IF;
--   END#
-- DELIMITER ;


-- ----------------------------------- --
--        CREATE TABLE `DEF_KW`        --
-- ----------------------------------- --
DROP TABLE IF EXISTS `DEF_KW`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `DEF_KW` (
  `keywordId` int NOT NULL,
  `definitionId` varchar(36) NOT NULL,
  CONSTRAINT `fk_def_kw_keywordId`
    FOREIGN KEY (`keywordId`) REFERENCES `KEYWORD` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_def_kw_definitionId`
    FOREIGN KEY (`definitionId`) REFERENCES `DEF` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

-- ----------------------------------- --
--        CREATE TABLE `DEF_VS`        --
-- ----------------------------------- --
DROP TABLE IF EXISTS `DEF_VS`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `DEF_VS` (
  `definitionId` varchar(36) NOT NULL,
  `valueset` varchar(64) NOT NULL,
  `version` varchar(64) NOT NULL,
  CONSTRAINT `fk_defvs_definitionId`
    FOREIGN KEY (`definitionId`) REFERENCES `DEF` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

-- ----------------------------------- --
--       CREATE TABLE `DEF_CMNT`       --
-- ----------------------------------- --
DROP TABLE IF EXISTS `DEF_CMNT`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `DEF_CMNT` (
  `id` int NOT NULL auto_increment,
  `definitionId` varchar(36) NOT NULL,
  `author` int NOT NULL,
  `comment` varchar(21844) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY(`definitionId`),
  CONSTRAINT `fk_defcomment_userId`
    FOREIGN KEY (`author`) REFERENCES `USER` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_defcomment_definitionId`
    FOREIGN KEY (`definitionId`) REFERENCES `DEF` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

-- ----------------------------------- --
--       CREATE TABLE `DEF_FILE`       --
-- ----------------------------------- --
-- DROP TABLE IF EXISTS `DEF_FILE`;
-- SET @saved_cs_client     = @@character_set_client;
-- SET character_set_client = utf8;
-- CREATE TABLE `DEF_FILE` (
--   `definitionId` varchar(36) NOT NULL,
--   `path` varchar(256) NOT NULL,
--   `mimetype` varchar(256) NOT NULL,
--   CONSTRAINT `fk_deffile_defId`
--     FOREIGN KEY (`definitionId`) REFERENCES `DEF` (`id`)
--     ON DELETE CASCADE ON UPDATE CASCADE,
-- ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
-- SET character_set_client = @saved_cs_client;

-- ----------------------------------- --
--         CREATE TABLE `PROP`         --
-- ----------------------------------- --
DROP TABLE IF EXISTS `PROP`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `PROP` (
  `id` int NOT NULL auto_increment,
  `property` varchar(64) NOT NULL,
  `governed` tinyint(1) DEFAULT FALSE,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

-- ----------------------------------- --
--       CREATE TABLE `PROP_VAL`       --
-- ----------------------------------- --
DROP TABLE IF EXISTS `PROP_VAL`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `PROP_VAL` (
  `id` int NOT NULL auto_increment,
  `propertyId` int NOT NULL,
  `value` varchar(21844),
  `governed` tinyint(1) DEFAULT FALSE,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_propval_propId`
    FOREIGN KEY (`propertyId`) REFERENCES `PROP` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

-- ----------------------------------- --
--       CREATE TABLE `DEF_PROP`       --
-- ----------------------------------- --
DROP TABLE IF EXISTS `DEF_PROP`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `DEF_PROP` (
  `definitionId` varchar(36) NOT NULL,
  `propertyValueId` int NOT NULL,
  CONSTRAINT `fk_defprop_defId`
    FOREIGN KEY (`definitionId`) REFERENCES `DEF` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_defprop_propValId`
    FOREIGN KEY (`propertyValueId`) REFERENCES `PROP_VAL` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

-- ----------------------------------- --
--  CREATE VIEW `DEFINITION_KEYWORDS`  --
-- ----------------------------------- --
DROP VIEW IF EXISTS `DEFINITION_KEYWORDS`;
CREATE VIEW `DEFINITION_KEYWORDS`
  AS SELECT def.definitionId, kw.keyword
  FROM DEF_KW def INNER JOIN KEYWORD kw ON def.keywordId = kw.id;

-- ----------------------------------- --
--    CREATE VIEW `PROPERTY_VALUES`    --
-- ----------------------------------- --
DROP VIEW IF EXISTS `PROPERTY_VALUES`;
CREATE VIEW `PROPERTY_VALUES`
  AS SELECT p.property, pv.* FROM PROP p INNER JOIN PROP_VAL pv ON pv.propertyId = p.id;

-- ----------------------------------- --
-- CREATE VIEW `DEFINITION_PROPERTIES` --
-- ----------------------------------- --
DROP VIEW IF EXISTS `DEFINITION_PROPERTIES`;
CREATE VIEW `DEFINITION_PROPERTIES`
  AS SELECT dp.definitionId, p.property, pv.value
  FROM PROP_VAL pv
  INNER JOIN PROP p ON pv.propertyId = p.id
  INNER JOIN def_prop dp ON dp.propertyValueId = pv.id;

-- ----------------------------------- --
--      CREATE VIEW `USER_ROLES`       --
-- ----------------------------------- --
DROP VIEW IF EXISTS `USER_ROLES`;
CREATE VIEW `USER_ROLES`
  AS SELECT ur.userId, r.role FROM USER_ROLE ur
  INNER JOIN ROLE r ON ur.roleId = r.id;

-- ----------------------------------- --
--      CREATE VIEW `DEFINITION`       --
-- ----------------------------------- --
DROP VIEW IF EXISTS `DEFINITION`;
 CREATE VIEW `DEFINITION`
   AS SELECT d.id, kw.keyword, p.property, p.value FROM DEF d
   INNER JOIN DEFINITION_KEYWORDS kw on kw.definitionId = d.id
   INNER JOIN DEFINITION_PROPERTIES p ON p.definitionId = d.id;

SET FOREIGN_KEY_CHECKS=1;
