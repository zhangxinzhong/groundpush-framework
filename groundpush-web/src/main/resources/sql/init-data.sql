truncate table t_user;
truncate table t_user_account;
truncate table t_role;
truncate table t_role_user;
truncate table t_privilege;
truncate table t_role_privilege;
truncate table t_uri;
truncate table t_privilege_uri;

insert into t_user(login_no, name, name_pinyin, mobile_no, work_email, status, photo, created_by, created_time, last_modified, last_modified_time) values('admin','admin','admin','13888888888','admin@zhongdi.com',0,null,null,current_timestamp,null,null);
insert into t_user(login_no, name, name_pinyin, mobile_no, work_email, status, photo, created_by, created_time, last_modified, last_modified_time) values('kitty','kitty','kitty','13999999999','kitty@zhongdi.com',0,null,null,current_timestamp,null,null);
insert into t_user_account(user_id, password, status, password_error_count, history_password, last_login_time, unlock_time, created_time, last_modified_time)values (1,'$2a$10$XK3KZVZ7jnOWEelQ4k7KMuANHn8.7Pxk43xXwkKwpzANnZ3U2cMzC',0,0,null,null,null,null,null);
insert into t_user_account(user_id, password, status, password_error_count, history_password, last_login_time, unlock_time, created_time, last_modified_time)values (2,'$2a$10$YBRQ5001G0gGZ0BQluB61eCmJx.s1.qFFFLfamceWNZEGaC3Pg7pO',0,0,null,null,null,null,null);

insert into t_role(name,code, status, created_by, created_time, last_modified_by, last_modified_time)values ('超级管理员','super_admin',0,null,null,null,null);
insert into t_role(name,code, status, created_by, created_time, last_modified_by, last_modified_time)values ('普通用户','user',0,null,null,null,null);

insert into t_role_user(user_id, role_id, status, created_by, created_time, last_modified_by, last_modified_time)values (1,1,0,null,null,null,null);
insert into t_role_user(user_id, role_id, status, created_by, created_time, last_modified_by, last_modified_time)values (2,2,0,null,null,null,null);
insert into t_role_user(user_id, role_id, status, created_by, created_time, last_modified_by, last_modified_time)values (2,1,0,null,null,null,null);

insert into t_privilege(name, status, created_by, created_time, last_modified_by, last_modified_time)values ('新增角色',0,null,null,null,null);
insert into t_privilege(name, status, created_by, created_time, last_modified_by, last_modified_time)values ('删除角色',0,null,null,null,null);

insert into t_role_privilege(role_id, privilege_id, status, created_by, created_time, last_modified_by, last_modified_time)values (1,1,0,null,null,null,null);
insert into t_role_privilege(role_id, privilege_id, status, created_by, created_time, last_modified_by, last_modified_time)values (1,2,0,null,null,null,null);
insert into t_role_privilege(role_id, privilege_id, status, created_by, created_time, last_modified_by, last_modified_time)values (2,1,0,null,null,null,null);
insert into t_role_privilege(role_id, privilege_id, status, created_by, created_time, last_modified_by, last_modified_time)values (2,2,0,null,null,null,null);


insert into t_uri(uri_pattern, status, created_by, created_time, last_modified_by, last_modified_time)values ('/addRole',0,null,null,null,null);
insert into t_uri(uri_pattern, status, created_by, created_time, last_modified_by, last_modified_time)values ('/delRole',0,null,null,null,null);

insert into t_privilege_uri(privilege_id, uri_id, status, created_by, created_time, last_modified_by, last_modified_time)values (1,1,0,null,null,null,null);
insert into t_privilege_uri(privilege_id, uri_id, status, created_by, created_time, last_modified_by, last_modified_time)values (1,2,0,null,null,null,null);
insert into t_privilege_uri(privilege_id, uri_id, status, created_by, created_time, last_modified_by, last_modified_time)values (2,1,0,null,null,null,null);
insert into t_privilege_uri(privilege_id, uri_id, status, created_by, created_time, last_modified_by, last_modified_time)values (2,2,0,null,null,null,null);


