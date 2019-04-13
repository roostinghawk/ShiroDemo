# noinspection SqlNoDataSourceInspectionForFile

DROP TABLE IF EXISTS `permission`;
create table permission(
 id int(4) not null primary key ,
 name char(20) not null,
 parent_id int(4) not null,
 permission char(20) not null,
 url char(20) not null,
 resource_type char(20) not null
 );

DROP TABLE IF EXISTS `role`;
create table role(
 id int(4) not null primary key ,
 available char(20) not null,
 description char(20) not null,
 role char(20) not null
 );

DROP TABLE IF EXISTS `role_permission`;
create table role_permission(
 permission_id int(4) not null  ,
 role_id int(4) not null
 );

DROP TABLE IF EXISTS `user_role`;
create table user_role(
 role_id int(4) not null,
 uid int(4) not null
 );

DROP TABLE IF EXISTS `user`;
create table user(
 uid char(20) not null primary key ,
 loginName char(20) not null,
 name char(20) not null,
 password char(50) not null,
 salt char(50) not null,
 state char(20) not null
);


INSERT INTO permission (`id`,`name`,`parent_id`,`permission`,`resource_type`,`url`) VALUES (1,'用户管理',0,'view','menu','user/list');
INSERT INTO permission (`id`,`name`,`parent_id`,`permission`,`resource_type`,`url`) VALUES (2,'用户添加',1,'add','button','user/add');
INSERT INTO permission (`id`,`name`,`parent_id`,`permission`,`resource_type`,`url`) VALUES (3,'用户删除',1,'delete','button','user/delete');
INSERT INTO permission (`id`,`name`,`parent_id`,`permission`,`resource_type`,`url`) VALUES (4,'用户修改',1,'edit','button','user/edit');
INSERT INTO permission (`id`,`name`,`parent_id`,`permission`,`resource_type`,`url`) VALUES (5,'用户查看',1,'view','button','user/view');
INSERT INTO role (`id`,`available`,`description`,`role`) VALUES (1,'0','管理员','admin');
INSERT INTO role (`id`,`available`,`description`,`role`) VALUES (2,'0','会员','member');

INSERT INTO role_permission (`role_id`,`permission_id`) VALUES (1,1);
INSERT INTO role_permission (`role_id`,`permission_id`) VALUES (1,2);
INSERT INTO role_permission (`role_id`,`permission_id`) VALUES (1,3);
INSERT INTO role_permission (`role_id`,`permission_id`) VALUES (1,4);
INSERT INTO role_permission (`role_id`,`permission_id`) VALUES (1,5);
INSERT INTO role_permission (`role_id`,`permission_id`) VALUES (2,1);
INSERT INTO role_permission (`role_id`,`permission_id`) VALUES (2,5);

INSERT INTO `user` (`uid`,`loginName`,`name`,`password`,`salt`,`state`) VALUES ('1', 'admin', '管理员', '274d19c08bd9506e8c9620db7b0e7a4f', '57ae27b03e03', 0);
INSERT INTO `user`  (`uid`,`loginName`,`name`,`password`,`salt`,`state`) VALUES ('2', 'member', '会员', '7040c84ee28c58e00ac45a1075bdfb28', '6f41e3b340d4', 0);

INSERT INTO user_role (`role_id`,`uid`) VALUES (1,1);
INSERT INTO user_role (`role_id`,`uid`) VALUES (2,2);