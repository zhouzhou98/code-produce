/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : code_generator

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 03/12/2022 17:36:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_datasource
-- ----------------------------
DROP TABLE IF EXISTS `t_datasource`;
CREATE TABLE `t_datasource` (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `db_type` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '数据库类型',
  `con_name` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '连接名称',
  `con_url` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '连接url',
  `username` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '连接名称',
  `password` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT '连接密码',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `data_version` int NOT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `datsource_unx` (`user_id`,`db_type`,`con_name`,`con_url`,`username`,`password`) USING BTREE,
  KEY `user_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_datasource
-- ----------------------------
BEGIN;
INSERT INTO `t_datasource` VALUES (1589187496717213696, 1586267286674845696, 'MySQL', '2', '2', '0af529fc008b98bd89557dc1391e86de', '0af529fc008b98bd89557dc1391e86de', '2022-11-06 17:26:40', '2022-11-06 20:52:18', 2);
INSERT INTO `t_datasource` VALUES (1589251569005838336, 1586267286674845696, 'MySQL', 'IM', 'http://127.0.0.1:3306', 'c1e6b344d4706535f12a5fa7cb4df347', '6a0d43e3347487e61e54f19473bfde05', '2022-11-06 21:41:16', '2022-11-06 21:41:16', 0);
INSERT INTO `t_datasource` VALUES (1589990692163362816, 1586267286674845696, 'com.mysql.cj.jdbc.Driver', 'light-chat', 'jdbc:mysql://127.0.0.1/light-chat?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true', 'c1e6b344d4706535f12a5fa7cb4df347', '6a0d43e3347487e61e54f19473bfde05', '2022-11-08 22:38:16', '2022-11-08 22:38:16', 0);
INSERT INTO `t_datasource` VALUES (1589991193516908544, 1586267286674845696, 'MySQL', 'light-chat', 'jdbc:mysql://127.0.0.1/light-chat?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true', 'c1e6b344d4706535f12a5fa7cb4df347', '6a0d43e3347487e61e54f19473bfde05', '2022-11-08 22:40:16', '2022-11-08 22:40:16', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_field_type
-- ----------------------------
DROP TABLE IF EXISTS `t_field_type`;
CREATE TABLE `t_field_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `column_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段类型',
  `java_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'Java属性类型',
  `java_package_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'Java属性包名',
  `js_package_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'Js属性包名',
  `js_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Js属性类型',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `data_version` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `column_type` (`column_type`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字段类型管理';

-- ----------------------------
-- Records of t_field_type
-- ----------------------------
BEGIN;
INSERT INTO `t_field_type` VALUES (1, 'datetime', 'Date', 'java.util.Date', NULL, 'Date', '2022-10-16 13:35:12', '2022-10-31 09:00:15', 0);
INSERT INTO `t_field_type` VALUES (2, 'date', 'Date', 'java.util.Date', NULL, 'Date', '2022-10-16 13:35:12', '2022-10-31 09:00:18', 0);
INSERT INTO `t_field_type` VALUES (3, 'tinyint', 'Integer', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:00:25', 0);
INSERT INTO `t_field_type` VALUES (4, 'smallint', 'Integer', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:00:28', 0);
INSERT INTO `t_field_type` VALUES (5, 'mediumint', 'Integer', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:00:30', 0);
INSERT INTO `t_field_type` VALUES (6, 'int', 'Integer', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:00:33', 0);
INSERT INTO `t_field_type` VALUES (7, 'integer', 'Integer', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:00:35', 0);
INSERT INTO `t_field_type` VALUES (8, 'bigint', 'Long', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:00:37', 0);
INSERT INTO `t_field_type` VALUES (9, 'float', 'Float', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:00:40', 0);
INSERT INTO `t_field_type` VALUES (10, 'double', 'Double', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:00:42', 0);
INSERT INTO `t_field_type` VALUES (11, 'decimal', 'BigDecimal', 'java.math.BigDecimal', NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:00:45', 0);
INSERT INTO `t_field_type` VALUES (12, 'bit', 'Boolean', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:00:49', 0);
INSERT INTO `t_field_type` VALUES (13, 'char', 'String', NULL, NULL, 'string', '2022-10-16 13:35:12', '2022-10-31 09:00:56', 0);
INSERT INTO `t_field_type` VALUES (14, 'varchar', 'String', NULL, NULL, 'string', '2022-10-16 13:35:12', '2022-10-31 09:00:58', 0);
INSERT INTO `t_field_type` VALUES (15, 'tinytext', 'String', NULL, NULL, 'string', '2022-10-16 13:35:12', '2022-10-31 09:01:01', 0);
INSERT INTO `t_field_type` VALUES (16, 'text', 'String', NULL, NULL, 'string', '2022-10-16 13:35:12', '2022-10-31 09:01:04', 0);
INSERT INTO `t_field_type` VALUES (17, 'mediumtext', 'String', NULL, NULL, 'string', '2022-10-16 13:35:12', '2022-10-31 09:01:06', 0);
INSERT INTO `t_field_type` VALUES (18, 'longtext', 'String', NULL, NULL, 'string', '2022-10-16 13:35:12', '2022-10-31 09:01:09', 0);
INSERT INTO `t_field_type` VALUES (19, 'timestamp', 'Date', 'java.util.Date', NULL, 'Date', '2022-10-16 13:35:12', '2022-10-31 09:01:15', 0);
INSERT INTO `t_field_type` VALUES (20, 'NUMBER', 'Integer', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:01:27', 0);
INSERT INTO `t_field_type` VALUES (21, 'BINARY_INTEGER', 'Integer', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:01:34', 0);
INSERT INTO `t_field_type` VALUES (22, 'BINARY_FLOAT', 'Float', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:01:42', 0);
INSERT INTO `t_field_type` VALUES (23, 'BINARY_DOUBLE', 'Double', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:01:49', 0);
INSERT INTO `t_field_type` VALUES (24, 'VARCHAR2', 'String', NULL, NULL, 'string', '2022-10-16 13:35:12', '2022-10-31 09:01:52', 0);
INSERT INTO `t_field_type` VALUES (25, 'NVARCHAR', 'String', NULL, NULL, 'string', '2022-10-16 13:35:12', '2022-10-31 09:01:56', 0);
INSERT INTO `t_field_type` VALUES (26, 'NVARCHAR2', 'String', NULL, NULL, 'string', '2022-10-16 13:35:12', '2022-10-31 09:01:59', 0);
INSERT INTO `t_field_type` VALUES (27, 'CLOB', 'String', NULL, NULL, 'string', '2022-10-16 13:35:12', '2022-10-31 09:02:02', 0);
INSERT INTO `t_field_type` VALUES (28, 'int8', 'Long', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:02:07', 0);
INSERT INTO `t_field_type` VALUES (29, 'int4', 'Integer', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:02:11', 0);
INSERT INTO `t_field_type` VALUES (30, 'int2', 'Integer', NULL, NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:02:15', 0);
INSERT INTO `t_field_type` VALUES (31, 'numeric', 'BigDecimal', 'java.math.BigDecimal', NULL, 'number', '2022-10-16 13:35:12', '2022-10-31 09:02:23', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_project
-- ----------------------------
DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint DEFAULT NULL,
  `project_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '项目名',
  `project_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '项目标识',
  `project_package` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '项目包名',
  `version` varchar(64) DEFAULT NULL,
  `author` varchar(64) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `data_version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_unx` (`user_id`,`project_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1594177081851469825 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='项目名变更';

-- ----------------------------
-- Records of t_project
-- ----------------------------
BEGIN;
INSERT INTO `t_project` VALUES (1594177081851469824, 1586267286674845696, 'light-chat', 'light-chat', 'com.chat', '1.0.0', 'zhou11', '211@qq.com', '2022-11-20 11:53:29', '2022-11-20 11:56:28', 1);
COMMIT;

-- ----------------------------
-- Table structure for t_table
-- ----------------------------
DROP TABLE IF EXISTS `t_table`;
CREATE TABLE `t_table` (
  `id` bigint NOT NULL COMMENT '主键',
  `datasource_id` bigint NOT NULL COMMENT '数据源id',
  `table_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '表名',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `comment` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类说明',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `data_version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `datasoutce_unx` (`datasource_id`,`table_name`) USING BTREE,
  KEY `user_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_table
-- ----------------------------
BEGIN;
INSERT INTO `t_table` VALUES (1596431806202249216, 1589991193516908544, 'friend_request', 1586267286674845696, '', '2022-11-26 17:12:58', '2022-11-26 17:12:58', 0);
INSERT INTO `t_table` VALUES (1596434687081017344, 1589991193516908544, 'group', 1586267286674845696, '', '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0);
INSERT INTO `t_table` VALUES (1596434687303315456, 1589991193516908544, 'message', 1586267286674845696, '', '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0);
INSERT INTO `t_table` VALUES (1596434687517224960, 1589991193516908544, 'sys_oauth_client', 1586267286674845696, '', '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0);
INSERT INTO `t_table` VALUES (1596434687735328768, 1589991193516908544, 'user_friend', 1586267286674845696, '', '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0);
INSERT INTO `t_table` VALUES (1596434687886323712, 1589991193516908544, 'user_group', 1586267286674845696, '', '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0);
INSERT INTO `t_table` VALUES (1596434688024735744, 1589991193516908544, 'user_info', 1586267286674845696, '', '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0);
COMMIT;

-- ----------------------------
-- Table structure for t_table_field
-- ----------------------------
DROP TABLE IF EXISTS `t_table_field`;
CREATE TABLE `t_table_field` (
  `table_id` bigint NOT NULL COMMENT '表ID',
  `field_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段名称',
  `field_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字段类型',
  `field_comment` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字段说明',
  `sort` int DEFAULT NULL COMMENT '排序',
  `auto_fill` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '自动填充  DEFAULT、INSERT、UPDATE、INSERT_UPDATE',
  `primary_pk` tinyint DEFAULT NULL COMMENT '主键 0：否  1：是',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `data_version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `field_default` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字段默认值',
  PRIMARY KEY (`table_id`,`field_name`),
  KEY `table_idx` (`table_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='代码生成表字段';

-- ----------------------------
-- Records of t_table_field
-- ----------------------------
BEGIN;
INSERT INTO `t_table_field` VALUES (1596431806202249216, 'apply_user_id', 'bigint', '申请人id', 1, 'DEFAULT', 0, '2022-11-26 17:22:48', '2022-11-26 17:22:48', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596431806202249216, 'audit_user_id', 'bigint', '审核人id', 2, 'DEFAULT', 0, '2022-11-26 17:22:48', '2022-11-26 17:22:48', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596431806202249216, 'comments', 'varchar', '备注', 3, 'DEFAULT', 0, '2022-11-26 17:22:48', '2022-11-26 17:22:48', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596431806202249216, 'created_at', 'datetime', '创建时间', 6, 'DEFAULT', 0, '2022-11-26 17:22:48', '2022-11-26 17:22:48', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596431806202249216, 'data_version', 'int', '版本号', 8, 'DEFAULT', 0, '2022-11-26 17:22:48', '2022-11-26 17:22:48', 0, '0');
INSERT INTO `t_table_field` VALUES (1596431806202249216, 'id', 'bigint', '主键', 0, 'DEFAULT', 1, '2022-11-26 17:22:48', '2022-11-26 17:22:48', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596431806202249216, 'read_flag', 'tinyint', '是否已读 0：未读，1：已读', 5, 'DEFAULT', 0, '2022-11-26 17:22:48', '2022-11-26 17:22:48', 0, '0');
INSERT INTO `t_table_field` VALUES (1596431806202249216, 'status', 'tinyint', '审核状态：0.已申请，1.已同意，2.已拒绝', 4, 'DEFAULT', 0, '2022-11-26 17:22:48', '2022-11-26 17:22:48', 0, '0');
INSERT INTO `t_table_field` VALUES (1596431806202249216, 'updated_at', 'datetime', '更新时间', 7, 'DEFAULT', 0, '2022-11-26 17:22:48', '2022-11-26 17:22:48', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596434687081017344, 'avatar', 'varchar', '群头像', 2, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687081017344, 'created_at', 'datetime', '创建时间', 4, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596434687081017344, 'data_version', 'int', '版本好', 6, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, '0');
INSERT INTO `t_table_field` VALUES (1596434687081017344, 'gname', 'varchar', '组名', 1, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687081017344, 'id', 'bigint', '主键', 0, 'DEFAULT', 1, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687081017344, 'updated_at', 'datetime', '更新时间', 5, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596434687081017344, 'user_id', 'bigint', '创建者', 3, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687303315456, 'chat_type', 'tinyint', '消息类型，单聊friend 0/群聊group 1', 3, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687303315456, 'content', 'json', '消息内容，json格式，不同消息类型，json格式不一样', 4, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687303315456, 'created_at', 'datetime', '创建时间', 7, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596434687303315456, 'data_version', 'int', '版本号', 9, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, '0');
INSERT INTO `t_table_field` VALUES (1596434687303315456, 'from_id', 'bigint', '消息发送者ID', 1, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687303315456, 'id', 'bigint', '主键', 0, 'DEFAULT', 1, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687303315456, 'read_flag', 'tinyint', '是否已读（单聊可用）', 6, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687303315456, 'send_time', 'datetime', '消息发送时间', 5, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687303315456, 'to_id', 'bigint', '消息接收者ID，如果为群聊消息，则为群ID', 2, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687303315456, 'updated_at', 'datetime', '更新时间', 8, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596434687517224960, 'access_token_validity', 'int', '', 7, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687517224960, 'additional_information', 'varchar', '', 9, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687517224960, 'authorities', 'varchar', '', 6, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687517224960, 'authorized_grant_types', 'varchar', '', 4, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687517224960, 'autoapprove', 'varchar', '', 10, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687517224960, 'client_id', 'varchar', '', 0, 'DEFAULT', 1, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687517224960, 'client_secret', 'varchar', '', 2, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687517224960, 'refresh_token_validity', 'int', '', 8, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687517224960, 'resource_ids', 'varchar', '', 1, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687517224960, 'scope', 'varchar', '', 3, 'DEFAULT', 0, '2022-11-26 17:24:24', '2022-11-26 17:24:24', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687517224960, 'web_server_redirect_uri', 'varchar', '', 5, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687735328768, 'created_at', 'datetime', '创建时间', 3, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596434687735328768, 'data_version', 'int', '版本号', 5, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, '0');
INSERT INTO `t_table_field` VALUES (1596434687735328768, 'friend_id', 'bigint', '朋友id', 2, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687735328768, 'id', 'bigint', '主键', 0, 'DEFAULT', 1, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687735328768, 'updated_at', 'datetime', '更新时间', 4, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596434687735328768, 'user_id', 'bigint', '用户id', 1, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687886323712, 'created_at', 'datetime', '创建时间', 3, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596434687886323712, 'data_version', 'int', '版本号', 5, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, '0');
INSERT INTO `t_table_field` VALUES (1596434687886323712, 'group_id', 'bigint', '组id', 2, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687886323712, 'id', 'bigint', '主键', 0, 'DEFAULT', 1, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434687886323712, 'updated_at', 'datetime', '更新时间', 4, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596434687886323712, 'user_id', 'bigint', '用户id', 1, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434688024735744, 'created_at', 'datetime', '创建时间', 6, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596434688024735744, 'data_version', 'int', '版本号', 8, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, '0');
INSERT INTO `t_table_field` VALUES (1596434688024735744, 'face_image', 'varchar', '头像', 4, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434688024735744, 'id', 'bigint', '主键', 0, 'DEFAULT', 1, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434688024735744, 'nickname', 'varchar', '别名', 1, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434688024735744, 'password', 'varchar', '密码', 3, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434688024735744, 'qrcode', 'varchar', '二维码', 5, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
INSERT INTO `t_table_field` VALUES (1596434688024735744, 'updated_at', 'datetime', '更新时间', 7, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, 'CURRENT_TIMESTAMP');
INSERT INTO `t_table_field` VALUES (1596434688024735744, 'username', 'varchar', '用户名', 2, 'DEFAULT', 0, '2022-11-26 17:24:25', '2022-11-26 17:24:25', 0, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
