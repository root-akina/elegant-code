/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 8.0.28 : Database - auth
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/*Table structure for table `tab_dept` */

DROP TABLE IF EXISTS `tab_dept`;

CREATE TABLE `tab_dept` (
  `id` bigint NOT NULL COMMENT '部门id',
  `parent_dept_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '父部门编号',
  `dept_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '部门编号:',
  `dept_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '部门名称',
  `sort_no` int NOT NULL DEFAULT '0' COMMENT '排序',
  `data_state` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `leader_id` bigint NOT NULL COMMENT '负责人Id',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='部门表';

/*Data for the table `tab_dept` */

insert  into `tab_dept`(`id`,`parent_dept_no`,`dept_no`,`dept_name`,`sort_no`,`data_state`,`create_time`,`update_time`,`create_by`,`update_by`,`leader_id`,`remark`) values 
(1,'100000000000000','100001000000000','公司',1,'0','2023-05-21 16:21:19','2023-06-23 03:40:22',NULL,1671362878457892866,1668522280851951617,''),
(2,'100001000000000','100001004000000','总裁办',1,'0','2023-06-23 03:42:07',NULL,1671362878457892866,NULL,1371500419615895553,NULL),
(3,'100001000000000','100001005000000','财务部',2,'0','2023-06-22 19:42:36','2023-06-23 03:43:32',1671362878457892866,1671362878457892866,1671403256519077890,NULL),
(4,'100001000000000','100001006000000','行政部',3,'0','2023-06-23 03:44:14',NULL,1671362878457892866,NULL,1668522280851951618,NULL);

/*Table structure for table `tab_dept_post_user` */

DROP TABLE IF EXISTS `tab_dept_post_user`;

CREATE TABLE `tab_dept_post_user` (
  `id` bigint NOT NULL COMMENT '部门id',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `dept_no` varchar(22) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '部门编号',
  `post_no` varchar(22) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '岗位编码',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `data_state` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='部门岗位用户关联表';

/*Data for the table `tab_dept_post_user` */


/*Table structure for table `tab_post` */

DROP TABLE IF EXISTS `tab_post`;

CREATE TABLE `tab_post` (
  `id` bigint NOT NULL COMMENT '岗位ID',
  `dept_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '部门编号',
  `post_no` varchar(22) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '岗位编码：父部门编号+01【2位】',
  `post_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '岗位名称',
  `sort_no` int NOT NULL COMMENT '显示顺序',
  `data_state` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='岗位表';

/*Data for the table `tab_post` */

insert  into `tab_post`(`id`,`dept_no`,`post_no`,`post_name`,`sort_no`,`data_state`,`create_time`,`update_time`,`remark`,`create_by`,`update_by`) values 
(1,'100001004000000','100001004001000','总裁',1,'0','2023-06-23 03:50:10',NULL,'总裁',1,NULL),
(2,'100001004000000','100001004002000','总监',2,'0','2023-06-23 03:50:27',NULL,'总监',1,NULL),
(3,'100001005001000','100001005001001','部门经理',1,'0','2023-06-23 03:51:10',NULL,'部门经理',1,NULL);

/*Table structure for table `tab_resource` */

DROP TABLE IF EXISTS `tab_resource`;

CREATE TABLE `tab_resource` (
  `id` bigint NOT NULL COMMENT '资源ID',
  `resource_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '资源编号',
  `parent_resource_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '父资源编号',
  `resource_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '资源名称',
  `resource_type` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '资源类型：s平台 c目录 m菜单 r按钮',
  `request_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '请求地址',
  `label` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '权限标识',
  `data_state` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `sort_no` int NOT NULL DEFAULT '0' COMMENT '排序',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '#' COMMENT '图标',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '备注',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='权限表';

/*Data for the table `tab_resource` */

insert  into `tab_resource`(`id`,`resource_no`,`parent_resource_no`,`resource_name`,`resource_type`,`request_path`,`label`,`data_state`,`sort_no`,`icon`,`create_time`,`update_time`,`remark`,`create_by`,`update_by`) values 
(1,'100001000000000','100000000000000','运营平台','s','project-web','project-web','0',0,'0','2023-05-26 00:46:54','2023-06-21 08:14:49','运营平台',1,1),
(2,'100001001000000','100001000000000','权限管理','c','authority','authority','0',8,'user','2023-05-26 16:46:54','2023-06-17 19:14:38','权限管理',1,1),
(3,'100001001001000','100001001000000','部门管理','m','dept','dept','0',0,'form','2023-05-27 16:46:54','2023-05-27 16:46:54','部门管理',NULL,NULL),
(4,'100001001002000','100001001000000','职位管理','m','post','post','0',0,'chart','2023-05-27 16:46:54','2023-05-27 16:46:54','职位管理',NULL,NULL),
(5,'100001001003000','100001001000000','资源管理','m','resource','resource','0',0,'user','2023-05-27 16:46:54','2023-05-27 16:46:54','资源管理',NULL,NULL),
(6,'100001001001001','100001001001000','部门新增','r','PUT/dept','PUT:dept','0',1,'ioc','2023-05-28 02:34:24','2023-06-18 02:53:58','部门新增',NULL,1),
(7,'100001001004000','100001001000000','角色管理','m','role','role','0',4,'component','2023-05-28 18:35:49','2023-05-28 18:35:49','角色管理',NULL,NULL),
(8,'100001001005000','100001001000000','用户管理','m','user','user','0',5,'user','2023-05-28 21:00:01','2023-05-28 21:00:01','用户管理',NULL,NULL),
(9,'100001001001002','100001001001000','部门分页','r','POST/dept/page/**','POST:dept:page','0',2,'ioc','2023-06-08 11:25:11','2023-06-08 11:25:11','部门分页',NULL,NULL),
(10,'100001001001003','100001001001000','部门修改','r','PATCH/dept','PATCH:dept','0',3,'ioc','2023-06-08 11:25:56','2023-06-08 11:25:56','部门修改',NULL,NULL),
(11,'100001001001004','100001001001000','部门树形','r','POST/dept/tree','POST:dept:tree','0',4,'ioc','2023-06-08 11:27:04','2023-06-08 11:27:04','部门树形',NULL,NULL),
(12,'100001001001005','100001001001000','部门列表','r','POST/dept/list','POST:dept:list','0',5,'ioc','2023-06-08 11:27:56','2023-06-08 11:27:56','部门列表',NULL,NULL),
(13,'100001001002001','100001001002000','职位新增','r','PUT/post','PUT:post','0',1,'ioc','2023-06-08 11:29:44','2023-06-08 11:29:44','职位新增',NULL,NULL),
(14,'100001001002002','100001001002000','职位分页','r','POST/post/page/**','POST:post:page','0',2,'ioc','2023-06-08 11:30:44','2023-06-08 11:30:44',' 职位分页',NULL,NULL),
(15,'100001001002003','100001001002000','职位修改','r','PATCH/post','PATCH:post','0',3,'ioc','2023-06-08 11:31:44','2023-06-08 11:31:44','职位修改',NULL,NULL),
(16,'100001001002004','100001001002000','职位列表','r','POST/post/list','POST:post:list','0',4,'ioc','2023-06-08 11:32:47','2023-06-08 11:32:47','职位列表',NULL,NULL),
(17,'100001001003001','100001001003000','资源新增','r','PUT/resource','PUT:resource','0',1,'ioc','2023-06-08 14:02:13','2023-06-08 14:02:13','资源新增',NULL,NULL),
(18,'100001001003002','100001001003000','资源分页','r','POST/resource/page/**','POST:resource:page','0',2,'ioc','2023-06-08 14:03:05','2023-06-08 14:03:05','资源分页',NULL,NULL),
(19,'100001001003003','100001001003000','资源修改','r','PATCH/resource','PATCH:resource','0',3,'ioc','2023-06-08 14:03:51','2023-06-08 14:03:51','资源修改',NULL,NULL),
(20,'100001001003005','100001001003000','资源树形','r','POST/resource/tree','POST:resource:tree','0',4,'ioc','2023-06-08 14:04:48','2023-06-08 14:04:48','资源树形',NULL,NULL),
(21,'100001001003004','100001001003000','资源列表','r','POST/resource/list','POST:resource:list','0',4,'ioc','2023-06-08 14:05:28','2023-06-08 14:05:28','资源列表',NULL,NULL),
(22,'100001001004001','100001001004000','角色新增','r','PUT/role','PUT:role','0',1,'ioc','2023-06-08 14:07:48','2023-06-08 14:07:48','角色新增',NULL,NULL),
(23,'100001001004002','100001001004000','角色分页','r','POST/role/page/**','POST:role:page','0',2,'ioc','2023-06-08 14:08:32','2023-06-08 14:08:32','角色分页',NULL,NULL),
(24,'100001001004003','100001001004000','角色修改','r','PATCH/role','PATCH:role','0',3,'ioc','2023-06-08 14:09:17','2023-06-08 14:09:17','角色修改',NULL,NULL),
(25,'100001001004004','100001001004000','角色列表','r','POST/role/list','POST:role:list','0',4,'ioc','2023-06-08 14:10:02','2023-06-08 14:10:02','角色列表',NULL,NULL),
(26,'100001001004005','100001001004000','角色下拉框','r','POST/role/init-roles','POST:role:init-roles','0',5,'ioc','2023-06-08 14:10:54','2023-06-08 14:10:54','角色下拉框',NULL,NULL),
(27,'100001001005001','100001001005000','用户新增','r','PUT/user','PUT:user','0',1,'ioc','2023-06-08 14:18:44','2023-06-08 14:18:44','用户新增',NULL,NULL),
(28,'100001001005002','100001001005000','用户分页','r','POST/user/page/**','POST:user:page','0',2,'ioc','2023-06-08 14:19:23','2023-06-08 14:19:23','用户分页',NULL,NULL),
(29,'100001001005003','100001001005000','用户修改','r','PATCH/user','PATCH:user','0',3,'ioc','2023-06-08 14:20:12','2023-06-08 14:20:12','用户修改',NULL,NULL),
(30,'100001001005004','100001001005000','用户列表','r','POST/user/list','POST:user:list','0',4,'ioc','2023-06-08 14:20:53','2023-06-08 14:20:53','用户列表',NULL,NULL),
(31,'100001001005005','100001001005000','当前用户','r','GET/user/current-user','GET:user:current-user','0',5,'ioc','2023-06-08 14:21:46','2023-06-08 14:21:46','当前用户',NULL,NULL),
(32,'100001001003006','100001001003000','左侧菜单','r','GET/resource/menus/**','GET:resource:menus','0',6,'ioc','2023-06-07 06:40:23','2023-06-16 08:34:26','左侧菜单',NULL,1),
(33,'100001001001006','100001001001000','创建编号','r','POST/dept/create-dept-no/**','POST:dept:create-dept-no','0',5,'ioc','2023-06-16 17:53:21','2023-06-17 10:18:39','编号创建',1,1),
(34,'100001001002005','100001001002000','创建编号','r','POST/post/create-post-no/**','POST:post:create-post-no','0',5,'ioc','2023-06-17 01:54:16','2023-06-17 10:18:28','创建编号',1,1),
(35,'100001001003007','100001001003000','创建编号','r','POST/resource/create-resource-no/**','POST:resource:create-resource-no','0',7,'ioc','2023-06-16 09:56:35','2023-06-17 10:18:44','创建编号',1,1),
<#assign id = 37>
<#assign seq = 100>
<#list db.tables as t>
(${id},'100001002${seq}000','100001002000000','${t.comment}','m','${t.name}','${t.name}','0',0,'form','2023-05-27 16:46:54','2023-05-27 16:46:54','${t.comment}',NULL,NULL),
<#assign id = id+1 >
<#assign seq = seq+1 >
</#list>
(36,'100001002000000','100001000000000','基础管理','c','biz','biz','0',8,'user','2023-05-26 16:46:54','2023-06-17 19:14:38','基础管理',NULL,1);



/*Table structure for table `tab_role` */

DROP TABLE IF EXISTS `tab_role`;

CREATE TABLE `tab_role` (
  `id` bigint NOT NULL COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `label` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '权限标识',
  `sort_no` int NOT NULL COMMENT '排序',
  `data_state` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `data_scope` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '数据范围：0本人 1自定义 2 本部门 3 本部门及以下  4全部',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='角色表';

/*Data for the table `tab_role` */

insert  into `tab_role`(`id`,`role_name`,`label`,`sort_no`,`data_state`,`create_time`,`update_time`,`remark`,`create_by`,`update_by`,`data_scope`) values 
(1,'超级管理员','admin',1,'0','2023-05-20 16:51:06','2023-06-22 22:48:33','超级管理员',NULL,1,'4');

/*Table structure for table `tab_role_dept` */

DROP TABLE IF EXISTS `tab_role_dept`;

CREATE TABLE `tab_role_dept` (
  `id` bigint NOT NULL COMMENT '部门id',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '部门编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `data_state` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

/*Data for the table `tab_role_dept` */

insert  into `tab_role_dept`(`id`,`role_id`,`dept_no`,`create_time`,`update_time`,`create_by`,`update_by`,`data_state`,`remark`) values 
(1,1,'100001008000000',NULL,NULL,NULL,NULL,'0',NULL);

/*Table structure for table `tab_role_resource` */

DROP TABLE IF EXISTS `tab_role_resource`;

CREATE TABLE `tab_role_resource` (
  `id` bigint NOT NULL COMMENT '角色资源Id',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `resource_no` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '资源编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `data_state` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='角色资源关联表';

/*Data for the table `tab_role_resource` */


/*Table structure for table `tab_user` */

DROP TABLE IF EXISTS `tab_user`;

CREATE TABLE `tab_user` (
  `id` bigint NOT NULL COMMENT '用户ID',
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户账号',
  `open_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'open_id标识',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密码',
  `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户昵称',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT '用户邮箱',
  `real_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `data_state` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `user_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '员工类别（0 员工 1 客户）',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

/*Data for the table `tab_user` */

insert  into `tab_user`(`id`,`username`,`open_id`,`password`,`nick_name`,`email`,`real_name`,`mobile`,`sex`,`data_state`,`create_time`,`update_time`,`remark`,`create_by`,`update_by`,`user_type`,`avatar`) values 
(1,'admin',NULL,'$2a$10$XHz4Q2xIuT81jJx68IUroeRd1wFueuI86bY61cxgQ/ZLKnUohovCK','超级管理员','admin@qq.com','超级管理员','17700009999','0','0','2023-05-22 18:35:10',NULL,'超级管理员',NULL,NULL,NULL,NULL);

/*Table structure for table `tab_user_role` */

DROP TABLE IF EXISTS `tab_user_role`;

CREATE TABLE `tab_user_role` (
  `id` bigint NOT NULL COMMENT '角色资源Id',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `data_state` char(1) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `create_by` bigint DEFAULT NULL COMMENT '创建者',
  `update_by` bigint DEFAULT NULL COMMENT '更新者',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

/*Data for the table `tab_user_role` */

insert  into `tab_user_role`(`id`,`user_id`,`role_id`,`create_time`,`update_time`,`data_state`,`create_by`,`update_by`,`remark`) values 
(1,1,1,'2023-06-08 19:37:46','2023-06-08 19:37:46','0',NULL,NULL,'');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
