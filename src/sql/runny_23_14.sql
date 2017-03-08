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

 Date: 03/08/2017 23:14:13 PM
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
--  Table structure for `runny_log`
-- ----------------------------
DROP TABLE IF EXISTS `runny_log`;
CREATE TABLE `runny_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `distance` double(55,2) DEFAULT NULL,
  `spend_time` double(55,2) DEFAULT NULL,
  `energy` double(55,1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `moment_content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `runny_log`
-- ----------------------------
BEGIN;
INSERT INTO `runny_log` VALUES ('1', '1', '10.21', '60.41', '192.4', '2017-03-05 16:12:14', null, '跑的我低血糖'), ('2', '1', '5.38', '30.87', '100.5', '2017-03-05 16:13:10', null, '这速度我自己都害怕'), ('3', '1', '8.80', '45.38', '150.3', '2017-03-15 16:15:30', null, '感觉自己好厉害'), ('4', '1', '12.00', '70.00', '180.9', '2017-02-28 19:49:14', null, '牛逼啊'), ('5', '2', '3.90', '28.98', '423.9', '2017-03-08 22:00:03', null, 'aaaaa'), ('6', '2', '4.80', '30.80', '534.4', '2017-03-08 22:00:58', null, 'bbbbb');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(55) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
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
  `signature` varchar(255) DEFAULT NULL,
  `age` smallint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'xiaohong', '123456', '2', 'https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=4225565668,46745471&fm=80&w=179&h=119&img.JPEG', '女', '180', '98', '2017-02-28 20:29:22', '浙江省宁波市鄞州区', '12', '3', '13', null, null), ('2', 'xiaoli', '123456', '2', 'https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=4225565668,46745471&fm=80&w=179&h=119&img.JPEG', '女', '180', '98', '2017-02-28 20:33:33', '浙江省宁波市鄞州区', '12', '3', '13', null, null), ('3', 'JasonWang', 'CF74FE3337EE674192CEF3DE846642AA05F517831F579E913D23373D', null, null, null, null, null, null, null, null, null, null, null, null), ('4', 'xiaoyao', '48E0B5B64D618D210C49F434F56C738C6865C5FDF2FBFAE93C2D6381', null, null, null, null, null, null, null, null, null, null, null, null), ('5', 'xiaohua', '8194E1A245E5D23F0E7680D17B2AD1615AA3621381C9D96852BE853B', null, null, null, null, null, null, null, null, null, null, null, '18');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
