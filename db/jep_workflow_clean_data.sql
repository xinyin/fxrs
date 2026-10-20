
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


drop table if exists ACT_RU_VARIABLE;
drop table if exists ACT_RU_IDENTITYLINK;
drop table if exists ACT_RU_TASK;

drop table if exists ACT_RU_EXECUTION;
drop table if exists ACT_RU_JOB;
drop table if exists ACT_RU_EVENT_SUBSCR;
drop table if exists ACT_EVT_LOG;
drop table if exists ACT_PROCDEF_INFO;


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



alter table ACT_PROCDEF_INFO
    drop FOREIGN KEY ACT_FK_INFO_JSON_BA;

alter table ACT_PROCDEF_INFO
    drop FOREIGN KEY ACT_FK_INFO_PROCDEF;

drop index ACT_IDX_ATHRZ_PROCEDEF on ACT_RU_IDENTITYLINK;
drop index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR;

drop table if exists ACT_RU_VARIABLE;

drop table if exists ACT_RU_IDENTITYLINK;
drop table if exists ACT_RU_TASK;

drop table if exists ACT_RU_EXECUTION;
drop table if exists ACT_RU_JOB;
drop table if exists ACT_RU_EVENT_SUBSCR;
drop table if exists ACT_EVT_LOG;
drop table if exists ACT_PROCDEF_INFO;


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