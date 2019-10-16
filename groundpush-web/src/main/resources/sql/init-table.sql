/*
Navicat MySQL Data Transfer

Source Server         : 测试环境
Source Server Version : 50727
Source Host           : 106.2.172.227:3306
Source Database       : groundpush_test

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2019-09-27 16:00:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_audit_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_audit_log`;
CREATE TABLE `t_audit_log` (
  `audit_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `task_id` int(20) DEFAULT NULL COMMENT '任务id',
  `order_time` datetime DEFAULT NULL COMMENT '订单时间',
  `audit_status` tinyint(4) DEFAULT NULL COMMENT '审核状态（1：审核通过 2：审核不通过）',
  `status` tinyint(4) DEFAULT NULL COMMENT '当前状态 是否可用（0否，1是）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `audit_opinion` varchar(500) DEFAULT NULL COMMENT '审核意见',
  `user_id` int(20) DEFAULT NULL COMMENT '用户id',
  `created_by` int(20) DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`audit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_audit_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_cashout_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_cashout_log`;
CREATE TABLE `t_cashout_log` (
  `cashout_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `amount` int(11) DEFAULT '0',
  `type` tinyint(1) DEFAULT NULL,
  `order_id` varchar(128) DEFAULT NULL,
  `out_biz_no` varchar(128) DEFAULT NULL,
  `pay_date` datetime DEFAULT NULL,
  `operation_time` datetime DEFAULT NULL,
  PRIMARY KEY (`cashout_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_cashout_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_channel`
-- ----------------------------
DROP TABLE IF EXISTS `t_channel`;
CREATE TABLE `t_channel` (
  `channel_id` int(20) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(200) DEFAULT NULL COMMENT '公司名称',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '当前状态 是否可用（0否，1是）',
  `link_name` varchar(50) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) DEFAULT NULL COMMENT '公司地址',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_by` int(20) DEFAULT NULL,
  PRIMARY KEY (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_channel
-- ----------------------------

-- ----------------------------
-- Table structure for `t_channel_data`
-- ----------------------------
DROP TABLE IF EXISTS `t_channel_data`;
CREATE TABLE `t_channel_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `channel_id` int(11) NOT NULL COMMENT '渠道id',
  `task_id` int(11) NOT NULL COMMENT '任务id',
  `unique_code` varchar(20) NOT NULL COMMENT '唯一标识',
  `channel_time` datetime NOT NULL COMMENT '渠道数据产生时间',
  `is_effective` tinyint(1) DEFAULT NULL,
  `is_exist_order` tinyint(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '说明（主要是失败或成功的说明）',
  `create_time` datetime NOT NULL COMMENT '数据入库时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_channel_data
-- ----------------------------

-- ----------------------------
-- Table structure for `t_channel_excel`
-- ----------------------------
DROP TABLE IF EXISTS `t_channel_excel`;
CREATE TABLE `t_channel_excel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `channel_id` int(11) NOT NULL COMMENT '渠道编号',
  `task_id` int(11) NOT NULL COMMENT '任务ID',
  `file_name` varchar(255) NOT NULL COMMENT '文件名',
  `mapping` json NOT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_use` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tch_tchd` (`channel_id`),
  CONSTRAINT `t_channel_excel_ibfk_1` FOREIGN KEY (`channel_id`) REFERENCES `t_channel` (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='渠道数据';

-- ----------------------------
-- Records of t_channel_excel
-- ----------------------------

-- ----------------------------
-- Table structure for `t_customer`
-- ----------------------------
DROP TABLE IF EXISTS `t_customer`;
CREATE TABLE `t_customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `nick_name` varchar(32) DEFAULT NULL,
  `img_uri` varchar(512) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `invite_code` varchar(32) DEFAULT NULL,
  `reputation` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_customer
-- ----------------------------

-- ----------------------------
-- Table structure for `t_customer_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_customer_account`;
CREATE TABLE `t_customer_account` (
  `customer_account_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `amount` int(11) DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`customer_account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_customer_account
-- ----------------------------

-- ----------------------------
-- Table structure for `t_customer_login_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_customer_login_account`;
CREATE TABLE `t_customer_login_account` (
  `customer_login_account_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `login_no` varchar(64) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`customer_login_account_id`),
  UNIQUE KEY `login_no` (`login_no`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_customer_login_account
-- ----------------------------

-- ----------------------------
-- Table structure for `t_dict`
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `dict_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `dict_type` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dict
-- ----------------------------

-- ----------------------------
-- Table structure for `t_dict_detail`
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_detail`;
CREATE TABLE `t_dict_detail` (
  `detail_id` int(11) NOT NULL AUTO_INCREMENT,
  `dict_id` int(11) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `is_leaf` tinyint(4) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dict_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `t_label`
-- ----------------------------
DROP TABLE IF EXISTS `t_label`;
CREATE TABLE `t_label` (
  `label_id` int(11) NOT NULL AUTO_INCREMENT,
  `label_name` varchar(50) DEFAULT NULL COMMENT '标签名称',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` int(11) DEFAULT NULL COMMENT '创建人',
  `status` tinyint(4) DEFAULT '1' COMMENT '是否可用（0否，1是）',
  `type` tinyint(4) DEFAULT '1' COMMENT '标签类型（0-次要标签，1-主要标签）',
  `sort` int(11) DEFAULT '0' COMMENT '排序大者在前',
  `modify_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`label_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='标签表';

-- ----------------------------
-- Records of t_label
-- ----------------------------

-- ----------------------------
-- Table structure for `t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `code` varchar(64) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `leaf` tinyint(1) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `path` varchar(128) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `t_operation_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_operation_log`;
CREATE TABLE `t_operation_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `method` varchar(255) DEFAULT NULL COMMENT '方法名',
  `args` text COMMENT '请求参数',
  `created_by` int(11) DEFAULT NULL COMMENT '用户ID',
  `operation_detail` varchar(255) DEFAULT NULL COMMENT '日志描述',
  `operation_type` varchar(255) DEFAULT NULL COMMENT '日志类型',
  `run_time` varchar(100) DEFAULT NULL COMMENT '运行时间',
  `type` tinyint(4) DEFAULT '0' COMMENT '类型（0-APP，1-PC）',
  `created_time` datetime DEFAULT NULL,
  `exception_detail` text,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of t_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(128) DEFAULT NULL,
  `channel_uri` varchar(128) DEFAULT NULL,
  `unique_code` varchar(128) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `settlement_amount` decimal(11,2) DEFAULT NULL,
  `settlement_status` tinyint(1) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL COMMENT '审核原因',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for `t_order_bonus`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_bonus`;
CREATE TABLE `t_order_bonus` (
  `bonus_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `bonus_amount` decimal(12,2) DEFAULT NULL,
  `bonus_type` int(11) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `bonus_customer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`bonus_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order_bonus
-- ----------------------------

-- ----------------------------
-- Table structure for `t_order_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_log`;
CREATE TABLE `t_order_log` (
  `log_id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'log主键id',
  `order_id` int(20) DEFAULT NULL COMMENT '订单id',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `unqiue_code` varchar(128) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL COMMENT '上传类型 1:任务结果集上传 2：申诉上传',
  `file_path` varchar(400) DEFAULT NULL COMMENT '文件url',
  `file_name` varchar(200) DEFAULT NULL COMMENT '文件名称oss key',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_order_task_customer`
-- ----------------------------
DROP TABLE IF EXISTS `t_order_task_customer`;
CREATE TABLE `t_order_task_customer` (
  `otc_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  PRIMARY KEY (`otc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order_task_customer
-- ----------------------------

-- ----------------------------
-- Table structure for `t_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `t_privilege`;
CREATE TABLE `t_privilege` (
  `privilege_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `code` varchar(64) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`privilege_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_privilege
-- ----------------------------

-- ----------------------------
-- Table structure for `t_privilege_uri`
-- ----------------------------
DROP TABLE IF EXISTS `t_privilege_uri`;
CREATE TABLE `t_privilege_uri` (
  `privilege_id` int(11) NOT NULL,
  `uri_id` int(11) NOT NULL,
  `status` tinyint(1) DEFAULT '1',
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_privilege_uri
-- ----------------------------

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `code` varchar(64) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------

-- ----------------------------
-- Table structure for `t_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `role_id` int(20) DEFAULT NULL,
  `menu_id` int(20) DEFAULT NULL,
  `created_by` int(20) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(20) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `t_role_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_privilege`;
CREATE TABLE `t_role_privilege` (
  `role_id` int(11) NOT NULL,
  `privilege_id` int(11) NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_privilege
-- ----------------------------

-- ----------------------------
-- Table structure for `t_role_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_user`;
CREATE TABLE `t_role_user` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_user
-- ----------------------------

-- ----------------------------
-- Table structure for `t_task`
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE `t_task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `img_uri` varchar(512) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0' COMMENT '发布状态（0-保存未发布；1-已发布；2-已过期）',
  `source` tinyint(1) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `amount` decimal(8,2) DEFAULT NULL,
  `location` varchar(32) DEFAULT NULL,
  `spread_total` int(11) DEFAULT NULL,
  `handler_num` int(11) DEFAULT NULL,
  `audit_duration` int(11) DEFAULT NULL,
  `expend_time` int(11) DEFAULT NULL,
  `complete_odds` decimal(8,2) DEFAULT NULL,
  `owner_ratio` decimal(8,2) DEFAULT NULL,
  `spread_ratio` decimal(8,2) DEFAULT NULL,
  `leader_ratio` decimal(8,2) DEFAULT NULL,
  `expired_Time` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `is_result` tinyint(1) DEFAULT NULL,
  `icon_uri` varchar(255) DEFAULT NULL COMMENT '缩略图',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `brief_title` varchar(255) DEFAULT NULL COMMENT '简略标题',
  `example_img` varchar(255) DEFAULT NULL COMMENT '示例图片',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_task
-- ----------------------------

-- ----------------------------
-- Table structure for `t_task_attribute`
-- ----------------------------
DROP TABLE IF EXISTS `t_task_attribute`;
CREATE TABLE `t_task_attribute` (
  `attribute_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL,
  `name` varchar(512) DEFAULT NULL,
  `content` varchar(512) DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL COMMENT '类型（1-我的任务编辑，2-推广任务编辑）',
  `seq` int(5) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `label_type` tinyint(1) DEFAULT NULL COMMENT '任务阶段，',
  `row_type` tinyint(4) DEFAULT NULL COMMENT '单行类型（1-标题，2-内容，3-URL，4-下载APP，5-扫描二维码，6-保存二维码，7-图片）',
  `create_uri` tinyint(4) DEFAULT '0' COMMENT '是否生成URL(0-否，1-是)',
  `img_code` varchar(50) DEFAULT NULL COMMENT '图片唯一识别码',
  PRIMARY KEY (`attribute_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_task_attribute
-- ----------------------------

-- ----------------------------
-- Table structure for `t_task_collect`
-- ----------------------------
DROP TABLE IF EXISTS `t_task_collect`;
CREATE TABLE `t_task_collect` (
  `collect_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`collect_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_task_collect
-- ----------------------------

-- ----------------------------
-- Table structure for `t_task_label`
-- ----------------------------
DROP TABLE IF EXISTS `t_task_label`;
CREATE TABLE `t_task_label` (
  `tl_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL COMMENT '任务ID',
  `label_id` int(11) NOT NULL COMMENT '标签ID',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`tl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='任务-标签关联表';

-- ----------------------------
-- Records of t_task_label
-- ----------------------------

-- ----------------------------
-- Table structure for `t_task_uri`
-- ----------------------------
DROP TABLE IF EXISTS `t_task_uri`;
CREATE TABLE `t_task_uri` (
  `task_uri_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '任务uri主键',
  `uri` varchar(500) DEFAULT NULL COMMENT 'uri链接',
  `created_by` int(20) DEFAULT NULL COMMENT '创建人',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modified_by` int(20) DEFAULT NULL COMMENT '最后创建人',
  `last_modified_time` datetime DEFAULT NULL COMMENT '最后创建时间',
  `task_id` int(20) DEFAULT NULL COMMENT '任务id',
  PRIMARY KEY (`task_uri_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_task_uri
-- ----------------------------

-- ----------------------------
-- Table structure for `t_uri`
-- ----------------------------
DROP TABLE IF EXISTS `t_uri`;
CREATE TABLE `t_uri` (
  `uri_id` int(11) NOT NULL AUTO_INCREMENT,
  `uri_pattern` varchar(128) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  `uri_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uri_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_uri
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_no` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `name_pinyin` varchar(32) DEFAULT NULL,
  `mobile_no` varchar(32) DEFAULT NULL,
  `work_email` varchar(64) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `photo` varchar(128) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user_account`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_account`;
CREATE TABLE `t_user_account` (
  `user_id` int(11) NOT NULL,
  `password` varchar(128) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `password_error_count` int(5) DEFAULT NULL,
  `history_password` varchar(128) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `unlock_time` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_account
-- ----------------------------

alter table t_channel_excel add column channel_time datetime;
alter table t_order add column is_special tinyint(1) DEFAULT NULL;
alter table t_task add column spread_parent_ratio decimal(8,2) DEFAULT NULL;
alter table t_task drop column owner_ratio;


---------  2019.10添加sql author：hss----------------------------

-- 特殊任务表
DROP TABLE IF EXISTS `t_special_task`;
CREATE TABLE `t_special_task` (
  `special_task_id` int(11) NOT NULL AUTO_INCREMENT,
  `team_id` int(20) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `task_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`special_task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 团队表
DROP TABLE IF EXISTS `t_team`;
CREATE TABLE `t_team` (
  `team_id` int(20) NOT NULL AUTO_INCREMENT,
  `team_name` varchar(500) DEFAULT NULL,
  `created_by` int(20) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`team_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- 团队客户关联表
DROP TABLE IF EXISTS `t_team_customer`;
CREATE TABLE `t_team_customer` (
  `team_id` int(20) DEFAULT NULL,
  `customer_id` int(20) DEFAULT NULL,
  `created_by` int(20) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table t_task add column task_title VARCHAR(255) default null;
alter table t_task add column task_content VARCHAR(255) default null;