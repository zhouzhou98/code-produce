/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : code_generator_user

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 03/12/2022 17:36:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint NOT NULL COMMENT '主键',
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名 电话号码',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `data_version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_unx` (`username`,`password`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES (1, 'admin', '$2a$10$2c6uqCzY3ObyCBU7WnY/AORFVU6ZAR.JfUnsogxX3ixgsszCJeiWW', '2022-10-27 22:42:53', '2022-10-27 22:42:53', 0);
INSERT INTO `t_user` VALUES (1586267286674845696, 'test', '$2a$10$inMWKT2ysvNRlz1HmFpv4O/Xq4szgtMaZ5SbPYpFJFdUifJpIICpa', '2022-10-29 16:02:47', '2022-10-29 16:02:47', 0);
INSERT INTO `t_user` VALUES (1598962153488949248, 'test1', '$2a$10$hL5rNisonSa7gNUk/YkjS.BlyPgHFdJ/qMelBfrpByalhEZsROBRa', '2022-12-03 16:47:39', '2022-12-03 16:47:39', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
