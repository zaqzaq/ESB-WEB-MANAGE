/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50521
Source Host           : localhost:3306
Source Database       : esb-web-manage

Target Server Type    : MYSQL
Target Server Version : 50521
File Encoding         : 65001

Date: 2015-12-14 13:17:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `username` varchar(32) NOT NULL DEFAULT '',
  `password` varchar(128) NOT NULL,
  `fullname` varchar(32) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('admin', 'xMpCOKC5I4INzFCab3WEmw==', '管理员');

-- ----------------------------
-- Table structure for `app_info`
-- ----------------------------
DROP TABLE IF EXISTS `app_info`;
CREATE TABLE `app_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) DEFAULT NULL COMMENT '上级节点',
  `filePath` varchar(128) NOT NULL COMMENT '应用文件路径',
  `name` varchar(32) NOT NULL COMMENT '应用名称',
  `remark` varchar(4000) DEFAULT NULL COMMENT '备注',
  `status` smallint(6) NOT NULL DEFAULT '0' COMMENT '状态 0：未部署 1：已部署',
  `timeStart` datetime DEFAULT NULL COMMENT '最后启动时间',
  `userLastUpdate` varchar(32) DEFAULT NULL COMMENT '最后更新的操作者',
  `flowFuns` varchar(2000) DEFAULT NULL COMMENT 'flow应用',
  `isDel` smallint(6) DEFAULT '0' COMMENT '是否删除 0:否 1：是',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_filePath` (`filePath`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app_info
-- ----------------------------
INSERT INTO `app_info` VALUES ('47', null, 'D:\\dev\\mule-standalone-3.7.0\\apps\\2project', '2project', null, '1', '2015-12-14 12:51:04', null, null, '0');
INSERT INTO `app_info` VALUES ('48', '47', 'D:\\dev\\mule-standalone-3.7.0\\apps\\2project\\2project.xml', '2project', '1', '1', '2015-12-14 12:51:04', '管理员', '未描述1', '0');
INSERT INTO `app_info` VALUES ('49', '47', 'D:\\dev\\mule-standalone-3.7.0\\apps\\2project\\WebServiceTest.xml', 'WebServiceTest', null, '0', '2015-12-11 00:00:00', '管理员', '未描述', '1');
INSERT INTO `app_info` VALUES ('50', null, 'D:\\dev\\mule-standalone-3.7.0\\apps\\default', 'default', null, '1', '2015-12-14 12:51:04', null, null, '0');
INSERT INTO `app_info` VALUES ('51', '50', 'D:\\dev\\mule-standalone-3.7.0\\apps\\default\\mule-config.xml', 'mule-config', '备注备注备注备注', '1', '2015-12-14 12:51:04', '管理员', '未描述1', '0');
INSERT INTO `app_info` VALUES ('52', null, 'D:\\dev\\mule-standalone-3.7.0\\apps\\firstproject', 'firstproject', null, '1', '2015-12-14 12:51:04', null, null, '0');
INSERT INTO `app_info` VALUES ('53', '52', 'D:\\dev\\mule-standalone-3.7.0\\apps\\firstproject\\filetest.xml', 'filetest', null, '1', '2015-12-14 12:51:05', '管理员', '未描述', '0');
INSERT INTO `app_info` VALUES ('54', '52', 'D:\\dev\\mule-standalone-3.7.0\\apps\\firstproject\\firstproject.xml', 'firstproject', null, '1', '2015-12-14 12:51:05', null, '未描述', '0');
INSERT INTO `app_info` VALUES ('55', '52', 'D:\\dev\\mule-standalone-3.7.0\\apps\\firstproject\\trantest.xml', 'trantest', null, '1', '2015-12-14 12:51:05', null, '未描述', '0');
INSERT INTO `app_info` VALUES ('56', '47', 'D:\\dev\\mule-standalone-3.7.0\\apps\\2project\\HelloWorld.xml', 'HelloWorld', null, '1', '2015-12-14 12:51:04', '管理员', '未描述', '0');
