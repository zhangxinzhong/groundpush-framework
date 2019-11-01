-- 初始化两个超级管理员  admin、manager
insert into t_user(login_no, name, name_pinyin, mobile_no, work_email, status, photo, created_by, created_time, last_modified, last_modified_time) values('admin','admin','admin','13888888888','admin@zhongdi.com',1,null,null,current_timestamp,null,null);
insert into t_user(login_no, name, name_pinyin, mobile_no, work_email, status, photo, created_by, created_time, last_modified, last_modified_time) values('manager','manager','manager','13999999999','manager@zhongdi.com',1,null,null,current_timestamp,null,null);

-- 添加虚拟用户
insert into t_customer(parent_id, nick_name, img_uri, status, invite_code, reputation, created_time) values (null,'virtual',null,1,null,null,current_timestamp);
insert into t_customer_login_account (customer_id, login_no, name, password, type, created_time) values (1,13333333333,'virtual',null,1,current_timestamp);
insert into t_customer_account(customer_id,created_time) values (1,current_timestamp);

-- 添加admin、manager 相关密码账户信息
insert into t_user_account(user_id, password, status, password_error_count, history_password, last_login_time, unlock_time, created_time, last_modified_time)values (1,'$2a$10$XK3KZVZ7jnOWEelQ4k7KMuANHn8.7Pxk43xXwkKwpzANnZ3U2cMzC',0,0,null,null,null,null,null);
insert into t_user_account(user_id, password, status, password_error_count, history_password, last_login_time, unlock_time, created_time, last_modified_time)values (2,'$2a$10$YBRQ5001G0gGZ0BQluB61eCmJx.s1.qFFFLfamceWNZEGaC3Pg7pO',0,0,null,null,null,null,null);

-- 创建超级管理员角色
insert into t_role(name,code, status, created_by, created_time, last_modified_by, last_modified_time)values ('超级管理员','super_admin',0,null,null,null,null);
insert into t_role(name,code, status, created_by, created_time, last_modified_by, last_modified_time)values ('普通用户','user',0,null,null,null,null);

--  创建超管角色与用户关联
insert into t_role_user(user_id, role_id, status, created_by, created_time, last_modified_by, last_modified_time)values (1,1,0,null,null,null,null);
insert into t_role_user(user_id, role_id, status, created_by, created_time, last_modified_by, last_modified_time)values (2,1,0,null,null,null,null);


-- 添加菜单

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('10', '首页', null, null, '1', '0', '1', '/page', null, '2019-09-18 10:00:40', null, '2019-09-18 17:54:56');
INSERT INTO `t_menu` VALUES ('11', '任务管理', '100010', '0', '2', '0', '1', '/task/toTaskList', null, '2019-09-18 10:04:48', null, null);
INSERT INTO `t_menu` VALUES ('12', '支付管理', '100011', '0', '3', '0', '1', '/payManage/toPay', null, '2019-09-18 10:06:50', null, null);
INSERT INTO `t_menu` VALUES ('13', '订单管理', '100012', '0', '4', '0', '1', '/order/toOrder', null, '2019-09-18 10:08:49', null, null);
INSERT INTO `t_menu` VALUES ('14', '渠道管理', '100013', '0', '5', '0', '1', '/channel/toChannel', null, '2019-09-18 10:09:13', null, null);
INSERT INTO `t_menu` VALUES ('15', '标签管理', '100014', '0', '6', '0', '1', '/label/toLabel', null, '2019-09-18 10:09:28', null, null);
INSERT INTO `t_menu` VALUES ('17', '菜单管理', '100016', '0', '8', '0', '1', '/menu/toMenuList', null, '2019-09-18 10:17:23', null, null);
INSERT INTO `t_menu` VALUES ('18', '数据字典', '100017', '0', '9', '0', '1', '/dict/toDictList', null, '2019-09-18 10:17:46', null, null);
INSERT INTO `t_menu` VALUES ('19', '用户管理', '100018', '0', '10', '0', '1', '/user/toUser', null, '2019-09-18 10:18:11', null, null);
INSERT INTO `t_menu` VALUES ('20', '角色管理', '100019', '0', '11', '0', '1', '/role/toRole', null, '2019-09-18 10:18:57', null, null);
INSERT INTO `t_menu` VALUES ('21', '权限管理', null, null, '12', '0', '1', '/privilege/toPrivilegeList', null, '2019-09-18 10:19:42', null, '2019-09-20 13:59:41');
INSERT INTO `t_menu` VALUES ('22', 'URI管理', null, null, '13', '0', '1', '/uri/toUriList', null, '2019-09-18 10:20:20', null, '2019-09-19 13:45:05');
INSERT INTO `t_menu` VALUES ('23', 'Home', '100022', '0', '13', '0', '1', '/home', null, '2019-09-18 20:09:00', null, null);
INSERT INTO `t_menu` VALUES ('24', '日志管理', '100023', '0', '14', '0', '1', '/operationLog/toOperationLogList', null, '2019-09-26 09:33:45', null, null);
INSERT INTO `t_menu` VALUES ('25', '特殊任务管理', null, null, '15', '0', '1', '/specialTask/toSpecialTask', null, '2019-10-11 09:17:01', null, '2019-10-11 09:18:12');
INSERT INTO `t_menu` VALUES ('26', '客户管理', '100025', '0', '16', '0', '1', '/customer/toCustomerList', null, '2019-10-11 10:00:38', null, null);
INSERT INTO `t_menu` VALUES ('27', '团队管理', '100026', '0', '17', '0', '1', '/team/toTeam', null, '2019-10-12 10:42:21', null, null);
INSERT INTO `t_menu` VALUES ('28', '版本管理', '100027', '0', '18', '0', '1', '/version/toVersionList', null, '2019-10-16 10:50:16', null, null);


