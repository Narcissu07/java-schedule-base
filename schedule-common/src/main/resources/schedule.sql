/*
Navicat MySQL Data Transfer

Source Server         : 测试库
Source Server Version : 50721
Source Host           : 10.55.120.45:3306
Source Database       : schedule

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-08-02 08:33:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for scheduler_config
-- ----------------------------
DROP TABLE IF EXISTS `scheduler_config`;
CREATE TABLE `scheduler_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_name` varchar(32) DEFAULT NULL COMMENT '任务名',
  `job_desc` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `cron_rule` varchar(64) DEFAULT NULL COMMENT 'cron调度规则',
  `invoke_url` varchar(256) DEFAULT NULL COMMENT '调用接口url',
  `status` varchar(32) DEFAULT NULL COMMENT '1:生效；0：不生效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_scheduler_config` (`job_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of scheduler_config
-- ----------------------------
INSERT INTO `scheduler_config` VALUES ('1', 'test1', '测试任务1', '0/5 * * * * ?', 'http://localhost:8088/schedule/test', '0');
INSERT INTO `scheduler_config` VALUES ('2', 'test2', '测试任务2', '2/5 * * * * ?', 'http://localhost:8088/schedule/test', '0');
INSERT INTO `scheduler_config` VALUES ('3', 'test3', '测试任务', '0/5 * * * * ?', null, null);
