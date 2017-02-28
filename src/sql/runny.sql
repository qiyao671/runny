/*
 Navicat Premium Data Transfer

 Source Server         : mysqlStudy
 Source Server Type    : MySQL
 Source Server Version : 50173
 Source Host           : localhost
 Source Database       : runny

 Target Server Type    : MySQL
 Target Server Version : 50173
 File Encoding         : utf-8

 Date: 02/28/2017 21:02:25 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `approve`
-- ----------------------------
DROP TABLE IF EXISTS `approve`;
CREATE TABLE `approve` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `moment_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `best_run`
-- ----------------------------
DROP TABLE IF EXISTS `best_run`;
CREATE TABLE `best_run` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fathest` double(11,0) DEFAULT NULL,
  `longest` double(11,0) DEFAULT NULL,
  `average_pace` double(11,0) DEFAULT NULL,
  `fast_pace` double(11,0) DEFAULT NULL,
  `five_pb` double(11,0) DEFAULT NULL,
  `ten_pb` double(11,0) DEFAULT NULL,
  `half_marathon_pb` double(11,0) DEFAULT NULL,
  `full_marathon_pb` double(11,0) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `moment_id` int(11) DEFAULT NULL,
  `comment_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `replied_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `community`
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `community_name` varchar(55) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `community_location` varchar(55) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `user_numb` int(11) DEFAULT NULL,
  `community_age` int(11) DEFAULT NULL,
  `introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `friends`
-- ----------------------------
DROP TABLE IF EXISTS `friends`;
CREATE TABLE `friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id_a` int(11) DEFAULT NULL,
  `user_id_b` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `moment`
-- ----------------------------
DROP TABLE IF EXISTS `moment`;
CREATE TABLE `moment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `moment_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `total_run`
-- ----------------------------
DROP TABLE IF EXISTS `total_run`;
CREATE TABLE `total_run` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `distance` double(11,0) DEFAULT NULL,
  `times` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(55) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(55) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `profile` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `sex` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `height` double(11,0) DEFAULT NULL,
  `weight` double(11,0) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `community_id` int(11) DEFAULT NULL,
  `total_run_id` int(11) DEFAULT NULL,
  `best_run_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'xiaohong', '123456', '2', 'https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=4225565668,46745471&fm=80&w=179&h=119&img.JPEG', '女', '180', '98', '2017-02-28 20:29:22', '浙江省宁波市鄞州区', '12', '3', '13'), ('2', 'xiaoli', '123456', '2', 'https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=4225565668,46745471&fm=80&w=179&h=119&img.JPEG', '女', '180', '98', '2017-02-28 20:33:33', '浙江省宁波市鄞州区', '12', '3', '13');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