-- 添加超管角色与菜单关联
SET FOREIGN_KEY_CHECKS=0;

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

INSERT INTO `t_role_menu` VALUES ('1', '10', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '11', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '12', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '13', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '14', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '15', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '17', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '18', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '19', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '20', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '21', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '22', '1', '2019-09-26 09:35:34', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '24', '1', '2019-09-26 09:35:34', null, null, '1');
-- 添加普通角色与菜单关联
INSERT INTO `t_role_menu` VALUES ('2', '21', '1', '2019-09-18 18:44:18', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('3', '22', '1', '2019-09-18 18:44:18', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '25', '1', '2019-10-16 10:53:00', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '26', '1', '2019-10-16 10:53:00', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '27', '1', '2019-10-16 10:53:00', null, null, '1');
INSERT INTO `t_role_menu` VALUES ('1', '28', '1', '2019-10-16 10:53:00', null, null, '1');






-- 添加权限
SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_privilege
-- ----------------------------
INSERT INTO `t_privilege` VALUES ('6', '默认权限', 'default_privilege', null, null, '2019-09-23 12:57:03', null, '2019-09-23 12:57:28');
INSERT INTO `t_privilege` VALUES ('7', '菜单管理权限', 'menu_privilege', null, null, '2019-09-26 13:59:24', null, '2019-09-26 13:59:24');
INSERT INTO `t_privilege` VALUES ('8', '渠道管理权限', 'channel_privilege', null, null, '2019-09-27 13:48:51', null, '2019-09-27 13:57:01');
INSERT INTO `t_privilege` VALUES ('9', '标签管理权限', 'label_privilege', null, null, '2019-09-27 13:56:51', null, '2019-09-27 13:57:04');
INSERT INTO `t_privilege` VALUES ('10', '订单管理权限', 'order_privilege', null, null, '2019-09-27 14:22:26', null, '2019-09-27 14:22:26');
INSERT INTO `t_privilege` VALUES ('11', '角色管理权限', 'role_privilege', null, null, '2019-09-27 14:24:35', null, '2019-09-27 14:24:35');
INSERT INTO `t_privilege` VALUES ('12', '支付管理权限', 'pay_privilege', null, null, '2019-09-27 15:06:47', null, '2019-09-27 15:06:47');
INSERT INTO `t_privilege` VALUES ('13', '任务管理权限', 'task_privilege', null, null, '2019-09-27 15:28:24', null, '2019-09-27 15:28:24');
INSERT INTO `t_privilege` VALUES ('14', '日志管理权限', 'operationLog_privilege', null, null, '2019-09-27 15:30:48', null, '2019-09-27 15:30:48');
INSERT INTO `t_privilege` VALUES ('15', 'URI管理权限', 'uri_privilege', null, null, '2019-09-27 15:32:23', null, '2019-09-27 15:32:23');
INSERT INTO `t_privilege` VALUES ('16', '权限管理权限', 'privilege_privilege', null, null, '2019-09-27 15:37:57', null, '2019-09-27 15:37:57');
INSERT INTO `t_privilege` VALUES ('17', '字典管理权限', 'dict_privilege', null, null, '2019-09-27 15:47:25', null, '2019-09-27 15:47:25');
INSERT INTO `t_privilege` VALUES ('18', '用户管理权限', 'user_privilege', null, null, '2019-09-27 15:53:29', null, '2019-09-27 15:53:29');



-- 添加权限uri关联
SET FOREIGN_KEY_CHECKS=0;

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
INSERT INTO `t_privilege_uri` VALUES ('1', '1', '1', null, null, null, null);
INSERT INTO `t_privilege_uri` VALUES ('1', '2', '1', null, null, null, null);
INSERT INTO `t_privilege_uri` VALUES ('2', '1', '1', null, null, null, null);
INSERT INTO `t_privilege_uri` VALUES ('2', '2', '1', null, null, null, null);
INSERT INTO `t_privilege_uri` VALUES ('3', '5', null, null, '2019-09-21 11:34:40', null, '2019-09-21 11:49:09');
INSERT INTO `t_privilege_uri` VALUES ('3', '11', null, null, '2019-09-21 11:49:30', null, '2019-09-21 11:49:30');
INSERT INTO `t_privilege_uri` VALUES ('3', '10', null, null, '2019-09-21 11:49:40', null, '2019-09-21 11:49:40');
INSERT INTO `t_privilege_uri` VALUES ('7', '14', '1', '1', '2019-09-26 13:59:36', null, null);
INSERT INTO `t_privilege_uri` VALUES ('7', '15', '1', '1', '2019-09-26 13:59:36', null, null);
INSERT INTO `t_privilege_uri` VALUES ('7', '16', '1', '1', '2019-09-26 13:59:36', null, null);
INSERT INTO `t_privilege_uri` VALUES ('7', '17', '1', '1', '2019-09-26 13:59:36', null, null);
INSERT INTO `t_privilege_uri` VALUES ('7', '18', '1', '1', '2019-09-26 13:59:36', null, null);
INSERT INTO `t_privilege_uri` VALUES ('7', '19', '1', '1', '2019-09-26 13:59:36', null, null);
INSERT INTO `t_privilege_uri` VALUES ('8', '20', '1', '1', '2019-09-27 13:49:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('8', '21', '1', '1', '2019-09-27 13:49:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('8', '22', '1', '1', '2019-09-27 13:49:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('8', '23', '1', '1', '2019-09-27 13:49:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('8', '24', '1', '1', '2019-09-27 13:49:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('8', '25', '1', '1', '2019-09-27 13:49:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('8', '26', '1', '1', '2019-09-27 13:49:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('8', '27', '1', '1', '2019-09-27 13:49:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('8', '28', '1', '1', '2019-09-27 13:49:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('9', '33', '1', '1', '2019-09-27 14:16:57', null, null);
INSERT INTO `t_privilege_uri` VALUES ('9', '32', '1', '1', '2019-09-27 14:16:57', null, null);
INSERT INTO `t_privilege_uri` VALUES ('9', '31', '1', '1', '2019-09-27 14:16:57', null, null);
INSERT INTO `t_privilege_uri` VALUES ('9', '30', '1', '1', '2019-09-27 14:16:57', null, null);
INSERT INTO `t_privilege_uri` VALUES ('9', '29', '1', '1', '2019-09-27 14:16:57', null, null);
INSERT INTO `t_privilege_uri` VALUES ('9', '35', '1', '1', '2019-09-27 14:16:57', null, null);
INSERT INTO `t_privilege_uri` VALUES ('9', '34', '1', '1', '2019-09-27 14:16:57', null, null);
INSERT INTO `t_privilege_uri` VALUES ('6', '12', '1', '1', '2019-09-27 14:18:33', null, null);
INSERT INTO `t_privilege_uri` VALUES ('6', '13', '1', '1', '2019-09-27 14:18:33', null, null);
INSERT INTO `t_privilege_uri` VALUES ('10', '39', '1', '1', '2019-09-27 14:22:41', null, null);
INSERT INTO `t_privilege_uri` VALUES ('10', '38', '1', '1', '2019-09-27 14:22:41', null, null);
INSERT INTO `t_privilege_uri` VALUES ('10', '37', '1', '1', '2019-09-27 14:22:41', null, null);
INSERT INTO `t_privilege_uri` VALUES ('10', '36', '1', '1', '2019-09-27 14:22:41', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '54', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '55', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '56', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '57', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '58', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '59', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '60', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '51', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '49', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '48', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '46', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '44', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '41', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('11', '40', '1', '1', '2019-09-27 15:05:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('12', '65', '1', '1', '2019-09-27 15:11:12', null, null);
INSERT INTO `t_privilege_uri` VALUES ('12', '64', '1', '1', '2019-09-27 15:11:12', null, null);
INSERT INTO `t_privilege_uri` VALUES ('12', '63', '1', '1', '2019-09-27 15:11:12', null, null);
INSERT INTO `t_privilege_uri` VALUES ('12', '62', '1', '1', '2019-09-27 15:11:12', null, null);
INSERT INTO `t_privilege_uri` VALUES ('12', '61', '1', '1', '2019-09-27 15:11:12', null, null);
INSERT INTO `t_privilege_uri` VALUES ('13', '42', '1', '1', '2019-09-27 15:29:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('13', '43', '1', '1', '2019-09-27 15:29:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('13', '45', '1', '1', '2019-09-27 15:29:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('13', '47', '1', '1', '2019-09-27 15:29:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('13', '50', '1', '1', '2019-09-27 15:29:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('13', '52', '1', '1', '2019-09-27 15:29:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('13', '53', '1', '1', '2019-09-27 15:29:22', null, null);
INSERT INTO `t_privilege_uri` VALUES ('14', '84', '1', '1', '2019-09-27 15:31:03', null, null);
INSERT INTO `t_privilege_uri` VALUES ('15', '75', '1', '1', '2019-09-27 15:34:53', null, null);
INSERT INTO `t_privilege_uri` VALUES ('15', '76', '1', '1', '2019-09-27 15:34:53', null, null);
INSERT INTO `t_privilege_uri` VALUES ('15', '77', '1', '1', '2019-09-27 15:34:53', null, null);
INSERT INTO `t_privilege_uri` VALUES ('15', '78', '1', '1', '2019-09-27 15:34:53', null, null);
INSERT INTO `t_privilege_uri` VALUES ('15', '79', '1', '1', '2019-09-27 15:34:53', null, null);
INSERT INTO `t_privilege_uri` VALUES ('15', '80', '1', '1', '2019-09-27 15:34:53', null, null);
INSERT INTO `t_privilege_uri` VALUES ('16', '66', '1', '1', '2019-09-27 15:42:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('16', '67', '1', '1', '2019-09-27 15:42:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('16', '68', '1', '1', '2019-09-27 15:42:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('16', '69', '1', '1', '2019-09-27 15:42:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('16', '70', '1', '1', '2019-09-27 15:42:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('16', '71', '1', '1', '2019-09-27 15:42:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('16', '72', '1', '1', '2019-09-27 15:42:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('16', '73', '1', '1', '2019-09-27 15:42:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('16', '74', '1', '1', '2019-09-27 15:42:08', null, null);
INSERT INTO `t_privilege_uri` VALUES ('17', '81', '1', '1', '2019-09-27 15:47:58', null, null);
INSERT INTO `t_privilege_uri` VALUES ('17', '85', '1', '1', '2019-09-27 15:47:58', null, null);
INSERT INTO `t_privilege_uri` VALUES ('17', '86', '1', '1', '2019-09-27 15:47:58', null, null);
INSERT INTO `t_privilege_uri` VALUES ('17', '82', '1', '1', '2019-09-27 15:47:58', null, null);
INSERT INTO `t_privilege_uri` VALUES ('18', '88', '1', '1', '2019-09-27 15:53:41', null, null);
INSERT INTO `t_privilege_uri` VALUES ('18', '87', '1', '1', '2019-09-27 15:53:41', null, null);



-- 添加uri
SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_uri
-- ----------------------------
INSERT INTO `t_uri` VALUES ('12', '/home', null, null, '2019-09-23 12:03:28', null, '2019-09-23 12:03:28', '访问Home页面');
INSERT INTO `t_uri` VALUES ('13', '/page', null, null, '2019-09-23 12:56:24', null, '2019-09-23 12:56:24', '访问Page页面');
INSERT INTO `t_uri` VALUES ('14', '/menu/toMenuList', null, null, '2019-09-26 13:53:36', null, '2019-09-26 13:54:59', '菜单管理');
INSERT INTO `t_uri` VALUES ('15', '/menu/loadMenu', null, null, '2019-09-26 13:55:51', null, '2019-09-26 13:55:51', '菜单管理表格数据');
INSERT INTO `t_uri` VALUES ('16', '/menu/add', null, null, '2019-09-26 13:56:11', null, '2019-09-26 13:56:11', '新增菜单');
INSERT INTO `t_uri` VALUES ('17', '/menu/detail', null, null, '2019-09-26 13:57:09', null, '2019-09-26 13:57:09', '菜单详情');
INSERT INTO `t_uri` VALUES ('18', '/menu/edit', null, null, '2019-09-26 13:57:45', null, '2019-09-26 13:57:45', '菜单编辑');
INSERT INTO `t_uri` VALUES ('19', '/menu/del', null, null, '2019-09-26 13:58:19', null, '2019-09-26 13:58:19', '菜单删除');
INSERT INTO `t_uri` VALUES ('20', '/channel/toChannel', null, null, '2019-09-27 13:29:19', null, '2019-09-27 13:29:19', '渠道管理');
INSERT INTO `t_uri` VALUES ('21', '/channel/getChannelPage', null, null, '2019-09-27 13:30:19', null, '2019-09-27 13:30:19', '渠道管理表格数据');
INSERT INTO `t_uri` VALUES ('22', '/channel/addChannel', null, null, '2019-09-27 13:31:07', null, '2019-09-27 13:31:07', '新增渠道');
INSERT INTO `t_uri` VALUES ('23', '/channel/updateChannel', null, null, '2019-09-27 13:31:36', null, '2019-09-27 13:31:36', '渠道编辑');
INSERT INTO `t_uri` VALUES ('24', '/channel/delChannel', null, null, '2019-09-27 13:32:26', null, '2019-09-27 13:32:26', '渠道删除');
INSERT INTO `t_uri` VALUES ('25', '/channel/importExcelData', null, null, '2019-09-27 13:42:34', null, '2019-09-27 13:42:34', '渠道导入数据');
INSERT INTO `t_uri` VALUES ('26', '/channel/getExcelTitle', null, null, '2019-09-27 13:44:06', null, '2019-09-27 13:44:06', '渠道导入excel');
INSERT INTO `t_uri` VALUES ('27', '/channel/detailChannel', null, null, '2019-09-27 13:44:51', null, '2019-09-27 13:44:51', '渠道详情');
INSERT INTO `t_uri` VALUES ('28', '/channel/getChannelAll', null, null, '2019-09-27 13:46:58', null, '2019-09-27 13:46:58', '查詢所有渠道列表');
INSERT INTO `t_uri` VALUES ('29', '/label/toLabel', null, null, '2019-09-27 13:51:12', null, '2019-09-27 13:51:12', '标签管理');
INSERT INTO `t_uri` VALUES ('30', '/label/getLabelPage', null, null, '2019-09-27 13:51:40', null, '2019-09-27 13:51:40', '标签管理表格数据');
INSERT INTO `t_uri` VALUES ('31', '/label/addLabel', null, null, '2019-09-27 13:52:40', null, '2019-09-27 13:52:40', '标签新增');
INSERT INTO `t_uri` VALUES ('32', '/label/updateLabel', null, null, '2019-09-27 13:53:25', null, '2019-09-27 13:53:25', '标签编辑');
INSERT INTO `t_uri` VALUES ('33', '/label/detailLabel', null, null, '2019-09-27 13:54:37', null, '2019-09-27 13:54:37', '标签详情');
INSERT INTO `t_uri` VALUES ('34', '/label/getLabelAll', null, null, '2019-09-27 13:55:38', null, '2019-09-27 13:57:59', '查询所有标签列表');
INSERT INTO `t_uri` VALUES ('35', '/label/delLabel', null, null, '2019-09-27 13:58:35', null, '2019-09-27 13:58:35', '标签删除');
INSERT INTO `t_uri` VALUES ('36', '/order/toOrder', null, null, '2019-09-27 14:19:46', null, '2019-09-27 14:19:46', '订单管理');
INSERT INTO `t_uri` VALUES ('37', '/order', null, null, '2019-09-27 14:20:18', null, '2019-09-27 14:20:18', '订单管理数据列表');
INSERT INTO `t_uri` VALUES ('38', '/order/queryOrderByKeys', null, null, '2019-09-27 14:21:10', null, '2019-09-27 14:21:10', '查询订单管理列表');
INSERT INTO `t_uri` VALUES ('39', '/order/editOrder', null, null, '2019-09-27 14:21:30', null, '2019-09-27 14:21:30', '订单编辑');
INSERT INTO `t_uri` VALUES ('40', '/role/toRole', null, null, '2019-09-27 14:27:06', null, '2019-09-27 14:27:06', '角色管理');
INSERT INTO `t_uri` VALUES ('41', '/role/queryAllRoles', null, null, '2019-09-27 14:27:40', null, '2019-09-27 14:27:40', '角色管理表格数据');
INSERT INTO `t_uri` VALUES ('42', '/task/toTaskList', null, null, '2019-09-27 14:27:49', null, '2019-09-27 14:27:49', '任务管理');
INSERT INTO `t_uri` VALUES ('43', '/task/save', null, null, '2019-09-27 14:29:26', null, '2019-09-27 14:29:26', '保存任务');
INSERT INTO `t_uri` VALUES ('44', '/role/addRole', null, null, '2019-09-27 14:32:39', null, '2019-09-27 14:32:39', '角色新增');
INSERT INTO `t_uri` VALUES ('45', '/task/updateTaskStatus', null, null, '2019-09-27 14:32:54', null, '2019-09-27 14:32:54', '删除任务');
INSERT INTO `t_uri` VALUES ('46', '/role/delRole', null, null, '2019-09-27 14:33:00', null, '2019-09-27 14:33:00', '角色删除');
INSERT INTO `t_uri` VALUES ('47', '/task/updateTaskStatus', null, null, '2019-09-27 14:33:09', null, '2019-09-27 14:33:09', '发布任务');
INSERT INTO `t_uri` VALUES ('48', '/role/updateRole', null, null, '2019-09-27 14:33:24', null, '2019-09-27 14:33:24', '角色编辑');
INSERT INTO `t_uri` VALUES ('49', '/role/addRoleUser', null, null, '2019-09-27 14:34:14', null, '2019-09-27 14:34:14', '角色用户关联');
INSERT INTO `t_uri` VALUES ('50', '/task/getTaskPageList', null, null, '2019-09-27 14:34:45', null, '2019-09-27 14:34:45', '任务分页列表');
INSERT INTO `t_uri` VALUES ('51', '/role/addPrivilege', null, null, '2019-09-27 14:35:04', null, '2019-09-27 14:35:04', '角色权限关联');
INSERT INTO `t_uri` VALUES ('52', '/task/getTask/*', null, null, '2019-09-27 14:37:19', null, '2019-09-27 14:40:13', '任务详情');
INSERT INTO `t_uri` VALUES ('53', '/task/uploadExcel', null, null, '2019-09-27 14:40:48', null, '2019-09-27 14:40:48', '上传任务URL');
INSERT INTO `t_uri` VALUES ('54', '/role/addRoleMenu', null, null, '2019-09-27 14:58:06', null, '2019-09-27 14:58:06', '角色菜单关联');
INSERT INTO `t_uri` VALUES ('55', '/role/findUsersByRoleId', null, null, '2019-09-27 15:00:32', null, '2019-09-27 15:00:32', '查询角色关联用户');
INSERT INTO `t_uri` VALUES ('56', '/role/findPrivilegesByRoleId', null, null, '2019-09-27 15:01:29', null, '2019-09-27 15:01:29', '查询角色关联权限');
INSERT INTO `t_uri` VALUES ('57', '/role/findMenusByRoleId', null, null, '2019-09-27 15:02:10', null, '2019-09-27 15:02:10', '查询角色关联菜单');
INSERT INTO `t_uri` VALUES ('58', '/role/queryAllUsersPages', null, null, '2019-09-27 15:02:51', null, '2019-09-27 15:02:51', '角色管理查询所有用户');
INSERT INTO `t_uri` VALUES ('59', '/role/queryAllMenusPages', null, null, '2019-09-27 15:03:11', null, '2019-09-27 15:03:11', '角色管理查询所有菜单');
INSERT INTO `t_uri` VALUES ('60', '/role/queryAllPrivilegePages', null, null, '2019-09-27 15:03:50', null, '2019-09-27 15:03:50', '角色管理查询所有权限');
INSERT INTO `t_uri` VALUES ('61', '/payManage/toPay', null, null, '2019-09-27 15:07:46', null, '2019-09-27 15:07:46', '支付管理');
INSERT INTO `t_uri` VALUES ('62', '/payManage/getTaskOrderlist', null, null, '2019-09-27 15:08:39', null, '2019-09-27 15:08:39', '支付管理表格数据');
INSERT INTO `t_uri` VALUES ('63', '/payManage/addAuditLog', null, null, '2019-09-27 15:09:23', null, '2019-09-27 15:09:23', '支付管理审核');
INSERT INTO `t_uri` VALUES ('64', '/payManage/queryOrderList', null, null, '2019-09-27 15:10:15', null, '2019-09-27 15:10:15', '支付管理查询订单列表');
INSERT INTO `t_uri` VALUES ('65', '/payManage', null, null, '2019-09-27 15:10:47', null, '2019-09-27 15:10:47', '支付管理支付');
INSERT INTO `t_uri` VALUES ('66', '/privilege/toPrivilegeList', null, null, '2019-09-27 15:11:00', null, '2019-09-27 15:11:00', '权限管理');
INSERT INTO `t_uri` VALUES ('67', '/privilege/getPrivilegePageList', null, null, '2019-09-27 15:11:54', null, '2019-09-27 15:11:54', '权限列表');
INSERT INTO `t_uri` VALUES ('68', '/privilege/save', null, null, '2019-09-27 15:13:14', null, '2019-09-27 15:13:14', '权限保存');
INSERT INTO `t_uri` VALUES ('69', '/privilege/getPrivilege/*', null, null, '2019-09-27 15:13:43', null, '2019-09-27 15:13:43', '获取权限');
INSERT INTO `t_uri` VALUES ('70', '/privilege/del', null, null, '2019-09-27 15:14:07', null, '2019-09-27 15:14:07', '权限删除');
INSERT INTO `t_uri` VALUES ('71', '/privilegeUri/*/getPrivilegeUriList', null, null, '2019-09-27 15:15:25', null, '2019-09-27 15:15:25', '权限URI关联表查询服务');
INSERT INTO `t_uri` VALUES ('72', '/privilegeUri/save', null, null, '2019-09-27 15:15:48', null, '2019-09-27 15:15:48', '权限URI关联表保存服务');
INSERT INTO `t_uri` VALUES ('73', '/privilegeUri/getPrivilegeUri', null, null, '2019-09-27 15:16:09', null, '2019-09-27 15:16:09', '获取权限URI关联表信息');
INSERT INTO `t_uri` VALUES ('74', '/privilegeUri/del', null, null, '2019-09-27 15:16:28', null, '2019-09-27 15:16:28', '权限URI关联删除');
INSERT INTO `t_uri` VALUES ('75', '/uri/toUriList', null, null, '2019-09-27 15:17:10', null, '2019-09-27 15:17:10', 'uri管理');
INSERT INTO `t_uri` VALUES ('76', '/uri/getUriPageList', null, null, '2019-09-27 15:17:31', null, '2019-09-27 15:18:02', 'uri查询服务');
INSERT INTO `t_uri` VALUES ('77', '/uri/save', null, null, '2019-09-27 15:17:54', null, '2019-09-27 15:17:54', 'uri保存服务');
INSERT INTO `t_uri` VALUES ('78', '/uri/getUri/*', null, null, '2019-09-27 15:18:21', null, '2019-09-27 15:18:21', '获取uri');
INSERT INTO `t_uri` VALUES ('79', '/uri/del', null, null, '2019-09-27 15:18:47', null, '2019-09-27 15:18:47', 'uri删除');
INSERT INTO `t_uri` VALUES ('80', '/uri/getUriALL', null, null, '2019-09-27 15:19:27', null, '2019-09-27 15:19:27', '获取所有uri');
INSERT INTO `t_uri` VALUES ('81', '/dict/toDictList', null, null, '2019-09-27 15:21:49', null, '2019-09-27 15:21:49', '字典管理');
INSERT INTO `t_uri` VALUES ('82', '/dict/*', null, null, '2019-09-27 15:23:22', null, '2019-09-27 15:23:22', '查看字典信息');
INSERT INTO `t_uri` VALUES ('83', '/operationLog/toOperationLogList', null, null, '2019-09-27 15:25:07', null, '2019-09-27 15:25:07', '日志管理');
INSERT INTO `t_uri` VALUES ('84', '/operationLog/getOperationLogList', null, null, '2019-09-27 15:25:36', null, '2019-09-27 15:25:36', '日志分页列表');
INSERT INTO `t_uri` VALUES ('85', '/dict/*/dictDetail', null, null, '2019-09-27 15:46:25', null, '2019-09-27 15:46:25', '查询某字段下所有字典项');
INSERT INTO `t_uri` VALUES ('86', '/dict', null, null, '2019-09-27 15:46:55', null, '2019-09-27 15:46:55', '字典CRUD');
INSERT INTO `t_uri` VALUES ('87', '/user/toUser', null, null, '2019-09-27 15:51:33', null, '2019-09-27 15:51:33', '用户管理');
INSERT INTO `t_uri` VALUES ('88', '/user', null, null, '2019-09-27 15:52:46', null, '2019-09-27 15:52:46', '用户CRUD');


-- 添加权限角色关联
SET FOREIGN_KEY_CHECKS=0;

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
INSERT INTO `t_role_privilege` VALUES ('2', '6', '1', '1', '2019-09-23 15:33:47', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '7', '1', '1', '2019-09-27 16:19:43', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '8', '1', '1', '2019-09-27 16:19:43', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '9', '1', '1', '2019-09-27 16:19:43', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '10', '1', '1', '2019-09-27 16:19:43', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '11', '1', '1', '2019-09-27 16:19:43', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '12', '1', '1', '2019-09-27 16:19:43', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '13', '1', '1', '2019-09-27 16:19:43', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '14', '1', '1', '2019-09-27 16:19:43', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '15', '1', '1', '2019-09-27 16:19:43', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '16', '1', '1', '2019-09-27 16:19:43', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '17', '1', '1', '2019-09-27 16:19:43', null, null);
INSERT INTO `t_role_privilege` VALUES ('1', '18', '1', '1', '2019-09-27 16:19:43', null, null);

-- ----------------------
-- 设置数据库时区为默认东八时区   注意：！！！必须将mysql配置文件my.ini中的default-time-zone='+08:00'！！！
-- ----------------------
set global time_zone = '+08:00';
set time_zone = '+08:00';
flush privileges;