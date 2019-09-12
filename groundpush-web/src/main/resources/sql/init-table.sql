-- 用户表
drop table if exists t_user;
CREATE TABLE `t_user` (
    `user_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `login_no` varchar(64) DEFAULT NULL,
    `name` varchar(64) DEFAULT NULL,
    `name_pinyin` varchar(32) DEFAULT NULL,
    `mobile_no` varchar(32) DEFAULT NULL,
    `work_email` varchar(64) DEFAULT NULL,
    `status` tinyint(1) DEFAULT NULL,
    `photo` varchar(128) DEFAULT NULL,
    `created_by` int(11) DEFAULT NULL,
    `created_time` datetime  DEFAULT NULL,
    `last_modified` int(11) DEFAULT NULL,
    `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 用户账户表
drop table if exists t_user_account;
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

-- 用户角色表
drop table if exists t_user_role;
CREATE TABLE `t_user_role` (
      `user_id` int(11) NOT NULL,
      `role_id` int(11) NOT NULL,
      `status` tinyint(1) DEFAULT NULL,
      `created_by` int(11) DEFAULT NULL,
      `created_time` datetime DEFAULT NULL,
      `last_modified_by` int(11) DEFAULT NULL,
      `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 权限表
drop table if exists t_privilege;
CREATE TABLE `t_privilege` (
       `privilege_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
       `name` varchar(64) DEFAULT NULL,
       `code` varchar(64) DEFAULT NULL,
       `status` tinyint(1) DEFAULT NULL,
       `created_by` int(11) DEFAULT NULL,
       `created_time` datetime DEFAULT NULL,
       `last_modified_by` int(11) DEFAULT NULL,
       `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 权限uri表
drop table if exists t_privilege_uri;
CREATE TABLE `t_privilege_uri` (
   `privilege_id` int(11) NOT NULL,
   `uri_id` int(11) NOT NULL,
   `status` tinyint(1) DEFAULT NULL,
   `created_by` int(11) DEFAULT NULL,
   `created_time` datetime DEFAULT NULL,
   `last_modified_by` int(11) DEFAULT NULL,
   `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 角色表
drop table if exists t_role;
CREATE TABLE `t_role` (
   `role_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
   `name` varchar(64) DEFAULT NULL,
   `code` varchar(64) DEFAULT NULL,
   `status` tinyint(1) DEFAULT NULL,
   `created_by` int(11) DEFAULT NULL,
   `created_time` datetime DEFAULT NULL,
   `last_modified_by` int(11) DEFAULT NULL,
   `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 角色权限表
drop table if exists t_role_privilege;
CREATE TABLE `t_role_privilege` (
  `role_id` int(11) NOT NULL,
  `privilege_id` int(11) NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_modified_by` int(11) DEFAULT NULL,
  `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- uri
drop table if exists t_uri;
CREATE TABLE `t_uri` (
    `uri_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `uri_pattern` varchar(128) DEFAULT NULL,
    `status` tinyint(1) DEFAULT NULL,
    `created_by` int(11) DEFAULT NULL,
    `created_time` datetime DEFAULT NULL,
    `last_modified_by` int(11) DEFAULT NULL,
    `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 菜单表
drop table if exists t_menu;
CREATE TABLE `t_menu` (
    `menu_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
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
   `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 任务表
drop table if exists t_task;
CREATE TABLE `t_task` (
      `task_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
      `title` varchar(128) DEFAULT NULL,
      `img_uri` varchar(512) DEFAULT NULL,
      `status` tinyint(1) DEFAULT NULL,
      `source` tinyint(1) DEFAULT NULL,
      `type` tinyint(1) DEFAULT NULL,
      `amount` DECIMAL(8,2) DEFAULT NULL,
      `location` varchar(32) DEFAULT NULL,
      `spread_total` int(11) DEFAULT NULL,
      `handler_num` int(11) DEFAULT NULL,
      `audit_duration` int(11) DEFAULT NULL,
      `expend_time` int(11) DEFAULT NULL,
      `complete_odds` DECIMAL(8,2) DEFAULT NULL,
      `owner_ratio` DECIMAL(8,2) DEFAULT NULL,
      `spread_ratio` DECIMAL(8,2) DEFAULT NULL,
      `leader_ratio` DECIMAL(8,2) DEFAULT NULL,
      `expired_Time` datetime DEFAULT NULL,
      `created_by` int(11) DEFAULT NULL,
      `created_time` datetime DEFAULT NULL,
      `last_modified_by` int(11) DEFAULT NULL,
      `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 任务属性表
drop table if exists t_task_attribute;
CREATE TABLE `t_task_attribute` (
      `attribute_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
      `task_id` int(11) NOT NULL,
      `name` varchar(512) DEFAULT NULL,
      `value` varchar(512) DEFAULT NULL,
      `type` tinyint(1) DEFAULT NULL,
      `seq` int(5) DEFAULT NULL,
      `created_by` int(11) DEFAULT NULL,
      `created_time` datetime DEFAULT NULL,
      `last_modified_by` int(11) DEFAULT NULL,
      `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 任务收藏表
drop table if exists t_task_collect;
CREATE TABLE `t_task_collect` (
      `collect_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
      `task_id` int(11) NOT NULL,
      `customer_id` int(11) NOT NULL,
      `created_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 订单
drop table if exists t_order;
CREATE TABLE `t_order` (
       `order_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
       `order_no` varchar(128) DEFAULT NULL,
       `channel_uri` varchar(128) DEFAULT NULL,
       `unique_code` varchar(128) DEFAULT NULL,
       `status` tinyint(1) DEFAULT NULL,
       `type` tinyint(1) DEFAULT NULL,
       `settlement_amount` DECIMAL(11,2) DEFAULT NULL,
       `settlement_status` tinyint(1) DEFAULT NULL,
       `created_time` datetime DEFAULT NULL,
       `last_modified_by` int(11) DEFAULT NULL,
       `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 订单分成
drop table if exists t_order_bonus;
CREATE TABLE `t_order_bonus` (
     `bonus_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
     `order_id` int(11) NOT NULL,
     `customer_id` int(11) DEFAULT NULL,
     `customer_bonus` DECIMAL(12,2) DEFAULT NULL,
     `bonus_type` int(11) DEFAULT NULL,
     `status` tinyint(1) DEFAULT NULL,
     `created_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 订单、任务、客户 关联表
drop table if exists t_order_task_customer;
CREATE TABLE `t_order_task_customer` (
     `otc_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
     `order_id` int(11) NOT NULL,
     `task_id` int(11) NOT NULL,
     `customer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户表
drop table if exists t_customer;
CREATE TABLE `t_customer` (
      `customer_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
      `parent_id` int(11) DEFAULT NULL,
      `nick_name` varchar(32) DEFAULT NULL,
      `img_uri` varchar(512) DEFAULT NULL,
      `status` tinyint(1) DEFAULT NULL,
      `invite_code` varchar(32) DEFAULT NULL,
      `reputation` int(11) DEFAULT NULL,
      `created_time` datetime DEFAULT NULL,
      `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户账户表
drop table if exists t_customer_account;
CREATE TABLE `t_customer_account` (
      `customer_account_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
      `customer_id` int(11) NOT NULL,
      `amount` int(11) DEFAULT 0,
      `created_time` datetime DEFAULT NULL,
      `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 客户账号表
drop table if exists t_customer_login_account;
CREATE TABLE `t_customer_login_account` (
    `customer_login_account_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `customer_id` int(11) NOT NULL,
    `login_no` varchar(64) DEFAULT NULL unique,
    `name` varchar(32) DEFAULT NULL,
    `password` varchar(128) DEFAULT NULL,
    `type` tinyint(1) DEFAULT NULL,
    `created_time` datetime DEFAULT NULL,
    `last_modified_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists t_cashout_log;
CREATE TABLE `t_cashout_log` (
     `cashout_id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
     `customer_id` int(11) NOT NULL,
     `amount` int(11) DEFAULT 0,
     `type` tinyint(1) DEFAULT NULL,
     `order_id` varchar(128) DEFAULT NULL,
     `out_biz_no` varchar(128) DEFAULT NULL,
     `pay_date` datetime DEFAULT NULL,
     `operation_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

















-- function
-- 递归获取父级
drop function if exists fn_recursive_query_customer;
CREATE FUNCTION fn_recursive_query_customer(rootId INT) RETURNS varchar(4000) CHARSET utf8
    #rootId为你要查询的节点 #deep 为你要查询的层级
BEGIN
    #声明两个临时变量
    DECLARE temp VARCHAR(4000);
    DECLARE temp_child VARCHAR(4000);
    DECLARE n INT DEFAULT 3;
    SET temp = '$';
    #把rootId强制转换为字符
    SET temp_child = CAST(rootId AS CHAR);
    WHILE temp_child is not null and n>0 DO
    SET temp = CONCAT(temp,',',temp_child);#循环把所有节点连接成字符串。
    SELECT GROUP_CONCAT(parent_id) INTO temp_child FROM t_customer where FIND_IN_SET(customer_id,temp_child)>0;
    set n = n-1;
    END WHILE;
    RETURN temp;

END;






