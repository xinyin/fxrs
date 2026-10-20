/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.222
Source Server Version : 50704
Source Host           : 192.168.1.222:3306
Source Database       : jep

Target Server Type    : MYSQL
Target Server Version : 50704
File Encoding         : 65001

Date: 2017-12-01 11:04:37
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `dev_school`
-- ----------------------------
DROP TABLE IF EXISTS `dev_school`;
CREATE TABLE `dev_school` (
  `ID_` int(11) NOT NULL AUTO_INCREMENT,
  `CODE_` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `NAME_` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `TYPE_` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `SCALE_` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS_` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `TAGS_` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `REMARK_` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATE_TIME_` datetime DEFAULT NULL,
  `UPDATE_TIME_` datetime DEFAULT NULL,
  `AREA_COUNT_` int(11) DEFAULT NULL,
  `CATE_ID_` int(10) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='学习管理表';

-- ----------------------------
-- Records of dev_school
-- ----------------------------
INSERT INTO `dev_school` VALUES ('1', null, '123', 'all', 'S20', null, 'T10', null, '2017-05-01 00:00:00', null, '2', null);
INSERT INTO `dev_school` VALUES ('2', '12', '211', 'all', 'S10', null, 'T10', null, '2017-01-30 00:00:00', null, '2', null);
INSERT INTO `dev_school` VALUES ('3', '11', '11', 'all', 'S10', null, null, null, '2017-06-01 00:00:00', null, null, '11');
INSERT INTO `dev_school` VALUES ('4', '55', '555', 'all', 'S10', null, 'T10', null, '2017-06-02 00:00:00', null, null, null);

-- ----------------------------
-- Table structure for `dev_school_cate`
-- ----------------------------
DROP TABLE IF EXISTS `dev_school_cate`;
CREATE TABLE `dev_school_cate` (
  `ID_` int(10) NOT NULL AUTO_INCREMENT,
  `CODE_` varchar(30) DEFAULT NULL,
  `NAME_` varchar(200) DEFAULT NULL,
  `PARENT_ID_` int(10) DEFAULT NULL,
  `LEVEL_` varchar(30) DEFAULT NULL,
  `PATH_` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dev_school_cate
-- ----------------------------
INSERT INTO `dev_school_cate` VALUES ('1', '211', '211', null, null, null);
INSERT INTO `dev_school_cate` VALUES ('2', '985', '985', null, null, null);
INSERT INTO `dev_school_cate` VALUES ('3', 'other', '其他', null, null, null);
INSERT INTO `dev_school_cate` VALUES ('4', '2', '2本', '3', null, null);
INSERT INTO `dev_school_cate` VALUES ('5', '3', '3本', '3', null, null);
INSERT INTO `dev_school_cate` VALUES ('6', '985002', '985重点', '2', null, null);
INSERT INTO `dev_school_cate` VALUES ('7', 'qwda', 'aaaa', '6', null, null);
INSERT INTO `dev_school_cate` VALUES ('8', '', '', null, null, null);
INSERT INTO `dev_school_cate` VALUES ('9', '', '新节点', '8', null, null);

-- ----------------------------
-- Table structure for `dev_school_department`
-- ----------------------------
DROP TABLE IF EXISTS `dev_school_department`;
CREATE TABLE `dev_school_department` (
  `ID_` int(11) NOT NULL AUTO_INCREMENT,
  `SCHOOL_ID_` int(11) DEFAULT NULL,
  `SPECIALITY_ID_` int(11) DEFAULT NULL,
  `NAME_` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `YEAR_` int(11) DEFAULT NULL,
  `CHAIRMAN_ID_` int(11) DEFAULT NULL,
  `REMARK_` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STUDENT_COUNT_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of dev_school_department
-- ----------------------------

-- ----------------------------
-- Table structure for `dev_school_staff`
-- ----------------------------
DROP TABLE IF EXISTS `dev_school_staff`;
CREATE TABLE `dev_school_staff` (
  `ID_` int(11) NOT NULL AUTO_INCREMENT,
  `SCHOOL_ID_` int(11) DEFAULT NULL,
  `NAME_` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `GENDER_` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `AGE_` int(11) DEFAULT NULL,
  `SPECIALITY_` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PHONE_` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL_` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BIRTHDAY_` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `IDCARD_` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ADDR_` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TYPE_` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'PROFESSOR\n            MANAGER',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of dev_school_staff
-- ----------------------------

-- ----------------------------
-- Table structure for `dev_speciality`
-- ----------------------------
DROP TABLE IF EXISTS `dev_speciality`;
CREATE TABLE `dev_speciality` (
  `ID_` varchar(64) NOT NULL,
  `CODE_` varchar(30) DEFAULT NULL,
  `NAME_` varchar(50) DEFAULT NULL,
  `REMARK_` varchar(200) DEFAULT NULL,
  `UNIT_NAME_` varchar(128) DEFAULT NULL,
  `UNIT_CODE_` varchar(64) DEFAULT NULL,
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dev_speciality
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_appconfig`
-- ----------------------------
DROP TABLE IF EXISTS `sys_appconfig`;
CREATE TABLE `sys_appconfig` (
  `CONFIG_CODE_` varchar(64) NOT NULL COMMENT '配置编码',
  `CONFIG_NAME_` varchar(128) NOT NULL COMMENT '配置名称',
  `CONFIG_DESC_` varchar(256) DEFAULT NULL COMMENT '描述',
  `CONFIG_VALUE_` varchar(256) NOT NULL COMMENT '配置值',
  `DEFAULT_VALUE_` varchar(256) DEFAULT NULL COMMENT '默认值',
  `CONFIG_LEVEL_` varchar(64) NOT NULL COMMENT '配置级别：SYS - 系统级，APP - 应用级',
  `VALUE_TYPE_` varchar(64) DEFAULT NULL COMMENT '值类型：0 - 字符,1 - 数字',
  `INPUT_TYPE_` varchar(64) DEFAULT NULL COMMENT '输入类型：0 - 手动,1 - 下拉选择',
  `PARENT_ID_` varchar(64) DEFAULT NULL COMMENT '父配置项，暂未启用',
  `UNIT_` varchar(64) DEFAULT NULL COMMENT '单位',
  `VALUE_RANGE_` varchar(256) DEFAULT NULL COMMENT '值范围',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`CONFIG_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用配置表，保存系统运行时的配置项，如附件上传目录，是否发送邮件等。';

-- ----------------------------
-- Records of sys_appconfig
-- ----------------------------
INSERT INTO `sys_appconfig` VALUES ('ALLOW_LOCAL_LOGIN', '是否允许本地登录', 'Y-是，N-否', 'Y', null, 'SYS', null, null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-18 15:51:03', null);
INSERT INTO `sys_appconfig` VALUES ('EMAIL', '邮件发送配置', '是否发送邮件', 'N', null, 'APP', null, null, null, null, null, null, null, null, null, null, null, null, null, '2017-06-15 15:56:11', null);
INSERT INTO `sys_appconfig` VALUES ('EMAIL_RETRY_TIMES', '邮件重发次数', null, '3', null, 'APP', null, null, 'EMAIL', null, null, null, null, null, null, null, null, null, null, '2017-08-29 15:41:39', null);
INSERT INTO `sys_appconfig` VALUES ('SMS', '短信发送配置', '是否发送短信', 'N', null, 'APP', null, null, null, null, null, null, null, null, null, null, null, null, null, '2017-06-15 16:52:13', null);
INSERT INTO `sys_appconfig` VALUES ('SMS_RETRY_TIMES', '短信重发次数', null, '3', null, 'APP', null, null, 'SMS', null, null, null, null, null, null, null, null, null, null, '2017-08-28 15:02:49', null);
INSERT INTO `sys_appconfig` VALUES ('UPLOADEXCTYPES', '禁止上传的附件类型', '禁止上传文件的类型，逗号分隔，-1代表不限制', '-1', null, 'SYS', null, null, null, null, null, null, null, null, null, null, null, null, null, '2017-08-28 17:37:35', null);
INSERT INTO `sys_appconfig` VALUES ('UPLOADINCTYPES', '允许上传的附件类型', '允许上传文件的类型，逗号分隔，-1代表不限制', '-1', null, 'SYS', null, null, null, null, null, null, null, null, null, null, null, null, null, '2017-08-28 17:37:35', null);
INSERT INTO `sys_appconfig` VALUES ('UPLOADPATH', '附件上传路径', '存放上传附件的路径', '/upload', null, 'SYS', null, null, null, null, null, null, null, null, null, null, null, null, null, '2017-06-15 16:52:34', null);
INSERT INTO `sys_appconfig` VALUES ('UPLOADSIZE', '单个附件大小', '单个上传文件的大小，整数，单位(M)，-1代表不限制', '50', null, 'SYS', null, null, null, null, null, null, null, null, null, null, null, null, null, '2017-06-15 16:52:26', null);
INSERT INTO `sys_appconfig` VALUES ('WORKFLOW', '工作流配置', null, '10', null, 'APP', null, null, null, null, null, null, null, null, null, null, null, null, null, '2017-11-27 08:32:28', null);
INSERT INTO `sys_appconfig` VALUES ('WORKFLOW_LONGTIME', '长时间未处理（时长）', '配置长时间未处理的时长（单位是天）', '1', null, 'APP', null, null, 'WORKFLOW', null, null, null, null, null, null, null, null, null, null, '2017-11-30 10:33:53', null);

-- ----------------------------
-- Table structure for `sys_attachment_type`
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment_type`;
CREATE TABLE `sys_attachment_type` (
  `ATTACH_TYPE_CODE_` varchar(64) NOT NULL COMMENT '上传类型编码',
  `ATTACH_TYPE_NAME_` varchar(128) NOT NULL COMMENT '上传类型名称',
  `ATTACH_FORMAT_` varchar(256) DEFAULT NULL COMMENT '附件格式，暂未启用',
  `ATTACH_SAVE_PATH_` varchar(256) NOT NULL COMMENT '定义该类型的附件在系统中的保存路径，相对于附件上传路径（系统配置项）',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ATTACH_TYPE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定义系统中的附件上传类型。';

-- ----------------------------
-- Records of sys_attachment_type
-- ----------------------------
INSERT INTO `sys_attachment_type` VALUES ('11', '11', null, '11', null, null, null, null, null, null, null, null, '2017-09-08 11:07:00', null);
INSERT INTO `sys_attachment_type` VALUES ('NOTIFY', '通知', null, 'notify', null, null, null, null, null, null, null, null, '2017-10-30 16:21:05', null);
INSERT INTO `sys_attachment_type` VALUES ('WORKFLOW', '工作流', null, 'WORKFLOW', null, null, null, null, null, null, null, null, '2017-12-01 11:03:31', null);

-- ----------------------------
-- Table structure for `sys_attachment`
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment` (
  `ID_` varchar(64) NOT NULL COMMENT '附件ID',
  `ENTITY_ID_` varchar(64) NOT NULL COMMENT '业务对象ID',
  `ATTACH_TYPE_CODE_` varchar(64) NOT NULL COMMENT '附件类型编码',
  `SRC_FILE_NAME_` varchar(256) NOT NULL COMMENT '源文件名',
  `SAVE_FILE_NAME_` varchar(128) NOT NULL COMMENT '磁盘的物理文件名',
  `FILE_FORMAT_` varchar(64) DEFAULT NULL COMMENT '附件扩展名',
  `FILE_SIZE_` int(11) DEFAULT NULL COMMENT '附件大小，单位字节',
  `FILE_DESC_` varchar(256) DEFAULT NULL COMMENT '附件描述',
  `UPLOAD_STAFF_ID_` varchar(64) DEFAULT NULL COMMENT '上传人编码',
  `UPLOAD_STAFF_NAME_` varchar(64) DEFAULT NULL COMMENT '上传人名称',
  `UPLOAD_TIME_` datetime DEFAULT NULL COMMENT '上传时间',
  `UPLOAD_SESSION_` varchar(64) DEFAULT NULL COMMENT '上传会话码',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`),
  KEY `FK_Reference_30` (`ATTACH_TYPE_CODE_`),
  CONSTRAINT `sys_attachment_ibfk_1` FOREIGN KEY (`ATTACH_TYPE_CODE_`) REFERENCES `sys_attachment_type` (`ATTACH_TYPE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表，保存系统附件，每个附件与一个业务对象关联。';

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------
INSERT INTO `sys_attachment` VALUES ('25077fd1-9443-11e7-9e24-00ffb5d5587a', 'ENTITY_ID_NOT_ASSIGNED', '11', 'timg', '20170908111015068-a750daa313b044298ff71f10b15763d5', 'jpg', '140179', 'timg', null, null, null, '20170908110945725-8b929787255043179505564d90d8ac5f', null, null, null, null, null, null, null, null, '2017-09-08 11:10:12', null);
INSERT INTO `sys_attachment` VALUES ('52d28a3a-bd4b-11e7-b461-00ffaa1f36b9', '589cff2e-bd4b-11e7-b461-00ffaa1f36b9', 'NOTIFY', 'timg', '20171030162150522-0ac56902266a4c2da89b0bc68a5b3ec0', 'jpg', '140179', 'timg', null, null, null, '20171030162137270-0613909c48aa46bf8ae40253ea3ad4d9', null, null, null, null, null, null, null, null, '2017-10-30 16:21:53', null);
INSERT INTO `sys_attachment` VALUES ('5cea3f8e-9443-11e7-9e24-00ffb5d5587a', 'ENTITY_ID_NOT_ASSIGNED', '11', 'Koala', '20170908111148814-6d7fc0db53394eb7a360c377b9e0ecbf', 'jpg', '780831', 'Koala', null, null, null, '20170908111108249-096241a93a774362adb2fadafaf97cfe', null, null, null, null, null, null, null, null, '2017-09-08 11:11:45', null);
INSERT INTO `sys_attachment` VALUES ('80060f3f-9443-11e7-9e24-00ffb5d5587a', 'ENTITY_ID_NOT_ASSIGNED', '11', 'Jellyfish', '20170908111247714-2a208e0a2ec14a50a3da96ee5392f344', 'jpg', '775702', 'Jellyfish', null, null, null, '20170908111108249-096241a93a774362adb2fadafaf97cfe', null, null, null, null, null, null, null, null, '2017-09-08 11:12:46', null);
INSERT INTO `sys_attachment` VALUES ('bbca1629-9442-11e7-9e24-00ffb5d5587a', 'ENTITY_ID_NOT_ASSIGNED', '11', 'timg', '20170908110718468-8f5df629ff524a368013f66fb461c9b8', 'jpg', '140179', 'timg', null, null, null, '20170908110712230-018f5af001074f42aadc935e2e7aeed3', null, null, null, null, null, null, null, null, '2017-09-08 11:07:15', null);

-- ----------------------------
-- Table structure for `sys_authorization`
-- ----------------------------
DROP TABLE IF EXISTS `sys_authorization`;
CREATE TABLE `sys_authorization` (
  `AUTHORIZATION_ID_` varchar(64) NOT NULL COMMENT '主键，系统自动生成',
  `AUZ_TARGET_` varchar(256) DEFAULT NULL COMMENT '授权对象为系统中的岗位，为特定级别岗位。',
  `AUZ_TYPE_` varchar(64) DEFAULT NULL COMMENT '岗位授权类型',
  `KIND_` varchar(256) DEFAULT NULL COMMENT 'MENU - 菜单权限, DAK - 数据权限, FP - 功能权限',
  `MENU_CODE_` varchar(64) DEFAULT NULL COMMENT '菜单编码',
  `RESOURCE_CODE_` varchar(256) DEFAULT NULL COMMENT '授权资源',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`AUTHORIZATION_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统授权表，保存系统中的三种授权方式，菜单权限、功能权限和数据权限。';

-- ----------------------------
-- Records of sys_authorization
-- ----------------------------
INSERT INTO `sys_authorization` VALUES ('201709061506423898EF10F4514579B487F86FDCE3E434', 'TEXT_009', 'ROLEGROUP', 'MENU', null, 'rule', null, null, null, null, null, null, null, null, '2017-09-06 15:06:41', null);
INSERT INTO `sys_authorization` VALUES ('20170906150642ADB1F7620DC348D6B0FDA18697654230', 'TEXT_009', 'ROLEGROUP', 'MENU', null, 'rule.10', null, null, null, null, null, null, null, null, '2017-09-06 15:06:41', null);
INSERT INTO `sys_authorization` VALUES ('201709141516023B11193EDC08434486667A35D32F9EF1', '50110', 'ROLE', 'MENU', null, 'sys.100', null, null, null, null, null, null, null, null, '2017-09-14 15:15:52', null);
INSERT INTO `sys_authorization` VALUES ('2017091415160253E93ED6D96547E5AF8562630859A38A', '50110', 'ROLE', 'MENU', null, 'sys', null, null, null, null, null, null, null, null, '2017-09-14 15:15:52', null);
INSERT INTO `sys_authorization` VALUES ('20170914151602A85F0191E06A42CCA175F3E456FCAD41', '50110', 'ROLE', 'MENU', null, 'sys.100.100', null, null, null, null, null, null, null, null, '2017-09-14 15:15:52', null);
INSERT INTO `sys_authorization` VALUES ('201710210948191E675F5262D0473FBCACF39A17292B37', '30110', 'ROLE', 'MENU', null, 'sys.100', null, null, null, null, null, null, null, null, '2017-10-21 09:48:08', null);
INSERT INTO `sys_authorization` VALUES ('20171021094819B0AE0F9376844B2EA545F8A2714BB845', '30110', 'ROLE', 'MENU', null, 'sys', null, null, null, null, null, null, null, null, '2017-10-21 09:48:08', null);
INSERT INTO `sys_authorization` VALUES ('2017102109482030AD98FA3D404E40BD44EA0856BF16B0', '30110', 'ROLE', 'MENU', null, 'notify.100', null, null, null, null, null, null, null, null, '2017-10-21 09:48:09', null);
INSERT INTO `sys_authorization` VALUES ('20171021094820D34E6B5A4B0249E4B11B176F077197FD', '30110', 'ROLE', 'MENU', null, 'notify', null, null, null, null, null, null, null, null, '2017-10-21 09:48:08', null);
INSERT INTO `sys_authorization` VALUES ('20171021094820E2E6E106597841099B19186CD64EFC7D', '30110', 'ROLE', 'MENU', null, 'sys.200', null, null, null, null, null, null, null, null, '2017-10-21 09:48:08', null);
INSERT INTO `sys_authorization` VALUES ('20171021094820E929B3B0A54046D886AA5BD89BC04266', '30110', 'ROLE', 'MENU', null, 'notify.200', null, null, null, null, null, null, null, null, '2017-10-21 09:48:09', null);
INSERT INTO `sys_authorization` VALUES ('20171031174628305656D64C3047CE81707138D180592F', '30120', 'ROLE', 'MENU', null, 'notify', null, null, null, null, null, null, null, null, '2017-10-31 17:46:19', null);
INSERT INTO `sys_authorization` VALUES ('201710311746284E095BBD78B245EF8620A30217CE2CEF', '30120', 'ROLE', 'MENU', null, 'sys', null, null, null, null, null, null, null, null, '2017-10-31 17:46:18', null);
INSERT INTO `sys_authorization` VALUES ('201710311746285D37779BC75F4810B12C6637AE5D4217', '30120', 'ROLE', 'MENU', null, 'sys.200', null, null, null, null, null, null, null, null, '2017-10-31 17:46:19', null);
INSERT INTO `sys_authorization` VALUES ('2017103117462871AD836037FC49FB8DF10426794077DB', '30120', 'ROLE', 'MENU', null, 'sys.100', null, null, null, null, null, null, null, null, '2017-10-31 17:46:19', null);
INSERT INTO `sys_authorization` VALUES ('2017103117462880500AEDDBD14BC6A545E76BE4786684', '30120', 'ROLE', 'MENU', null, 'notify.100', null, null, null, null, null, null, null, null, '2017-10-31 17:46:19', null);

-- ----------------------------
-- Table structure for `sys_unit`
-- ----------------------------
DROP TABLE IF EXISTS `sys_unit`;
CREATE TABLE `sys_unit` (
  `UNIT_CODE_` varchar(64) NOT NULL COMMENT '机构编码',
  `UNIT_NAME_` varchar(128) NOT NULL COMMENT '机构名称',
  `UNIT_DESC_` varchar(256) DEFAULT NULL COMMENT '机构描述',
  `DISPLAY_NAME_` varchar(128) DEFAULT NULL COMMENT '显示名称',
  `KIND_` varchar(64) NOT NULL COMMENT '类别：机构| 部门',
  `HIERARCHY_` varchar(64) DEFAULT NULL COMMENT '层级：总行| 分行| 支行',
  `OWNER_` varchar(64) DEFAULT NULL COMMENT '所属机构',
  `PATH_CODE_` varchar(256) NOT NULL COMMENT '级别：.父机构编码..机构编码.',
  `PATH_NAME_` varchar(512) DEFAULT NULL COMMENT '名称路径',
  `IS_BIZ_DEPT_` varchar(64) DEFAULT NULL COMMENT '是否为业务部门',
  `BIZ_TYPE_CODE_` varchar(256) DEFAULT NULL COMMENT '冗余业务种类编码',
  `BIZ_TYPE_NAME_` varchar(256) DEFAULT NULL COMMENT '冗余业务类型名称',
  `PARENT_ID_` varchar(64) DEFAULT NULL COMMENT '父机构编码',
  `AREA_CODE_` varchar(64) DEFAULT NULL COMMENT '地区编码',
  `FAX_` varchar(64) DEFAULT NULL COMMENT '传真号码',
  `MAIL_CODE_` varchar(256) DEFAULT NULL COMMENT '邮政地址',
  `OFFICE_ADDR_` varchar(256) DEFAULT NULL COMMENT '办公地址',
  `POST_CODE_` varchar(64) DEFAULT NULL COMMENT '邮编',
  `TELPHONE_` varchar(64) DEFAULT NULL COMMENT '电话',
  `RESPONSE_UNIT_CODE_` varchar(64) DEFAULT NULL COMMENT '上级主管部门',
  `STATUS_` varchar(64) NOT NULL COMMENT '状态，APPLY- 申请，OPEN- 经营，CLOSE- 停业，CANCEL- 撤销',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`UNIT_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构树表\r\n1. 机构下不能存在用户；2. 部门可以存在用户';

-- ----------------------------
-- Records of sys_unit
-- ----------------------------
INSERT INTO `sys_unit` VALUES ('02', 'XXX银行', '', 'XXX银行', 'UNIT', '30', '02', '.02.', 'XXX银行', 'Y', null, '资产业务', null, null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-20 12:13:43', null);
INSERT INTO `sys_unit` VALUES ('0201', '总行', '', 'XXX银行总行', 'DEPT', '30', '02', '.02..0201.', 'XXX银行/总行', 'N', null, '', '02', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-20 17:08:32', null);
INSERT INTO `sys_unit` VALUES ('020101', '合规部', '', 'XXX银行总行合规部', 'DEPT', '30', '02', '.02..0201..020101.', 'XXX银行/总行/合规部', null, null, null, '0201', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:17', null);
INSERT INTO `sys_unit` VALUES ('020102', '风险部', '', 'XXX银行总行风险部', 'DEPT', '30', '02', '.02..0201..020102.', 'XXX银行/总行/风险部', null, null, null, '0201', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:20', null);
INSERT INTO `sys_unit` VALUES ('020103', 'IT部', '', 'XXX银行总行IT部', 'DEPT', '30', '02', '.02..0201..020103.', 'XXX银行/总行/IT部', 'Y', null, null, '0201', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:23', null);
INSERT INTO `sys_unit` VALUES ('02010301', '软件开发部', '', 'XXX银行总行IT部软件开发部', 'DEPT', '30', '02', '.02..0201..020103..02010301.', 'XXX银行/总行/IT部/软件开发部', null, null, null, '020103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:25', null);
INSERT INTO `sys_unit` VALUES ('02010302', '运行维护部', '', 'XXX银行总行IT部运行维护部', 'DEPT', '30', '02', '.02..0201..020103..02010302.', 'XXX银行/总行/IT部/运行维护部', null, null, null, '020103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:28', null);
INSERT INTO `sys_unit` VALUES ('0202', '四川分行', '', 'XXX银行四川分行', 'UNIT', '50', '0202', '.02..0202.', 'XXX银行/四川分行', 'Y', null, '', '02', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-20 17:08:48', null);
INSERT INTO `sys_unit` VALUES ('020201', '成都分行', '', 'XXX银行四川分行成都分行', 'UNIT', '70', '020201', '.02..0202..020201.', 'XXX银行/四川分行/成都分行', null, null, null, '0202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:33', null);
INSERT INTO `sys_unit` VALUES ('02020101', '武侯支行', '', 'XXX银行四川分行成都分行武侯支行', 'UNIT', '90', '02020101', '.02..0202..020201..02020101.', 'XXX银行/四川分行/成都分行/武侯支行', null, null, null, '020201', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:37', null);
INSERT INTO `sys_unit` VALUES ('0202010101', '风险部', '', 'XXX银行四川分行成都分行武侯支行风险部', 'DEPT', '90', '02020101', '.02..0202..020201..02020101..0202010101.', 'XXX银行/四川分行/成都分行/武侯支行/风险部', null, null, null, '02020101', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:41', null);
INSERT INTO `sys_unit` VALUES ('0202010102', '合规部', '', 'XXX银行四川分行成都分行武侯支行合规部', 'DEPT', '90', '02020101', '.02..0202..020201..02020101..0202010102.', 'XXX银行/四川分行/成都分行/武侯支行/合规部', 'Y', null, null, '02020101', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:11:27', null);
INSERT INTO `sys_unit` VALUES ('0202010103', 'IT部门', '', 'XXX银行四川分行成都分行武侯支行IT部门', 'DEPT', '90', '02020101', '.02..0202..020201..02020101..0202010103.', 'XXX银行/四川分行/成都分行/武侯支行/IT部门', 'Y', null, null, '02020101', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:46:51', null);
INSERT INTO `sys_unit` VALUES ('020201010301', '软件开发部', '', 'XXX银行四川分行成都分行武侯支行IT部门软件开发部', 'DEPT', '90', '02020101', '.02..0202..020201..02020101..0202010103..020201010301.', 'XXX银行/四川分行/成都分行/武侯支行/IT部门/软件开发部', null, null, null, '0202010103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:47:03', null);
INSERT INTO `sys_unit` VALUES ('020201010302', '运行维护部门', '', 'XXX银行四川分行成都分行武侯支行IT部门运行维护部门', 'DEPT', '90', '02020101', '.02..0202..020201..02020101..0202010103..020201010302.', 'XXX银行/四川分行/成都分行/武侯支行/IT部门/运行维护部门', null, null, null, '0202010103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:47:32', null);
INSERT INTO `sys_unit` VALUES ('02020102', '成华支行', '', 'XXX银行四川分行成都分行成华支行', 'UNIT', '90', '02020102', '.02..0202..020201..02020102.', 'XXX银行/四川分行/成都分行/成华支行', null, null, null, '020201', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:47', null);
INSERT INTO `sys_unit` VALUES ('0202010201', '风险部', '', 'XXX银行四川分行成都分行成华支行风险部', 'DEPT', '90', '02020102', '.02..0202..020201..02020102..0202010201.', 'XXX银行/四川分行/成都分行/成华支行/风险部', null, null, null, '02020102', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:41', null);
INSERT INTO `sys_unit` VALUES ('0202010202', '合规部', '', 'XXX银行四川分行成都分行成华支行合规部', 'DEPT', '90', '02020102', '.02..0202..020201..02020102..0202010202.', 'XXX银行/四川分行/成都分行/成华支行/合规部', 'Y', null, null, '02020102', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:11:27', null);
INSERT INTO `sys_unit` VALUES ('0202010203', 'IT部门', '', 'XXX银行四川分行成都分行成华支行IT部门', 'DEPT', '90', '02020102', '.02..0202..020201..02020102..0202010203.', 'XXX银行/四川分行/成都分行/成华支行/IT部门', 'Y', null, null, '02020102', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:46:51', null);
INSERT INTO `sys_unit` VALUES ('020201020301', '软件开发部', '', 'XXX银行四川分行成都分行成华支行IT部门软件开发部', 'DEPT', '90', '02020102', '.02..0202..020201..02020102..0202010203..020201020301.', 'XXX银行/四川分行/成都分行/成华支行/IT部门/软件开发部', null, null, null, '0202010203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:47:03', null);
INSERT INTO `sys_unit` VALUES ('020201020302', '运行维护部门', '', 'XXX银行四川分行成都分行成华支行IT部门运行维护部门', 'DEPT', '90', '02020102', '.02..0202..020201..02020102..0202010203..020201020302.', 'XXX银行/四川分行/成都分行/成华支行/IT部门/运行维护部门', null, null, null, '0202010203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:47:32', null);
INSERT INTO `sys_unit` VALUES ('02020103', '金牛支行', '', 'XXX银行四川分行成都分行金牛支行', 'UNIT', '90', '02020103', '.02..0202..020201..02020103.', 'XXX银行/四川分行/成都分行/金牛支行', 'Y', null, null, '020201', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:51', null);
INSERT INTO `sys_unit` VALUES ('0202010301', '风险部', '', 'XXX银行四川分行成都分行金牛支行风险部', 'DEPT', '90', '02020103', '.02..0202..020201..02020103..0202010301.', 'XXX银行/四川分行/成都分行/金牛支行/风险部', null, null, null, '02020103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:41', null);
INSERT INTO `sys_unit` VALUES ('0202010302', '合规部', '', 'XXX银行四川分行成都分行金牛支行合规部', 'DEPT', '90', '02020103', '.02..0202..020201..02020103..0202010302.', 'XXX银行/四川分行/成都分行/金牛支行/合规部', 'Y', null, null, '02020103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:11:27', null);
INSERT INTO `sys_unit` VALUES ('0202010303', 'IT部门', '', 'XXX银行四川分行成都分行金牛支行IT部门', 'DEPT', '90', '02020103', '.02..0202..020201..02020103..0202010303.', 'XXX银行/四川分行/成都分行/金牛支行/IT部门', 'Y', null, null, '02020103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:46:51', null);
INSERT INTO `sys_unit` VALUES ('020201030301', '软件开发部', '', 'XXX银行四川分行成都分行金牛支行IT部门软件开发部', 'DEPT', '90', '02020103', '.02..0202..020201..02020103..0202010303..020201030301.', 'XXX银行/四川分行/成都分行/金牛支行/IT部门/软件开发部', null, null, null, '0202010303', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:47:03', null);
INSERT INTO `sys_unit` VALUES ('020201030302', '运行维护部门', '', 'XXX银行四川分行成都分行金牛支行IT部门运行维护部门', 'DEPT', '90', '02020103', '.02..0202..020201..02020103..0202010303..020201030302.', 'XXX银行/四川分行/成都分行/金牛支行/IT部门/运行维护部门', null, null, null, '0202010303', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:47:32', null);
INSERT INTO `sys_unit` VALUES ('02020104', '市分行', '', 'XXX银行四川分行成都分行市分行', 'DEPT', '70', '020201', '.02..0202..020201..02020104.', 'XXX银行/四川分行/成都分行/市分行', null, null, null, '020201', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:07:30', null);
INSERT INTO `sys_unit` VALUES ('0202010401', '合规部', '', 'XXX银行四川分行成都分行市分行合规部', 'DEPT', '70', '020201', '.02..0202..020201..02020104..0202010401.', 'XXX银行/四川分行/成都分行/市分行/合规部', null, null, null, '02020104', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:08:15', null);
INSERT INTO `sys_unit` VALUES ('0202010402', '风险部', '', 'XXX银行四川分行成都分行市分行风险部', 'DEPT', '70', '020201', '.02..0202..020201..02020104..0202010402.', 'XXX银行/四川分行/成都分行/市分行/风险部', null, null, null, '02020104', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:08:45', null);
INSERT INTO `sys_unit` VALUES ('0202010403', 'IT部', '', 'XXX银行四川分行成都分行市分行IT部', 'DEPT', '70', '020201', '.02..0202..020201..02020104..0202010403.', 'XXX银行/四川分行/成都分行/市分行/IT部', 'N', null, '', '02020104', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-20 17:20:48', null);
INSERT INTO `sys_unit` VALUES ('020201040301', '软件开发部门', '', 'XXX银行四川分行成都分行市分行IT部软件开发部门', 'DEPT', '70', '020201', '.02..0202..020201..02020104..0202010403..020201040301.', 'XXX银行/四川分行/成都分行/市分行/IT部/软件开发部门', null, null, null, '0202010403', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:09:56', null);
INSERT INTO `sys_unit` VALUES ('020201040302', '运行维护部', '', 'XXX银行四川分行成都分行市分行IT部运行维护部', 'DEPT', '70', '020201', '.02..0202..020201..02020104..0202010403..020201040302.', 'XXX银行/四川分行/成都分行/市分行/IT部/运行维护部', null, null, null, '0202010403', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:10:46', null);
INSERT INTO `sys_unit` VALUES ('020202', '德阳分行', '测试', 'XXX银行四川分行德阳分行', 'UNIT', '70', '020202', '.02..0202..020202.', 'XXX银行/四川分行/德阳分行', null, null, null, '0202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:54', null);
INSERT INTO `sys_unit` VALUES ('02020201', '锦阳支行', '', 'XXX银行四川分行德阳分行锦阳支行', 'UNIT', '90', '02020201', '.02..0202..020202..02020201.', 'XXX银行/四川分行/德阳分行/锦阳支行', null, null, null, '020202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:37', null);
INSERT INTO `sys_unit` VALUES ('0202020101', '风险部', '', 'XXX银行四川分行德阳分行锦阳支行风险部', 'DEPT', '90', '02020201', '.02..0202..020202..02020201..0202020101.', 'XXX银行/四川分行/德阳分行/锦阳支行/风险部', null, null, null, '02020201', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:41', null);
INSERT INTO `sys_unit` VALUES ('0202020102', '合规部', '', 'XXX银行四川分行德阳分行锦阳支行合规部', 'DEPT', '90', '02020201', '.02..0202..020202..02020201..0202020102.', 'XXX银行/四川分行/德阳分行/锦阳支行/合规部', 'Y', null, null, '02020201', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:11:27', null);
INSERT INTO `sys_unit` VALUES ('0202020103', 'IT部门', '', 'XXX银行四川分行德阳分行锦阳支行IT部门', 'DEPT', '90', '02020201', '.02..0202..020202..02020201..0202020103.', 'XXX银行/四川分行/德阳分行/锦阳支行/IT部门', 'Y', null, null, '02020201', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:46:51', null);
INSERT INTO `sys_unit` VALUES ('020202010301', '软件开发部', '', 'XXX银行四川分行德阳分行锦阳支行IT部门软件开发部', 'DEPT', '90', '02020201', '.02..0202..020202..02020201..0202020103..020202010301.', 'XXX银行/四川分行/德阳分行/锦阳支行/IT部门/软件开发部', null, null, null, '0202020103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:47:03', null);
INSERT INTO `sys_unit` VALUES ('020202010302', '运行维护部门', '', 'XXX银行四川分行德阳分行锦阳支行IT部门运行维护部门', 'DEPT', '90', '02020201', '.02..0202..020202..02020201..0202020103..020202010302.', 'XXX银行/四川分行/德阳分行/锦阳支行/IT部门/运行维护部门', null, null, null, '0202020103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:47:32', null);
INSERT INTO `sys_unit` VALUES ('02020202', '市分行', '', 'XXX银行四川分行德阳分行市分行', 'DEPT', '70', '020202', '.02..0202..020202..02020202.', 'XXX银行/四川分行/德阳分行/市分行', null, null, null, '020202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:07:30', null);
INSERT INTO `sys_unit` VALUES ('0202020201', '合规部', '', 'XXX银行四川分行德阳分行市分行合规部', 'DEPT', '70', '020202', '.02..0202..020202..02020202..0202020201.', 'XXX银行/四川分行/德阳分行/市分行/合规部', null, null, null, '02020202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:08:15', null);
INSERT INTO `sys_unit` VALUES ('0202020202', '风险部', '', 'XXX银行四川分行德阳分行市分行风险部', 'DEPT', '70', '020202', '.02..0202..020202..02020202..0202020202.', 'XXX银行/四川分行/德阳分行/市分行/风险部', null, null, null, '02020202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:08:45', null);
INSERT INTO `sys_unit` VALUES ('0202020203', 'IT部', '', 'XXX银行四川分行德阳分行市分行IT部', 'DEPT', '70', '020202', '.02..0202..020202..02020202..0202020203.', 'XXX银行/四川分行/德阳分行/市分行/IT部', 'Y', null, null, '02020202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:09:17', null);
INSERT INTO `sys_unit` VALUES ('020202020301', '软件开发部门', '', 'XXX银行四川分行德阳分行市分行IT部软件开发部门', 'DEPT', '70', '020202', '.02..0202..020202..02020202..0202020203..020202020301.', 'XXX银行/四川分行/德阳分行/市分行/IT部/软件开发部门', null, null, null, '0202020203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:09:56', null);
INSERT INTO `sys_unit` VALUES ('020202020302', '运行维护部', '', 'XXX银行四川分行德阳分行市分行IT部运行维护部', 'DEPT', '70', '020202', '.02..0202..020202..02020202..0202020203..020202020302.', 'XXX银行/四川分行/德阳分行/市分行/IT部/运行维护部', null, null, null, '0202020203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:10:46', null);
INSERT INTO `sys_unit` VALUES ('020203', '绵阳分行', '', 'XXX银行四川分行绵阳分行', 'UNIT', '70', '020203', '.02..0202..020203.', 'XXX银行/四川分行/绵阳分行', 'Y', null, null, '0202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:57', null);
INSERT INTO `sys_unit` VALUES ('02020301', '游仙支行', '', 'XXX银行四川分行绵阳分行游仙支行', 'UNIT', '90', '02020301', '.02..0202..020203..02020301.', 'XXX银行/四川分行/绵阳分行/游仙支行', null, null, null, '020203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:37', null);
INSERT INTO `sys_unit` VALUES ('0202030101', '风险部', '', 'XXX银行四川分行绵阳分行游仙支行风险部', 'DEPT', '90', '02020301', '.02..0202..020203..02020301..0202030101.', 'XXX银行/四川分行/绵阳分行/游仙支行/风险部', null, null, null, '02020301', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:41', null);
INSERT INTO `sys_unit` VALUES ('0202030102', '合规部', '', 'XXX银行四川分行绵阳分行游仙支行合规部', 'DEPT', '90', '02020301', '.02..0202..020203..02020301..0202030102.', 'XXX银行/四川分行/绵阳分行/游仙支行/合规部', 'Y', null, null, '02020301', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:11:27', null);
INSERT INTO `sys_unit` VALUES ('0202030103', 'IT部门', '', 'XXX银行四川分行绵阳分行游仙支行IT部门', 'DEPT', '90', '02020301', '.02..0202..020203..02020301..0202030103.', 'XXX银行/四川分行/绵阳分行/游仙支行/IT部门', 'Y', null, null, '02020301', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:46:51', null);
INSERT INTO `sys_unit` VALUES ('020203010301', '软件开发部', '', 'XXX银行四川分行绵阳分行游仙支行IT部门软件开发部', 'DEPT', '90', '02020301', '.02..0202..020203..02020301..0202030103..020203010301.', 'XXX银行/四川分行/绵阳分行/游仙支行/IT部门/软件开发部', null, null, null, '0202030103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:47:03', null);
INSERT INTO `sys_unit` VALUES ('020203010302', '运行维护部门', '', 'XXX银行四川分行绵阳分行游仙支行IT部门运行维护部门', 'DEPT', '90', '02020301', '.02..0202..020203..02020301..0202030103..020203010302.', 'XXX银行/四川分行/绵阳分行/游仙支行/IT部门/运行维护部门', null, null, null, '0202030103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:47:32', null);
INSERT INTO `sys_unit` VALUES ('02020302', '市分行', '', 'XXX银行四川分行绵阳分行市分行', 'DEPT', '70', '020203', '.02..0202..020203..02020302.', 'XXX银行/四川分行/绵阳分行/市分行', null, null, null, '020203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:07:30', null);
INSERT INTO `sys_unit` VALUES ('0202030201', '合规部', '', 'XXX银行四川分行绵阳分行市分行合规部', 'DEPT', '70', '020203', '.02..0202..020203..02020302..0202030201.', 'XXX银行/四川分行/绵阳分行/市分行/合规部', null, null, null, '02020302', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:08:15', null);
INSERT INTO `sys_unit` VALUES ('0202030202', '风险部', '', 'XXX银行四川分行绵阳分行市分行风险部', 'DEPT', '70', '020203', '.02..0202..020203..02020302..0202030202.', 'XXX银行/四川分行/绵阳分行/市分行/风险部', null, null, null, '02020302', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:08:45', null);
INSERT INTO `sys_unit` VALUES ('0202030203', 'IT部', '', 'XXX银行四川分行绵阳分行市分行IT部', 'DEPT', '70', '020203', '.02..0202..020203..02020302..0202030203.', 'XXX银行/四川分行/绵阳分行/市分行/IT部', 'Y', null, null, '02020302', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:09:17', null);
INSERT INTO `sys_unit` VALUES ('020203020301', '软件开发部门', '', 'XXX银行四川分行绵阳分行市分行IT部软件开发部门', 'DEPT', '70', '020203', '.02..0202..020203..02020302..0202030203..020203020301.', 'XXX银行/四川分行/绵阳分行/市分行/IT部/软件开发部门', null, null, null, '0202030203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:09:56', null);
INSERT INTO `sys_unit` VALUES ('020203020302', '运行维护部', '', 'XXX银行四川分行绵阳分行市分行IT部运行维护部', 'DEPT', '70', '020203', '.02..0202..020203..02020302..0202030203..020203020302.', 'XXX银行/四川分行/绵阳分行/市分行/IT部/运行维护部', null, null, null, '0202030203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:10:46', null);
INSERT INTO `sys_unit` VALUES ('020204', '省分行', '', 'XXX银行四川分行省分行', 'DEPT', '50', '0202', '.02..0202..020204.', 'XXX银行/四川分行/省分行', null, null, null, '0202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:59', null);
INSERT INTO `sys_unit` VALUES ('02020401', '合规部', '', 'XXX银行四川分行省分行合规部', 'DEPT', '50', '0202', '.02..0202..020204..02020401.', 'XXX银行/四川分行/省分行/合规部', 'N', null, null, '020204', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:04:03', null);
INSERT INTO `sys_unit` VALUES ('02020402', '风险部', '', 'XXX银行四川分行省分行风险部', 'DEPT', '50', '0202', '.02..0202..020204..02020402.', 'XXX银行/四川分行/省分行/风险部', null, null, null, '020204', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:04:05', null);
INSERT INTO `sys_unit` VALUES ('02020403', 'IT部', '', 'XXX银行四川分行省分行IT部', 'DEPT', '50', '0202', '.02..0202..020204..02020403.', 'XXX银行/四川分行/省分行/IT部', 'Y', null, null, '020204', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:04:08', null);
INSERT INTO `sys_unit` VALUES ('0202040301', '软件开发部', '', 'XXX银行四川分行省分行IT部软件开发部', 'DEPT', '50', '0202', '.02..0202..020204..02020403..0202040301.', 'XXX银行/四川分行/省分行/IT部/软件开发部', null, null, null, '02020403', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:05:35', null);
INSERT INTO `sys_unit` VALUES ('0202040302', '运行维护部', '', 'XXX银行四川分行省分行IT部运行维护部', 'DEPT', '50', '0202', '.02..0202..020204..02020403..0202040302.', 'XXX银行/四川分行/省分行/IT部/运行维护部', null, null, null, '02020403', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:06:17', null);
INSERT INTO `sys_unit` VALUES ('0203', '贵州分行', '', 'XXX银行贵州分行', 'UNIT', '50', '0203', '.02..0203.', 'XXX银行/贵州分行', null, null, null, '02', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-19 16:32:37', null);
INSERT INTO `sys_unit` VALUES ('020301', '省分行', '', 'XXX银行贵州分行省分行', 'DEPT', '50', '0203', '.02..0203..020301.', 'XXX银行/贵州分行/省分行', null, null, null, '0203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:12:27', null);
INSERT INTO `sys_unit` VALUES ('02030101', '合规部', '', 'XXX银行贵州分行省分行合规部', 'DEPT', '50', '0203', '.02..0203..020301..02030101.', 'XXX银行/贵州分行/省分行/合规部', 'N', null, null, '020301', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:12:30', null);
INSERT INTO `sys_unit` VALUES ('02030102', '风险部', '', 'XXX银行贵州分行省分行风险部', 'DEPT', '50', '0203', '.02..0203..020301..02030102.', 'XXX银行/贵州分行/省分行/风险部', null, null, null, '020301', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:12:32', null);
INSERT INTO `sys_unit` VALUES ('02030103', 'IT部', '', 'XXX银行贵州分行省分行IT部', 'DEPT', '50', '0203', '.02..0203..020301..02030103.', 'XXX银行/贵州分行/省分行/IT部', 'Y', null, null, '020301', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:12:34', null);
INSERT INTO `sys_unit` VALUES ('0203010301', '软件开发部', '', 'XXX银行贵州分行省分行IT部软件开发部', 'DEPT', '50', '0203', '.02..0203..020301..02030103..0203010301.', 'XXX银行/贵州分行/省分行/IT部/软件开发部', null, null, null, '02030103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:12:36', null);
INSERT INTO `sys_unit` VALUES ('0203010302', '运行维护部', '', 'XXX银行贵州分行省分行IT部运行维护部', 'DEPT', '50', '0203', '.02..0203..020301..02030103..0203010302.', 'XXX银行/贵州分行/省分行/IT部/运行维护部', null, null, null, '02030103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:12:43', null);
INSERT INTO `sys_unit` VALUES ('020302', '贵阳分行', '', 'XXX银行贵州分行贵阳分行', 'UNIT', '70', '020302', '.02..0203..020302.', 'XXX银行/贵州分行/贵阳分行', null, null, null, '0203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:33', null);
INSERT INTO `sys_unit` VALUES ('02030202', '市分行', '', 'XXX银行贵州分行贵阳分行市分行', 'DEPT', '70', '020302', '.02..0203..020302..02030202.', 'XXX银行/贵州分行/贵阳分行/市分行', null, null, null, '020302', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:07:30', null);
INSERT INTO `sys_unit` VALUES ('0203020201', '合规部', '', 'XXX银行贵州分行贵阳分行市分行合规部', 'DEPT', '70', '020302', '.02..0203..020302..02030202..0203020201.', 'XXX银行/贵州分行/贵阳分行/市分行/合规部', null, null, null, '02030202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:08:15', null);
INSERT INTO `sys_unit` VALUES ('0203020202', '风险部', '', 'XXX银行贵州分行贵阳分行市分行风险部', 'DEPT', '70', '020302', '.02..0203..020302..02030202..0203020202.', 'XXX银行/贵州分行/贵阳分行/市分行/风险部', null, null, null, '02030202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:08:45', null);
INSERT INTO `sys_unit` VALUES ('0203020203', 'IT部', '', 'XXX银行贵州分行贵阳分行市分行IT部', 'DEPT', '70', '020302', '.02..0203..020302..02030202..0203020203.', 'XXX银行/贵州分行/贵阳分行/市分行/IT部', 'Y', null, null, '02030202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:09:17', null);
INSERT INTO `sys_unit` VALUES ('020302020301', '软件开发部门', '', 'XXX银行贵州分行贵阳分行市分行IT部软件开发部门', 'DEPT', '70', '020302', '.02..0203..020302..02030202..0203020203..020302020301.', 'XXX银行/贵州分行/贵阳分行/市分行/IT部/软件开发部门', null, null, null, '0203020203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:09:56', null);
INSERT INTO `sys_unit` VALUES ('020302020302', '运行维护部', '', 'XXX银行贵州分行贵阳分行市分行IT部运行维护部', 'DEPT', '70', '020302', '.02..0203..020302..02030202..0203020203..020302020302.', 'XXX银行/贵州分行/贵阳分行/市分行/IT部/运行维护部', null, null, null, '0203020203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:10:46', null);
INSERT INTO `sys_unit` VALUES ('0204', '云南分行', '', 'XXX银行云南分行', 'UNIT', '50', '0204', '.02..0204.', 'XXX银行/云南分行', null, null, null, '02', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-19 17:01:27', null);
INSERT INTO `sys_unit` VALUES ('020401', '省分行', '', 'XXX银行云南分行省分行', 'DEPT', '50', '0204', '.02..0204..020401.', 'XXX银行/云南分行/省分行', null, null, null, '0204', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:17:13', null);
INSERT INTO `sys_unit` VALUES ('02040101', '合规部', '', 'XXX银行云南分行省分行合规部', 'DEPT', '50', '0204', '.02..0204..020401..02040101.', 'XXX银行/云南分行/省分行/合规部', 'N', null, null, '020401', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:17:19', null);
INSERT INTO `sys_unit` VALUES ('02040102', '风险部', '', 'XXX银行云南分行省分行风险部', 'DEPT', '50', '0204', '.02..0204..020401..02040102.', 'XXX银行/云南分行/省分行/风险部', null, null, null, '020401', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:17:24', null);
INSERT INTO `sys_unit` VALUES ('02040103', 'IT部', '', 'XXX银行云南分行省分行IT部', 'DEPT', '50', '0204', '.02..0204..020401..02040103.', 'XXX银行/云南分行/省分行/IT部', 'Y', null, null, '020401', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:17:27', null);
INSERT INTO `sys_unit` VALUES ('0204010301', '软件开发部', '', 'XXX银行云南分行省分行IT部软件开发部', 'DEPT', '50', '0204', '.02..0204..020401..02040103..0204010301.', 'XXX银行/云南分行/省分行/IT部/软件开发部', null, null, null, '02040103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:17:34', null);
INSERT INTO `sys_unit` VALUES ('0204010302', '运行维护部', '', 'XXX银行云南分行省分行IT部运行维护部', 'DEPT', '50', '0204', '.02..0204..020401..02040103..0204010302.', 'XXX银行/云南分行/省分行/IT部/运行维护部', null, null, null, '02040103', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 22:17:40', null);
INSERT INTO `sys_unit` VALUES ('020402', '贵阳分行', '', 'XXX银行贵州分行贵阳分行', 'UNIT', '70', '020402', '.02..0204..020402.', 'XXX银行/贵州分行/贵阳分行', null, null, null, '0204', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:03:33', null);
INSERT INTO `sys_unit` VALUES ('02040202', '市分行', '', 'XXX银行贵州分行贵阳分行市分行', 'DEPT', '70', '020402', '.02..0204..020402..02040202.', 'XXX银行/贵州分行/贵阳分行/市分行', null, null, null, '020402', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:07:30', null);
INSERT INTO `sys_unit` VALUES ('0204020201', '合规部', '', 'XXX银行贵州分行贵阳分行市分行合规部', 'DEPT', '70', '020402', '.02..0204..020402..02040202..0204020201.', 'XXX银行/贵州分行/贵阳分行/市分行/合规部', null, null, null, '02040202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:08:15', null);
INSERT INTO `sys_unit` VALUES ('0204020202', '风险部', '', 'XXX银行贵州分行贵阳分行市分行风险部', 'DEPT', '70', '020402', '.02..0204..020402..02040202..0204020202.', 'XXX银行/贵州分行/贵阳分行/市分行/风险部', null, null, null, '02040202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:08:45', null);
INSERT INTO `sys_unit` VALUES ('0204020203', 'IT部', '', 'XXX银行贵州分行贵阳分行市分行IT部', 'DEPT', '70', '020402', '.02..0204..020402..02040202..0204020203.', 'XXX银行/贵州分行/贵阳分行/市分行/IT部', 'Y', null, null, '02040202', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:09:17', null);
INSERT INTO `sys_unit` VALUES ('020402020301', '软件开发部门', '', 'XXX银行贵州分行贵阳分行市分行IT部软件开发部门', 'DEPT', '70', '020402', '.02..0204..020402..02040202..0204020203..020402020301.', 'XXX银行/贵州分行/贵阳分行/市分行/IT部/软件开发部门', null, null, null, '0204020203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:09:56', null);
INSERT INTO `sys_unit` VALUES ('020402020302', '运行维护部', '', 'XXX银行贵州分行贵阳分行市分行IT部运行维护部', 'DEPT', '70', '020402', '.02..0204..020402..02040202..0204020203..020402020302.', 'XXX银行/贵州分行/贵阳分行/市分行/IT部/运行维护部', null, null, null, '0204020203', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-07-18 21:10:46', null);
INSERT INTO `sys_unit` VALUES ('099', '风控部门', '机构描述', 'XXX银行总行风控部门', 'DEPT', '30', '099', '.02..0201..099.', 'XXX银行/总行/风控部门', 'Y', null, '资产业务', '0201', null, null, null, null, null, null, null, 'APPLY', null, null, null, null, null, null, null, null, '2017-08-29 14:57:17', null);

-- ----------------------------
-- Table structure for sys_staff
-- ----------------------------
DROP TABLE IF EXISTS `sys_staff`;
CREATE TABLE `sys_staff` (
  `STAFF_CODE_` varchar(64) NOT NULL COMMENT '用户编码',
  `STAFF_NAME_` varchar(128) NOT NULL COMMENT '用户名称',
  `DEPT_CODE_` varchar(64) NOT NULL COMMENT '所属部门',
  `DEPT_NAME_` varchar(128) DEFAULT NULL COMMENT '所属部门',
  `UNIT_CODE_` varchar(64) DEFAULT NULL COMMENT '机构编码',
  `UNIT_NAME_` varchar(128) DEFAULT NULL COMMENT '机构编码',
  `POSITION_NAME_` varchar(128) DEFAULT NULL COMMENT '职位名称',
  `POST_NAME_` varchar(128) DEFAULT NULL COMMENT '汇报人',
  `GENDER_` varchar(64) NOT NULL COMMENT '性别',
  `ID_CARD_` varchar(64) DEFAULT NULL COMMENT '身份证号',
  `BIRTHDAY_` date DEFAULT NULL COMMENT '出生日期',
  `EMAIL_` varchar(128) DEFAULT NULL COMMENT '邮件',
  `TELEPHONE_` varchar(64) DEFAULT NULL COMMENT '电话',
  `MOBILE_` varchar(64) DEFAULT NULL COMMENT '移动电话',
  `ADDRESS_` varchar(256) DEFAULT NULL COMMENT '地址',
  `STATUS_` varchar(64) NOT NULL COMMENT '状态，ENABLE - 启用，DISABLE - 停用',
  `ADMIN_LEVEL_` varchar(64) DEFAULT NULL COMMENT '管理级别，暂未启用',
  `IS_PROXY_` varchar(64) DEFAULT NULL COMMENT '是否被借调',
  `PROXY_STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '借调人编码',
  `STAFF_TYPE_` varchar(64) DEFAULT NULL COMMENT '用户类型： 管理| 普通',
  `STAFF_TYPE2_` varchar(64) DEFAULT NULL COMMENT '用户类型： 前端 | 后端',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`STAFF_CODE_`),
  KEY `FK_Reference_7` (`DEPT_CODE_`),
  CONSTRAINT `sys_staff_ibfk_1` FOREIGN KEY (`DEPT_CODE_`) REFERENCES `sys_unit` (`UNIT_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基本信息表';

-- ----------------------------
-- Records of sys_staff
-- ----------------------------
INSERT INTO `sys_staff` VALUES ('090', '小红', '020102', '风险部', '02', 'XXX银行', null, null, 'MAN', null, '2017-08-08', '136776768@qq.com', null, '15678987786', null, 'ENABLE', null, null, null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('brm', '柏荣幕', '0201', '总行', '02', 'XXX银行', null, null, 'MAN', null, '1983-12-10', 'brm@gmail.com', null, '18392038473', '', 'ENABLE', null, 'N', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('brm1212', '121212', '020101', '合规部', '02', 'XXX银行', null, null, 'MAN', null, null, null, null, null, null, 'ENABLE', null, 'N', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('brm_proxy_0202010101', '柏荣幕（借调）', '0202010101', '风险部', '02020101', '武侯支行', null, null, 'MAN', null, '1983-12-10', 'brm@gmail.com', null, '18392038473', '', 'ENABLE', null, 'Y', 'brm', 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('brm_proxy_0202010101_proxy_020101', '柏荣幕（借调）（借调）', '020101', '合规部', '02', 'XXX银行', null, null, 'MAN', null, '1983-12-10', 'brm@gmail.com', null, '18392038473', '', 'ENABLE', null, 'N', 'brm_proxy_0202010101', 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('brm_proxy_02020401', '柏荣幕（借调）', '02020401', '合规部', '0202', '四川分行', null, null, 'MAN', null, '1983-12-10', 'brm@gmail.com', null, '18392038473', '', 'ENABLE', null, 'N', 'brm', 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('concurFApprove', '周小川', '020401', '省分行', '0204', '省分行', null, null, 'MAN', null, null, null, null, null, null, 'ENABLE', null, null, null, 'COMMON', 'FRONT', null, null, null, null, null, null, null, null, '2018-03-02 17:33:53', null);
INSERT INTO `sys_staff` VALUES ('concurGMApprove', '周毅', '020401', '省分行', '0204', '省分行', null, null, 'MAN', null, null, null, null, null, null, 'ENABLE', null, null, null, 'COMMON', 'FRONT', null, null, null, null, null, null, null, null, '2018-03-02 17:33:49', null);
INSERT INTO `sys_staff` VALUES ('concurPMApprov', '周曦', '02040101', '合规部', '0204', '合规部', null, null, 'MAN', null, null, null, null, null, null, 'ENABLE', null, null, null, 'COMMON', 'FRONT', null, null, null, null, null, null, null, null, '2018-03-02 17:33:47', null);
INSERT INTO `sys_staff` VALUES ('concurSubmit', '周小斌', '02040101', '合规部', '0203', '省分行', null, null, 'MAN', null, null, null, null, null, null, 'ENABLE', null, null, null, 'COMMON', 'FRONT', null, null, null, null, null, null, null, null, '2018-03-02 17:33:45', null);
INSERT INTO `sys_staff` VALUES ('fxe', '冯絮儿', '099', '风控部门', '02', 'XXX银行', null, null, 'MAN', null, '1995-06-26', 'fxe@gmail.com', null, '13808142673', 'ypoop/ioiuy', 'DISABLE', null, 'N', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('jepAdmin', '超级管理员', '0201', '总行', '02', 'XXX银行', null, null, 'MAN', null, '2017-07-27', '', null, '', '', 'ENABLE', null, null, null, 'ADMIN', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('leaveCheck', '检查人员', '020301', '省分行', '0203', '省分行', null, null, 'MAN', null, null, null, null, null, null, 'ENABLE', null, null, null, 'COMMON', 'FRONT', null, null, null, null, null, null, null, null, '2018-03-02 17:34:16', null);
INSERT INTO `sys_staff` VALUES ('leaveHRApprove', '王二', '020301', '省分行', '0203', '省分行', null, null, 'MAN', null, null, null, null, null, null, 'ENABLE', null, null, null, 'COMMON', 'FRONT', null, null, null, null, null, null, null, null, '2018-03-02 17:34:11', null);
INSERT INTO `sys_staff` VALUES ('leavePMApprove', '李四', '02030101', '合规部', '0203', '合规部', null, null, 'MAN', null, null, null, null, null, null, 'ENABLE', null, null, null, 'COMMON', 'FRONT', null, null, null, null, null, null, null, null, '2018-03-02 17:34:09', null);
INSERT INTO `sys_staff` VALUES ('leaveSubmit', '张三丰', '02030101', '合规部', '0203', '合规部', null, null, 'MAN', null, '2018-03-01', null, null, null, null, 'ENABLE', null, null, null, 'COMMON', 'FRONT', null, null, null, null, null, null, null, null, '2018-03-02 17:34:07', null);
INSERT INTO `sys_staff` VALUES ('lrg', '龙瑞刚', '020103', 'IT部', '02', 'XXX银行', null, null, 'MAN', null, '1989-04-18', 'lrg@gmail.com', null, '15193048332', '', 'ENABLE', null, 'N', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('odn', '欧德恩', '0201', '总行', '02', 'XXX银行', null, null, 'MAN', null, '1988-10-05', 'odn@gmail.com', null, '17209372957', '', 'ENABLE', null, 'Y', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('wmd', '吴梦蝶', '020101', '合规部', '02', '合规部', null, null, 'FEMALE', null, '1994-03-16', 'wmd@jep.com', null, '15920930947', '无锡', 'ENABLE', null, null, null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('wrj', '魏荣建', '0201', '总行', '02', 'XXX银行', null, null, 'MAN', null, '1982-06-16', 'wrj@gmail.com', null, '19193824872', '', 'ENABLE', null, 'N', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('wxw', '吴宪魏', '020101', '合规部', '02', 'XXX银行', null, null, 'MAN', null, '1985-08-12', 'wxw@gmail.com', null, '15609678732', '', 'ENABLE', null, 'N', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('xhf', '肖鸿飞', '020101', '合规部', '02', 'XXX银行', null, null, 'MAN', null, '1990-05-25', 'xhf@gmail.com', null, '18769542019', '', 'ENABLE', null, 'N', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('xmr', '席明瑞', '020101', '合规部', '02', 'XXX银行', null, null, 'MAN', null, '1989-08-23', 'xmr@gmail.com', null, '13792373800', '', 'ENABLE', null, 'N', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('xyl', '席玉龙', '0202010402', '风险部', '020201', '成都分行', null, null, 'MAN', null, '1987-11-20', 'xyl@gmail.com', null, '18978374093', '', 'ENABLE', null, 'N', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('xyr', '薛嫣然', '0202010402', '风险部', '020201', '成都分行', null, null, 'FEMALE', null, '1995-11-10', 'xyr@gmail.com', null, '18398347203', '', 'ENABLE', null, 'N', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);
INSERT INTO `sys_staff` VALUES ('yxf', '叶雪飞', '0202010401', '合规部', '020201', '成都分行', null, null, 'FEMALE', null, '1992-05-23', 'yxf@gmail.com', null, '18909113387', '', 'ENABLE', null, 'N', null, 'COMMON', 'BACK', null, null, null, null, null, null, null, null, '2018-03-02 09:16:03', null);


drop table if exists SYS_TENANT;

/*==============================================================*/
/* Table: SYS_TENANT                                            */
/*==============================================================*/
create table SYS_TENANT
(
   ID_                  varchar(64) not null comment '主键',
   CODE_                varchar(64) comment '编码',
   NAME_                varchar(256) comment '名称',
   STATUS_							varchar(64) comment '状态',
   IS_ROOT_             varchar(64) comment '是否根租户',
   REMARK_              varchar(512) comment '备注',
   TENANT_ID_       		varchar(64) DEFAULT NULL COMMENT '租户',
   CREATED_BY_ID_ 			varchar(64) DEFAULT NULL COMMENT '创建人ID',
   CREATED_BY_NAME_ 		varchar(64) DEFAULT NULL COMMENT '创建人名称',
   CREATED_TIME_ 				datetime DEFAULT NULL COMMENT '创建时间',
   DELFLAG_ 						varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
   DISPLAY_ORDER_ 			int(10) DEFAULT NULL COMMENT '显示序号',
   MODIFIED_BY_ID_ 			varchar(64) DEFAULT NULL COMMENT '修改人ID',
   MODIFIED_BY_NAME_ 		varchar(64) DEFAULT NULL COMMENT '修改人名称',
   MODIFIED_TIME_ 			timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
   VERSION_ 						int(10) 		DEFAULT NULL COMMENT '版本',
   primary key (ID_)
);

alter table SYS_TENANT comment '租户表';

INSERT INTO `sys_tenant` (`ID_`, `CODE_`, `NAME_`, `STATUS_`, `IS_ROOT_`, `REMARK_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('e2c92947-1c9e-11e8-ac60-28c63ffd0c51', NULL, 'XXX银行', 'ENABLED', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2018-3-1 10:58:28', NULL);

-- ----------------------------
-- Table structure for `sys_biz_code`
-- ----------------------------
DROP TABLE IF EXISTS `sys_biz_code`;
CREATE TABLE `sys_biz_code` (
  `CODE_KEY_` varchar(64) NOT NULL COMMENT '主键编码',
  `CURR_SEQ_` int(11) DEFAULT NULL COMMENT '当前序号',
  `LAST_SEQ_` int(11) DEFAULT NULL COMMENT '上个序号',
  `LAST_USED_TIME_` datetime DEFAULT NULL COMMENT '上次使用时间',
  `PATTERN_` varchar(256) DEFAULT NULL COMMENT '生成模式',
  `POOL_SIZE_` int(11) DEFAULT NULL COMMENT '池大小，暂未启用',
  `RESET_TYPE_` varchar(64) DEFAULT NULL COMMENT '重置周期：DAY - 日，MONTH - 月，YEAR - 年',
  `SEQ_WIDTH_` int(11) DEFAULT NULL COMMENT '数据宽度，如果设置为4，则生成 0001， 0002',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`CODE_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定义业务主键的生成方式，保存业务主键的序列。';

-- ----------------------------
-- Records of sys_biz_code
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_biz_dept_proxy`
-- ----------------------------
DROP TABLE IF EXISTS `sys_biz_dept_proxy`;
CREATE TABLE `sys_biz_dept_proxy` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `DEPT_CODE_` varchar(64) DEFAULT NULL COMMENT '部门代码',
  `DEPT_NAME_` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `STD_ROLE_CODE_` varchar(64) DEFAULT NULL COMMENT '标准角色编码',
  `STD_ROLE_NAME_` varchar(128) DEFAULT NULL COMMENT '标准角色名称',
  `PROXY_DEPT_CODE_` varchar(64) DEFAULT NULL COMMENT '代管部门代码',
  `PROXY_DEPT_NAME_` varchar(128) DEFAULT NULL COMMENT '代管部门名称',
  `PROXY_STD_ROLE_CODE_` varchar(64) DEFAULT NULL COMMENT '代管标准角色编码',
  `PROXY_STD_ROLE_NAME_` varchar(128) DEFAULT NULL COMMENT '代管标准角色名称',
  `REMARK_` varchar(256) DEFAULT NULL COMMENT '备注',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构代管关系表';

-- ----------------------------
-- Records of sys_biz_dept_proxy
-- ----------------------------
INSERT INTO `sys_biz_dept_proxy` VALUES ('a8981a1f-8ca6-11e7-9e24-00ffb5d5587a', '020103', 'IT部', '110', '管理岗', '0202', '四川分行', '110', '管理岗', '测试2', null, null, null, null, null, null, null, null, null, '2017-08-29 18:42:22', null);
INSERT INTO `sys_biz_dept_proxy` VALUES ('b85c2c02-8c87-11e7-9e24-00ffb5d5587a', '0202010102', '合规部', '110', '管理岗', '020101', '合规部', '110', '管理岗', 'sdfas2', null, null, null, null, null, null, null, null, null, '2017-08-29 18:41:48', null);

-- ----------------------------
-- Table structure for `sys_biz_dept_report`
-- ----------------------------
DROP TABLE IF EXISTS `sys_biz_dept_report`;
CREATE TABLE `sys_biz_dept_report` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `DEPT_CODE_` varchar(64) DEFAULT NULL COMMENT '部门代码',
  `DEPT_NAME_` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `BIZ_CODE_` varchar(64) DEFAULT NULL COMMENT '部门业务种类编码',
  `BIZ_NAME_` varchar(128) DEFAULT NULL COMMENT '部门业务种类名称',
  `MANAGER_DEPT_CODE_` varchar(64) DEFAULT NULL COMMENT '管理部门代码',
  `MANAGER_DEPT_NAME_` varchar(128) DEFAULT NULL COMMENT '管理部门名称',
  `REMARK_` varchar(256) DEFAULT NULL COMMENT '备注',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构上报关系表';

-- ----------------------------
-- Records of sys_biz_dept_report
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_biz_dept_staff`
-- ----------------------------
DROP TABLE IF EXISTS `sys_biz_dept_staff`;
CREATE TABLE `sys_biz_dept_staff` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '用户编码',
  `STAFF_NAME_` varchar(128) DEFAULT NULL COMMENT '用户名称',
  `DEPT_CODE_` varchar(64) DEFAULT NULL COMMENT '部门编码',
  `DEPT_NAME_` varchar(128) DEFAULT NULL COMMENT '部门编码',
  `UNIT_CODE_` varchar(64) DEFAULT NULL COMMENT '机构编码',
  `UNIT_NAME_` varchar(128) DEFAULT NULL COMMENT '机构名称',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`),
  KEY `FK_Reference_36` (`DEPT_CODE_`),
  KEY `FK_Reference_37` (`STAFF_CODE_`),
  CONSTRAINT `sys_biz_dept_staff_ibfk_1` FOREIGN KEY (`STAFF_CODE_`) REFERENCES `sys_staff` (`STAFF_CODE_`),
  CONSTRAINT `sys_biz_dept_staff_ibfk_2` FOREIGN KEY (`DEPT_CODE_`) REFERENCES `sys_unit` (`UNIT_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于确定人员所在条线，一个人可以在多个条线中。一个条线也存在多个人员。方便用于剪枝的机构树进行数据权限、工作流等控制';

-- ----------------------------
-- Records of sys_biz_dept_staff
-- ----------------------------
INSERT INTO `sys_biz_dept_staff` VALUES ('2017072410291590226510C3304DB49C390858497DF180', 'lrg', '龙瑞刚', '020103', 'IT部', '02', 'XXX银行', null, null, null, null, null, null, null, null, '2017-07-24 10:29:03', null);

-- ----------------------------
-- Table structure for `sys_biz_type`
-- ----------------------------
DROP TABLE IF EXISTS `sys_biz_type`;
CREATE TABLE `sys_biz_type` (
  `BIZ_TYPE_CODE_` varchar(64) NOT NULL COMMENT '业务种类编码',
  `BIZ_TYPE_NAME_` varchar(128) DEFAULT NULL COMMENT '业务种类名称',
  `PARENT_ID_` varchar(64) DEFAULT NULL COMMENT '父类编码',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态',
  `REMARK_` varchar(256) DEFAULT NULL COMMENT '备注',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`BIZ_TYPE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务种类表';

-- ----------------------------
-- Records of sys_biz_type
-- ----------------------------
INSERT INTO `sys_biz_type` VALUES ('ASSET', '资产业务', null, 'DRAFT', '资产业务，是商业银行的主要收入来源。', null, null, null, null, null, null, null, null, '2017-07-19 11:17:51', null);
INSERT INTO `sys_biz_type` VALUES ('BILL DISCOUNT', '票据贴现贷款', 'CREDIT', 'ENABLE', '顾客将未到期的票据提交银行，由银行扣除自贴现日起至到期日止的利息而取得现款。', null, null, null, null, null, null, null, null, '2017-07-19 14:05:23', null);
INSERT INTO `sys_biz_type` VALUES ('CHIT SECURED LOAN', '保证书担保贷款', 'LOAN', 'ENABLE', '保证书担保贷款，是指由经第三者出具保证书担保的贷款。', null, null, null, null, null, null, null, null, '2017-07-19 14:15:23', null);
INSERT INTO `sys_biz_type` VALUES ('CONSUMER', '消费者贷款', 'CREDIT', 'ENABLE', '消费者贷款是对消费个人发放的用于购买耐用消费品或支付其他费用的贷款，商业银行向客户提供这种贷款时，要进行多方面的审查。', null, null, null, null, null, null, null, null, '2017-07-19 14:04:47', null);
INSERT INTO `sys_biz_type` VALUES ('CREDIT', '信用贷款', 'LOAN', 'DRAFT', '信用贷款，指单凭借款人的信誉，而不需提供任何抵押品的贷款，是一种资本贷款', null, null, null, null, null, null, null, null, '2017-07-19 11:20:13', null);
INSERT INTO `sys_biz_type` VALUES ('CURRENT', '活期存款', 'DEBT', 'ENABLE', '活期存款是相对于定期存款而言的，是不需预先通知可随时提取或支付的存款。', null, null, null, null, null, null, null, null, '2017-07-19 14:21:14', null);
INSERT INTO `sys_biz_type` VALUES ('CUSTOMER BILL', '客帐贷款', 'MORTGAGE', 'ENABLE', '银行发放的以应收帐款作为抵押的短期贷款，称为“客帐贷款”。这种贷款一般都为一种持续性的信贷协定。', null, null, null, null, null, null, null, null, '2017-07-19 14:11:08', null);
INSERT INTO `sys_biz_type` VALUES ('DEBT', '负债业务', null, 'ENABLE', '负债是银行由于授信而承担的将以资产或资本偿付的能以货币计量的债务。', null, null, null, null, null, null, null, null, '2017-07-19 14:20:28', null);
INSERT INTO `sys_biz_type` VALUES ('FIXED DEPOSIT', '定期存款', 'DEBT', 'ENABLE', '定期存款是相对于活期存款而言的，是一种由存户预先约定期限的存款。', null, null, null, null, null, null, null, null, '2017-07-19 14:22:01', null);
INSERT INTO `sys_biz_type` VALUES ('INTERMEDIARY BIZ', '中间业务', null, 'ENABLE', '中间业务又称表外业务，其收入不列入银行资产负债表。', null, null, null, null, null, null, null, null, '2017-07-19 14:23:34', null);
INSERT INTO `sys_biz_type` VALUES ('INVENTORY', '存货贷款', 'MORTGAGE', 'ENABLE', '存货贷款也称商品贷款，是一种以企业的存贷或商品作为抵押品的短期贷款。', null, null, null, null, null, null, null, null, '2017-07-19 14:09:31', null);
INSERT INTO `sys_biz_type` VALUES ('INVESTMENT', '投资业务', 'ASSET', 'ENABLE', '商业银行的投资业务是指银行购买有价证券的活动。投资是商业银行一项重要的资产业务，是银行收入的主要来源之一。', null, null, null, null, null, null, null, null, '2017-07-19 14:16:55', null);
INSERT INTO `sys_biz_type` VALUES ('LOAN', '贷款业务', 'ASSET', 'DRAFT', '商业银行最主要的资产业务。', null, null, null, null, null, null, null, null, '2017-07-19 11:19:08', null);
INSERT INTO `sys_biz_type` VALUES ('LOAN SERCURITIZATION', '贷款证券化', 'LOAN', 'ENABLE', '贷款证券化是指商业银行通过一定程序将贷款转化为证券发行的总理资过程。', null, null, null, null, null, null, null, null, '2017-07-19 14:16:06', null);
INSERT INTO `sys_biz_type` VALUES ('MID-TERM LONGER-TERM DEBT', '中长期债券', 'INVESTMENT', 'ENABLE', '中长期债券是国家为了基建投资的资金需要而发行的一种债券，其利率一般较高，期限也较长，是商业银行较好的投资对象。', null, null, null, null, null, null, null, null, '2017-07-19 14:19:57', null);
INSERT INTO `sys_biz_type` VALUES ('MORTGAGE', '抵押贷款', 'LOAN', 'ENABLE', '存贷或商品作为抵押品的短期贷款。', null, null, null, null, null, null, null, null, '2017-07-19 14:07:05', null);
INSERT INTO `sys_biz_type` VALUES ('ORDINARY LOAN', '普通借款限额', 'CREDIT', 'DRAFT', '企业与银行订立一种非正式协议，以确定一个贷款，在限额内，企业可随时得到银行的贷款支持，限额的有效期一般不超过90天。普通贷款限额内的贷款，利率是浮动的，与银行的优惠利率挂钩。', null, null, null, null, null, null, null, null, '2017-07-19 14:01:51', null);
INSERT INTO `sys_biz_type` VALUES ('OVERDRAFT', '透支贷款', 'CREDIT', 'DRAFT', '银行通过允许客户在其帐户上透支的方式向客户提供贷款。提供这种便利被视为银行对客户所承担的合同之外的“附加义务”。', null, null, null, null, null, null, null, null, '2017-07-19 14:02:45', null);
INSERT INTO `sys_biz_type` VALUES ('REAL ESTATE', '不动产抵押贷款', 'MORTGAGE', 'ENABLE', '通常是指以房地产或企业设备抵押品的贷款。', null, null, null, null, null, null, null, null, '2017-07-19 14:13:02', null);
INSERT INTO `sys_biz_type` VALUES ('SAVINGS DEPOSIT', '储蓄存款', 'DEBT', 'ENABLE', '储蓄存款是个人为积蓄货币和取得利息收入而开立的存款帐户，储蓄存款又可分为活期和定期。', null, null, null, null, null, null, null, null, '2017-07-19 14:22:31', null);
INSERT INTO `sys_biz_type` VALUES ('SECURITY LOAN', '证券贷款', 'MORTGAGE', 'ENABLE', '银行发放的企业借款，除以应收款和存货作为抵押外，也有不少是用各种证券特别是公司企业发行的股票和债券作押的。这类贷款称为“证券贷款”。', null, null, null, null, null, null, null, null, '2017-07-19 14:12:17', null);
INSERT INTO `sys_biz_type` VALUES ('STANDBY LOAN', '备用贷款承诺', 'CREDIT', 'ENABLE', '银行与企业签订正式合同，在合同中银行承诺在指定期限和限额内向企业提供相应贷款，企业要为银行的承诺提供费用。', null, null, null, null, null, null, null, null, '2017-07-19 14:03:42', null);
INSERT INTO `sys_biz_type` VALUES ('TREASURY BILL', '国库券', 'INVESTMENT', 'ENABLE', '国库券是政府短期债券，期限在一年以下。', null, null, null, null, null, null, null, null, '2017-07-19 14:18:32', null);

-- ----------------------------
-- Table structure for `sys_biz_dept_type`
-- ----------------------------
DROP TABLE IF EXISTS `sys_biz_dept_type`;
CREATE TABLE `sys_biz_dept_type` (
  `DEPT_TYPE_ID_` varchar(64) NOT NULL COMMENT '主键',
  `DEPT_CODE_` varchar(64) DEFAULT NULL COMMENT '机构编码',
  `DEPT_NAME_` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `DEPT_PATH_CPDE_` varchar(256) DEFAULT NULL COMMENT '部门路径',
  `BIZ_TYPE_CODE_` varchar(64) DEFAULT NULL COMMENT '业务种类编码',
  `BIZ_TYPE_NAME_` varchar(128) DEFAULT NULL COMMENT '业务种类名称',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`DEPT_TYPE_ID_`),
  KEY `FK_Reference_34` (`DEPT_CODE_`),
  KEY `FK_Reference_35` (`BIZ_TYPE_CODE_`),
  CONSTRAINT `sys_biz_dept_type_ibfk_1` FOREIGN KEY (`BIZ_TYPE_CODE_`) REFERENCES `sys_biz_type` (`BIZ_TYPE_CODE_`),
  CONSTRAINT `sys_biz_dept_type_ibfk_2` FOREIGN KEY (`DEPT_CODE_`) REFERENCES `sys_unit` (`UNIT_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门与业务种类关系表';

-- ----------------------------
-- Records of sys_biz_dept_type
-- ----------------------------
INSERT INTO `sys_biz_dept_type` VALUES ('20170720121348700BB4CA357E48B7A38C238C44621CAB', '02', 'XXX银行', null, 'ASSET', '资产业务', null, null, null, null, null, null, null, null, '2017-07-20 12:13:43', null);
INSERT INTO `sys_biz_dept_type` VALUES ('20170720170848A9A690C7C3724B93B4271A8D21C55443', '0202', '四川分行', null, 'DEBT', '负债业务', null, null, null, null, null, null, null, null, '2017-07-20 17:08:42', null);
INSERT INTO `sys_biz_dept_type` VALUES ('2017082914542104EA15BBBFAC48E282595AE73C1B18C5', '099', '风控部门', null, 'ASSET', '资产业务', null, null, null, null, null, null, null, null, '2017-08-29 14:57:17', null);

-- ----------------------------
-- Table structure for `sys_role_std`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_std`;
CREATE TABLE `sys_role_std` (
  `ROLE_STD_CODE_` varchar(64) NOT NULL COMMENT '标准角色编码',
  `ROLE_STD_NAME_` varchar(128) NOT NULL COMMENT '标准角色名称',
  `ROLE_STD_DESC_` varchar(256) DEFAULT NULL COMMENT '标准角色描述',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ROLE_STD_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统定义的标准角色';

-- ----------------------------
-- Records of sys_role_std
-- ----------------------------
INSERT INTO `sys_role_std` VALUES ('110', '管理岗', '管理岗位', null, null, null, null, null, null, null, null, '2017-08-16 16:00:58', null);
INSERT INTO `sys_role_std` VALUES ('120', '业务岗', '业务岗位', null, null, null, null, null, null, null, null, '2017-07-20 14:40:40', null);
INSERT INTO `sys_role_std` VALUES ('130', '审核岗', '审核岗位', null, null, null, null, null, null, null, null, '2017-07-20 14:40:40', null);
INSERT INTO `sys_role_std` VALUES ('140', '复核岗', '复核岗位', null, null, null, null, null, null, null, null, '2017-07-20 14:40:40', null);
INSERT INTO `sys_role_std` VALUES ('150', '审批岗', '审批岗位', null, null, null, null, null, null, null, null, '2017-07-20 14:40:40', null);

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_CODE_` varchar(64) NOT NULL COMMENT '角色编码',
  `ROLE_NAME_` varchar(128) DEFAULT NULL COMMENT '角色名称',
  `ROLE_DESC_` varchar(256) DEFAULT NULL COMMENT '角色描述',
  `ROLE_STD_CODE_` varchar(64) DEFAULT NULL COMMENT '标准角色编码',
  `HIERARCHY_` varchar(64) DEFAULT NULL COMMENT '层级，总行30， 分行50，支行70',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态，ENABLE - 启用，DISABLE - 停用',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ROLE_CODE_`),
  KEY `FK_Reference_1` (`ROLE_STD_CODE_`),
  CONSTRAINT `sys_role_ibfk_1` FOREIGN KEY (`ROLE_STD_CODE_`) REFERENCES `sys_role_std` (`ROLE_STD_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('30110', '总行管理岗', '总行管理岗', '110', '30', 'ENABLE', null, null, null, null, null, null, null, null, '2017-09-14 15:11:07', null);
INSERT INTO `sys_role` VALUES ('30120', '总行业务岗', '总行业务岗位', '120', '30', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:42:07', null);
INSERT INTO `sys_role` VALUES ('30130', '总行审核岗', '总行审核岗位', '130', '30', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:42:40', null);
INSERT INTO `sys_role` VALUES ('30140', '总行复核岗', '总行复核岗位', '140', '30', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:43:12', null);
INSERT INTO `sys_role` VALUES ('30150', '总行审批岗', '总行审批岗位', '150', '30', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:43:39', null);
INSERT INTO `sys_role` VALUES ('50110', '一级分行管理岗', '一级分行管理岗', '110', '50', 'ENABLE', null, null, null, null, null, null, null, null, '2017-09-14 15:18:15', null);
INSERT INTO `sys_role` VALUES ('50120', '一级分行业务', '一级分行业务岗位', '120', '50', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:44:43', null);
INSERT INTO `sys_role` VALUES ('50130', '一级分行审核', '一级分行审核岗位', '130', '50', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:45:03', null);
INSERT INTO `sys_role` VALUES ('50140', '一级分行复核', '一级分行复核岗位', '140', '50', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:44:43', null);
INSERT INTO `sys_role` VALUES ('50150', '一级分行审批', '一级分行审批岗位', '150', '50', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:45:03', null);
INSERT INTO `sys_role` VALUES ('70110', '二级分行管理', '二级分行管理岗位', '110', '70', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:45:31', null);
INSERT INTO `sys_role` VALUES ('70120', '二级分行业务', '二级分行业务岗位', '120', '70', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:45:53', null);
INSERT INTO `sys_role` VALUES ('70130', '二级分行审核', '二级分行审核岗位', '130', '70', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:46:14', null);
INSERT INTO `sys_role` VALUES ('70140', '二级分行复核', '二级分行复核岗位', '140', '70', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:45:53', null);
INSERT INTO `sys_role` VALUES ('70150', '二级分行审批', '二级分行审批岗位', '150', '70', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:46:14', null);
INSERT INTO `sys_role` VALUES ('90110', '支行管理岗', '支行管理岗位', '110', '90', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:46:37', null);
INSERT INTO `sys_role` VALUES ('90120', '支行业务岗', '支行业务岗位', '120', '90', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:46:59', null);
INSERT INTO `sys_role` VALUES ('90130', '支行审核岗', '支行审核岗位', '130', '90', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:47:24', null);
INSERT INTO `sys_role` VALUES ('90140', '支行复核岗', '支行复核岗位', '140', '90', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:46:37', null);
INSERT INTO `sys_role` VALUES ('90150', '支行审批岗', '支行审批岗位', '150', '90', 'ENABLE', null, null, null, null, null, null, null, null, '2017-07-20 14:46:59', null);

-- ----------------------------
-- Table structure for `sys_role_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_group`;
CREATE TABLE `sys_role_group` (
  `ROLE_GROUP_CODE_` varchar(64) NOT NULL COMMENT '角色组编码',
  `ROLE_GROUP_NAME_` varchar(128) NOT NULL COMMENT '角色组名称',
  `ROLE_GROUP_DESC_` varchar(256) DEFAULT NULL COMMENT '角色组描述',
  `HIERARCHY_` varchar(64) NOT NULL COMMENT '层级，总行30，一级分行50，二级分行70，支行90',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ROLE_GROUP_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色组，用来对角色进行打包，当系统使用细化的角色设置时方便用户分配角色操作使用。';

-- ----------------------------
-- Records of sys_role_group
-- ----------------------------
INSERT INTO `sys_role_group` VALUES ('BIZ_ROLE', '业务组', null, '30', null, null, null, null, null, null, null, null, '2017-08-14 09:43:15', null);
INSERT INTO `sys_role_group` VALUES ('TEXT_009', '测试', '测试', '30', null, null, null, null, null, null, null, null, '2017-08-29 16:25:44', null);

-- ----------------------------
-- Table structure for `sys_role_group_map`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_group_map`;
CREATE TABLE `sys_role_group_map` (
  `MAP_ID_` varchar(64) NOT NULL COMMENT '主键，系统自动生成',
  `ROLE_GROUP_CODE_` varchar(64) NOT NULL COMMENT '角色组编码',
  `ROLE_CODE_` varchar(64) NOT NULL COMMENT '角色编码',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`MAP_ID_`),
  KEY `FK_Reference_2` (`ROLE_CODE_`),
  KEY `FK_Reference_3` (`ROLE_GROUP_CODE_`),
  CONSTRAINT `sys_role_group_map_ibfk_1` FOREIGN KEY (`ROLE_GROUP_CODE_`) REFERENCES `sys_role_group` (`ROLE_GROUP_CODE_`),
  CONSTRAINT `sys_role_group_map_ibfk_2` FOREIGN KEY (`ROLE_CODE_`) REFERENCES `sys_role` (`ROLE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色关联角色组';

-- ----------------------------
-- Records of sys_role_group_map
-- ----------------------------
INSERT INTO `sys_role_group_map` VALUES ('201708140949468C1E079DE65B4712BAF0836692708665', 'BIZ_ROLE', '30150', null, null, null, null, null, null, null, null, '2017-08-14 09:49:40', null);
INSERT INTO `sys_role_group_map` VALUES ('20170814094946AA681ECCF8C747AFB3601BDCCE647336', 'BIZ_ROLE', '30120', null, null, null, null, null, null, null, null, '2017-08-14 09:49:40', null);
INSERT INTO `sys_role_group_map` VALUES ('2017091415111865F82540A76E4725B13DA191F3B4FFEF', 'BIZ_ROLE', '30110', null, null, null, null, null, null, null, null, '2017-09-14 15:11:07', null);

-- ----------------------------
-- Table structure for `sys_staff_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_staff_group`;
CREATE TABLE `sys_staff_group` (
  `STAFF_GROUP_CODE_` varchar(64) NOT NULL COMMENT '用户组编码',
  `STAFF_GROUP_NAME_` varchar(128) DEFAULT NULL COMMENT '用户组名称',
  `STAFF_GROUP_DESC_` varchar(256) DEFAULT NULL COMMENT '用户组描述',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`STAFF_GROUP_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组包含的用户';

-- ----------------------------
-- Records of sys_staff_group
-- ----------------------------
INSERT INTO `sys_staff_group` VALUES ('BIZ001', '业务组2', '业务组2', null, null, null, null, null, null, null, null, '2017-08-28 16:08:36', null);
INSERT INTO `sys_staff_group` VALUES ('op909', '用户名2', '2', null, null, null, null, null, null, null, null, '2017-08-28 16:14:14', null);

-- ----------------------------
-- Table structure for `sys_staff_group_map`
-- ----------------------------
DROP TABLE IF EXISTS `sys_staff_group_map`;
CREATE TABLE `sys_staff_group_map` (
  `MAP_ID_` varchar(64) NOT NULL COMMENT '主键，系统自动生成',
  `STAFF_GROUP_CODE_` varchar(64) NOT NULL COMMENT '用户组编码',
  `STAFF_CODE_` varchar(64) NOT NULL COMMENT '用户编码',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`MAP_ID_`),
  KEY `FK_Reference_8` (`STAFF_GROUP_CODE_`),
  KEY `FK_Reference_9` (`STAFF_CODE_`),
  CONSTRAINT `sys_staff_group_map_ibfk_1` FOREIGN KEY (`STAFF_CODE_`) REFERENCES `sys_staff` (`STAFF_CODE_`),
  CONSTRAINT `sys_staff_group_map_ibfk_2` FOREIGN KEY (`STAFF_GROUP_CODE_`) REFERENCES `sys_staff_group` (`STAFF_GROUP_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关联用户组';

-- ----------------------------
-- Records of sys_staff_group_map
-- ----------------------------
INSERT INTO `sys_staff_group_map` VALUES ('2017082816090556BF3D15F1934697AE613F21D21E3612', 'BIZ001', 'lrg', null, null, null, null, null, null, null, null, '2017-08-28 16:12:03', null);
INSERT INTO `sys_staff_group_map` VALUES ('20170828160905B1FC8CADC03643AB904312621F38F8D8', 'BIZ001', 'xyl', null, null, null, null, null, null, null, null, '2017-08-28 16:12:03', null);
INSERT INTO `sys_staff_group_map` VALUES ('20170828160905BC9294703458484F9ED395BAF6E3E153', 'BIZ001', 'wxw', null, null, null, null, null, null, null, null, '2017-08-28 16:12:03', null);
INSERT INTO `sys_staff_group_map` VALUES ('20170828160905C97168480486442EAD0A7BB01C2EA415', 'BIZ001', 'brm_proxy_02020401', null, null, null, null, null, null, null, null, '2017-08-28 16:12:03', null);
INSERT INTO `sys_staff_group_map` VALUES ('20170828160905DAA069572BD04969BDC92ACF5A770664', 'BIZ001', 'brm', null, null, null, null, null, null, null, null, '2017-08-28 16:12:03', null);
INSERT INTO `sys_staff_group_map` VALUES ('20170906145438E147E654C75344C0A785D34A5BEE54CE', 'BIZ001', 'brm1212', null, null, null, null, null, null, null, null, '2017-09-06 14:57:43', null);

-- ----------------------------
-- Table structure for `sys_staff_group_role_group_map`
-- ----------------------------
DROP TABLE IF EXISTS `sys_staff_group_role_group_map`;
CREATE TABLE `sys_staff_group_role_group_map` (
  `MAP_ID_` varchar(64) NOT NULL COMMENT '主键，系统自动生成',
  `STAFF_GROUP_CODE_` varchar(64) NOT NULL COMMENT '用户组编码',
  `STAFF_GROUP_NAME_` varchar(128) NOT NULL COMMENT '用户组名称',
  `ROLE_GROUP_CODE_` varchar(64) NOT NULL COMMENT '角色组编码',
  `ROLE_GROUP_NAME_` varchar(128) NOT NULL COMMENT '角色组名称',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态，ENABLE - 启用，DISABLE - 停用',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`MAP_ID_`),
  KEY `FK_Reference_26` (`STAFF_GROUP_CODE_`),
  KEY `FK_Reference_31` (`ROLE_GROUP_CODE_`),
  CONSTRAINT `sys_staff_group_role_group_map_ibfk_1` FOREIGN KEY (`ROLE_GROUP_CODE_`) REFERENCES `sys_role_group` (`ROLE_GROUP_CODE_`),
  CONSTRAINT `sys_staff_group_role_group_map_ibfk_2` FOREIGN KEY (`STAFF_GROUP_CODE_`) REFERENCES `sys_staff_group` (`STAFF_GROUP_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组关联角色组';

-- ----------------------------
-- Records of sys_staff_group_role_group_map
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_staff_group_role_map`
-- ----------------------------
DROP TABLE IF EXISTS `sys_staff_group_role_map`;
CREATE TABLE `sys_staff_group_role_map` (
  `MAP_ID_` varchar(64) NOT NULL COMMENT '主键，系统自动生成',
  `STAFF_GROUP_CODE_` varchar(64) NOT NULL COMMENT '用户组编码',
  `STAFF_GROUP_NAME_` varchar(128) NOT NULL COMMENT '用户组名称',
  `ROLE_CODE_` varchar(64) NOT NULL COMMENT '角色编码',
  `ROLE_NAME_` varchar(128) NOT NULL COMMENT '角色名称',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态，ENABLE - 启用，DISABLE - 停用',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`MAP_ID_`),
  KEY `FK_Reference_32` (`ROLE_CODE_`),
  KEY `FK_Reference_33` (`STAFF_GROUP_CODE_`),
  CONSTRAINT `sys_staff_group_role_map_ibfk_1` FOREIGN KEY (`STAFF_GROUP_CODE_`) REFERENCES `sys_staff_group` (`STAFF_GROUP_CODE_`),
  CONSTRAINT `sys_staff_group_role_map_ibfk_2` FOREIGN KEY (`ROLE_CODE_`) REFERENCES `sys_role` (`ROLE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组关联角色';

-- ----------------------------
-- Records of sys_staff_group_role_map
-- ----------------------------
INSERT INTO `sys_staff_group_role_map` VALUES ('4c80ce08-8c93-11e7-9e24-00ffb5d5587a', 'BIZ001', '业务组2', '90130', '支行审核岗', null, null, null, null, null, null, null, null, null, '2017-08-29 16:23:34', null);
INSERT INTO `sys_staff_group_role_map` VALUES ('4caa323d-8c93-11e7-9e24-00ffb5d5587a', 'BIZ001', '业务组2', '90140', '支行复核岗', null, null, null, null, null, null, null, null, null, '2017-08-29 16:23:34', null);
INSERT INTO `sys_staff_group_role_map` VALUES ('4cba2fa5-8c93-11e7-9e24-00ffb5d5587a', 'BIZ001', '业务组2', '90150', '支行审批岗', null, null, null, null, null, null, null, null, null, '2017-08-29 16:23:34', null);
INSERT INTO `sys_staff_group_role_map` VALUES ('4cca7dd2-8c93-11e7-9e24-00ffb5d5587a', 'BIZ001', '业务组2', '70150', '二级分行审批', null, null, null, null, null, null, null, null, null, '2017-08-29 16:23:34', null);
INSERT INTO `sys_staff_group_role_map` VALUES ('5e871d9a-8c93-11e7-9e24-00ffb5d5587a', 'op909', '用户名2', '90130', '支行审核岗', null, null, null, null, null, null, null, null, null, '2017-08-29 16:24:04', null);

-- ----------------------------
-- Table structure for `sys_staff_proxy_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_staff_proxy_log`;
CREATE TABLE `sys_staff_proxy_log` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '用户编码',
  `STAFF_NAME_` varchar(128) DEFAULT NULL COMMENT '用户名称',
  `PROXY_STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '借调人编码',
  `PROXY_STAFF_NAME_` varchar(128) DEFAULT NULL COMMENT '借调人名称',
  `DEPT_CODE_` varchar(64) NOT NULL COMMENT '借调部门编码',
  `DEPT_NAME_` varchar(128) DEFAULT NULL COMMENT '借调部门名称',
  `START_DATE_` date DEFAULT NULL COMMENT '借调起始时间',
  `PRE_END_DATE_` date DEFAULT NULL COMMENT '预计借调结束时间',
  `END_DATE_` date DEFAULT NULL COMMENT '实际结束时间',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员借调记录表';

-- ----------------------------
-- Records of sys_staff_proxy_log
-- ----------------------------
INSERT INTO `sys_staff_proxy_log` VALUES ('085cd598-8c95-11e7-9e24-00ffb5d5587a', 'fxe', '冯絮儿', 'fxe_proxy_020103', '冯絮儿（借调）', '020103', 'IT部', '2017-08-29', '2017-08-29', '2017-08-30', 'END', null, null, null, null, null, null, null, null, '2017-08-29 16:35:58', null);
INSERT INTO `sys_staff_proxy_log` VALUES ('0b70c689-92d5-11e7-9e24-00ffb5d5587a', 'brm1212', '121212', 'brm1212_proxy_020103', '121212（借调）', '020103', 'IT部', '2017-09-06', null, '2017-09-06', 'END', null, null, null, null, null, null, null, null, '2017-09-06 15:29:33', null);
INSERT INTO `sys_staff_proxy_log` VALUES ('2e0dd782-92d3-11e7-9e24-00ffb5d5587a', 'brm_proxy_0202010101', '柏荣幕（借调）', 'brm_proxy_0202010101_proxy_020101', '柏荣幕（借调）（借调）', '020101', '合规部', '2017-09-06', null, null, 'ON', null, null, null, null, null, null, null, null, '2017-09-06 15:16:12', null);
INSERT INTO `sys_staff_proxy_log` VALUES ('3b7c5d8c-8d49-11e7-9e24-00ffb5d5587a', 'fxe', '冯絮儿', 'fxe_proxy_0202010101', '冯絮儿（借调）', '0202010101', '风险部', '2017-08-30', '2017-08-30', '2017-08-30', 'END', null, null, null, null, null, null, null, null, '2017-08-30 14:05:53', null);
INSERT INTO `sys_staff_proxy_log` VALUES ('757134e0-92d5-11e7-9e24-00ffb5d5587a', 'brm1212', '121212', 'brm1212_proxy_020102', '121212（借调）', '020102', 'XXX银行总行风险部', '2017-09-06', null, '2017-09-06', 'END', null, null, null, null, null, null, null, null, '2017-09-06 15:32:31', null);
INSERT INTO `sys_staff_proxy_log` VALUES ('7d262d9e-92d4-11e7-9e24-00ffb5d5587a', 'brm1212', '121212', 'brm1212_proxy_0202010103', '121212（借调）', '0202010103', 'IT部门', '2017-09-06', null, '2017-09-06', 'END', null, null, null, null, null, null, null, null, '2017-09-06 15:25:34', null);
INSERT INTO `sys_staff_proxy_log` VALUES ('e268ba4f-8bd7-11e7-9e24-00ffb5d5587a', 'wrj', '魏荣建', 'wrj_proxy_0202010202', '魏荣建（借调）', '0202010202', '合规部', '2017-08-28', '2017-09-03', '2017-08-28', 'END', null, null, null, null, null, null, null, null, '2017-08-28 18:02:00', null);
INSERT INTO `sys_staff_proxy_log` VALUES ('fb1e43a4-92d2-11e7-9e24-00ffb5d5587a', 'brm1212', '121212', 'brm1212_proxy_0202010101', '121212（借调）', '0202010101', '风险部', '2017-09-06', null, '2017-09-06', 'END', null, null, null, null, null, null, null, null, '2017-09-06 15:14:46', null);

-- ----------------------------
-- Table structure for `sys_staff_role_group_map`
-- ----------------------------
DROP TABLE IF EXISTS `sys_staff_role_group_map`;
CREATE TABLE `sys_staff_role_group_map` (
  `MAP_ID_` varchar(64) NOT NULL COMMENT '主键，系统自动生成',
  `STAFF_CODE_` varchar(64) NOT NULL COMMENT '用户编码',
  `STAFF_NAME_` varchar(128) NOT NULL COMMENT '用户名称',
  `ROLE_GROUP_CODE_` varchar(64) NOT NULL COMMENT '角色组编码',
  `ROLE_GROUP_NAME_` varchar(128) NOT NULL COMMENT '角色组名称',
  `IS_PRIMARY_` varchar(64) DEFAULT NULL COMMENT '是否主要角色组：Y - 是，N - 否',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态，ENABLE - 启用，DISABLE - 停用',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`MAP_ID_`),
  KEY `FK_Reference_27` (`STAFF_CODE_`),
  KEY `FK_Reference_28` (`ROLE_GROUP_CODE_`),
  CONSTRAINT `sys_staff_role_group_map_ibfk_1` FOREIGN KEY (`ROLE_GROUP_CODE_`) REFERENCES `sys_role_group` (`ROLE_GROUP_CODE_`),
  CONSTRAINT `sys_staff_role_group_map_ibfk_2` FOREIGN KEY (`STAFF_CODE_`) REFERENCES `sys_staff` (`STAFF_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户关联角色组';

-- ----------------------------
-- Records of sys_staff_role_group_map
-- ----------------------------
INSERT INTO `sys_staff_role_group_map` VALUES ('2017081409440462314E41A9E0471BB9DB18F721053D9A', 'wxw', '吴宪魏', 'BIZ_ROLE', '业务组', null, null, null, null, null, null, null, null, null, null, '2017-08-14 09:43:58', null);
INSERT INTO `sys_staff_role_group_map` VALUES ('20170914175414D91D820345EE42F79EC32964179C7822', 'brm', '柏荣幕', 'BIZ_ROLE', '业务组', null, null, null, null, null, null, null, null, null, null, '2017-09-14 17:54:04', null);

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID_` varchar(64) NOT NULL COMMENT '用户编码',
  `USER_NAME_` varchar(128) NOT NULL COMMENT '用户名称',
  `PASSWORD_` varchar(128) NOT NULL COMMENT '密码',
  `PASSWORD_EXPIRE_DAYS_` int(11) DEFAULT NULL COMMENT '密码过期天数',
  `PASSWORD_EXPIRE_TIME_` datetime DEFAULT NULL COMMENT '密码过期时间',
  `ACCOUNT_EXPIRE_DAYS_` int(11) DEFAULT NULL COMMENT '过期天数',
  `ACCOUNT_EXPIRE_TIME_` datetime DEFAULT NULL COMMENT '过期日期',
  `ACCOUNT_NON_EXPIRED_` smallint(6) NOT NULL COMMENT '账户过期标识',
  `ACCOUNT_NON_LOCKED_` smallint(6) NOT NULL COMMENT '账户锁定标识',
  `CREDENTIAL_NON_EXPIRED_` smallint(6) NOT NULL COMMENT '证书过期标识',
  `ENABLED_` smallint(6) NOT NULL COMMENT '是否启用',
  `FAILED_COUNT_` int(11) DEFAULT NULL COMMENT '登录失败次数',
  `LOGIN_IP_` varchar(256) DEFAULT NULL COMMENT '登录IP',
  `LOGIN_TIME_` datetime DEFAULT NULL COMMENT '登录时间',
  `MAX_FAILED_COUNT_` int(11) DEFAULT NULL COMMENT '最大失败次数',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`USER_ID_`),
  CONSTRAINT `sys_user_ibfk_1` FOREIGN KEY (`USER_ID_`) REFERENCES `sys_staff` (`STAFF_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户的登录信息';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('090', '小红', 'c682ee57916e38069afbe7eeb2579b1e', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-08-28 16:04:01', null);
INSERT INTO `sys_user` VALUES ('brm', '柏荣幕', 'a58f7bbcf9ec061ed13f44e7bb314e3f', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-20 17:14:33', null);
INSERT INTO `sys_user` VALUES ('brm1212', '121212', 'a539711e044462db9e4b3cfc63768d7e', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-08-17 10:54:23', null);
INSERT INTO `sys_user` VALUES ('fxe', '冯絮儿', '98b73cf5ccb938463ad81f43cad66aa7', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-24 10:20:38', null);
INSERT INTO `sys_user` VALUES ('jepAdmin', '超级管理员', 'ab0e9205094014c5281a2c254453688f', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-27 16:41:07', null);
INSERT INTO `sys_user` VALUES ('lrg', '龙瑞刚', 'fbdc4f8a316b8f000cdef0bdfce97532', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-24 10:21:15', null);
INSERT INTO `sys_user` VALUES ('odn', '欧德恩', '0754cbfcad139eb0bec67a5cb53530cb', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-24 10:21:48', null);
INSERT INTO `sys_user` VALUES ('wrj', '魏荣建', 'cfab691ecb682838d1d752a64bb3ea5c', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-24 10:22:33', null);
INSERT INTO `sys_user` VALUES ('wxw', '吴宪魏', '70bddfda7df6dbc640ca5a81e4579906', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-24 10:23:12', null);
INSERT INTO `sys_user` VALUES ('wmd', '吴梦蝶', '3c82119bd23faa190504c36367a44671', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2018-02-06 10:42:08', null);
INSERT INTO `sys_user` VALUES ('xhf', '肖鸿飞', 'b9d14f1e17cf1c5aef3b2cfee382b463', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-24 10:23:49', null);
INSERT INTO `sys_user` VALUES ('xmr', '席明瑞', 'e349753138ede36abb0a9c728b87a125', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-24 10:24:29', null);
INSERT INTO `sys_user` VALUES ('xyl', '席玉龙', '3eba374a96115d18d6447ac619e455e8', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-24 10:25:11', null);
INSERT INTO `sys_user` VALUES ('xyr', '薛嫣然', 'dea8267a6783f5d846667bd86a92591a', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-24 10:25:47', null);
INSERT INTO `sys_user` VALUES ('yxf', '叶雪飞', 'c1e3a23ab0a81b4ddf127dbd9a466ade', null, null, null, null, '1', '0', '1', '1', null, null, null, null, null, null, null, null, null, null, null, null, '2017-07-24 10:26:35', null);

-- ----------------------------
-- Table structure for `sys_dash_layout`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dash_layout`;
CREATE TABLE `sys_dash_layout` (
  `CODE_` varchar(64) NOT NULL COMMENT '布局编码',
  `NAME_` varchar(128) DEFAULT NULL COMMENT '布局名称',
  `DESC_` varchar(256) DEFAULT NULL COMMENT '布局描述',
  `PRIORITY_` int(11) NOT NULL COMMENT '布局优先级，数字越大越优先',
  `DESIGN_TYPE_` varchar(64) NOT NULL COMMENT '设计类型：普通模式、专家模式',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='布局的基本信息';

-- ----------------------------
-- Records of sys_dash_layout
-- ----------------------------
INSERT INTO `sys_dash_layout` VALUES ('default', '默认布局', '系统默认通用仪表盘布局', 5, 'NORMAL', null, null, null, null, null, null, null, null, '2018-02-06 11:14:04', null);

-- ----------------------------
-- Table structure for `sys_dash_layout_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dash_layout_config`;
CREATE TABLE `sys_dash_layout_config` (
  `LAYOUT_CODE_` varchar(64) NOT NULL COMMENT '布局编码',
  `CONFIG_` longtext COMMENT '组件详细配置，JSON 格式',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`LAYOUT_CODE_`),
  CONSTRAINT `FK_DASH_6_JEP` FOREIGN KEY (`LAYOUT_CODE_`) REFERENCES `sys_dash_layout` (`CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='布局的配置';

-- ----------------------------
-- Records of sys_dash_layout_config
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dash_layout_map`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dash_layout_map`;
CREATE TABLE `sys_dash_layout_map` (
  `MAP_ID_` varchar(64) NOT NULL COMMENT '映射id',
  `LAYOUT_CODE_` varchar(64) NOT NULL COMMENT '布局编码',
  `MAP_TYPE_` varchar(64) NOT NULL COMMENT '映射类型：ROLE/ROLE_GROUP',
  `MAP_TYPE_CODE_` varchar(64) NOT NULL COMMENT '映射类型编码，如角色编码',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`MAP_ID_`),
  UNIQUE KEY `IDX_DSB_LAYOUT_MP2` (`LAYOUT_CODE_`,`MAP_TYPE_`,`MAP_TYPE_CODE_`),
  CONSTRAINT `FK_DASH_4_JEP` FOREIGN KEY (`LAYOUT_CODE_`) REFERENCES `sys_dash_layout` (`CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页仪表盘布局映射表，可映射到角色、角色组等，现在只使用映射到角色。';

-- ----------------------------
-- Records of sys_dash_layout_map
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dash_widget`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dash_widget`;
CREATE TABLE `sys_dash_widget` (
  `CODE_` varchar(64) NOT NULL COMMENT '组件编码',
  `NAME_` varchar(128) DEFAULT NULL COMMENT '组件名称',
  `DESC_` varchar(256) DEFAULT NULL COMMENT '组件描述',
  `TYPE_` varchar(64) DEFAULT NULL COMMENT 'DATA - 数据组件	CHART - 图表组件	 CUSTOM - 自定义组件',
  `IMPL_CLASS_` varchar(64) DEFAULT NULL COMMENT '组件实现类，只对自定义类型的组件有效。',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT 'ENABLE - 启用 DISABLE - 停用',
  `REMARK_` varchar(1024) DEFAULT NULL COMMENT '备注',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组件表基本信息表';

-- ----------------------------
-- Records of sys_dash_widget
-- ----------------------------
INSERT INTO `sys_dash_widget` VALUES ('TODO', '待办任务', '显示待办任务的组件', 'CUSTOM', 'com.yzj.jep.sys.dash.widget.custom.TodoWidget', 'ENABLED', '这是备注\r\n你设置了吗', null, null, null, null, null, null, null, null, '2017-12-01 10:53:27', null);

-- ----------------------------
-- Table structure for `sys_dash_widget_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dash_widget_config`;
CREATE TABLE `sys_dash_widget_config` (
  `WIDGET_CODE_` varchar(64) NOT NULL COMMENT '组件编码',
  `CONFIG_` longtext COMMENT '组件详细配置，JSON 格式',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`WIDGET_CODE_`),
  CONSTRAINT `FK_DASH_1_JEP` FOREIGN KEY (`WIDGET_CODE_`) REFERENCES `sys_dash_widget` (`CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组件详细配置信息。';

-- ----------------------------
-- Records of sys_dash_widget_config
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dash_widget_dak`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dash_widget_dak`;
CREATE TABLE `sys_dash_widget_dak` (
  `DAK_ID_` varchar(64) NOT NULL COMMENT '权限ID',
  `DAK_CODE_` varchar(64) DEFAULT NULL COMMENT '权限编码',
  `WIDGET_CODE_` varchar(64) DEFAULT NULL COMMENT '组件编码',
  `DAK_MAP_TYPE_` varchar(64) DEFAULT NULL COMMENT '目前只支持角色（ROLEGROUP/ROLE）',
  `DAK_USING_CODE_` varchar(64) DEFAULT NULL COMMENT '关联角色或角色组编码',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`DAK_ID_`),
  KEY `FK_DASH_3_JEP` (`WIDGET_CODE_`),
  CONSTRAINT `FK_DASH_3_JEP` FOREIGN KEY (`WIDGET_CODE_`) REFERENCES `sys_dash_widget` (`CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色的组件数据权限对应表。';

-- ----------------------------
-- Records of sys_dash_widget_dak
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_data_provider`
-- ----------------------------
DROP TABLE IF EXISTS sys_data_provider;
CREATE TABLE sys_data_provider 
(
   QUERY_ID_ varchar(64) NOT NULL COMMENT '查询ID', 
   TABLE_ID_ varchar(64) COMMENT '元数据ID', 
   QUERY_NAME_ varchar(256) COMMENT '查询名称', 
   SCRIPT_ longtext COMMENT '查询脚本', 
   QUERY_TYPE_ varchar(64) COMMENT '公共查询：PUBLIC 私有查询：PRIVATE', 
   STATUS_ varchar(64) COMMENT '状态：10 - 草稿 30 - 已生效 50 - 已失效', 
   UNIT_CODE_ varchar(64) COMMENT '机构编码', 
   UNIT_NAME_ varchar(256) COMMENT '机构名称', 
   DEPT_CODE_ varchar(64) COMMENT '部门编码', 
   DEPT_NAME_ varchar(256) COMMENT '部门名称', 
   STAFF_CODE_ varchar(64) COMMENT '用户编码', 
   STAFF_NAME_ varchar(256) COMMENT '用户名称', 
   TENANT_ID_ varchar(64) COMMENT '租户', 
   CREATED_BY_ID_ varchar(64) COMMENT '创建人ID', 
   CREATED_BY_NAME_ varchar(64) COMMENT '创建人名称', 
   CREATED_TIME_ datetime COMMENT '创建时间', 
   DELFLAG_ varchar(1) COMMENT '逻辑删除标记', 
   DISPLAY_ORDER_ int COMMENT '显示序号', 
   MODIFIED_BY_ID_ varchar(64) COMMENT '修改人ID', 
   MODIFIED_BY_NAME_ varchar(64) COMMENT '修改人名称', 
   MODIFIED_TIME_ timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间', 
   VERSION_ int COMMENT '版本', 
   PRIMARY KEY (QUERY_ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仪表盘数据提供程序';

-- ----------------------------
-- Records of sys_data_provider
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dash_widget_provider`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dash_widget_provider`;
CREATE TABLE `sys_dash_widget_provider` (
  `PROVIER_ID_` varchar(64) NOT NULL COMMENT '提供程序ID',
  `PROVIDER_NAME_` varchar(64) DEFAULT NULL COMMENT '数据提供程序名称',
  `PROVIDER_CODE_` varchar(64) DEFAULT NULL COMMENT '数据提供程序编码',
  `PROVIDER_PARAMS_` varchar(4000) DEFAULT NULL COMMENT '数据提供程序参数，JSON 格式，不同的数据提供程序有不同格式。',
  `WIDGET_CODE_` varchar(64) DEFAULT NULL COMMENT '组件编码',
  `BEEN_USED_` varchar(64) DEFAULT NULL COMMENT '是否被引用，Y/N。一旦被引用，该数据提供程序将不能被移除。',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`PROVIER_ID_`),
  KEY `FK_DASH_2_JEP` (`WIDGET_CODE_`),
  KEY `FK_DASH_5_JEP` (`PROVIDER_CODE_`),
  CONSTRAINT `FK_DASH_2_JEP` FOREIGN KEY (`WIDGET_CODE_`) REFERENCES `sys_dash_widget` (`CODE_`),
  CONSTRAINT `FK_DATA_PROVIDER` FOREIGN KEY (`PROVIDER_CODE_`) REFERENCES `sys_data_provider` (`QUERY_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组件所包含的数据提供程序，可能有多个。';

-- ----------------------------
-- Records of sys_dash_widget_provider
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dict_tree`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_tree`;
CREATE TABLE `sys_dict_tree` (
  `NODE_CODE_` varchar(64) NOT NULL COMMENT '节点编码',
  `NODE_NAME_` varchar(128) DEFAULT NULL COMMENT '节点名称',
  `NODE_DESC_` varchar(256) DEFAULT NULL COMMENT '节点描述',
  `PARENT_ID_` varchar(64) DEFAULT NULL COMMENT '父节点',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`NODE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典树';

-- ----------------------------
-- Records of sys_dict_tree
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_dict_cate`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_cate`;
CREATE TABLE `sys_dict_cate` (
  `DICT_CATE_CODE_` varchar(64) NOT NULL COMMENT '类别编码',
  `DICT_CATE_NAME_` varchar(128) NOT NULL COMMENT '类别名称',
  `DICT_CATE_DESC_` varchar(256) DEFAULT NULL COMMENT '类别描述',
  `PARENT_ID_` varchar(64) DEFAULT NULL COMMENT '父类别编码',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`DICT_CATE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类别表';

-- ----------------------------
-- Records of sys_dict_cate
-- ----------------------------
INSERT INTO `sys_dict_cate` VALUES ('SYS', '系统字典', '系统字典', null, null, null, null, null, null, null, null, null, '2017-08-08 14:40:29', null);
INSERT INTO `sys_dict_cate` VALUES ('SYS.ROLE', '角色字典', '22', 'SYS', null, null, null, null, null, null, null, null, '2017-09-19 15:36:55', null);

-- ----------------------------
-- Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `DICT_CODE_` varchar(64) NOT NULL COMMENT '字典编码',
  `DICT_NAME_` varchar(128) NOT NULL COMMENT '字典名称',
  `DICT_DESC_` varchar(256) DEFAULT NULL COMMENT '字典描述',
  `DICT_CATE_CODE_` varchar(64) DEFAULT NULL COMMENT '类别编码',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`DICT_CODE_`),
  KEY `FK_Reference_20` (`DICT_CATE_CODE_`),
  CONSTRAINT `sys_dict_ibfk_1` FOREIGN KEY (`DICT_CATE_CODE_`) REFERENCES `sys_dict_cate` (`DICT_CATE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表，定义一种字典，字典属于某个字典类别，字典下关联多个字典项';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('0009', '审批状态', '用于审批流程', 'SYS.ROLE', null, null, null, null, null, null, null, null, '2017-08-28 15:18:40', null);
INSERT INTO `sys_dict` VALUES ('0090', '审批状态2', '用于审批2', 'SYS.ROLE', null, null, null, null, null, null, null, null, '2017-08-28 15:17:28', null);
INSERT INTO `sys_dict` VALUES ('11', '11', '11', 'SYS', null, null, null, null, null, null, null, null, '2017-08-29 10:52:23', null);
INSERT INTO `sys_dict` VALUES ('21', '212', '212', 'SYS', null, null, null, null, null, null, null, null, '2017-08-29 19:04:34', null);
INSERT INTO `sys_dict` VALUES ('22', '22', '22', 'SYS', null, null, null, null, null, null, null, null, '2017-08-29 10:53:57', null);
INSERT INTO `sys_dict` VALUES ('RILEStTATUS', '角色状态', '角色状态', 'SYS.ROLE', null, null, null, null, null, null, null, null, '2017-08-08 14:47:55', null);

-- ----------------------------
-- Table structure for `sys_dict_item`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `ITEM_ID_` varchar(64) NOT NULL COMMENT '主键，系统自动生成',
  `DICT_CODE_` varchar(64) DEFAULT NULL COMMENT '字典编码',
  `DICT_CATE_CODE_` varchar(64) DEFAULT NULL COMMENT '类别编码',
  `ITEM_LABEL_` varchar(128) NOT NULL COMMENT '标签',
  `ITEM_VALUE_` varchar(64) NOT NULL COMMENT '值',
  `ITEM_DESC_` varchar(256) DEFAULT NULL COMMENT '字典项描述',
  `FILTER_` varchar(64) DEFAULT NULL COMMENT '过滤器，暂未启用',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ITEM_ID_`),
  KEY `FK_Reference_13` (`DICT_CODE_`),
  CONSTRAINT `sys_dict_item_ibfk_1` FOREIGN KEY (`DICT_CODE_`) REFERENCES `sys_dict` (`DICT_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典项，属于某个字典。';

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES ('13012aeb-7c07-11e7-9810-0a00270000031', 'RILEStTATUS', 'SYS.ROLE', '启用', 'ENABLE', '', null, null, null, null, null, null, null, null, null, '2017-08-08 15:15:07', null);
INSERT INTO `sys_dict_item` VALUES ('495c4b22-7c09-11e7-9810-0a0027000003', 'RILEStTATUS', 'SYS.ROLE', '停用', 'DISABLE', '停用', null, null, null, null, null, null, null, null, null, '2017-08-08 15:15:53', null);
INSERT INTO `sys_dict_item` VALUES ('bbe873fd-8c8d-11e7-9e24-00ffb5d5587a', '11', 'SYS', '11', '11', '11', null, null, null, null, null, null, null, null, null, '2017-08-29 15:43:43', null);
INSERT INTO `sys_dict_item` VALUES ('c9784ef0-8c8d-11e7-9e24-00ffb5d5587a', '11', 'SYS', '22', '22', '2245', null, null, null, null, null, null, null, null, null, '2017-08-29 16:50:18', null);
INSERT INTO `sys_dict_item` VALUES ('d13c53ce-8c8d-11e7-9e24-00ffb5d5587a', '11', 'SYS', '33', '332431', '35', null, null, null, null, null, null, null, null, null, '2017-08-29 19:04:45', null);

-- ----------------------------
-- Table structure for `sys_position`
-- ----------------------------
DROP TABLE IF EXISTS `sys_position`;
CREATE TABLE `sys_position` (
  `POSITION_CODE_` varchar(64) NOT NULL COMMENT '职位编码，角色编码@机构编码',
  `POSITION_NAME_` varchar(128) DEFAULT NULL COMMENT '职位名称：角色名称@机构名称',
  `POSITION_DEPT_CODE_` varchar(64) DEFAULT NULL COMMENT '角色编码@当前用户所属部门编码',
  `POSITION_DEPT_NAME_` varchar(128) DEFAULT NULL COMMENT '角色组名称@当前用户所属部门名称',
  `UNIT_CODE_` varchar(64) DEFAULT NULL COMMENT '机构编码',
  `ROLE_CODE_` varchar(64) DEFAULT NULL COMMENT '角色编码',
  `ROLE_GROUP_CODE_` varchar(64) DEFAULT NULL COMMENT '角色组编码',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态',
  `TYPE_` varchar(64) DEFAULT NULL COMMENT '类型',
  `KIND_` varchar(64) DEFAULT NULL COMMENT '类别，暂未启用',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`POSITION_CODE_`),
  KEY `FK_Reference_16` (`ROLE_CODE_`),
  KEY `FK_Reference_17` (`UNIT_CODE_`),
  CONSTRAINT `sys_position_ibfk_1` FOREIGN KEY (`UNIT_CODE_`) REFERENCES `sys_unit` (`UNIT_CODE_`),
  CONSTRAINT `sys_position_ibfk_2` FOREIGN KEY (`ROLE_CODE_`) REFERENCES `sys_role` (`ROLE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定义某个部门所拥有的职位。';

-- ----------------------------
-- Records of sys_position
-- ----------------------------
INSERT INTO `sys_position` VALUES ('30110@0201', '总行管理岗@总行', '30110@null', '总行管理岗@null', '0201', '30110', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-07-20 17:14:33', null);
INSERT INTO `sys_position` VALUES ('30110@020101', '总行管理岗@合规部', '30110@null', '总行管理岗@null', '020101', '30110', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-08-14 09:39:24', null);
INSERT INTO `sys_position` VALUES ('30110@099', '总行管理岗@风控部门', '30110@null', '总行管理岗@null', '099', '30110', 'BIZ_ROLE', null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-08-29 09:59:01', null);
INSERT INTO `sys_position` VALUES ('30120@0201', '总行业务岗@总行', '30120@null', '总行业务岗@null', '0201', '30120', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-07-21 11:20:06', null);
INSERT INTO `sys_position` VALUES ('30120@020101', '总行业务岗@合规部', '30120@null', '总行业务岗@null', '020101', '30120', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-08-14 09:39:33', null);
INSERT INTO `sys_position` VALUES ('30120@020102', '总行业务岗@风险部', '30120@null', '总行业务岗@null', '020102', '30120', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-08-28 16:04:01', null);
INSERT INTO `sys_position` VALUES ('30120@099', '总行业务岗@风控部门', '30120@null', '总行业务岗@null', '099', '30120', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-08-29 09:57:18', null);
INSERT INTO `sys_position` VALUES ('30130@0201', '总行审核岗@总行', '30130@null', '总行审核岗@null', '0201', '30130', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-07-24 10:20:38', null);
INSERT INTO `sys_position` VALUES ('30130@020101', '总行审核岗@合规部', '30130@null', '总行审核岗@null', '020101', '30130', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-07-24 10:23:12', null);
INSERT INTO `sys_position` VALUES ('30130@020102', '总行审核岗@风险部', '30130@null', '总行审核岗@null', '020102', '30130', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-08-28 15:38:17', null);
INSERT INTO `sys_position` VALUES ('30130@099', '总行审核岗@风控部门', '30130@null', '总行审核岗@null', '099', '30130', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-08-29 09:57:18', null);
INSERT INTO `sys_position` VALUES ('30140@0201', '总行复核岗@总行', '30140@null', '总行复核岗@null', '0201', '30140', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-07-21 11:20:06', null);
INSERT INTO `sys_position` VALUES ('30140@020101', '总行复核岗@合规部', '30140@null', '总行复核岗@null', '020101', '30140', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-08-14 09:40:15', null);
INSERT INTO `sys_position` VALUES ('30150@0201', '总行审批岗@总行', '30150@0201', '总行审批岗@总行', '0201', '30150', 'BIZ_ROLE', null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-09-14 17:54:04', null);
INSERT INTO `sys_position` VALUES ('30150@020101', '总行审批岗@合规部', '30150@null', '总行审批岗@null', '020101', '30150', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-07-24 10:23:48', null);
INSERT INTO `sys_position` VALUES ('30150@099', '总行审批岗@风控部门', '30150@null', '总行审批岗@null', '099', '30150', 'BIZ_ROLE', null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-08-29 09:59:01', null);
INSERT INTO `sys_position` VALUES ('70110@0202010402', '二级分行管理@风险部', '70110@null', '二级分行管理@null', '0202010402', '70110', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-07-24 10:25:11', null);
INSERT INTO `sys_position` VALUES ('70110@0202010403', '二级分行管理@IT部', '70110@null', '二级分行管理@null', '0202010403', '70110', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-07-20 17:19:40', null);
INSERT INTO `sys_position` VALUES ('70120@0202010401', '二级分行业务@合规部', '70120@null', '二级分行业务@null', '0202010401', '70120', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-07-24 10:26:35', null);
INSERT INTO `sys_position` VALUES ('70120@0202010402', '二级分行业务@风险部', '70120@null', '二级分行业务@null', '0202010402', '70120', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-07-24 10:25:47', null);
INSERT INTO `sys_position` VALUES ('90120@0201', 'null@总行', '90120@null', 'null@null', '0201', '90120', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-08-01 10:26:27', null);
INSERT INTO `sys_position` VALUES ('90120@0202010101', '支行业务岗@风险部', '90120@null', '支行业务岗@null', '0202010101', '90120', null, null, null, 'DEPT', null, null, null, null, null, null, null, null, '2017-07-21 15:50:31', null);

-- ----------------------------
-- Table structure for `sys_duty`
-- ----------------------------
DROP TABLE IF EXISTS `sys_duty`;
CREATE TABLE `sys_duty` (
  `DUTY_ID_` varchar(64) NOT NULL COMMENT '主键，系统自动生成',
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '用户编码',
  `POSITION_CODE_` varchar(64) DEFAULT NULL COMMENT '职位编码',
  `DUTY_SOURCE_` varchar(64) DEFAULT NULL COMMENT '职责来源',
  `IS_PRIMARY_` smallint(6) DEFAULT NULL COMMENT '是否主要职责1 - 是，0 - 否',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`DUTY_ID_`),
  KEY `FK_Reference_14` (`STAFF_CODE_`),
  KEY `FK_Reference_15` (`POSITION_CODE_`),
  CONSTRAINT `sys_duty_ibfk_1` FOREIGN KEY (`POSITION_CODE_`) REFERENCES `sys_position` (`POSITION_CODE_`),
  CONSTRAINT `sys_duty_ibfk_2` FOREIGN KEY (`STAFF_CODE_`) REFERENCES `sys_staff` (`STAFF_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='为用户定义职责';

-- ----------------------------
-- Records of sys_duty
-- ----------------------------
INSERT INTO `sys_duty` VALUES ('20170724102200B0F1AF70F668427690417962E1E9FDFB', 'odn', '30120@0201', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-07-24 10:21:48', null);
INSERT INTO `sys_duty` VALUES ('2017072410224586122C1B6CC8470F9CD0537221395683', 'wrj', '30120@0201', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-07-24 10:22:33', null);
INSERT INTO `sys_duty` VALUES ('20170724102400F6E77C1BEE0441AAA730C57B4979B89E', 'xhf', '30150@020101', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-07-24 10:23:48', null);
INSERT INTO `sys_duty` VALUES ('20170724102441719494BF98284BE28E9669A1723B6E29', 'xmr', '30130@020101', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-07-24 10:24:29', null);
INSERT INTO `sys_duty` VALUES ('20170724102523BBF0FF06E5464BF6B2A4F0EAC18EE806', 'xyl', '70110@0202010402', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-07-24 10:25:11', null);
INSERT INTO `sys_duty` VALUES ('20170724102647862D6D0BD9FA43DD81AF872932D3CCBF', 'yxf', '70120@0202010401', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-07-24 10:26:35', null);
INSERT INTO `sys_duty` VALUES ('2017072410270165E81404C5F34AA5A14F8CCEAEF7F275', 'xyr', '70120@0202010402', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-07-24 10:26:50', null);
INSERT INTO `sys_duty` VALUES ('20170801102629495A1146D99E4C03ADAD1525D04716DB', 'brm', '90120@0201', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-08-01 10:26:27', null);
INSERT INTO `sys_duty` VALUES ('20170801102629A6ED6A2CF8974BC4A400BD3A6FFA73BF', 'brm', '30110@0201', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-08-01 10:26:28', null);
INSERT INTO `sys_duty` VALUES ('2017081409412173490BF2EB4C445E9362FAB2434B985D', 'wxw', '30150@020101', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-08-14 09:41:15', null);
INSERT INTO `sys_duty` VALUES ('20170814094121A5E555D2C7B949369D70A8B104F16414', 'wxw', '30110@020101', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-08-14 09:41:15', null);
INSERT INTO `sys_duty` VALUES ('20170814094121C2AA1C5384B9422BAA453E02D4CE1FCF', 'wxw', '30130@020101', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-08-14 09:41:15', null);
INSERT INTO `sys_duty` VALUES ('201708140944043278976348964A038C670DD0713D6D82', 'wxw', '30140@020101', 'ROLEGROUP', null, null, null, null, null, null, null, null, null, '2017-08-14 09:43:57', null);
INSERT INTO `sys_duty` VALUES ('20170814094404634C08513DB24F9497D5B33128B2205C', 'wxw', '30120@020101', 'ROLEGROUP', null, null, null, null, null, null, null, null, null, '2017-08-14 09:43:58', null);
INSERT INTO `sys_duty` VALUES ('2017081409440471E27C0C70B9475AA6F9AEAEFA3DAE63', 'wxw', '30110@020101', 'ROLEGROUP', null, null, null, null, null, null, null, null, null, '2017-08-14 09:43:57', null);
INSERT INTO `sys_duty` VALUES ('201708281601033E134C1464DA4701AD6AED327AEDF034', '090', '30120@020102', 'ROLE', null, null, null, null, null, null, null, null, null, '2017-08-28 16:04:01', null);
INSERT INTO `sys_duty` VALUES ('20170914175414193D380218DA4E3EA405F61C9973103B', 'brm', '30120@0201', 'ROLEGROUP', null, null, null, null, null, null, null, null, null, '2017-09-14 17:54:04', null);
INSERT INTO `sys_duty` VALUES ('20170914175414234A3209ED58478D8B9F499A8F50F38A', 'brm', '30150@0201', 'ROLEGROUP', null, null, null, null, null, null, null, null, null, '2017-09-14 17:54:04', null);
INSERT INTO `sys_duty` VALUES ('201709141754149907D929CD8B4EB88D9A49CFA0DD9D96', 'brm', '30110@0201', 'ROLEGROUP', null, null, null, null, null, null, null, null, null, '2017-09-14 17:54:04', null);

-- ----------------------------
-- Table structure for `sys_module`
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module` (
  `MODULE_CODE_` varchar(64) NOT NULL COMMENT '模块编码',
  `MODULE_NAME_` varchar(128) DEFAULT NULL COMMENT '模块名称',
  `MODULE_DESC_` varchar(256) DEFAULT NULL COMMENT '模块描述',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`MODULE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单模块';

-- ----------------------------
-- Records of sys_module
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `MENU_CODE_` varchar(64) NOT NULL COMMENT '菜单编码',
  `MENU_NAME_` varchar(128) NOT NULL COMMENT '菜单名称',
  `MODULE_CODE_` varchar(64) DEFAULT NULL COMMENT '模块编码',
  `PARENT_ID_` varchar(64) DEFAULT NULL COMMENT '父菜单编码',
  `URL_` varchar(256) NOT NULL COMMENT '路径',
  `MENU_LEVEL_` varchar(256) DEFAULT NULL COMMENT '菜单级别：BIZ - 业务模块，OTHERS - 其他模块',
  `MENU_IMAGE_` varchar(256) DEFAULT NULL COMMENT '图标',
  `MENU_TARGET_` varchar(256) DEFAULT NULL COMMENT '菜单目标，暂未启用',
  `ENABLED_` varchar(64) DEFAULT NULL COMMENT '是否启用：1 - 启用, 0 - 停用',
  `VISIBLED_` varchar(64) DEFAULT NULL COMMENT '是否显示：1 - 显示, 0 - 不显示',
  `PUBLIC_MENU_` varchar(64) DEFAULT NULL COMMENT '是否为公共菜单：1 - 公共菜单，所有用户可见；0 - 非公共菜单， 授权用户可见',
  `TIPS_` varchar(256) DEFAULT NULL COMMENT '提示',
  `LEAF_NODE_` varchar(64) DEFAULT NULL COMMENT '叶节点标识：Y - 叶节点, N - 非叶节点',
  `CONTEXT_RELATIVE_` varchar(256) DEFAULT NULL COMMENT '上下文，暂未启用',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`MENU_CODE_`),
  KEY `FK_Reference_25` (`MODULE_CODE_`),
  CONSTRAINT `sys_menu_ibfk_1` FOREIGN KEY (`MODULE_CODE_`) REFERENCES `sys_module` (`MODULE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单定义表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('sys', '系统管理', null, null, '#', null, null, null, 'Y', 'Y', 'Y', '系统管理', null, null, null, null, null, null, null, '10', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.100', '系统配置', null, 'sys', '#', null, null, null, 'Y', 'Y', null, '', null, null, null, null, null, null, null, '10', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.100.100', '菜单配置', null, 'sys.100', 'sys/menu/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, '10', null, null, '2017-09-14 11:13:25', null);
INSERT INTO `sys_menu` VALUES ('sys.100.200', '系统参数配置', null, 'sys.100', 'sys/appConfig/sysList', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '20', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.100.300', '应用参数配置', null, 'sys.100', 'sys/appConfig/appList', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '30', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.100.400', '角色类型', null, 'sys.100', 'sys/roleStd/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '40', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.100.500', '字典类别管理', null, 'sys.100', 'sys/dictCate/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '50', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.100.600', '字典管理', null, 'sys.100', 'sys/dict/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '60', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.100.700', '附件类型管理', null, 'sys.100', 'sys/attachmentType/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '700', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.200', '机构人员权限', null, 'sys', '#', null, null, null, 'Y', 'Y', 'Y', '', null, null, null, null, null, null, null, '20', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.200.100', '机构树', null, 'sys.200', 'sys/unit/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '10', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.200.200', '用户管理', null, 'sys.200', 'sys/staff/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '20', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.200.300', '用户组管理', null, 'sys.200', 'sys/staffGroup/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '30', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.200.400', '角色管理', null, 'sys.200', 'sys/role/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '40', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.200.500', '角色组管理', null, 'sys.200', 'sys/roleGroup/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '50', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.200.600', '授权管理', null, 'sys.200', 'sys/authorization/list', null, null, null, 'Y', 'Y', 'Y', '', 'Y', null, null, null, null, null, null, '60', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.200.700', '业务种类管理', null, 'sys.200', 'sys/bizType/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '70', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.200.800', '借调管理', null, 'sys.200', 'sys/staffProxyLog/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, '80', null, null, '2017-08-28 17:54:11', null);
INSERT INTO `sys_menu` VALUES ('sys.200.810', '上报管理', null, 'sys.200', 'sys/bizDeptReport/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '810', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('sys.200.820', '代管管理', null, 'sys.200', 'sys/bizDeptProxy/list', null, null, null, 'Y', 'Y', null, '', 'Y', null, null, null, null, null, null, '820', null, null, '2017-08-11 14:26:50', null);
INSERT INTO `sys_menu` VALUES ('dashboard', '仪表盘配置', null, 'sys', '#', null, null, null, 'Y', 'Y', null, '仪表盘配置', 'N', null, null, null, null, null, null, 40, null, null, '2017-11-17 11:23:02', null);
INSERT INTO `sys_menu` VALUES ('dashboard.100', '组件管理', null, 'dashboard', 'sys/dashWidget/list', null, null, null, 'Y', 'Y', null, null, null, null, null, null, null, null, null, 10, null, null, '2017-11-17 14:31:16', null);
INSERT INTO `sys_menu` VALUES ('dashboard.200', '布局管理', null, 'dashboard', 'sys/dashLayout/list', null, null, null, 'Y', 'Y', null, null, null, null, null, null, null, null, null, 20, null, null, '2017-11-17 14:01:14', null);
INSERT INTO `sys_menu` VALUES ('dashboard.300', '组件授权', null, 'dashboard', 'sys/dashWidgetDak/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 30, null, null, '2017-12-18 09:26:42', null);
INSERT INTO `sys_menu` VALUES ('dashboard.400', '布局授权', null, 'dashboard', 'sys/dashLayoutMap/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 40, null, null, '2018-01-02 09:31:29', null);
INSERT INTO `sys_menu` VALUES ('dashboard.500', '数据提供器管理', null, 'dashboard', 'sys/dataProvider/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 5, null, null, '2018-01-24 14:15:48', null);
INSERT INTO `sys_menu` VALUES ('notify', '通知中心', null, 'sys', '#', null, null, null, 'Y', 'Y', null, '通知中心', 'N', null, null, null, null, null, null, 30, null, null, '2017-11-20 10:36:38', null);
INSERT INTO `sys_menu` VALUES ('notify.100', '消息中心', null, 'notify', 'sys/staffNotify/list', null, null, null, 'Y', 'Y', null, '消息中心', 'Y', null, null, null, null, null, null, 20, null, null, '2017-10-16 11:07:56', null);
INSERT INTO `sys_menu` VALUES ('notify.200', '系统公告', null, 'notify', 'sys/notify/announceList', null, null, null, 'Y', 'Y', null, '系统公告', 'Y', null, null, null, null, null, null, 10, null, null, '2017-10-17 08:53:04', null);
-- INSERT INTO `sys_menu` VALUES ('notify.300', '待办配置', null, 'notify', 'sys/notifyTodoOper/add', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 30, null, null, '2017-11-03 14:28:04', null);
INSERT INTO `sys_menu` VALUES ('sys.500', '元数据管理', NULL, 'sys', '#', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'N', NULL, NULL, NULL, NULL, NULL, NULL, 500, NULL, NULL, '2018-1-17 14:27:45', NULL);
INSERT INTO `sys_menu` VALUES ('sys.500.100', '数据区域管理', NULL, 'sys.500', 'sys/metaRegion/list', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 100, NULL, NULL, '2018-1-3 14:07:41', NULL);
INSERT INTO `sys_menu` VALUES ('sys.500.200', '元数据管理', NULL, 'sys.500', 'sys/metaTable/list', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 200, NULL, NULL, '2018-1-3 14:12:11', NULL);
INSERT INTO `sys_menu` VALUES ('sys.500.300', '元数据授权', NULL, 'sys.500', 'sys/metaAuthority/list', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 300, NULL, NULL, '2018-1-3 14:14:03', NULL);
INSERT INTO `sys_menu` VALUES ('sys.500.400', '查询分析器', NULL, 'sys.500', 'sys/dataAnalysis/main', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 400, NULL, NULL, '2018-1-4 17:05:26', NULL);
INSERT INTO `sys_menu` VALUES ('sch', '调度管理', null, null, '#', null, null, null, 'Y', 'Y', null, null, null, null, null, null, null, null, null, 40, null, null, '2017-10-27 16:32:11', null);
INSERT INTO `sys_menu` VALUES ('sch.100', '集群管理', null, 'sch', 'sch/cluster/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 10, null, null, '2017-10-27 16:35:06', null);
INSERT INTO `sys_menu` VALUES ('sch.200', '节点管理', null, 'sch', 'sch/node/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 20, null, null, '2017-10-27 16:35:15', null);
INSERT INTO `sys_menu` VALUES ('sch.300', '任务类型', null, 'sch', 'sch/jobGroup/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 30, null, null, '2017-11-09 10:12:46', null);
INSERT INTO `sys_menu` VALUES ('sch.400', '日历管理', null, 'sch', 'sch/calendarDetail/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 40, null, null, '2017-10-27 16:52:28', null);
INSERT INTO `sys_menu` VALUES ('sch.500', '任务调度', null, 'sch', 'sch/jobEntity/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 50, null, null, '2017-10-27 16:38:59', null);
INSERT INTO `sys_menu` VALUES ('sch.600', '监控中心', null, 'sch', '#', null, null, null, 'Y', 'Y', null, null, 'N', null, null, null, null, null, null, 60, null, null, '2017-10-27 16:39:36', null);
INSERT INTO `sys_menu` VALUES ('sch.600.100', '节点监控', null, 'sch.600', 'sch/nodeState/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 10, null, null, '2017-11-03 09:42:31', null);
INSERT INTO `sys_menu` VALUES ('sch.600.200', '实例监控', null, 'sch.600', 'sch/jobInst/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 20, null, null, '2017-11-17 09:15:21', null);
INSERT INTO `sys_menu` VALUES ('workflow', '工作流引擎', null, null, '#', null, null, null, 'Y', 'Y', 'Y', '工作流引擎', 'N', null, null, null, null, null, null, 30, null, null, '2017-09-18 17:46:13', null);
INSERT INTO `sys_menu` VALUES ('workflow.100', '基础功能', null, 'workflow', '#', null, null, null, 'Y', 'Y', null, '工作流程引擎的基础功能', 'N', null, null, null, null, null, null, 10, null, null, '2017-09-18 17:50:25', null);
INSERT INTO `sys_menu` VALUES ('workflow.100.100', '流程模板管理', null, 'workflow.100', 'workflow/procTemplate/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 10, null, null, '2017-09-19 15:56:37', null);
INSERT INTO `sys_menu` VALUES ('workflow.100.200', '表单管理', null, 'workflow.100', 'workflow/formDef/list', null, null, null, 'Y', 'Y', null, null, null, null, null, null, null, null, null, 20, null, null, '2017-09-19 10:08:12', null);
INSERT INTO `sys_menu` VALUES ('workflow.100.300', '业务模块管理', null, 'workflow.100', 'workflow/bizModule/list', null, null, null, 'Y', 'Y', null, '业务模块管理', 'Y', null, null, null, null, null, null, 30, null, null, '2017-09-19 15:19:03', null);
INSERT INTO `sys_menu` VALUES ('workflow.100.400', '业务流程管理', null, 'workflow.100', 'workflow/procDef/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 40, null, null, '2017-09-19 15:19:03', null);
INSERT INTO `sys_menu` VALUES ('workflow.200', '监控中心', null, 'workflow', '#', null, null, null, 'Y', 'Y', null, null, 'N', null, null, null, null, null, null, 200, null, null, '2017-12-21 22:44:52', null);
INSERT INTO `sys_menu` VALUES ('workflow.200.100', '流程调整', null, 'workflow.200', 'workflow/monitor/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 100, null, null, '2017-11-09 14:34:26', null);
INSERT INTO `sys_menu` VALUES ('workflow.200.200', '流程监控', null, 'workflow.200', 'workflow/monitor/monitorCenter', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 200, null, null, '2017-12-21 17:01:32', null);
INSERT INTO `sys_menu` VALUES ('workflow.300', '我的流程', null, 'workflow', '#', null, null, null, 'Y', 'Y', 'Y', null, 'N', null, null, null, null, null, null, 300, null, null, '2017-12-21 22:45:42', null);
INSERT INTO `sys_menu` VALUES ('workflow.300.100', '未提交', null, 'workflow.300', 'workflow/process/draftList', null, null, null, 'Y', 'Y', 'Y', null, 'Y', null, null, null, null, null, null, 100, null, null, '2017-11-17 14:19:24', null);
INSERT INTO `sys_menu` VALUES ('workflow.300.200', '审批中', null, 'workflow.300', 'workflow/process/runingList', null, null, null, 'Y', 'Y', 'Y', null, 'Y', null, null, null, null, null, null, 200, null, null, '2017-11-17 14:19:21', null);
INSERT INTO `sys_menu` VALUES ('workflow.300.300', '已审批', null, 'workflow.300', 'workflow/process/completeList', null, null, null, 'Y', 'Y', 'Y', null, 'Y', null, null, null, null, null, null, 300, null, null, '2017-11-17 14:19:17', null);
INSERT INTO `sys_menu` VALUES ('workflow.400', '我的审批', null, 'workflow', '#', null, null, null, 'Y', 'Y', 'Y', null, 'N', null, null, null, null, null, null, 400, null, null, '2017-12-21 22:45:50', null);
INSERT INTO `sys_menu` VALUES ('workflow.400.100', '待审批', null, 'workflow.400', 'workflow/approve/todoList', null, null, null, 'Y', 'Y', 'Y', null, 'Y', null, null, null, null, null, null, 100, null, null, '2017-11-17 14:19:09', null);
INSERT INTO `sys_menu` VALUES ('workflow.400.200', '已审批', null, 'workflow.400', 'workflow/approve/doneList', null, null, null, 'Y', 'Y', 'Y', null, 'Y', null, null, null, null, null, null, 200, null, null, '2017-11-17 14:19:13', null);
INSERT INTO `sys_menu` VALUES ('workflow.500', '审批托管', null, 'workflow', 'wf/procAutoTransfer/list', null, null, null, 'Y', 'Y', 'Y', null, 'Y', null, null, null, null, null, null, 500, null, null, '2017-12-04 16:59:14', null);
INSERT INTO `sys_menu` VALUES ('workflow.666', '工作流测试', null, 'workflow', 'demo/speciality/testList', null, null, null, 'Y', 'Y', 'Y', null, 'Y', null, null, null, null, null, null, 240, null, null, '2017-12-21 22:45:17', null);
INSERT INTO `sys_menu` VALUES ('workflow.testsubmit', '测试工作流提交', null, 'workflow', 'workflow/procDef/workflowTestSubmit', null, null, null, 'Y', 'Y', 'Y', null, 'Y', null, null, null, null, null, null, 260, null, null, '2017-12-21 22:45:23', null);
INSERT INTO `sys_menu` VALUES ('rule', '规则引擎', null, null, '#', null, null, null, 'Y', 'Y', null, null, 'N', null, null, null, null, null, null, 20, null, null, '2017-08-16 09:44:45', null);
INSERT INTO `sys_menu` VALUES ('rule.100', '基础数据管理', null, 'rule', 'rul/baseTable/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 10, null, null, '2018-01-03 10:15:50', null);
INSERT INTO `sys_menu` VALUES ('rule.200', '计算元管理', null, 'rule', 'rul/dataElement/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 30, null, null, '2018-01-03 10:15:54', null);
INSERT INTO `sys_menu` VALUES ('rule.300', '交易规则管理', null, 'rule', 'rul/trade/list', null, null, null, 'Y', 'Y', null, null, 'Y', null, null, null, null, null, null, 40, null, null, '2018-01-03 10:16:02', null);

-- ----------------------------
-- Table structure for `sys_notify`
-- ----------------------------
DROP TABLE IF EXISTS `sys_notify`;
CREATE TABLE `sys_notify` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `TYPE_` varchar(64) DEFAULT NULL COMMENT 'ANNOUNCE: 系统公告\n            TODO: 待办任务\n            REMIND: 提醒\n            MESSAGE: 站内信',
  `TARGET_` varchar(64) DEFAULT NULL COMMENT '目标ID',
  `TARGET_TYPE_` varchar(64) DEFAULT NULL COMMENT '目标类型',
  `ACTION_` varchar(64) DEFAULT NULL COMMENT '动作，在不同的目标对象上，可能发生不同的动作，由具体业务模块来维护和处理。',
  `SENDER_` varchar(64) DEFAULT NULL COMMENT '发送者',
  `SENDER_NAME_` varchar(64) DEFAULT NULL COMMENT '发送者名称',
  `SEND_TIME_` datetime DEFAULT NULL COMMENT '发送时间',
  `SUBJECT_` varchar(256) DEFAULT NULL COMMENT '主题',
  `CONTENT_` longtext COMMENT '内容',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT 'DRAFT: 草稿\n            SEND: 已发送\n            TRASH: 已删除',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  `IS_UPLOAD_` varchar(64) DEFAULT NULL,
  `RECEIVER_` varchar(256) DEFAULT NULL,
  `RECEIVER_NAME_` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知表，比如说，工作流的某个节点创建了任务等。';

-- ----------------------------
-- Records of sys_notify
-- ----------------------------
INSERT INTO `sys_notify` VALUES ('80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'ANNOUNCE', null, null, null, 'jepAdmin', '超级管理员', '2018-02-06 10:57:42', '系统消息中心测试', '<p>Hi all，</p><p>本邮件为系统消息中心功能测试邮件，请勿回复。</p><p>谢谢!</p>', 'SEND', null, null, null, null, null, null, null, null, '2018-02-06 10:57:43', null, 'N', '090,brm,brm1212,fxe,jepAdmin,lrg,odn,wmd,wrj,wxw,xhf,xmr,xyl,xyr,yxf,', '小红,柏荣幕,121212,冯絮儿,超级管理员,龙瑞刚,欧德恩,吴梦蝶,魏荣建,吴宪魏,肖鸿飞,席明瑞,席玉龙,薛嫣然,叶雪飞');

-- ----------------------------
-- Table structure for `sys_staff_notify`
-- ----------------------------
DROP TABLE IF EXISTS `sys_staff_notify`;
CREATE TABLE `sys_staff_notify` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '用户编码',
  `STAFF_NAME_` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `NOTIFY_ID_` varchar(64) DEFAULT NULL COMMENT '通知主键',
  `READ_` varchar(64) DEFAULT NULL COMMENT '是否已读',
  `DONE_` varchar(64) DEFAULT NULL COMMENT '是否已处理',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT 'INBOX: 收件箱\n            TRASH: 垃圾箱',
  `TRASH_TIME_` datetime DEFAULT NULL COMMENT '删除时间',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`),
  KEY `FK_Reference_29` (`NOTIFY_ID_`),
  CONSTRAINT `FK_Reference_29` FOREIGN KEY (`NOTIFY_ID_`) REFERENCES `sys_notify` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='具体到某个用户的通知';

-- ----------------------------
-- Records of sys_staff_notify
-- ----------------------------
INSERT INTO `sys_staff_notify` VALUES ('808bf775-0ae9-11e8-aaf6-34e6d777b6da', '090', '小红', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:43', null);
INSERT INTO `sys_staff_notify` VALUES ('80937e97-0ae9-11e8-aaf6-34e6d777b6da', 'brm', '柏荣幕', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:43', null);
INSERT INTO `sys_staff_notify` VALUES ('809cde7a-0ae9-11e8-aaf6-34e6d777b6da', 'brm1212', '121212', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:43', null);
INSERT INTO `sys_staff_notify` VALUES ('80abb742-0ae9-11e8-aaf6-34e6d777b6da', 'fxe', '冯絮儿', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:43', null);
INSERT INTO `sys_staff_notify` VALUES ('80af3bcd-0ae9-11e8-aaf6-34e6d777b6da', 'jepAdmin', '超级管理员', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:43', null);
INSERT INTO `sys_staff_notify` VALUES ('80b043b6-0ae9-11e8-aaf6-34e6d777b6da', 'lrg', '龙瑞刚', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:43', null);
INSERT INTO `sys_staff_notify` VALUES ('80b1b291-0ae9-11e8-aaf6-34e6d777b6da', 'odn', '欧德恩', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:43', null);
INSERT INTO `sys_staff_notify` VALUES ('80b2b921-0ae9-11e8-aaf6-34e6d777b6da', 'wmd', '吴梦蝶', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:43', null);
INSERT INTO `sys_staff_notify` VALUES ('80b47376-0ae9-11e8-aaf6-34e6d777b6da', 'wrj', '魏荣建', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:43', null);
INSERT INTO `sys_staff_notify` VALUES ('80b5a532-0ae9-11e8-aaf6-34e6d777b6da', 'wxw', '吴宪魏', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:43', null);
INSERT INTO `sys_staff_notify` VALUES ('80b75c60-0ae9-11e8-aaf6-34e6d777b6da', 'xhf', '肖鸿飞', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:44', null);
INSERT INTO `sys_staff_notify` VALUES ('80b87102-0ae9-11e8-aaf6-34e6d777b6da', 'xmr', '席明瑞', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:44', null);
INSERT INTO `sys_staff_notify` VALUES ('80b9cc82-0ae9-11e8-aaf6-34e6d777b6da', 'xyl', '席玉龙', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:44', null);
INSERT INTO `sys_staff_notify` VALUES ('80bacb86-0ae9-11e8-aaf6-34e6d777b6da', 'xyr', '薛嫣然', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:44', null);
INSERT INTO `sys_staff_notify` VALUES ('80bbd8d0-0ae9-11e8-aaf6-34e6d777b6da', 'yxf', '叶雪飞', '80869a70-0ae9-11e8-aaf6-34e6d777b6da', 'N', 'N', 'INBOX', null, null, null, null, '2018-02-06 10:57:44', null, null, null, null, '2018-02-06 10:57:44', null);

-- ----------------------------
-- Table structure for `sys_notify_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_notify_config`;
CREATE TABLE `sys_notify_config` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `STAFF_CODE_` varchar(128) DEFAULT NULL COMMENT '用户编码',
  `STAFF_NAME_` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `STAFF_PHONE_` varchar(64) DEFAULT NULL COMMENT '用户手机号',
  `STAFF_EMAIL_` varchar(128) DEFAULT NULL COMMENT '用户邮箱',
  `IS_RECEIVE_REMIND_` varchar(64) DEFAULT NULL COMMENT '是否接收提醒消息',
  `IS_RECEIVE_MSG_` varchar(64) DEFAULT NULL COMMENT '是否接受私信',
  `IS_RECEIVE_SMS_` varchar(64) DEFAULT NULL COMMENT '是否接收短信',
  `IS_RECEIVE_EMAIL_` varchar(64) DEFAULT NULL COMMENT '是否接收邮件',
  `SMS_RECEIVE_TYPE_` varchar(64) DEFAULT NULL COMMENT '短信接收类型：\n            ANNOUNCE: 系统公告\n            TODO: 待办任务\n            REMIND: 提醒\n            MESSAGE: 站内信\n            ',
  `EMAIL_RECEIVE_TYPE_` varchar(64) DEFAULT NULL COMMENT '邮件接收类型\n            ANNOUNCE: 系统公告\n            TODO: 待办任务\n            REMIND: 提醒\n            MESSAGE: 站内信',
  `INBOX_RETENTION_TIME_` varchar(64) DEFAULT NULL COMMENT '收件箱保留时长',
  `TRASH_RETENTION_TIME_` varchar(64) DEFAULT NULL COMMENT '垃圾箱保留时长',
  `BLACKLIST_` varchar(4000) DEFAULT NULL COMMENT '黑名单',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  `BLACKLIST_TEXT_` varchar(4000) DEFAULT NULL,
  `SMS_RETRY_TIMES_` smallint(1) DEFAULT NULL,
  `EMAIL_RETRY_TIMES_` smallint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通知配置表';

-- ----------------------------
-- Records of sys_notify_config
-- ----------------------------
INSERT INTO `sys_notify_config` VALUES ('SYS_DEFAULT_CONFIG', null, null, null, null, 'RECEIVE', 'RECEIVE', 'REFUSE', 'REFUSE', null, null, '30', '30', null, null, null, null, null, null, null, null, null, '2017-10-31 16:36:55', null, null, '3', '3');

-- ----------------------------
-- Table structure for `sys_notify_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_notify_log`;
CREATE TABLE `sys_notify_log` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `NOTIFY_CONFIG_ID_` varchar(64) DEFAULT NULL COMMENT '配置主键',
  `NOTIFY_ID_` varchar(64) DEFAULT NULL COMMENT '通知主键',
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '用户编码',
  `STAFF_NAME_` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `STAFF_PHONE_` varchar(64) DEFAULT NULL COMMENT '用户手机号',
  `STAFF_EMAIL_` varchar(128) DEFAULT NULL COMMENT '用户邮箱',
  `TYPE_` varchar(64) DEFAULT NULL COMMENT '类型：\n            短信 - SMS\n            邮件 - EMAIL',
  `RETRY_TIMES_` int(2) DEFAULT NULL COMMENT '重发次数',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态\n            发送成功 - SUCCESS\n            发送失败 - FAILURE\n            ',
  `CONTENT_` longtext COMMENT '内容\n            短信内容',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`),
  KEY `FK_Reference_39` (`NOTIFY_CONFIG_ID_`),
  KEY `FK_Reference_40` (`NOTIFY_ID_`),
  CONSTRAINT `FK_Reference_39` FOREIGN KEY (`NOTIFY_CONFIG_ID_`) REFERENCES `sys_notify_config` (`ID_`),
  CONSTRAINT `FK_Reference_40` FOREIGN KEY (`NOTIFY_ID_`) REFERENCES `sys_notify` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户通知记录表，包含短信和邮件两种通知类型的记录';

-- ----------------------------
-- Records of sys_notify_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_meta_authority`
-- ----------------------------
DROP TABLE IF EXISTS `sys_meta_authority`;
CREATE TABLE `sys_meta_authority` (
  `AUTH_ID_` varchar(64) NOT NULL COMMENT '授权ID',
  `ROLE_CODE_` varchar(64) DEFAULT NULL COMMENT '角色编码',
  `AUTH_TYPE_` varchar(64) DEFAULT NULL COMMENT 'OPER - 操作权限\r\n            DAK - 数据权限\r\n            ',
  `AUTH_TARGET_TYPE_` varchar(64) DEFAULT NULL COMMENT '授权对象类型\r\n            M - 数据集市\r\n            T - 元数据\r\n            C - 元数据列',
  `AUTH_TARGET_` varchar(64) DEFAULT NULL COMMENT '授权对象',
  `AUTH_DATA_` varchar(64) DEFAULT NULL COMMENT '授权内容：\r\n            操作权限： Y - 允许，N - 拒绝\r\n            数据权限： DAK_ORG, DAK_ORG_ALL, DAK_DEPT, DAK_DEPT_ALL, DAK_FORBIDDEN',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`AUTH_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元数据授权，包括操作权限与数据权限。';

-- ----------------------------
-- Records of sys_meta_authority
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_meta_column`
-- ----------------------------
DROP TABLE IF EXISTS `sys_meta_column`;
CREATE TABLE `sys_meta_column` (
  `COLUMN_ID_` varchar(64) NOT NULL COMMENT '元数据ID',
  `TABLE_ID_` varchar(64) NOT NULL COMMENT '表元数据ID',
  `TABLE_CODE_` varchar(64) DEFAULT NULL COMMENT '表名',
  `COLUMN_CODE_` varchar(64) DEFAULT NULL COMMENT '字段名',
  `COLUMN_NAME_` varchar(1024) DEFAULT NULL COMMENT '中文名',
  `DATA_TYPE_` varchar(64) DEFAULT NULL COMMENT '数据类型',
  `LENGTH_` bigint(20) DEFAULT NULL COMMENT '字段长度',
  `SCALE_` bigint(20) DEFAULT NULL COMMENT '字段精度',
  `IS_KEY_` varchar(64) DEFAULT NULL COMMENT '是否主键',
  `IS_SENSITIVE_` varchar(64) DEFAULT NULL COMMENT '是否敏感字段',
  `IS_FOREIGN_KEY_` varchar(64) DEFAULT NULL COMMENT '是否外键',
  `ORDER_` int(11) DEFAULT NULL COMMENT '字段顺序',
  `PROP_` varchar(64) DEFAULT NULL COMMENT '字段属性：\r\n            O - 机构字段\r\n            D - 日期字段\r\n            N - 普通字段',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`COLUMN_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='列元数据表';

-- ----------------------------
-- Records of sys_meta_column
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_meta_query`
-- ----------------------------
DROP TABLE IF EXISTS `sys_meta_query`;
CREATE TABLE `sys_meta_query` (
  `TABLE_ID_` varchar(64) DEFAULT NULL COMMENT '元数据ID',
  `QUERY_ID_` varchar(64) NOT NULL COMMENT '查询ID',
  `QUERY_NAME_` varchar(256) DEFAULT NULL COMMENT '查询名称',
  `QUERY_TYPE_` varchar(64) DEFAULT NULL COMMENT '公共查询：PUBLIC\r\n            私有查询：PRIVATE',
  `SCRIPT_` mediumtext COMMENT '查询内容',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态：\r\n            10 - 草稿\r\n            30 - 已生效\r\n            50 - 已失效',
  `UNIT_CODE_` varchar(64) DEFAULT NULL COMMENT '机构编码',
  `UNIT_NAME_` varchar(256) DEFAULT NULL COMMENT '机构名称',
  `DEPT_CODE_` varchar(64) DEFAULT NULL COMMENT '部门编码',
  `DEPT_NAME_` varchar(256) DEFAULT NULL COMMENT '部门名称',
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '用户编码',
  `STAFF_NAME_` varchar(256) DEFAULT NULL COMMENT '用户名称',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`QUERY_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元数据表的自定义查询';

-- ----------------------------
-- Records of sys_meta_query
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_meta_region`
-- ----------------------------
DROP TABLE IF EXISTS `sys_meta_region`;
CREATE TABLE `sys_meta_region` (
  `REGION_ID_` varchar(64) NOT NULL COMMENT '区域ID',
  `REGION_CODE_` varchar(64) NOT NULL COMMENT '区域编码',
  `REGION_NAME_` varchar(256) DEFAULT NULL COMMENT '区域名称',
  `REGION_DESC_` varchar(1024) DEFAULT NULL COMMENT '区域描述',
  `TABLESPACE_ID_` varchar(64) DEFAULT NULL COMMENT '表空间ID',
  `TABLESPACE_CODE_` varchar(64) DEFAULT NULL COMMENT '表空间编码',
  `PARENT_ID_` varchar(64) DEFAULT NULL COMMENT '父区域',
  `PATH_CODE_` varchar(512) DEFAULT NULL COMMENT '编号路径',
  `PATH_NAME_` varchar(1024) DEFAULT NULL COMMENT '名称路径',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '状态：\r\n            10 - 草稿\r\n            30 - 启用\r\n            50 - 停用',
  `HIERARCHY_` int(11) DEFAULT NULL COMMENT '层级',
  `DISORDER_` int(11) DEFAULT NULL COMMENT '显示顺序',
  `TABLE_COUNT_` int(11) DEFAULT NULL COMMENT '表数量',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`REGION_ID_`),
  UNIQUE KEY `U_REGION_CODE` (`REGION_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元数据区域，元数据的分类。';

-- ----------------------------
-- Records of sys_meta_region
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_meta_region_table`
-- ----------------------------
DROP TABLE IF EXISTS `sys_meta_region_table`;
CREATE TABLE `sys_meta_region_table` (
  `REGION_TABLE_ID_` varchar(64) NOT NULL COMMENT '关系ID',
  `REGION_ID_` varchar(64) DEFAULT NULL COMMENT '区域ID',
  `TABLE_ID_` varchar(64) DEFAULT NULL COMMENT '元数据ID',
  `SCHEMA_` varchar(64) DEFAULT NULL COMMENT '模式',
  `TABLE_CODE_` varchar(64) DEFAULT NULL COMMENT '元数据编码',
  `TABLE_TYPE_` varchar(64) NOT NULL COMMENT 'M - 模型结果表\r\n            S - 步骤结果表\r\n            B - 业务数据表',
  `OBJECT_TYPE_` varchar(64) DEFAULT NULL COMMENT '对象类型：\r\n            T - 表\r\n            V  -  视图',
  `OBJECT_SOURCE_` varchar(64) DEFAULT NULL COMMENT '对象来源：\r\n            S - 系统同步\r\n            C - 用户创建\r\n            M - 模型创建',
  `UNIT_CODE_` varchar(64) DEFAULT NULL COMMENT '机构编码',
  `UNIT_NAME_` varchar(256) DEFAULT NULL COMMENT '机构名称',
  `DEPT_CODE_` varchar(64) DEFAULT NULL COMMENT '部门编码',
  `DEPT_NAME_` varchar(256) DEFAULT NULL COMMENT '部门名称',
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '用户编码',
  `STAFF_NAME_` varchar(256) DEFAULT NULL COMMENT '用户名称',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`REGION_TABLE_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元数据区域关系表';

-- ----------------------------
-- Records of sys_meta_region_table
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_meta_relation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_meta_relation`;
CREATE TABLE `sys_meta_relation` (
  `RELATION_ID_` varchar(64) NOT NULL COMMENT '表关系ID',
  `MASTER_TABLE_ID_` varchar(64) DEFAULT NULL COMMENT '主表ID',
  `MASTER_TABLE_SCHEMA_` varchar(64) DEFAULT NULL COMMENT '主表模式',
  `MASTER_TABLE_CODE_` varchar(64) DEFAULT NULL COMMENT '主表编码',
  `MASTER_COLUMN_CODE_` varchar(64) DEFAULT NULL COMMENT '主表列编码',
  `JOIN_TABLE_ID_` varchar(64) DEFAULT NULL COMMENT '关联表ID',
  `JOIN_TABLE_SCHEMA_` varchar(64) DEFAULT NULL COMMENT '关联表模式',
  `JOIN_TABLE_CODE_` varchar(64) DEFAULT NULL COMMENT '关联表编码',
  `JOIN_COLUMN_CODE_` varchar(64) DEFAULT NULL COMMENT '关联表列编码',
  `JOIN_DETAILS_` varchar(1024) DEFAULT NULL COMMENT '关联关系',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`RELATION_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='描述元数据表之间的关联';

-- ----------------------------
-- Records of sys_meta_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_meta_table`
-- ----------------------------
DROP TABLE IF EXISTS `sys_meta_table`;
CREATE TABLE `sys_meta_table` (
  `TABLE_ID_` varchar(64) NOT NULL COMMENT '元数据ID',
  `SCHEMA_` varchar(64) DEFAULT NULL COMMENT '表所属的用户',
  `TABLE_CODE_` varchar(64) NOT NULL COMMENT '表名',
  `TABLE_NAME_` varchar(1024) DEFAULT NULL COMMENT '表中文名',
  `TABLE_TYPE_` varchar(64) NOT NULL COMMENT 'M - 模型结果表\r\n            S - 步骤结果表\r\n            B - 业务数据表',
  `OBJECT_TYPE_` varchar(64) DEFAULT NULL COMMENT '对象类型：\r\n            T - 表\r\n            V  -  视图',
  `OBJECT_SOURCE_` varchar(64) DEFAULT NULL COMMENT '对象来源：\r\n            S - 系统同步\r\n            C - 用户创建\r\n            M - 模型创建',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '10 - 草稿\r\n            30 - 已生效\r\n            50 - 已失效',
  `REMARK_` varchar(2000) DEFAULT NULL COMMENT '备注',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`TABLE_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='描述元数据的表';

-- ----------------------------
-- Records of sys_meta_table
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_meta_tablespace`
-- ----------------------------
DROP TABLE IF EXISTS `sys_meta_tablespace`;
CREATE TABLE `sys_meta_tablespace` (
  `TABLESPACE_ID_` varchar(64) NOT NULL COMMENT '表空间ID',
  `TABLESPACE_CODE_` varchar(64) NOT NULL COMMENT '表空间编码',
  `TABLESPACE_NAME_` varchar(256) DEFAULT NULL COMMENT '表空间名称',
  `TABLESPACE_DESC_` varchar(1024) DEFAULT NULL COMMENT '表空间描述',
  `DATA_FILE_LOC_` varchar(1024) DEFAULT NULL COMMENT '数据文件',
  `INIT_SIZE_` int(11) DEFAULT NULL COMMENT '初始大小(M)',
  `PAGE_SIZE_` int(11) DEFAULT NULL COMMENT '页面大小(K)',
  `AUTO_EXTEND_` varchar(64) DEFAULT NULL COMMENT '是否扩展: Y/N',
  `EXTEND_SIZE_` int(11) DEFAULT NULL COMMENT '扩展大小(M)',
  `MAX_SIZE_` int(11) DEFAULT NULL COMMENT '最大容量(M)',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT '表空间状态:\r\n            10 - 草稿\r\n            30 - 启用\r\n            50 - 停用',
  `REMARK_` varchar(1024) DEFAULT NULL COMMENT '备注',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`TABLESPACE_ID_`),
  UNIQUE KEY `U_TABLESPACE_CODE` (`TABLESPACE_CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元数据表空间';

-- ----------------------------
-- Records of sys_meta_tablespace
-- ----------------------------
