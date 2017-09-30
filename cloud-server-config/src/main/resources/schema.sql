CREATE DATABASE IF NOT EXISTS `configCenter` DEFAULT CHARACTER SET utf8;

USE `configCenter`;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `service-xx-dev`;
CREATE TABLE `service-xx-dev` (
  `id` varchar(50) NOT NULL,
  `property` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `version` varchar(10),
  `lable` varchar(20),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
insert into `service-xx-dev`(id,property,value) values(123447,'spring.rabbitmq.password','root')
