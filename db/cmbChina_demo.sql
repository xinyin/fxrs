-- ----------------------------
-- Table structure for demo_concur
-- ----------------------------
DROP TABLE IF EXISTS `demo_concur`;
CREATE TABLE `demo_concur` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `STAFF_CODE_` varchar(64) DEFAULT NULL,
  `STAFF_NAME_` varchar(128) DEFAULT NULL COMMENT '报销人姓名',
  `MONEY_` decimal(10,0) DEFAULT NULL,
  `APPLY_TIME_` date DEFAULT NULL,
  `CONTENT_` varchar(512) DEFAULT NULL,
  `PM_ID_` varchar(64) DEFAULT NULL,
  `REMARK_` varchar(512) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) DEFAULT NULL COMMENT '流程实例ID',
  `STATUS_` varchar(64) DEFAULT NULL,
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` date DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报销内容';


-- ----------------------------
-- Table structure for demo_concur_list
-- ----------------------------
DROP TABLE IF EXISTS `demo_concur_list`;
CREATE TABLE `demo_concur_list` (
  `ID_` varchar(64) NOT NULL,
  `CONCUR_ID_` varchar(64) DEFAULT NULL,
  `CONCUR_TYPE_` varchar(64) DEFAULT NULL,
  `MONEY_` decimal(10,0) DEFAULT NULL,
  `PASS_TIME_` varchar(64) DEFAULT NULL,
  `REMARK_` varchar(256) DEFAULT NULL,
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` date DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`),
  KEY `FK_REFERENCE_1_JEP` (`CONCUR_ID_`),
  CONSTRAINT `FK_REFERENCE_1_JEP` FOREIGN KEY (`CONCUR_ID_`) REFERENCES `demo_concur` (`ID_`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报销详情';

-- ----------------------------
-- Records of demo_concur_list
-- ----------------------------

-- ----------------------------
-- Table structure for demo_leave
-- ----------------------------
DROP TABLE IF EXISTS `demo_leave`;
CREATE TABLE `demo_leave` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `APPLY_TIME_` date DEFAULT NULL,
  `LEAVE_STAFF_CODE_` varchar(64) DEFAULT NULL COMMENT '请假人编码',
  `LEAVE_STAFF_NAME_` varchar(128) DEFAULT NULL COMMENT '请假人姓名',
  `LEAVE_START_TIME_` varchar(64) DEFAULT NULL COMMENT '请假开始时间',
  `LEAVE_END_TIME_` varchar(64) DEFAULT NULL COMMENT '请假结束时间',
  `LEAVE_DAYS_` int(11) DEFAULT NULL COMMENT '请假天数',
  `LEAVE_TYPE_` varchar(64) DEFAULT NULL COMMENT '请假假种',
  `TEL_` varchar(64) DEFAULT NULL COMMENT '联系电话',
  `EXIGENCY_WORKER_` varchar(64) DEFAULT NULL COMMENT '紧急工作处理人',
  `LEAVE_REASON_` varchar(256) DEFAULT NULL COMMENT '请假原因',
  `STATUS_` varchar(64) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) DEFAULT NULL COMMENT '流程实例ID',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` date DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假流程';
