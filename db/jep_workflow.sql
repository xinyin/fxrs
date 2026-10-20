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
-- Table structure for `wf_activity_creation`
-- ----------------------------
DROP TABLE IF EXISTS `wf_activity_creation`;
CREATE TABLE `wf_activity_creation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FACTORY_NAME` varchar(255) DEFAULT NULL,
  `PROCESS_DEFINITION_ID` varchar(255) DEFAULT NULL,
  `PROCESS_INSTANCE_ID` varchar(255) DEFAULT NULL,
  `PROPERTIES_TEXT` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wf_activity_creation
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_activity_permission`
-- ----------------------------
DROP TABLE IF EXISTS `wf_activity_permission`;
CREATE TABLE `wf_activity_permission` (
  `ID` int(19) NOT NULL AUTO_INCREMENT,
  `ACTIVITY_KEY` varchar(255) DEFAULT NULL,
  `ASSIGNED_USER` varchar(255) DEFAULT NULL,
  `GRANTED_GROUPS` varchar(255) DEFAULT NULL,
  `GRANTED_USERS` varchar(255) DEFAULT NULL,
  `PROCESS_DEFINITION_ID` varchar(255) DEFAULT NULL,
  `OP_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wf_activity_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_biz_module`
-- ----------------------------
DROP TABLE IF EXISTS `wf_biz_module`;
CREATE TABLE `wf_biz_module` (
  `CODE_` varchar(64) NOT NULL,
  `NAME_` varchar(64) DEFAULT NULL,
  `REMARK_` varchar(64) DEFAULT NULL,
  `PARENT_ID_` varchar(64) DEFAULT NULL COMMENT '父配置项，暂未启用',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务模块表';

-- ----------------------------
-- Records of wf_biz_module
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_delegation`
-- ----------------------------
DROP TABLE IF EXISTS `wf_delegation`;
CREATE TABLE `wf_delegation` (
  `ID` int(19) NOT NULL AUTO_INCREMENT,
  `DELEGATED` varchar(255) DEFAULT NULL,
  `DELEGATE` varchar(255) DEFAULT NULL,
  `OP_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wf_delegation
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_form_def`
-- ----------------------------
DROP TABLE IF EXISTS `wf_form_def`;
CREATE TABLE `wf_form_def` (
  `CODE_` varchar(64) NOT NULL COMMENT '表单编码',
  `NAME_` varchar(64) DEFAULT NULL COMMENT '表单名称',
  `FORM_TYPE_` varchar(64) DEFAULT NULL COMMENT '表单属性',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT 'enable - 启用\r\n            disable - 停用',
  `REMARK_` varchar(512) DEFAULT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单定义表';

-- ----------------------------
-- Table structure for `wf_form_event`
-- ----------------------------
DROP TABLE IF EXISTS `wf_form_event`;
CREATE TABLE `wf_form_event` (
  `ID_` varchar(64) NOT NULL,
  `FORM_CODE_` varchar(64) DEFAULT NULL,
  `PROP_CODE_` varchar(64) DEFAULT NULL COMMENT '属性编码',
  `TYPE_` varchar(64) DEFAULT NULL COMMENT 'FORM - 表单事件\r\n            PROP - 属性事件',
  `NAME_` varchar(64) DEFAULT NULL COMMENT 'string, long, boolean, date',
  `SCRIPT_` longtext COMMENT 'TEXT - 单行文本\r\n            MTEXT - 多行文本\r\n            NTEXT - 数字输入\r\n            CHECK - 复选\r\n            RADIO - 单选\r\n            SELECT - 下拉选择\r\n            DATE - 日期选择\r\n            UNIT - 机构选择\r\n            STAFF - 人员选择\r\n            ATTACH - 附件',
  `REMARK_` varchar(512) DEFAULT NULL,
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
  KEY `FK_Reference_WF13` (`FORM_CODE_`),
  CONSTRAINT `FK_Reference_WF13` FOREIGN KEY (`FORM_CODE_`) REFERENCES `wf_form_def` (`CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单事件表';

-- ----------------------------
-- Records of wf_form_event
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_form_prop`
-- ----------------------------
DROP TABLE IF EXISTS `wf_form_prop`;
CREATE TABLE `wf_form_prop` (
  `ID_` varchar(64) NOT NULL,
  `FORM_CODE_` varchar(64) DEFAULT NULL,
  `PROP_CODE_` varchar(64) DEFAULT NULL COMMENT '属性编码',
  `PROP_NAME_` varchar(64) DEFAULT NULL COMMENT '属性名称',
  `PRIMARY_KEY_` varchar(64) DEFAULT NULL,
  `PROP_TYPE_` varchar(64) DEFAULT NULL COMMENT 'string, long, boolean, date',
  `INPUT_TYPE_` varchar(64) DEFAULT NULL COMMENT 'TEXT - 单行文本\r\n            MTEXT - 多行文本\r\n            NTEXT - 数字输入\r\n            CHECK - 复选\r\n            RADIO - 单选\r\n            SELECT - 下拉选择\r\n            DATE - 日期选择\r\n            UNIT - 机构选择\r\n            STAFF - 人员选择\r\n            ATTACH - 附件',
  `INPUT_CONFIG_` varchar(2000) DEFAULT NULL COMMENT '输入配置，JSON 格式，不同类型的输入有不同的配置项 ',
  `PROP_ORDER_` int(11) DEFAULT NULL,
  `REMARK_` varchar(512) DEFAULT NULL,
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
  KEY `FK_Reference_WF3` (`FORM_CODE_`),
  CONSTRAINT `FK_Reference_WF3` FOREIGN KEY (`FORM_CODE_`) REFERENCES `wf_form_def` (`CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单属性表';


-- ----------------------------
-- Table structure for `wf_his_proc_inst`
-- ----------------------------
DROP TABLE IF EXISTS `wf_his_proc_inst`;
CREATE TABLE `wf_his_proc_inst` (
  `ID_` varchar(64) NOT NULL,
  `PROCESS_INSTANT_ID_` varchar(64) DEFAULT NULL COMMENT 'activiti 中的流程实例ID',
  `BIZ_ID_` varchar(255) NOT NULL,
  `PROC_ID_` varchar(64) DEFAULT NULL,
  `PROC_CODE_` varchar(64) DEFAULT NULL,
  `PROC_NAME_` varchar(64) DEFAULT NULL,
  `PROC_VERSION_` int(11) DEFAULT NULL,
  `MODULE_CODE_` varchar(64) DEFAULT NULL,
  `MODULE_NAME_` varchar(64) DEFAULT NULL,
  `TEMPLATE_CODE_` varchar(64) DEFAULT NULL,
  `TEMPLATE_NAME_` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `PROCESS_DEF_ID_` varchar(64) DEFAULT NULL COMMENT '流程定义ID，部署流程后，由activiti回写',
  `PROCESS_DEF_KEY_` varchar(255) DEFAULT NULL COMMENT '流程定义KEY，部署流程后，由activiti回写',
  `PROCESS_DEF_NAME_` varchar(255) DEFAULT NULL COMMENT '流程定义NAME，部署流程后，由activiti回写',
  `FORM_CODE_` varchar(64) DEFAULT NULL,
  `FORM_NAME_` varchar(64) DEFAULT NULL COMMENT '表单名称',
  `TASK_ID_` varchar(64) DEFAULT NULL,
  `TASK_KEY_` varchar(255) DEFAULT NULL,
  `TASK_NAME_` varchar(255) DEFAULT NULL,
  `STARTER_CODE_` varchar(64) DEFAULT NULL,
  `STARTER_NAME_` varchar(64) DEFAULT NULL,
  `STARTER_UNIT_HIERARCHY_` varchar(64) DEFAULT NULL COMMENT '发起人机构层级',
  `STARTER_UNIT_CODE_` varchar(64) DEFAULT NULL,
  `STARTER_UNIT_NAME_` varchar(64) DEFAULT NULL,
  `STARTER_DEPT_CODE_` varchar(64) DEFAULT NULL,
  `STARTER_DEPT_NAME_` varchar(64) DEFAULT NULL,
  `START_TIME_` datetime DEFAULT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `PROCESS_TIME_` datetime DEFAULT NULL COMMENT '处理时间: 每次处理流程的时间',
  `DURATION_` bigint(20) DEFAULT NULL,
  `STATUS_` varchar(64) DEFAULT NULL COMMENT 'run - 运行中\r\n            complete - 已完成\r\n            suspend- 挂起\r\n            terminate - 终止',
  `ARCHIEVE_TIME_` datetime DEFAULT NULL,
  `SKIP_SAME_APPROVER_` varchar(64) DEFAULT NULL COMMENT '是否跳过同一审批人',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程实例历史表';

-- ----------------------------
-- Records of wf_his_proc_inst
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_his_proc_log`
-- ----------------------------
DROP TABLE IF EXISTS `wf_his_proc_log`;
CREATE TABLE `wf_his_proc_log` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `PROCESS_INSTANT_ID_` varchar(64) DEFAULT NULL COMMENT 'activiti 中的流程实例ID',
  `BIZ_ID_` varchar(255) NOT NULL,
  `PROC_INST_ID_` varchar(64) DEFAULT NULL,
  `PROC_ID_` varchar(64) DEFAULT NULL,
  `PROC_CODE_` varchar(64) DEFAULT NULL,
  `PROC_NAME_` varchar(64) DEFAULT NULL,
  `PROC_VERSION_` int(11) DEFAULT NULL,
  `MODULE_CODE_` varchar(64) DEFAULT NULL,
  `MODULE_NAME_` varchar(64) DEFAULT NULL,
  `TEMPLATE_CODE_` varchar(64) DEFAULT NULL,
  `TEMPLATE_NAME_` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `PROCESS_DEF_ID_` varchar(64) DEFAULT NULL COMMENT '流程定义ID，部署流程后，由activiti回写',
  `PROCESS_DEF_KEY_` varchar(255) DEFAULT NULL COMMENT '流程定义KEY，部署流程后，由activiti回写',
  `PROCESS_DEF_NAME_` varchar(255) DEFAULT NULL COMMENT '流程定义NAME，部署流程后，由activiti回写',
  `TASK_ID_` varchar(64) DEFAULT NULL,
  `TASK_KEY_` varchar(255) DEFAULT NULL,
  `TASK_NAME_` varchar(255) DEFAULT NULL,
  `TYPE_` varchar(64) DEFAULT NULL COMMENT 'NORMAL - 正常\r\n            ERROR - 错误',
  `ACTION_` varchar(64) DEFAULT NULL COMMENT '操作类型：\r\n            启动、任务领取、任务完成、退回、结束、增加节点、分裂',
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '申请人',
  `STAFF_NAME_` varchar(64) DEFAULT NULL COMMENT '申请姓名',
  `UNIT_CODE_` varchar(64) DEFAULT NULL COMMENT '申请人机构',
  `DEPT_CODE_` varchar(64) DEFAULT NULL,
  `DEPT_NAME_` varchar(64) DEFAULT NULL,
  `UNIT_NAME_` varchar(64) DEFAULT NULL,
  `MEMO_` varchar(1024) DEFAULT NULL COMMENT '审批意见',
  `LOG_TIME_` datetime DEFAULT NULL COMMENT '申请时间',
  `NEXT_TASK_ID_` varchar(64) DEFAULT NULL,
  `NEXT_TASK_KEY_` varchar(255) DEFAULT NULL,
  `NEXT_TASK_NAME_` varchar(255) DEFAULT NULL,
  `NEXT_STAFF_` varchar(512) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL COMMENT '处理耗时',
  `NANO_TIME_` bigint(20) DEFAULT NULL,
  `CONTENT_` varchar(1024) DEFAULT NULL,
  `ERROR_MESSAGE_` varchar(1024) DEFAULT NULL,
  `ARCHIEVE_TIME_` datetime DEFAULT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程操作日志历史表。';

-- ----------------------------
-- Records of wf_his_proc_log
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_membership`
-- ----------------------------
DROP TABLE IF EXISTS `wf_membership`;
CREATE TABLE `wf_membership` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `GROUP_ID` varchar(255) DEFAULT NULL,
  `USER_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wf_membership
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_notification`
-- ----------------------------
DROP TABLE IF EXISTS `wf_notification`;
CREATE TABLE `wf_notification` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TASK_ID` varchar(255) DEFAULT NULL,
  `OP_TIME` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wf_notification
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_auth`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_auth`;
CREATE TABLE `wf_proc_auth` (
  `ID_` varchar(64) NOT NULL,
  `PROC_ID_` varchar(64) DEFAULT NULL,
  `PROC_CODE_` varchar(64) DEFAULT NULL,
  `TEMPLATE_CODE_` varchar(64) DEFAULT NULL,
  `NODE_KEY_` varchar(64) DEFAULT NULL,
  `VAR_CODE_` varchar(64) DEFAULT NULL,
  `USER_SCOPE_` varchar(64) DEFAULT NULL COMMENT 'CUSTOM - 自定义\r\n            FORM - 表单属性',
  `AUTH_USER_NAME_` varchar(512) DEFAULT NULL COMMENT '用户名称，多个用户逗号分隔',
  `AUTH_USER_` varchar(512) DEFAULT NULL COMMENT '用户编码，多个用户逗号分隔',
  `ROLE_SCOPE_` varchar(64) DEFAULT NULL COMMENT 'ALL - 全部\r\n            UNIT - 本机构\r\n            DEPT - 本部门\r\n            HEAD - 上级主管部门\r\n            FORM - 表单属性',
  `AUTH_ROLE_NAME_` varchar(512) DEFAULT NULL COMMENT '岗位名称，多个岗位逗号分隔',
  `AUTH_ROLE_PROP_` varchar(64) DEFAULT NULL COMMENT '岗位字段名称',
  `AUTH_ROLE_` varchar(512) DEFAULT NULL COMMENT '岗位编码，多个岗位逗号分隔',
  `BIZ_AUTH_CODE_`   varchar(64) DEFAULT NULL COMMENT '用户自定义方法编码',
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
  KEY `FK_Reference_WF8` (`PROC_ID_`),
  CONSTRAINT `FK_Reference_WF8` FOREIGN KEY (`PROC_ID_`) REFERENCES `wf_proc_def` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流程节点授权表';

-- ----------------------------
-- Records of wf_proc_auth
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_data_log`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_data_log`;
CREATE TABLE `wf_proc_data_log` (
  `ID_` varchar(64) NOT NULL,
  `PROCESS_INSTANT_ID_` varchar(64) DEFAULT NULL COMMENT 'activiti 中的流程实例ID',
  `BIZ_ID_` varchar(255) NOT NULL,
  `PROC_ID_` varchar(64) DEFAULT NULL,
  `PROC_CODE_` varchar(64) DEFAULT NULL,
  `PROC_NAME_` varchar(64) DEFAULT NULL,
  `PROC_VERSION_` int(11) DEFAULT NULL,
  `PROCESS_DEF_ID_` varchar(64) DEFAULT NULL COMMENT '流程定义ID，部署流程后，由activiti回写',
  `PROCESS_DEF_KEY_` varchar(255) DEFAULT NULL COMMENT '流程定义KEY，部署流程后，由activiti回写',
  `PROCESS_DEF_NAME_` varchar(255) DEFAULT NULL COMMENT '流程定义NAME，部署流程后，由activiti回写',
  `TASK_ID_` varchar(64) DEFAULT NULL,
  `TASK_KEY_` varchar(255) DEFAULT NULL,
  `TASK_NAME_` varchar(255) DEFAULT NULL,
  `PROP_CODE_` varchar(64) DEFAULT NULL,
  `OLD_VALUE_` varchar(1024) DEFAULT NULL,
  `NEW_VALUE_` varchar(1024) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) DEFAULT NULL,
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '申请人',
  `STAFF_NAME_` varchar(64) DEFAULT NULL COMMENT '申请姓名',
  `UNIT_CODE_` varchar(64) DEFAULT NULL COMMENT '申请人机构',
  `DEPT_CODE_` varchar(64) DEFAULT NULL,
  `DEPT_NAME_` varchar(64) DEFAULT NULL,
  `UNIT_NAME_` varchar(64) DEFAULT NULL,
  `CHANGE_TIME_` datetime DEFAULT NULL,
  `REMARK_` varchar(1024) DEFAULT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程数据变动日志表';

-- ----------------------------
-- Records of wf_proc_data_log
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_def`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_def`;
CREATE TABLE `wf_proc_def` (
  `ID_` varchar(64) NOT NULL,
  `CODE_` varchar(64) NOT NULL,
  `NAME_` varchar(128) DEFAULT NULL,
  `MODULE_CODE_` varchar(64) DEFAULT NULL,
  `MODULE_NAME_` varchar(128) DEFAULT NULL,
  `PROC_TEMPLATE_CODE_` varchar(64) DEFAULT NULL,
  `PROC_TEMPLATE_NAME_` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `PROCESS_DEF_ID_` varchar(64) DEFAULT NULL COMMENT '流程定义ID，部署流程后，由activiti回写',
  `PROCESS_DEF_KEY_` varchar(255) DEFAULT NULL COMMENT '流程定义KEY，部署流程后，由activiti回写',
  `PROCESS_DEF_NAME_` varchar(255) DEFAULT NULL COMMENT '流程定义NAME，部署流程后，由activiti回写',
  `FORM_CODE_` varchar(64) DEFAULT NULL,
  `FORM_NAME_` varchar(128) DEFAULT NULL COMMENT '表单名称',
  `FORM_TYPE_` varchar(64) DEFAULT NULL COMMENT '表单类型',
  `PROC_VERSION_` int(11) DEFAULT NULL,
  `PROC_VERSION_STATUS_` varchar(10) DEFAULT NULL COMMENT 'D - 草稿\r\n            H - 历史\r\n            C - 当前版本',
  `SKIP_SAME_APPROVER_` varchar(64) DEFAULT NULL COMMENT '是否跳过同一审批人',
  `CLIENT_URL_` varchar(256) DEFAULT NULL COMMENT '客户端地址',
  `CLIENT_PORT_` varchar(64) DEFAULT NULL COMMENT '客户端端口',
  `CLIENT_CONTEXT_` varchar(256) DEFAULT NULL COMMENT '客户端上下文',
  `STATUS_` varchar(10) DEFAULT NULL COMMENT 'enable - 启用\r\n            disable - 停用',
  `REMARK_` varchar(256) DEFAULT NULL,
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
  KEY `FK_Reference_WF4` (`PROC_TEMPLATE_CODE_`),
  KEY `FK_Reference_WF5` (`FORM_CODE_`),
  KEY `FK_Reference_WF6` (`MODULE_CODE_`),
  CONSTRAINT `FK_Reference_WF4` FOREIGN KEY (`PROC_TEMPLATE_CODE_`) REFERENCES `wf_proc_template` (`CODE_`),
  CONSTRAINT `FK_Reference_WF5` FOREIGN KEY (`FORM_CODE_`) REFERENCES `wf_form_def` (`CODE_`),
  CONSTRAINT `FK_Reference_WF6` FOREIGN KEY (`MODULE_CODE_`) REFERENCES `wf_biz_module` (`CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流程定义表';

-- ----------------------------
-- Records of wf_proc_def
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_form_cfg`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_form_cfg`;
CREATE TABLE `wf_proc_form_cfg` (
  `ID_` varchar(64) NOT NULL,
  `PROC_ID_` varchar(64) DEFAULT NULL,
  `PROC_CODE_` varchar(64) DEFAULT NULL,
  `FORM_CODE_` varchar(64) DEFAULT NULL COMMENT '表单编码',
  `FORM_NAME_` varchar(64) DEFAULT NULL COMMENT '表单名称',
  `HEAD_` varchar(64) DEFAULT NULL,
  `NODE_KEY_` varchar(64) DEFAULT NULL,
  `FORM_CONFIG_` varchar(4000) DEFAULT NULL COMMENT '保存每个表单属性在每个任务节点上的可读、可写及是否必须等配置。',
  `REMARK_` varchar(512) DEFAULT NULL,
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
  KEY `FK_Reference_WF7` (`PROC_ID_`),
  CONSTRAINT `FK_Reference_WF7` FOREIGN KEY (`PROC_ID_`) REFERENCES `wf_proc_def` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流程表单配置表';

-- ----------------------------
-- Records of wf_proc_form_cfg
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_inst`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_inst`;
CREATE TABLE `wf_proc_inst` (
  `ID_` varchar(64) NOT NULL,
  `PROCESS_INSTANT_ID_` varchar(64) DEFAULT NULL COMMENT 'activiti 中的流程实例ID',
  `BIZ_ID_` varchar(255) NOT NULL,
  `PROC_ID_` varchar(64) DEFAULT NULL,
  `PROC_CODE_` varchar(64) DEFAULT NULL,
  `PROC_NAME_` varchar(64) DEFAULT NULL,
  `PROC_VERSION_` int(11) DEFAULT NULL,
  `MODULE_CODE_` varchar(64) DEFAULT NULL,
  `MODULE_NAME_` varchar(64) DEFAULT NULL,
  `TEMPLATE_CODE_` varchar(64) DEFAULT NULL,
  `TEMPLATE_NAME_` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `PROCESS_DEF_ID_` varchar(64) DEFAULT NULL COMMENT '流程定义ID，部署流程后，由activiti回写',
  `PROCESS_DEF_KEY_` varchar(255) DEFAULT NULL COMMENT '流程定义KEY，部署流程后，由activiti回写',
  `PROCESS_DEF_NAME_` varchar(255) DEFAULT NULL COMMENT '流程定义NAME，部署流程后，由activiti回写',
  `FORM_CODE_` varchar(64) DEFAULT NULL,
  `FORM_NAME_` varchar(64) DEFAULT NULL COMMENT '表单名称',
  `TASK_ID_` varchar(64) DEFAULT NULL,
  `TASK_KEY_` varchar(255) DEFAULT NULL,
  `TASK_NAME_` varchar(255) DEFAULT NULL,
  `STARTER_CODE_` varchar(64) DEFAULT NULL,
  `STARTER_NAME_` varchar(64) DEFAULT NULL,
  `STARTER_UNIT_HIERARCHY_` varchar(64) DEFAULT NULL COMMENT '发起人机构层级',
  `STARTER_UNIT_CODE_` varchar(64) DEFAULT NULL,
  `STARTER_UNIT_NAME_` varchar(64) DEFAULT NULL,
  `STARTER_DEPT_CODE_` varchar(64) DEFAULT NULL,
  `STARTER_DEPT_NAME_` varchar(64) DEFAULT NULL,
  `START_TIME_` datetime DEFAULT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `PROCESS_TIME_` datetime DEFAULT NULL COMMENT '处理时间: 每次处理流程的时间',
  `DURATION_` bigint(20) DEFAULT NULL,
  `STATUS_` varchar(64) DEFAULT NULL COMMENT 'draft - 草稿\r\n            run - 运行中\r\n            complete - 已完成\r\n            suspend- 挂起\r\n            terminate - 终止',
  `NOTIFY_STATUS_` varchar(64) DEFAULT NULL COMMENT 'WAITING - 待通知\r\n            NOTIFYING - 通知中\r\n            FAILED - 失败\r\n            SUCCESS - 成功',
  `NOTIFY_RETRY_TIMES_` int(11) DEFAULT NULL,
  `SKIP_SAME_APPROVER_` varchar(64) DEFAULT NULL COMMENT '是否跳过同一审批人',
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
  KEY `FK_Reference_WF11` (`PROC_ID_`),
  CONSTRAINT `FK_Reference_WF11` FOREIGN KEY (`PROC_ID_`) REFERENCES `wf_proc_def` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程实例表';

-- ----------------------------
-- Records of wf_proc_inst
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_inst_data`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_inst_data`;
CREATE TABLE `wf_proc_inst_data` (
  `PROC_INST_ID_` varchar(64) NOT NULL,
  `BIZ_ID_` varchar(255) DEFAULT NULL,
  `OLD_FORM_DATA_` longtext,
  `FORM_DATA_` longtext,
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
  PRIMARY KEY (`PROC_INST_ID_`),
  CONSTRAINT `FK_Reference_WF12` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `wf_proc_inst` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程数据表';

-- ----------------------------
-- Records of wf_proc_inst_data
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_inst_data_notify`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_inst_data_notify`;
CREATE TABLE `wf_proc_inst_data_notify` (
  `PROC_INST_ID_` varchar(64) NOT NULL,
  `BIZ_ID_` varchar(255) DEFAULT NULL,
  `PROC_CODE_` varchar(64) DEFAULT NULL COMMENT '业务流程编码',
  `STATUS_` varchar(64) DEFAULT NULL COMMENT 'UNLOCK: 未锁定\r\n            LOCK: 锁定',
  `LOCK_TIME_` datetime DEFAULT NULL,
  `LOCK_DURATION_` bigint(20) DEFAULT NULL,
  `LOCK_EXPIRE_TIME_` datetime DEFAULT NULL,
  `NOTIFY_TIMES_` int(11) DEFAULT NULL COMMENT '通知次数',
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
  PRIMARY KEY (`PROC_INST_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wf_proc_inst_data_notify
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_inst_data_notify_error`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_inst_data_notify_error`;
CREATE TABLE `wf_proc_inst_data_notify_error` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `PROC_INST_ID_` varchar(64) NOT NULL COMMENT '流程实例ID',
  `BIZ_ID_` varchar(255) DEFAULT NULL COMMENT '业务主键',
  `PROC_CODE_` varchar(64) DEFAULT NULL COMMENT '业务流程编码',
  `NOTIFY_TIME_` datetime DEFAULT NULL COMMENT '通知时间',
  `NOTIFY_ERROR_TIMES_` int(11) DEFAULT NULL COMMENT '通知次数',
  `ERROR_CONTENT_` longtext COMMENT '错误内容',
  `ACTION_` varchar(64) DEFAULT NULL COMMENT '动作: 通知客户端、 客户端取数、 客户端最终反馈',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程数据通知错误表';

-- ----------------------------
-- Records of wf_proc_inst_data_notify_error
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_log`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_log`;
CREATE TABLE `wf_proc_log` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `PROCESS_INSTANT_ID_` varchar(64) DEFAULT NULL COMMENT 'activiti 中的流程实例ID',
  `BIZ_ID_` varchar(255) NOT NULL,
  `PROC_INST_ID_` varchar(64) DEFAULT NULL,
  `PROC_ID_` varchar(64) DEFAULT NULL,
  `PROC_CODE_` varchar(64) DEFAULT NULL,
  `PROC_NAME_` varchar(64) DEFAULT NULL,
  `PROC_VERSION_` int(11) DEFAULT NULL,
  `MODULE_CODE_` varchar(64) DEFAULT NULL,
  `MODULE_NAME_` varchar(64) DEFAULT NULL,
  `TEMPLATE_CODE_` varchar(64) DEFAULT NULL,
  `TEMPLATE_NAME_` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `PROCESS_DEF_ID_` varchar(64) DEFAULT NULL COMMENT '流程定义ID，部署流程后，由activiti回写',
  `PROCESS_DEF_KEY_` varchar(255) DEFAULT NULL COMMENT '流程定义KEY，部署流程后，由activiti回写',
  `PROCESS_DEF_NAME_` varchar(255) DEFAULT NULL COMMENT '流程定义NAME，部署流程后，由activiti回写',
  `TASK_ID_` varchar(64) DEFAULT NULL,
  `TASK_KEY_` varchar(255) DEFAULT NULL,
  `TASK_NAME_` varchar(255) DEFAULT NULL,
  `ACTION_` varchar(64) DEFAULT NULL COMMENT '操作类型：增删改',
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '申请人',
  `STAFF_NAME_` varchar(64) DEFAULT NULL COMMENT '申请姓名',
  `UNIT_CODE_` varchar(64) DEFAULT NULL COMMENT '申请人机构',
  `DEPT_CODE_` varchar(64) DEFAULT NULL,
  `DEPT_NAME_` varchar(64) DEFAULT NULL,
  `UNIT_NAME_` varchar(64) DEFAULT NULL,
  `MEMO_` varchar(1024) DEFAULT NULL COMMENT '审批意见',
  `LOG_TIME_` datetime DEFAULT NULL COMMENT '申请时间',
  `NEXT_TASK_ID_` varchar(64) DEFAULT NULL,
  `NEXT_TASK_KEY_` varchar(255) DEFAULT NULL,
  `NEXT_TASK_NAME_` varchar(255) DEFAULT NULL,
  `NEXT_STAFF_` varchar(512) DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL COMMENT '处理耗时',
  `NANO_TIME_` bigint(20) DEFAULT NULL,
  `CONTENT_` varchar(1024) DEFAULT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程操作日志表。该业务信息在数据进入工作流引擎时传过来。在工作流引擎运行期间使用。';

-- ----------------------------
-- Records of wf_proc_log
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_node`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_node`;
CREATE TABLE `wf_proc_node` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `TEMPLATE_CODE_` varchar(64) DEFAULT NULL,
  `DEFINITION_ID_` varchar(64) DEFAULT NULL COMMENT '模板ID',
  `DEFINITION_KEY_` varchar(64) DEFAULT NULL COMMENT '模板KEY',
  `DEFINITION_NAME_` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `NAME_` varchar(64) DEFAULT NULL COMMENT '节点名称',
  `KEY_` varchar(64) DEFAULT NULL COMMENT '节点ID',
  `TYPE_` varchar(64) DEFAULT NULL COMMENT '节点类型:\r\n            STARTEVENT\r\n            ENDEVENT\r\n            USERTASK\r\n            FLOW',
  `HEAD_` varchar(64) DEFAULT NULL,
  `HEIGHT_` double DEFAULT NULL COMMENT '高度，对于startevent和endevent，记录半径；对于flow为0',
  `WIDTH_` double DEFAULT NULL COMMENT '宽度',
  `START_X_` double DEFAULT NULL COMMENT 'X坐标',
  `START_Y_` double DEFAULT NULL COMMENT 'Y坐标',
  `WAY_POINTS_` varchar(1024) DEFAULT NULL COMMENT '连接点',
  `REMARK_` varchar(1024) DEFAULT NULL,
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
  KEY `FK_Reference_WF1` (`TEMPLATE_CODE_`),
  CONSTRAINT `FK_Reference_WF1` FOREIGN KEY (`TEMPLATE_CODE_`) REFERENCES `wf_proc_template` (`CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程节点信息，流程模板部署时读取到数据库中。';

-- ----------------------------
-- Records of wf_proc_node
-- ----------------------------

DROP TABLE IF EXISTS `wf_proc_auto_transfer`;
CREATE TABLE `wf_proc_auto_transfer` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '用户编码',
  `STAFF_NAME_` varchar(128) DEFAULT NULL COMMENT '用户名称',
  `TRANSFER_STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '转办用户编码',
  `TRANSFER_STAFF_NAME_` varchar(128) DEFAULT NULL COMMENT '转办用户名称',
  `START_TIME_` date DEFAULT NULL COMMENT '开始时间',
  `END_TIME_` date DEFAULT NULL COMMENT '结束时间',
  `IS_INCLUDE_ONESELF_` varchar(64) DEFAULT NULL COMMENT '是否自己能处理',
  `REMARK_` varchar(512) DEFAULT NULL COMMENT '备注',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程自动转办定义';


DROP TABLE IF EXISTS `wf_proc_auto_transfer_inst`;
CREATE TABLE `wf_proc_auto_transfer_inst` (
  `ID_` varchar(64) NOT NULL,
  `PROCESS_INSTANT_ID_` varchar(255) DEFAULT NULL COMMENT 'activiti 中的流程实例ID',
  `BIZ_ID_` varchar(255) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) DEFAULT NULL,
  `TASK_ID_` varchar(255) DEFAULT NULL,
  `STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '用户编码',
  `STAFF_NAME_` varchar(128) DEFAULT NULL COMMENT '用户名称',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存放自动转办的数据，用于判断当前用户处理时，是否是转办';

-- ----------------------------
-- Table structure for `wf_proc_notify`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_notify`;
CREATE TABLE `wf_proc_notify` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `PROC_ID_` varchar(64) DEFAULT NULL,
  `PROC_CODE_` varchar(64) DEFAULT NULL,
  `NODE_KEY_` varchar(64) DEFAULT NULL,
  `EVENT_TYPE_` varchar(64) DEFAULT NULL COMMENT '事件类型:\r\n            processStart 流程启动\r\n            processEnd       流程结束\r\n            processReject  流程回退',
  `TYPE_` varchar(64) DEFAULT NULL COMMENT 'LISTENER - 监听器\r\n            RESTFUL - restful 接口',
  `TARGET_` varchar(512) DEFAULT NULL COMMENT '监听器: java类',
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
  KEY `FK_Reference_WF10` (`PROC_ID_`),
  CONSTRAINT `FK_Reference_WF10` FOREIGN KEY (`PROC_ID_`) REFERENCES `wf_proc_def` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流程事件通知配置表';

-- ----------------------------
-- Records of wf_proc_notify
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_template`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_template`;
CREATE TABLE `wf_proc_template` (
  `CODE_` varchar(64) NOT NULL COMMENT '模板编码',
  `NAME_` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `PROCESS_DEF_ID_` varchar(64) DEFAULT NULL COMMENT '流程定义ID，部署流程后，由activiti回写',
  `PROCESS_DEF_KEY_` varchar(255) DEFAULT NULL COMMENT '流程定义KEY，部署流程后，由activiti回写',
  `PROCESS_DEF_NAME_` varchar(255) DEFAULT NULL COMMENT '流程定义NAME，部署流程后，由activiti回写',
  `PROCESS_DEP_ID_` varchar(64) DEFAULT NULL COMMENT '流程发布ID',
  `MODEL_ID_` varchar(64) DEFAULT NULL,
  `STATUS_` varchar(64) DEFAULT NULL COMMENT 'enable - 启用\r\n            disable - 停用',
  `REMARK_` varchar(512) DEFAULT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程模板';

-- ----------------------------
-- Table structure for `wf_proc_variable`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_variable`;
CREATE TABLE `wf_proc_variable` (
  `ID_` varchar(64) NOT NULL,
  `TEMPLATE_CODE_` varchar(64) DEFAULT NULL,
  `DEFINITION_ID_` varchar(64) DEFAULT NULL COMMENT '模板ID',
  `DEFINITION_KEY_` varchar(64) DEFAULT NULL COMMENT '模板KEY',
  `DEFINITION_NAME_` varchar(64) DEFAULT NULL COMMENT '模板名称',
  `NODE_NAME_` varchar(255) DEFAULT NULL COMMENT '节点名称',
  `NODE_KEY_` varchar(255) DEFAULT NULL COMMENT '节点ID',
  `NODE_TYPE_` varchar(64) DEFAULT NULL COMMENT '节点类型:\r\n            STARTEVENT\r\n            ENDEVENT\r\n            USERTASK\r\n            FLOW',
  `CODE_` varchar(64) DEFAULT NULL,
  `TYPE_` varchar(64) DEFAULT NULL COMMENT 'candidate\r\n            condiftion',
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
  KEY `FK_Reference_WF2` (`TEMPLATE_CODE_`),
  CONSTRAINT `FK_Reference_WF2` FOREIGN KEY (`TEMPLATE_CODE_`) REFERENCES `wf_proc_template` (`CODE_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程模板变量表';

-- ----------------------------
-- Records of wf_proc_variable
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_proc_var_map`
-- ----------------------------
DROP TABLE IF EXISTS `wf_proc_var_map`;
CREATE TABLE `wf_proc_var_map` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `PROC_ID_` varchar(64) DEFAULT NULL,
  `PROC_CODE_` varchar(64) DEFAULT NULL,
  `NODE_NAME_` varchar(255) DEFAULT NULL COMMENT '节点名称',
  `NODE_KEY_` varchar(255) DEFAULT NULL COMMENT '节点ID',
  `NODE_TYPE_` varchar(64) DEFAULT NULL COMMENT '节点类型:\r\n            STARTEVENT\r\n            ENDEVENT\r\n            USERTASK\r\n            FLOW',
  `TYPE_` varchar(64) DEFAULT NULL COMMENT 'candidate\r\n            condiftion',
  `VAR_CODE_` varchar(64) DEFAULT NULL,
  `PROP_CODE_` varchar(64) DEFAULT NULL,
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
  KEY `FK_Reference_WF9` (`PROC_ID_`),
  CONSTRAINT `FK_Reference_WF9` FOREIGN KEY (`PROC_ID_`) REFERENCES `wf_proc_def` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务流程变量映射表';

-- ----------------------------
-- Records of wf_proc_var_map
-- ----------------------------

-- ----------------------------
-- Table structure for `wf_user`
-- ----------------------------
DROP TABLE IF EXISTS `wf_user`;
CREATE TABLE `wf_user` (
  `USER_ID` varchar(255) NOT NULL DEFAULT '',
  `EMAIL` varchar(255) DEFAULT NULL,
  `NICK_NAME` varchar(255) DEFAULT NULL,
  `MOBILE_PHONE_NUMBER` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wf_user
-- ----------------------------

    
drop table if exists ACT_GE_PROPERTY;
drop table if exists ACT_RU_VARIABLE;
drop table if exists ACT_GE_BYTEARRAY;
drop table if exists ACT_RE_DEPLOYMENT;
drop table if exists ACT_RE_MODEL;
drop table if exists ACT_RU_IDENTITYLINK;
drop table if exists ACT_RU_TASK;

drop table if exists ACT_RU_EXECUTION;
drop table if exists ACT_RU_JOB; 
drop table if exists ACT_RU_EVENT_SUBSCR;
drop table if exists ACT_EVT_LOG;
drop table if exists ACT_PROCDEF_INFO;
drop table if exists ACT_RE_PROCDEF;


drop table if exists ACT_HI_PROCINST;
drop table if exists ACT_HI_ACTINST;
drop table if exists ACT_HI_VARINST;
drop table if exists ACT_HI_TASKINST;
drop table if exists ACT_HI_DETAIL;
drop table if exists ACT_HI_COMMENT;
drop table if exists ACT_HI_ATTACHMENT;
drop table if exists ACT_HI_IDENTITYLINK;

drop table if exists ACT_ID_INFO;
drop table if exists ACT_ID_MEMBERSHIP;
drop table if exists ACT_ID_GROUP;
drop table if exists ACT_ID_USER;
create table ACT_GE_PROPERTY (
    NAME_ varchar(64),
    VALUE_ varchar(300),
    REV_ integer,
    primary key (NAME_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

insert into ACT_GE_PROPERTY
values ('schema.version', '5.22.0.0', 1);

insert into ACT_GE_PROPERTY
values ('schema.history', 'create(5.22.0.0)', 1);

insert into ACT_GE_PROPERTY
values ('next.dbid', '1', 1);

create table ACT_GE_BYTEARRAY (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    DEPLOYMENT_ID_ varchar(64),
    BYTES_ LONGBLOB,
    GENERATED_ TINYINT,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RE_DEPLOYMENT (
    ID_ varchar(64),
    NAME_ varchar(255),
    CATEGORY_ varchar(255),
    TENANT_ID_ varchar(255) default '',
    DEPLOY_TIME_ timestamp(3) NULL,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RE_MODEL (
    ID_ varchar(64) not null,
    REV_ integer,
    NAME_ varchar(255),
    KEY_ varchar(255),
    CATEGORY_ varchar(255),
    CREATE_TIME_ timestamp(3) null,
    LAST_UPDATE_TIME_ timestamp(3) null,
    VERSION_ integer,
    META_INFO_ varchar(4000),
    DEPLOYMENT_ID_ varchar(64),
    EDITOR_SOURCE_VALUE_ID_ varchar(64),
    EDITOR_SOURCE_EXTRA_VALUE_ID_ varchar(64),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_EXECUTION (
    ID_ varchar(64),
    REV_ integer,
    PROC_INST_ID_ varchar(64),
    BUSINESS_KEY_ varchar(255),
    PARENT_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    SUPER_EXEC_ varchar(64),
    ACT_ID_ varchar(255),
    IS_ACTIVE_ TINYINT,
    IS_CONCURRENT_ TINYINT,
    IS_SCOPE_ TINYINT,
    IS_EVENT_SCOPE_ TINYINT,
    SUSPENSION_STATE_ integer,
    CACHED_ENT_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    NAME_ varchar(255),
    LOCK_TIME_ timestamp(3) NULL,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_JOB (
    ID_ varchar(64) NOT NULL,
    REV_ integer,
    TYPE_ varchar(255) NOT NULL,
    LOCK_EXP_TIME_ timestamp(3) NULL,
    LOCK_OWNER_ varchar(255),
    EXCLUSIVE_ boolean,
    EXECUTION_ID_ varchar(64),
    PROCESS_INSTANCE_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    RETRIES_ integer,
    EXCEPTION_STACK_ID_ varchar(64),
    EXCEPTION_MSG_ varchar(4000),
    DUEDATE_ timestamp(3) NULL,
    REPEAT_ varchar(255),
    HANDLER_TYPE_ varchar(255),
    HANDLER_CFG_ varchar(4000),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RE_PROCDEF (
    ID_ varchar(64) not null,
    REV_ integer,
    CATEGORY_ varchar(255),
    NAME_ varchar(255),
    KEY_ varchar(255) not null,
    VERSION_ integer not null,
    DEPLOYMENT_ID_ varchar(64),
    RESOURCE_NAME_ varchar(4000),
    DGRM_RESOURCE_NAME_ varchar(4000),
    DESCRIPTION_ varchar(4000),
    HAS_START_FORM_KEY_ TINYINT,
    HAS_GRAPHICAL_NOTATION_ TINYINT,
    SUSPENSION_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_TASK (
    ID_ varchar(64),
    REV_ integer,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    TASK_DEF_KEY_ varchar(255),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    DELEGATION_ varchar(64),
    PRIORITY_ integer,
    CREATE_TIME_ timestamp(3) NULL,
    DUE_DATE_ datetime(3),
    CATEGORY_ varchar(255),
    SUSPENSION_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    FORM_KEY_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_IDENTITYLINK (
    ID_ varchar(64),
    REV_ integer,
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),    
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_VARIABLE (
    ID_ varchar(64) not null,
    REV_ integer,
    TYPE_ varchar(255) not null,
    NAME_ varchar(255) not null,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    TASK_ID_ varchar(64),
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_EVENT_SUBSCR (
    ID_ varchar(64) not null,
    REV_ integer,
    EVENT_TYPE_ varchar(255) not null,
    EVENT_NAME_ varchar(255),
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTIVITY_ID_ varchar(64),
    CONFIGURATION_ varchar(255),
    CREATED_ timestamp(3) not null DEFAULT CURRENT_TIMESTAMP(3),
    PROC_DEF_ID_ varchar(64),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_EVT_LOG (
    LOG_NR_ bigint auto_increment,
    TYPE_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    TIME_STAMP_ timestamp(3) not null,
    USER_ID_ varchar(255),
    DATA_ LONGBLOB,
    LOCK_OWNER_ varchar(255),
    LOCK_TIME_ timestamp(3) null,
    IS_PROCESSED_ tinyint default 0,
    primary key (LOG_NR_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_PROCDEF_INFO (
	ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64) not null,
    REV_ integer,
    INFO_JSON_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create index ACT_IDX_EXEC_BUSKEY on ACT_RU_EXECUTION(BUSINESS_KEY_);
create index ACT_IDX_TASK_CREATE on ACT_RU_TASK(CREATE_TIME_);
create index ACT_IDX_IDENT_LNK_USER on ACT_RU_IDENTITYLINK(USER_ID_);
create index ACT_IDX_IDENT_LNK_GROUP on ACT_RU_IDENTITYLINK(GROUP_ID_);
create index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR(CONFIGURATION_);
create index ACT_IDX_VARIABLE_TASK_ID on ACT_RU_VARIABLE(TASK_ID_);
create index ACT_IDX_ATHRZ_PROCEDEF on ACT_RU_IDENTITYLINK(PROC_DEF_ID_);
create index ACT_IDX_INFO_PROCDEF on ACT_PROCDEF_INFO(PROC_DEF_ID_);

alter table ACT_GE_BYTEARRAY
    add constraint ACT_FK_BYTEARR_DEPL
    foreign key (DEPLOYMENT_ID_)
    references ACT_RE_DEPLOYMENT (ID_);

alter table ACT_RE_PROCDEF
    add constraint ACT_UNIQ_PROCDEF
    unique (KEY_,VERSION_, TENANT_ID_);
    
alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PROCINST 
    foreign key (PROC_INST_ID_) 
    references ACT_RU_EXECUTION (ID_) on delete cascade on update cascade;

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PARENT 
    foreign key (PARENT_ID_) 
    references ACT_RU_EXECUTION (ID_);
    
alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_SUPER 
    foreign key (SUPER_EXEC_) 
    references ACT_RU_EXECUTION (ID_);

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PROCDEF 
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF (ID_);
    
alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_TSKASS_TASK 
    foreign key (TASK_ID_) 
    references ACT_RU_TASK (ID_);
    
alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_ATHRZ_PROCEDEF 
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF(ID_);
    
alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_IDL_PROCINST
    foreign key (PROC_INST_ID_) 
    references ACT_RU_EXECUTION (ID_);       
    
alter table ACT_RU_TASK
    add constraint ACT_FK_TASK_EXE
    foreign key (EXECUTION_ID_)
    references ACT_RU_EXECUTION (ID_);
    
alter table ACT_RU_TASK
    add constraint ACT_FK_TASK_PROCINST
    foreign key (PROC_INST_ID_)
    references ACT_RU_EXECUTION (ID_);
    
alter table ACT_RU_TASK
  	add constraint ACT_FK_TASK_PROCDEF
  	foreign key (PROC_DEF_ID_)
  	references ACT_RE_PROCDEF (ID_);
  
alter table ACT_RU_VARIABLE 
    add constraint ACT_FK_VAR_EXE 
    foreign key (EXECUTION_ID_) 
    references ACT_RU_EXECUTION (ID_);

alter table ACT_RU_VARIABLE
    add constraint ACT_FK_VAR_PROCINST
    foreign key (PROC_INST_ID_)
    references ACT_RU_EXECUTION(ID_);

alter table ACT_RU_VARIABLE 
    add constraint ACT_FK_VAR_BYTEARRAY 
    foreign key (BYTEARRAY_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RU_JOB 
    add constraint ACT_FK_JOB_EXCEPTION 
    foreign key (EXCEPTION_STACK_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RU_EVENT_SUBSCR
    add constraint ACT_FK_EVENT_EXEC
    foreign key (EXECUTION_ID_)
    references ACT_RU_EXECUTION(ID_);
    
alter table ACT_RE_MODEL
    add constraint ACT_FK_MODEL_SOURCE
    foreign key (EDITOR_SOURCE_VALUE_ID_)
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RE_MODEL
    add constraint ACT_FK_MODEL_SOURCE_EXTRA
    foreign key (EDITOR_SOURCE_EXTRA_VALUE_ID_)
    references ACT_GE_BYTEARRAY (ID_);
    
alter table ACT_RE_MODEL
    add constraint ACT_FK_MODEL_DEPLOYMENT
    foreign key (DEPLOYMENT_ID_)
    references ACT_RE_DEPLOYMENT (ID_);

alter table ACT_PROCDEF_INFO 
    add constraint ACT_FK_INFO_JSON_BA 
    foreign key (INFO_JSON_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_PROCDEF_INFO 
    add constraint ACT_FK_INFO_PROCDEF 
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF (ID_);
    
alter table ACT_PROCDEF_INFO
    add constraint ACT_UNIQ_INFO_PROCDEF
    unique (PROC_DEF_ID_);
    create table ACT_HI_PROCINST (
    ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64) not null,
    BUSINESS_KEY_ varchar(255),
    PROC_DEF_ID_ varchar(64) not null,
    START_TIME_ datetime(3) not null,
    END_TIME_ datetime(3),
    DURATION_ bigint,
    START_USER_ID_ varchar(255),
    START_ACT_ID_ varchar(255),
    END_ACT_ID_ varchar(255),
    SUPER_PROCESS_INSTANCE_ID_ varchar(64),
    DELETE_REASON_ varchar(4000),
    TENANT_ID_ varchar(255) default '',
    NAME_ varchar(255),
    primary key (ID_),
    unique (PROC_INST_ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_ACTINST (
    ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64) not null,
    EXECUTION_ID_ varchar(64) not null,
    ACT_ID_ varchar(255) not null,
    TASK_ID_ varchar(64),
    CALL_PROC_INST_ID_ varchar(64),
    ACT_NAME_ varchar(255),
    ACT_TYPE_ varchar(255) not null,
    ASSIGNEE_ varchar(255),
    START_TIME_ datetime(3) not null,
    END_TIME_ datetime(3),
    DURATION_ bigint,
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_TASKINST (
    ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64),
    TASK_DEF_KEY_ varchar(255),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    START_TIME_ datetime(3) not null,
    CLAIM_TIME_ datetime(3),
    END_TIME_ datetime(3),
    DURATION_ bigint,
    DELETE_REASON_ varchar(4000),
    PRIORITY_ integer,
    DUE_DATE_ datetime(3),
    FORM_KEY_ varchar(255),
    CATEGORY_ varchar(255),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_VARINST (
    ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    NAME_ varchar(255) not null,
    VAR_TYPE_ varchar(100),
    REV_ integer,
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    CREATE_TIME_ datetime(3),
    LAST_UPDATED_TIME_ datetime(3),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_DETAIL (
    ID_ varchar(64) not null,
    TYPE_ varchar(255) not null,
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    ACT_INST_ID_ varchar(64),
    NAME_ varchar(255) not null,
    VAR_TYPE_ varchar(255),
    REV_ integer,
    TIME_ datetime(3) not null,
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_COMMENT (
    ID_ varchar(64) not null,
    TYPE_ varchar(255),
    TIME_ datetime(3) not null,
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTION_ varchar(255),
    MESSAGE_ varchar(4000),
    FULL_MSG_ LONGBLOB,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_ATTACHMENT (
    ID_ varchar(64) not null,
    REV_ integer,
    USER_ID_ varchar(255),
    NAME_ varchar(255),
    DESCRIPTION_ varchar(4000),
    TYPE_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    URL_ varchar(4000),
    CONTENT_ID_ varchar(64),
    TIME_ datetime(3),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_IDENTITYLINK (
    ID_ varchar(64),
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


create index ACT_IDX_HI_PRO_INST_END on ACT_HI_PROCINST(END_TIME_);
create index ACT_IDX_HI_PRO_I_BUSKEY on ACT_HI_PROCINST(BUSINESS_KEY_);
create index ACT_IDX_HI_ACT_INST_START on ACT_HI_ACTINST(START_TIME_);
create index ACT_IDX_HI_ACT_INST_END on ACT_HI_ACTINST(END_TIME_);
create index ACT_IDX_HI_DETAIL_PROC_INST on ACT_HI_DETAIL(PROC_INST_ID_);
create index ACT_IDX_HI_DETAIL_ACT_INST on ACT_HI_DETAIL(ACT_INST_ID_);
create index ACT_IDX_HI_DETAIL_TIME on ACT_HI_DETAIL(TIME_);
create index ACT_IDX_HI_DETAIL_NAME on ACT_HI_DETAIL(NAME_);
create index ACT_IDX_HI_DETAIL_TASK_ID on ACT_HI_DETAIL(TASK_ID_);
create index ACT_IDX_HI_PROCVAR_PROC_INST on ACT_HI_VARINST(PROC_INST_ID_);
create index ACT_IDX_HI_PROCVAR_NAME_TYPE on ACT_HI_VARINST(NAME_, VAR_TYPE_);
create index ACT_IDX_HI_PROCVAR_TASK_ID on ACT_HI_VARINST(TASK_ID_);
create index ACT_IDX_HI_ACT_INST_PROCINST on ACT_HI_ACTINST(PROC_INST_ID_, ACT_ID_);
create index ACT_IDX_HI_ACT_INST_EXEC on ACT_HI_ACTINST(EXECUTION_ID_, ACT_ID_);
create index ACT_IDX_HI_IDENT_LNK_USER on ACT_HI_IDENTITYLINK(USER_ID_);
create index ACT_IDX_HI_IDENT_LNK_TASK on ACT_HI_IDENTITYLINK(TASK_ID_);
create index ACT_IDX_HI_IDENT_LNK_PROCINST on ACT_HI_IDENTITYLINK(PROC_INST_ID_);
create index ACT_IDX_HI_TASK_INST_PROCINST on ACT_HI_TASKINST(PROC_INST_ID_);
create table ACT_ID_GROUP (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    TYPE_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_ID_MEMBERSHIP (
    USER_ID_ varchar(64),
    GROUP_ID_ varchar(64),
    primary key (USER_ID_, GROUP_ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_ID_USER (
    ID_ varchar(64),
    REV_ integer,
    FIRST_ varchar(255),
    LAST_ varchar(255),
    EMAIL_ varchar(255),
    PWD_ varchar(255),
    PICTURE_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_ID_INFO (
    ID_ varchar(64),
    REV_ integer,
    USER_ID_ varchar(64),
    TYPE_ varchar(64),
    KEY_ varchar(255),
    VALUE_ varchar(255),
    PASSWORD_ LONGBLOB,
    PARENT_ID_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

alter table ACT_ID_MEMBERSHIP 
    add constraint ACT_FK_MEMB_GROUP 
    foreign key (GROUP_ID_) 
    references ACT_ID_GROUP (ID_);

alter table ACT_ID_MEMBERSHIP 
    add constraint ACT_FK_MEMB_USER 
    foreign key (USER_ID_) 
    references ACT_ID_USER (ID_);-- ----------------------------
-- Records of wf_user
-- ----------------------------
drop index ACT_IDX_EXEC_BUSKEY on ACT_RU_EXECUTION;
drop index ACT_IDX_TASK_CREATE on ACT_RU_TASK;
drop index ACT_IDX_IDENT_LNK_USER on ACT_RU_IDENTITYLINK;
drop index ACT_IDX_IDENT_LNK_GROUP on ACT_RU_IDENTITYLINK;
drop index ACT_IDX_VARIABLE_TASK_ID on ACT_RU_VARIABLE;
drop index ACT_IDX_INFO_PROCDEF on ACT_PROCDEF_INFO;

alter table ACT_GE_BYTEARRAY
    drop FOREIGN KEY ACT_FK_BYTEARR_DEPL;

alter table ACT_RU_EXECUTION
    drop FOREIGN KEY ACT_FK_EXE_PROCINST;

alter table ACT_RU_EXECUTION 
    drop FOREIGN KEY ACT_FK_EXE_PARENT;

alter table ACT_RU_EXECUTION 
    drop FOREIGN KEY ACT_FK_EXE_SUPER;
    
alter table ACT_RU_EXECUTION 
    drop FOREIGN KEY ACT_FK_EXE_PROCDEF;

alter table ACT_RU_IDENTITYLINK
    drop FOREIGN KEY ACT_FK_TSKASS_TASK;

alter table ACT_RU_IDENTITYLINK
    drop FOREIGN KEY ACT_FK_ATHRZ_PROCEDEF;
    
alter table ACT_RU_TASK
	drop FOREIGN KEY ACT_FK_TASK_EXE;

alter table ACT_RU_TASK
	drop FOREIGN KEY ACT_FK_TASK_PROCINST;
	
alter table ACT_RU_TASK
	drop FOREIGN KEY ACT_FK_TASK_PROCDEF;
    
alter table ACT_RU_VARIABLE
    drop FOREIGN KEY ACT_FK_VAR_EXE;
    
alter table ACT_RU_VARIABLE
	drop FOREIGN KEY ACT_FK_VAR_PROCINST;    

alter table ACT_RU_VARIABLE
    drop FOREIGN KEY ACT_FK_VAR_BYTEARRAY;

alter table ACT_RU_JOB
    drop FOREIGN KEY ACT_FK_JOB_EXCEPTION;
    
alter table ACT_RU_EVENT_SUBSCR
    drop FOREIGN KEY ACT_FK_EVENT_EXEC;

alter table ACT_RE_MODEL
    drop FOREIGN KEY ACT_FK_MODEL_SOURCE;

alter table ACT_RE_MODEL
    drop FOREIGN KEY ACT_FK_MODEL_SOURCE_EXTRA;

alter table ACT_RE_MODEL
    drop FOREIGN KEY ACT_FK_MODEL_DEPLOYMENT;

alter table ACT_PROCDEF_INFO 
    drop FOREIGN KEY ACT_FK_INFO_JSON_BA;
    
alter table ACT_PROCDEF_INFO 
    drop FOREIGN KEY ACT_FK_INFO_PROCDEF;
    
drop index ACT_IDX_ATHRZ_PROCEDEF on ACT_RU_IDENTITYLINK;
drop index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR;
    
drop table if exists ACT_GE_PROPERTY;
drop table if exists ACT_RU_VARIABLE;
drop table if exists ACT_GE_BYTEARRAY;
drop table if exists ACT_RE_DEPLOYMENT;
drop table if exists ACT_RE_MODEL;
drop table if exists ACT_RU_IDENTITYLINK;
drop table if exists ACT_RU_TASK;

drop table if exists ACT_RU_EXECUTION;
drop table if exists ACT_RU_JOB; 
drop table if exists ACT_RU_EVENT_SUBSCR;
drop table if exists ACT_EVT_LOG;
drop table if exists ACT_PROCDEF_INFO;
drop table if exists ACT_RE_PROCDEF;

drop index ACT_IDX_HI_PRO_INST_END on ACT_HI_PROCINST;
drop index ACT_IDX_HI_PRO_I_BUSKEY on ACT_HI_PROCINST;
drop index ACT_IDX_HI_ACT_INST_START on ACT_HI_ACTINST;
drop index ACT_IDX_HI_ACT_INST_END on ACT_HI_ACTINST;
drop index ACT_IDX_HI_DETAIL_PROC_INST on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_ACT_INST on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_TIME on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_NAME on ACT_HI_DETAIL;
drop index ACT_IDX_HI_DETAIL_TASK_ID on ACT_HI_DETAIL;
drop index ACT_IDX_HI_PROCVAR_PROC_INST on ACT_HI_VARINST;
drop index ACT_IDX_HI_PROCVAR_NAME_TYPE on ACT_HI_VARINST;
drop index ACT_IDX_HI_ACT_INST_PROCINST on ACT_HI_ACTINST;
drop index ACT_IDX_HI_IDENT_LNK_USER on ACT_HI_IDENTITYLINK;
drop index ACT_IDX_HI_IDENT_LNK_TASK on ACT_HI_IDENTITYLINK;
drop index ACT_IDX_HI_IDENT_LNK_PROCINST on ACT_HI_IDENTITYLINK;
drop index ACT_IDX_HI_TASK_INST_PROCINST on ACT_HI_TASKINST;

drop table if exists ACT_HI_PROCINST;
drop table if exists ACT_HI_ACTINST;
drop table if exists ACT_HI_VARINST;
drop table if exists ACT_HI_TASKINST;
drop table if exists ACT_HI_DETAIL;
drop table if exists ACT_HI_COMMENT;
drop table if exists ACT_HI_ATTACHMENT;
drop table if exists ACT_HI_IDENTITYLINK;
alter table ACT_ID_MEMBERSHIP 
    drop FOREIGN KEY ACT_FK_MEMB_GROUP;
    
alter table ACT_ID_MEMBERSHIP 
    drop FOREIGN KEY ACT_FK_MEMB_USER;

drop table if exists ACT_ID_INFO;
drop table if exists ACT_ID_MEMBERSHIP;
drop table if exists ACT_ID_GROUP;
drop table if exists ACT_ID_USER;
create table ACT_GE_PROPERTY (
    NAME_ varchar(64),
    VALUE_ varchar(300),
    REV_ integer,
    primary key (NAME_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

insert into ACT_GE_PROPERTY
values ('schema.version', '5.22.0.0', 1);

insert into ACT_GE_PROPERTY
values ('schema.history', 'create(5.22.0.0)', 1);

insert into ACT_GE_PROPERTY
values ('next.dbid', '1', 1);

create table ACT_GE_BYTEARRAY (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    DEPLOYMENT_ID_ varchar(64),
    BYTES_ LONGBLOB,
    GENERATED_ TINYINT,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RE_DEPLOYMENT (
    ID_ varchar(64),
    NAME_ varchar(255),
    CATEGORY_ varchar(255),
    TENANT_ID_ varchar(255) default '',
    DEPLOY_TIME_ timestamp(3) NULL,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RE_MODEL (
    ID_ varchar(64) not null,
    REV_ integer,
    NAME_ varchar(255),
    KEY_ varchar(255),
    CATEGORY_ varchar(255),
    CREATE_TIME_ timestamp(3) null,
    LAST_UPDATE_TIME_ timestamp(3) null,
    VERSION_ integer,
    META_INFO_ varchar(4000),
    DEPLOYMENT_ID_ varchar(64),
    EDITOR_SOURCE_VALUE_ID_ varchar(64),
    EDITOR_SOURCE_EXTRA_VALUE_ID_ varchar(64),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_EXECUTION (
    ID_ varchar(64),
    REV_ integer,
    PROC_INST_ID_ varchar(64),
    BUSINESS_KEY_ varchar(255),
    PARENT_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    SUPER_EXEC_ varchar(64),
    ACT_ID_ varchar(255),
    IS_ACTIVE_ TINYINT,
    IS_CONCURRENT_ TINYINT,
    IS_SCOPE_ TINYINT,
    IS_EVENT_SCOPE_ TINYINT,
    SUSPENSION_STATE_ integer,
    CACHED_ENT_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    NAME_ varchar(255),
    LOCK_TIME_ timestamp(3) NULL,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_JOB (
    ID_ varchar(64) NOT NULL,
    REV_ integer,
    TYPE_ varchar(255) NOT NULL,
    LOCK_EXP_TIME_ timestamp(3) NULL,
    LOCK_OWNER_ varchar(255),
    EXCLUSIVE_ boolean,
    EXECUTION_ID_ varchar(64),
    PROCESS_INSTANCE_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    RETRIES_ integer,
    EXCEPTION_STACK_ID_ varchar(64),
    EXCEPTION_MSG_ varchar(4000),
    DUEDATE_ timestamp(3) NULL,
    REPEAT_ varchar(255),
    HANDLER_TYPE_ varchar(255),
    HANDLER_CFG_ varchar(4000),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RE_PROCDEF (
    ID_ varchar(64) not null,
    REV_ integer,
    CATEGORY_ varchar(255),
    NAME_ varchar(255),
    KEY_ varchar(255) not null,
    VERSION_ integer not null,
    DEPLOYMENT_ID_ varchar(64),
    RESOURCE_NAME_ varchar(4000),
    DGRM_RESOURCE_NAME_ varchar(4000),
    DESCRIPTION_ varchar(4000),
    HAS_START_FORM_KEY_ TINYINT,
    HAS_GRAPHICAL_NOTATION_ TINYINT,
    SUSPENSION_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_TASK (
    ID_ varchar(64),
    REV_ integer,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    TASK_DEF_KEY_ varchar(255),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    DELEGATION_ varchar(64),
    PRIORITY_ integer,
    CREATE_TIME_ timestamp(3) NULL,
    DUE_DATE_ datetime(3),
    CATEGORY_ varchar(255),
    SUSPENSION_STATE_ integer,
    TENANT_ID_ varchar(255) default '',
    FORM_KEY_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_IDENTITYLINK (
    ID_ varchar(64),
    REV_ integer,
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    PROC_DEF_ID_ varchar(64),    
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_VARIABLE (
    ID_ varchar(64) not null,
    REV_ integer,
    TYPE_ varchar(255) not null,
    NAME_ varchar(255) not null,
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    TASK_ID_ varchar(64),
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_RU_EVENT_SUBSCR (
    ID_ varchar(64) not null,
    REV_ integer,
    EVENT_TYPE_ varchar(255) not null,
    EVENT_NAME_ varchar(255),
    EXECUTION_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTIVITY_ID_ varchar(64),
    CONFIGURATION_ varchar(255),
    CREATED_ timestamp(3) not null DEFAULT CURRENT_TIMESTAMP(3),
    PROC_DEF_ID_ varchar(64),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_EVT_LOG (
    LOG_NR_ bigint auto_increment,
    TYPE_ varchar(64),
    PROC_DEF_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    TIME_STAMP_ timestamp(3) not null,
    USER_ID_ varchar(255),
    DATA_ LONGBLOB,
    LOCK_OWNER_ varchar(255),
    LOCK_TIME_ timestamp(3) null,
    IS_PROCESSED_ tinyint default 0,
    primary key (LOG_NR_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_PROCDEF_INFO (
	ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64) not null,
    REV_ integer,
    INFO_JSON_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create index ACT_IDX_EXEC_BUSKEY on ACT_RU_EXECUTION(BUSINESS_KEY_);
create index ACT_IDX_TASK_CREATE on ACT_RU_TASK(CREATE_TIME_);
create index ACT_IDX_IDENT_LNK_USER on ACT_RU_IDENTITYLINK(USER_ID_);
create index ACT_IDX_IDENT_LNK_GROUP on ACT_RU_IDENTITYLINK(GROUP_ID_);
create index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR(CONFIGURATION_);
create index ACT_IDX_VARIABLE_TASK_ID on ACT_RU_VARIABLE(TASK_ID_);
create index ACT_IDX_ATHRZ_PROCEDEF on ACT_RU_IDENTITYLINK(PROC_DEF_ID_);
create index ACT_IDX_INFO_PROCDEF on ACT_PROCDEF_INFO(PROC_DEF_ID_);

alter table ACT_GE_BYTEARRAY
    add constraint ACT_FK_BYTEARR_DEPL
    foreign key (DEPLOYMENT_ID_)
    references ACT_RE_DEPLOYMENT (ID_);

alter table ACT_RE_PROCDEF
    add constraint ACT_UNIQ_PROCDEF
    unique (KEY_,VERSION_, TENANT_ID_);

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PROCINST 
    foreign key (PROC_INST_ID_) 
    references ACT_RU_EXECUTION (ID_) on delete cascade on update cascade;

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PARENT 
    foreign key (PARENT_ID_) 
    references ACT_RU_EXECUTION (ID_);
    
alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_SUPER 
    foreign key (SUPER_EXEC_) 
    references ACT_RU_EXECUTION (ID_);

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PROCDEF 
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF (ID_);
    
alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_TSKASS_TASK 
    foreign key (TASK_ID_) 
    references ACT_RU_TASK (ID_);
    
alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_ATHRZ_PROCEDEF 
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF(ID_);
    
alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_IDL_PROCINST
    foreign key (PROC_INST_ID_) 
    references ACT_RU_EXECUTION (ID_);       
    
alter table ACT_RU_TASK
    add constraint ACT_FK_TASK_EXE
    foreign key (EXECUTION_ID_)
    references ACT_RU_EXECUTION (ID_);
    
alter table ACT_RU_TASK
    add constraint ACT_FK_TASK_PROCINST
    foreign key (PROC_INST_ID_)
    references ACT_RU_EXECUTION (ID_);
    
alter table ACT_RU_TASK
  	add constraint ACT_FK_TASK_PROCDEF
  	foreign key (PROC_DEF_ID_)
  	references ACT_RE_PROCDEF (ID_);
  
alter table ACT_RU_VARIABLE 
    add constraint ACT_FK_VAR_EXE 
    foreign key (EXECUTION_ID_) 
    references ACT_RU_EXECUTION (ID_);

alter table ACT_RU_VARIABLE
    add constraint ACT_FK_VAR_PROCINST
    foreign key (PROC_INST_ID_)
    references ACT_RU_EXECUTION(ID_);

alter table ACT_RU_VARIABLE 
    add constraint ACT_FK_VAR_BYTEARRAY 
    foreign key (BYTEARRAY_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RU_JOB 
    add constraint ACT_FK_JOB_EXCEPTION 
    foreign key (EXCEPTION_STACK_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RU_EVENT_SUBSCR
    add constraint ACT_FK_EVENT_EXEC
    foreign key (EXECUTION_ID_)
    references ACT_RU_EXECUTION(ID_);

alter table ACT_RE_MODEL
    add constraint ACT_FK_MODEL_SOURCE
    foreign key (EDITOR_SOURCE_VALUE_ID_)
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RE_MODEL
    add constraint ACT_FK_MODEL_SOURCE_EXTRA
    foreign key (EDITOR_SOURCE_EXTRA_VALUE_ID_)
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RE_MODEL
    add constraint ACT_FK_MODEL_DEPLOYMENT
    foreign key (DEPLOYMENT_ID_)
    references ACT_RE_DEPLOYMENT (ID_);

alter table ACT_PROCDEF_INFO 
    add constraint ACT_FK_INFO_JSON_BA 
    foreign key (INFO_JSON_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

alter table ACT_PROCDEF_INFO 
    add constraint ACT_FK_INFO_PROCDEF 
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF (ID_);
    
alter table ACT_PROCDEF_INFO
    add constraint ACT_UNIQ_INFO_PROCDEF
    unique (PROC_DEF_ID_);
    create table ACT_HI_PROCINST (
    ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64) not null,
    BUSINESS_KEY_ varchar(255),
    PROC_DEF_ID_ varchar(64) not null,
    START_TIME_ datetime(3) not null,
    END_TIME_ datetime(3),
    DURATION_ bigint,
    START_USER_ID_ varchar(255),
    START_ACT_ID_ varchar(255),
    END_ACT_ID_ varchar(255),
    SUPER_PROCESS_INSTANCE_ID_ varchar(64),
    DELETE_REASON_ varchar(4000),
    TENANT_ID_ varchar(255) default '',
    NAME_ varchar(255),
    primary key (ID_),
    unique (PROC_INST_ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_ACTINST (
    ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64) not null,
    EXECUTION_ID_ varchar(64) not null,
    ACT_ID_ varchar(255) not null,
    TASK_ID_ varchar(64),
    CALL_PROC_INST_ID_ varchar(64),
    ACT_NAME_ varchar(255),
    ACT_TYPE_ varchar(255) not null,
    ASSIGNEE_ varchar(255),
    START_TIME_ datetime(3) not null,
    END_TIME_ datetime(3),
    DURATION_ bigint,
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_TASKINST (
    ID_ varchar(64) not null,
    PROC_DEF_ID_ varchar(64),
    TASK_DEF_KEY_ varchar(255),
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    NAME_ varchar(255),
    PARENT_TASK_ID_ varchar(64),
    DESCRIPTION_ varchar(4000),
    OWNER_ varchar(255),
    ASSIGNEE_ varchar(255),
    START_TIME_ datetime(3) not null,
    CLAIM_TIME_ datetime(3),
    END_TIME_ datetime(3),
    DURATION_ bigint,
    DELETE_REASON_ varchar(4000),
    PRIORITY_ integer,
    DUE_DATE_ datetime(3),
    FORM_KEY_ varchar(255),
    CATEGORY_ varchar(255),
    TENANT_ID_ varchar(255) default '',
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_VARINST (
    ID_ varchar(64) not null,
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    NAME_ varchar(255) not null,
    VAR_TYPE_ varchar(100),
    REV_ integer,
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    CREATE_TIME_ datetime(3),
    LAST_UPDATED_TIME_ datetime(3),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_DETAIL (
    ID_ varchar(64) not null,
    TYPE_ varchar(255) not null,
    PROC_INST_ID_ varchar(64),
    EXECUTION_ID_ varchar(64),
    TASK_ID_ varchar(64),
    ACT_INST_ID_ varchar(64),
    NAME_ varchar(255) not null,
    VAR_TYPE_ varchar(255),
    REV_ integer,
    TIME_ datetime(3) not null,
    BYTEARRAY_ID_ varchar(64),
    DOUBLE_ double,
    LONG_ bigint,
    TEXT_ varchar(4000),
    TEXT2_ varchar(4000),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_COMMENT (
    ID_ varchar(64) not null,
    TYPE_ varchar(255),
    TIME_ datetime(3) not null,
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    ACTION_ varchar(255),
    MESSAGE_ varchar(4000),
    FULL_MSG_ LONGBLOB,
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_ATTACHMENT (
    ID_ varchar(64) not null,
    REV_ integer,
    USER_ID_ varchar(255),
    NAME_ varchar(255),
    DESCRIPTION_ varchar(4000),
    TYPE_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    URL_ varchar(4000),
    CONTENT_ID_ varchar(64),
    TIME_ datetime(3),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_HI_IDENTITYLINK (
    ID_ varchar(64),
    GROUP_ID_ varchar(255),
    TYPE_ varchar(255),
    USER_ID_ varchar(255),
    TASK_ID_ varchar(64),
    PROC_INST_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


create index ACT_IDX_HI_PRO_INST_END on ACT_HI_PROCINST(END_TIME_);
create index ACT_IDX_HI_PRO_I_BUSKEY on ACT_HI_PROCINST(BUSINESS_KEY_);
create index ACT_IDX_HI_ACT_INST_START on ACT_HI_ACTINST(START_TIME_);
create index ACT_IDX_HI_ACT_INST_END on ACT_HI_ACTINST(END_TIME_);
create index ACT_IDX_HI_DETAIL_PROC_INST on ACT_HI_DETAIL(PROC_INST_ID_);
create index ACT_IDX_HI_DETAIL_ACT_INST on ACT_HI_DETAIL(ACT_INST_ID_);
create index ACT_IDX_HI_DETAIL_TIME on ACT_HI_DETAIL(TIME_);
create index ACT_IDX_HI_DETAIL_NAME on ACT_HI_DETAIL(NAME_);
create index ACT_IDX_HI_DETAIL_TASK_ID on ACT_HI_DETAIL(TASK_ID_);
create index ACT_IDX_HI_PROCVAR_PROC_INST on ACT_HI_VARINST(PROC_INST_ID_);
create index ACT_IDX_HI_PROCVAR_NAME_TYPE on ACT_HI_VARINST(NAME_, VAR_TYPE_);
create index ACT_IDX_HI_PROCVAR_TASK_ID on ACT_HI_VARINST(TASK_ID_);
create index ACT_IDX_HI_ACT_INST_PROCINST on ACT_HI_ACTINST(PROC_INST_ID_, ACT_ID_);
create index ACT_IDX_HI_ACT_INST_EXEC on ACT_HI_ACTINST(EXECUTION_ID_, ACT_ID_);
create index ACT_IDX_HI_IDENT_LNK_USER on ACT_HI_IDENTITYLINK(USER_ID_);
create index ACT_IDX_HI_IDENT_LNK_TASK on ACT_HI_IDENTITYLINK(TASK_ID_);
create index ACT_IDX_HI_IDENT_LNK_PROCINST on ACT_HI_IDENTITYLINK(PROC_INST_ID_);
create index ACT_IDX_HI_TASK_INST_PROCINST on ACT_HI_TASKINST(PROC_INST_ID_);
create table ACT_ID_GROUP (
    ID_ varchar(64),
    REV_ integer,
    NAME_ varchar(255),
    TYPE_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_ID_MEMBERSHIP (
    USER_ID_ varchar(64),
    GROUP_ID_ varchar(64),
    primary key (USER_ID_, GROUP_ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_ID_USER (
    ID_ varchar(64),
    REV_ integer,
    FIRST_ varchar(255),
    LAST_ varchar(255),
    EMAIL_ varchar(255),
    PWD_ varchar(255),
    PICTURE_ID_ varchar(64),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

create table ACT_ID_INFO (
    ID_ varchar(64),
    REV_ integer,
    USER_ID_ varchar(64),
    TYPE_ varchar(64),
    KEY_ varchar(255),
    VALUE_ varchar(255),
    PASSWORD_ LONGBLOB,
    PARENT_ID_ varchar(255),
    primary key (ID_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

alter table ACT_ID_MEMBERSHIP 
    add constraint ACT_FK_MEMB_GROUP 
    foreign key (GROUP_ID_) 
    references ACT_ID_GROUP (ID_);

alter table ACT_ID_MEMBERSHIP 
    add constraint ACT_FK_MEMB_USER 
    foreign key (USER_ID_) 
    references ACT_ID_USER (ID_);
    
delete  from `sys_menu`  where menu_code_ like 'workflow%';
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow', '工作流引擎', NULL, NULL, '#', NULL, NULL, NULL, 'Y', 'Y', 'Y', '工作流引擎', 'N', NULL, NULL, NULL, NULL, NULL, NULL, 30, NULL, NULL, '2017-9-18 17:46:13', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.100', '基础功能', NULL, 'workflow', '#', NULL, NULL, NULL, 'Y', 'Y', NULL, '工作流程引擎的基础功能', 'N', NULL, NULL, NULL, NULL, NULL, NULL, 10, NULL, NULL, '2017-9-18 17:50:25', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.100.100', '流程模板管理', NULL, 'workflow.100', 'workflow/procTemplate/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 10, NULL, NULL, '2017-9-19 15:56:37', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.100.200', '表单管理', NULL, 'workflow.100', 'workflow/formDef/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 20, NULL, NULL, '2017-9-19 10:08:12', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.100.300', '业务模块管理', NULL, 'workflow.100', 'workflow/bizModule/list', NULL, NULL, NULL, 'Y', 'Y', NULL, '业务模块管理', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 30, NULL, NULL, '2017-9-19 15:19:03', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.100.400', '业务流程管理', NULL, 'workflow.100', 'workflow/procDef/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 40, NULL, NULL, '2017-9-19 15:19:03', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.300', '我的流程', NULL, 'workflow', '#', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'N', NULL, NULL, NULL, NULL, NULL, NULL, 300, NULL, NULL, '2017-12-21 22:45:42', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.300.100', '未提交', NULL, 'workflow.300', 'workflow/process/draftList', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 100, NULL, NULL, '2017-11-17 14:19:24', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.300.200', '审批中', NULL, 'workflow.300', 'workflow/process/runingList', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 200, NULL, NULL, '2017-11-17 14:19:21', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.300.300', '已审批', NULL, 'workflow.300', 'workflow/process/completeList', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 300, NULL, NULL, '2017-11-17 14:19:17', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.400', '我的审批', NULL, 'workflow', '#', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'N', NULL, NULL, NULL, NULL, NULL, NULL, 400, NULL, NULL, '2017-12-21 22:45:50', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.400.100', '待审批', NULL, 'workflow.400', 'workflow/approve/todoList', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 100, NULL, NULL, '2017-11-17 14:19:09', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.400.200', '已审批', NULL, 'workflow.400', 'workflow/approve/doneList', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 200, NULL, NULL, '2017-11-17 14:19:13', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.200', '监控中心', NULL, 'workflow', '#', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'N', NULL, NULL, NULL, NULL, NULL, NULL, 200, NULL, NULL, '2017-12-21 22:44:52', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.200.100', '流程调整', NULL, 'workflow.200', 'workflow/monitor/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 100, NULL, NULL, '2017-11-9 14:34:26', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.200.200', '流程监控', NULL, 'workflow.200', 'workflow/monitor/monitorCenter', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 200, NULL, NULL, '2017-12-21 17:01:32', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.500', '审批托管', NULL, 'workflow', 'wf/procAutoTransfer/list', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 500, NULL, NULL, '2017-12-4 16:59:14', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.666', '工作流测试', NULL, 'workflow', 'demo/speciality/testList', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 240, NULL, NULL, '2017-12-21 22:45:17', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('workflow.testsubmit', '测试工作流提交', NULL, 'workflow', 'workflow/procDef/workflowTestSubmit', NULL, NULL, NULL, 'Y', 'Y', 'Y', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 260, NULL, NULL, '2017-12-21 22:45:23', NULL);
