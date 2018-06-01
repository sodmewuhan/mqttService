/*
Navicat MySQL Data Transfer

Source Server         : 192.168.152.130
Source Server Version : 50721
Source Host           : 192.168.152.130:3306
Source Database       : datasensorn

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-06-01 17:02:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_boxinfo
-- ----------------------------
DROP TABLE IF EXISTS `t_boxinfo`;
CREATE TABLE `t_boxinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `boxNumber` varchar(32) DEFAULT NULL COMMENT '盒子编号',
  `registerPhone` varchar(16) DEFAULT NULL COMMENT '注册用电话号码',
  `boxSimNumber` varchar(16) DEFAULT NULL COMMENT '盒子sim卡信息',
  `userId` int(11) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='盒子基本信息';

-- ----------------------------
-- Records of t_boxinfo
-- ----------------------------
INSERT INTO `t_boxinfo` VALUES ('1', '122', '122', '123', '1');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountName` varchar(32) DEFAULT NULL COMMENT '账号名称',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `userName` varchar(32) DEFAULT NULL COMMENT '用户姓名',
  `phoneNum` varchar(32) DEFAULT NULL COMMENT '电话号码',
  `userType` varchar(8) DEFAULT NULL COMMENT '用户类型：1 企业用户  2 农民',
  `companyName` varchar(128) DEFAULT NULL COMMENT '公司名称',
  `province` varchar(64) DEFAULT NULL COMMENT '省',
  `city` varchar(64) DEFAULT NULL COMMENT '地市',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'cailong', 'uxxxx', null, '12344', null, 'coship', null, 'wuhan');
INSERT INTO `t_user` VALUES ('2', 'cailong', 'uxxxx', null, '12344', null, 'coship', null, 'wuhan');
