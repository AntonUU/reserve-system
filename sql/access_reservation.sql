/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80100 (8.1.0)
 Source Host           : localhost:3306
 Source Schema         : access_reservation

 Target Server Type    : MySQL
 Target Server Version : 80100 (8.1.0)
 File Encoding         : 65001

 Date: 18/06/2024 19:40:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tab_admin
-- ----------------------------
DROP TABLE IF EXISTS `tab_admin`;
CREATE TABLE `tab_admin` (
  `admin_id` int unsigned NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `admin_phone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `permissions` varchar(255) DEFAULT NULL COMMENT '权限（111/读写改/11/读写/1/读）',
  `admin_ username` varchar(32) DEFAULT NULL COMMENT '登录用户名',
  `admin_passwd` varchar(32) DEFAULT NULL COMMENT '登录密码',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tab_cat
-- ----------------------------
DROP TABLE IF EXISTS `tab_cat`;
CREATE TABLE `tab_cat` (
  `tab_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `person_name` varchar(32) NOT NULL COMMENT '申请人姓名',
  `person_phone` varchar(255) DEFAULT NULL COMMENT '申请人手机号',
  `cat_id` varchar(32) DEFAULT NULL COMMENT '车牌号',
  `reserve_status` tinyint DEFAULT NULL COMMENT '预约状态 0等待受理 1通过 2过期 3拒绝',
  `start_datetime` datetime DEFAULT NULL COMMENT '预约开始时间',
  `end_datetime` datetime DEFAULT NULL COMMENT '预约结束时间',
  `entrance` tinyint DEFAULT NULL COMMENT '进出校门',
  `update_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`tab_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tab_reserve
-- ----------------------------
DROP TABLE IF EXISTS `tab_reserve`;
CREATE TABLE `tab_reserve` (
  `reserve_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `reserve_group` tinyint(1) DEFAULT NULL COMMENT '个人/单位(预约)',
  `reserve_name` varchar(32) DEFAULT NULL COMMENT '预约人姓名',
  `reserve_sex` tinyint(1) DEFAULT NULL COMMENT '性别',
  `reserve_phone` char(11) DEFAULT NULL COMMENT '手机号',
  `reserve_card` varchar(255) DEFAULT NULL COMMENT '身份证',
  `portrait` varchar(4096) DEFAULT NULL COMMENT '人像图',
  `reserve_door` tinyint DEFAULT NULL COMMENT '预约校门 1北门 2西门 3东门',
  `reserve_status` tinyint DEFAULT NULL COMMENT '预约状态 0等待受理 1通过 2过期 3拒绝',
  `start_datetime` datetime DEFAULT NULL COMMENT '预约开始时间',
  `end_datetime` datetime DEFAULT NULL COMMENT '预约结束时间',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新日志',
  PRIMARY KEY (`reserve_id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tab_reserve_visit_association
-- ----------------------------
DROP TABLE IF EXISTS `tab_reserve_visit_association`;
CREATE TABLE `tab_reserve_visit_association` (
  `association_id` bigint NOT NULL AUTO_INCREMENT,
  `reserve_id` bigint DEFAULT NULL COMMENT '预约id',
  `cat_id` bigint DEFAULT NULL,
  `visit_id` bigint DEFAULT NULL COMMENT '被访id',
  `reserve_type` tinyint(1) DEFAULT NULL COMMENT '预约类型 1person 2cat',
  `update_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`association_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tab_visit
-- ----------------------------
DROP TABLE IF EXISTS `tab_visit`;
CREATE TABLE `tab_visit` (
  `visit_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `visit_name` varchar(32) DEFAULT NULL COMMENT '被拜访人姓名',
  `visit_unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '拜访部门',
  `accompanying_num` tinyint unsigned DEFAULT NULL COMMENT '随行人员',
  `visit_context` varchar(255) DEFAULT NULL COMMENT '拜访事由',
  `update_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`visit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- View structure for view_wait_acceptance
-- ----------------------------
DROP VIEW IF EXISTS `view_wait_acceptance`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `view_wait_acceptance` AS select count(0) AS `wait_acceptance_count` from `tab_reserve` where (`tab_reserve`.`reserve_status` = 0);

SET FOREIGN_KEY_CHECKS = 1;
