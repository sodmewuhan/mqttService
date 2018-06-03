/*
Navicat MySQL Data Transfer

Source Server         : centos
Source Server Version : 50624
Source Host           : 192.168.108.129:3306
Source Database       : datasensorn

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2018-06-03 21:50:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_box_sensor
-- ----------------------------
DROP TABLE IF EXISTS `t_box_sensor`;
CREATE TABLE `t_box_sensor` (
  `id` int(11) NOT NULL,
  `box_id` int(11) NOT NULL COMMENT '盒子编号',
  `sensor_name` varchar(32) NOT NULL COMMENT '传感器名称',
  `sensor_propose` varchar(256) DEFAULT NULL COMMENT '传感器用途',
  `sensor_num` varchar(32) DEFAULT NULL COMMENT '传感器编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='盒子传感器';

-- ----------------------------
-- Records of t_box_sensor
-- ----------------------------

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
-- Table structure for t_fishpond
-- ----------------------------
DROP TABLE IF EXISTS `t_fishpond`;
CREATE TABLE `t_fishpond` (
  `id` int(11) NOT NULL,
  `poundName` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '鱼塘名称',
  `poundAddress` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '鱼塘地址',
  `poundArea` float(12,4) DEFAULT NULL COMMENT '鱼塘面积（平方米）',
  `poundDeepth` float(12,4) DEFAULT NULL COMMENT '鱼塘深度',
  `breedType` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '养殖水产品种',
  `breedName` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '养殖水产名字',
  `counts` int(11) DEFAULT NULL COMMENT '养殖数量',
  `boxInfoId` int(11) DEFAULT NULL COMMENT '盒子编号',
  `userId` int(11) DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='鱼塘信息';

-- ----------------------------
-- Records of t_fishpond
-- ----------------------------

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
