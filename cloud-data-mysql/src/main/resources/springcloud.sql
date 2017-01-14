
CREATE DATABASE /*!32312 IF NOT EXISTS*/`springcloud` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `springcloud`;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `loginName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nickName` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `registerTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `updateTime` datetime DEFAULT NULL COMMENT 'user info',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'erdaoya', '1234', 'erdaoya', '12345678909', '0', 'xx@gmail.com', '2017-01-14 08:08:43', null);
INSERT INTO `user` VALUES ('2', 'henry', '1234', 'henry', '12345678901', '1', '12345678909', '2017-01-14 08:08:47', null);


