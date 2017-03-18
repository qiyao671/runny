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

 Date: 03/17/2017 16:50:29 PM
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
  `approve_user_id` int(11) DEFAULT NULL COMMENT '被点赞的用户',
  `status` smallint(11) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `approve`
-- ----------------------------
BEGIN;
INSERT INTO `approve` VALUES ('1', '5', '3', '1', '1', '2017-03-12 21:18:42', null), ('2', '5', '1', '1', '1', '2017-03-12 21:19:17', null), ('3', '5', '5', '1', '1', '2017-03-12 21:21:35', null), ('4', '5', '4', '1', '1', '2017-03-12 21:22:59', null);
COMMIT;

-- ----------------------------
--  Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `moment_id` int(11) DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '谁评论的',
  `replied_user_id` int(11) DEFAULT NULL COMMENT '谁回复的',
  `status` smallint(11) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
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
--  Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `friend_id` int(11) DEFAULT NULL,
  `status` tinyint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `friend`
-- ----------------------------
BEGIN;
INSERT INTO `friend` VALUES ('1', '1', '3', null, null, null), ('3', '2', '3', null, null, null), ('4', '2', '5', null, null, null), ('5', '3', '5', null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `moment`
-- ----------------------------
DROP TABLE IF EXISTS `moment`;
CREATE TABLE `moment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `content` text,
  `picture` varchar(500) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `status` smallint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `moment`
-- ----------------------------
BEGIN;
INSERT INTO `moment` VALUES ('1', '3', '今天又有个姑娘对我笑', '最近看了30多份简历，也面试了几个人，发现有些人的能力其实并不差，但在简历上表现出来却资质平平，没有什么亮点，如果不是约过来面谈，估计简历就石沉大海了。  那我作为一名PPT达人，同时也是一位职场人士，在简历制作这块也小有心得，今天就想来和大家聊聊关于简历制作，你必须要知道的事。', 'http://upload.jianshu.io/users/upload_avatars/4617498/ac833e71e1d3.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/120/h/120', '2017-03-12 18:13:29', null, '1'), ('2', '1', '空姐再到飞行员', '泰网友称图中的美女为“全泰最美飞行员”，不单只形容她的容貌，更是对她的努力拼搏表示肯定。她名叫 Piyathida Suphasi，28岁，目前担任清迈-曼谷固定往返航线的商务客机机长，驾驶空中巴士及波音等机型。', 'http://upload.jianshu.io/users/upload_avatars/2124507/83084e13c40a.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/144/h/144', '2017-03-12 18:14:55', null, '1'), ('5', '1', 'WiFi或将退出历史舞台', '凤凰科技讯 北京时间3月10日消息，据外媒报道，无论是智能手机、恒温器还是电视机，几乎每一台联网设备都有Wi-Fi的图标。但是现在，Wi-Fi正开始被人遗忘。  　　随着美国所有主流运营商纷纷推出不限流量套餐，消费者终于不再需要登录Wi-Fi网络来避免昂贵的流量费了。这一重大变化可能促使Wi-Fi退出历史舞台，而一些竞争性的新技术出现更是令Wi-Fi的未来看起来暗淡无光。  　　“你可能会看到一个很大的转变，”行业组织Telecom Media Finance Associates创始人蒂姆·法勒说道。“现在，咖啡店可能不再那么需要为顾客提供Wi-Fi了。”', 'http://img003.21cnimg.com/photos/album/20170310/m600/D179CB093C9D9F8750A283A242311D1B.jpeg', '2017-03-12 18:22:09', null, '1'), ('3', '5', '哈哈哈', '这里我把压箱底的代码都贡献出来了，呵呵。 是的，前端需要记的东西太多，平时遇到的问题解决后急需一个能归纳总结的这些问题点的地方，有时候一问题一行代码就能轻松解决而不必再次上网...', 'http://img2.utuku.china.com/1000x0/news/20170310/c9d862ad-85d4-4707-a331-94c1881f9864.jpg', '2017-03-12 18:15:47', null, '1'), ('4', '4', '世上最危险演唱会', '迈克尔杰克逊布加勒斯特92演唱会。这场演唱会可谓是MJ最经典的一场演唱会，全场一共7万2000人，整个运动场全部黑压压一片人，还有很多歌迷在球场聆听，没有进场。离球场最近的街道交通全部是军队的人来维持次序。', 'https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=475998174,4198207634&fm=80&w=179&h=119&img.JPEG', '2017-03-12 18:17:34', null, '1');
COMMIT;

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
  `altitude` double(55,2) DEFAULT NULL COMMENT '海拔',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `runny_log`
-- ----------------------------
BEGIN;
INSERT INTO `runny_log` VALUES ('1', '1', '10.21', '60.41', '192.4', '2017-03-05 16:12:14', null, '跑的我低血糖', null), ('2', '1', '5.38', '30.87', '100.5', '2017-03-05 16:13:10', null, '这速度我自己都害怕', null), ('3', '1', '8.80', '45.38', '150.3', '2017-03-15 16:15:30', null, '感觉自己好厉害', null), ('4', '1', '12.00', '70.00', '180.9', '2017-02-28 19:49:14', null, '牛逼啊', null), ('5', '2', '3.90', '28.98', '423.9', '2017-03-08 22:00:03', null, 'aaaaa', null), ('6', '2', '4.80', '30.80', '534.4', '2017-03-08 22:00:58', null, 'bbbbb', null);
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
INSERT INTO `user` VALUES ('1', 'xiaohong', '123456', '2', 'https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=4225565668,46745471&fm=80&w=179&h=119&img.JPEG', '女', '180', '98', '2017-02-28 20:29:22', '浙江省宁波市鄞州区', '12', '3', '13', null, null), ('2', 'xiaoli', '123456', '2', 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAAQABAAD/2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQYGBcUFhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSj/2wBDAQcHBwoIChMKChMoGhYaKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCj/wAARCADIAIUDAREAAhEB', '女', '180', '98', '2017-02-28 20:33:33', '浙江省宁波市鄞州区', '12', '3', '13', null, null), ('3', 'JasonWang', 'CF74FE3337EE674192CEF3DE846642AA05F517831F579E913D23373D', null, 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAAQABAAD/2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQYGBcUFhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSj/2wBDAQcHBwoIChMKChMoGhYaKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCj/wAARCADIAIYDASIAAhEB', null, null, null, null, null, null, null, null, null, null), ('4', 'xiaoyao', '48E0B5B64D618D210C49F434F56C738C6865C5FDF2FBFAE93C2D6381', null, 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/7RhcUGhvdG9zaG9wIDMuMAA4QklNBCUAAAAAABAAAAAAAAAAAAAAAAAAAAAAOEJJTQPtAAAAAAAQAEgAAAABAAIASAAAAAEAAjhCSU0EJgAAAAAADgAAAAAAAAAAAAA/gAAAOEJJTQQNAAAAAAAEAAAAHjhCSU0EGQAAAAAABAAAAB44QklNA/MAAAAAAAkAAAAAAAAAAAEA', null, null, null, null, null, null, null, null, null, null), ('5', 'xiaohua', '8194E1A245E5D23F0E7680D17B2AD1615AA3621381C9D96852BE853B', null, 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAAQABAAD/2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQYGBcUFhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSj/2wBDAQcHBwoIChMKChMoGhYaKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCj/wAARCADIAIUDASIAAhEB', null, null, null, null, null, null, null, null, null, '18');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
