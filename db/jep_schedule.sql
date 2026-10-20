
drop table if exists SCH_JOB_GROUP_CLUSTER;

drop table if exists SCH_JOB_LOG;

drop table if exists SCH_NODE_STATE;

drop table if exists SCH_CALENDAR_DETAIL;

drop table if exists SCH_JOB_INST;

drop table if exists SCH_NODE;

drop table if exists SCH_CLUSTER;

drop table if exists SCH_JOB_ENTITY;

drop table if exists SCH_JOB_GROUP;

/*==============================================================*/
/* Table: SCH_CALENDAR_DETAIL                                   */
/*==============================================================*/
create table SCH_CALENDAR_DETAIL
(
   CODE_                VARCHAR(64) not null comment '日历编码',
   NAME_                VARCHAR(256) comment '日历名称',
   WEEKLY_              VARCHAR(64) comment '是否包含周末:
            Y - 包含
            N - 不包含',
   TIME_INTERVAL_       VARCHAR(2000) comment '排除时段设置',
   REMARK_              VARCHAR(512) comment '备注',
   TENANT_ID_           varchar(64) comment '租户',
   CREATED_BY_ID_       varchar(64) comment '创建人ID',
   CREATED_BY_NAME_     varchar(64) comment '创建人名称',
   CREATED_TIME_        datetime comment '创建时间',
   DELFLAG_             varchar(1) comment '逻辑删除标记',
   DISPLAY_ORDER_       int comment '显示序号',
   MODIFIED_BY_ID_      varchar(64) comment '修改人ID',
   MODIFIED_BY_NAME_    varchar(64) comment '修改人名称',
   MODIFIED_TIME_       timestamp comment '修改时间',
   VERSION_             int comment '版本',
   primary key (CODE_)
);

alter table SCH_CALENDAR_DETAIL comment '集群管理';

/*==============================================================*/
/* Table: SCH_CLUSTER                                           */
/*==============================================================*/
create table SCH_CLUSTER
(
   CODE_                VARCHAR(64) not null comment '服务器编码',
   NAME_                VARCHAR(256) comment '服务器名称',
   REMARK_              VARCHAR(512) comment '备注',
   TENANT_ID_           varchar(64) comment '租户',
   CREATED_BY_ID_       varchar(64) comment '创建人ID',
   CREATED_BY_NAME_     varchar(64) comment '创建人名称',
   CREATED_TIME_        datetime comment '创建时间',
   DELFLAG_             varchar(1) comment '逻辑删除标记',
   DISPLAY_ORDER_       int comment '显示序号',
   MODIFIED_BY_ID_      varchar(64) comment '修改人ID',
   MODIFIED_BY_NAME_    varchar(64) comment '修改人名称',
   MODIFIED_TIME_       timestamp comment '修改时间',
   VERSION_             int comment '版本',
   primary key (CODE_)
);

alter table SCH_CLUSTER comment '集群管理';

/*==============================================================*/
/* Table: SCH_JOB_ENTITY                                        */
/*==============================================================*/
create table SCH_JOB_ENTITY
(
   CODE_                VARCHAR(64) not null comment '任务编码',
   NAME_                VARCHAR(256) comment '任务名称',
   CALENDAR_CODE_       VARCHAR(200) comment '日历',
   GROUP_CODE_          VARCHAR(64) comment '任务类型',
   JOB_TYPE1_           VARCHAR(64) comment '任务类型1:
            CONCURRENT - 并行
            BLOCK - 串行
            默认为串行',
   JOB_TYPE2_           VARCHAR(64) comment '任务类型2:
            STATEFUL - 有
            STATELESS - 无
            默认为无',
   NODE_SCOPE_TYPE_     VARCHAR(64) comment 'CLUSTER -  集群
            NODE - 节点',
   NODE_SCOPE_          VARCHAR(2000) comment '节点范围, 以逗号隔开',
   NODE_STRATEGY_       VARCHAR(64) comment '节点算法:
            SMART - 智能选择
            HASH - 一次性哈希
            BALANCE - 负载均衡',
   START_DATE_          DATE comment '开始日期',
   END_DATE_            DATE comment '结束日期',
   TIMEOUT_             int comment '超时时间',
   TIMEOUT_UNIT_        VARCHAR(64) comment '超时时间单位:
            H - 小时 1-23
            M - 分钟 1 - 59',
   TIMEOUT_INSTRUCTION_ VARCHAR(64) comment '超时处理:
            REFIRE- 重做
            ABANDON- 放弃
            STOP - 暂停',
   ERROR_INSTRUCTION_   VARCHAR(64) comment '错误处理:
            REFIRE- 重做
            ABANDON- 放弃
            STOP - 暂停',
   PRIORITY_            int comment '优先级:1 - 10',
   CRON_EXPRESSION_     VARCHAR(256) comment '任务表达式',
   PARAM_               VARCHAR(2000) comment '任务参数:JSON字符串。',
   STATUS_              VARCHAR(64) comment '状态:
            DRAFT - 草稿
            RUN - 运行中
            COMPLETE - 完成
            PAUSE - 暂停
            STOP - 终止',
   REMARK_              VARCHAR(512) comment '备注',
   TENANT_ID_           varchar(64) comment '租户',
   CREATED_BY_ID_       varchar(64) comment '创建人ID',
   CREATED_BY_NAME_     varchar(64) comment '创建人名称',
   CREATED_TIME_        datetime comment '创建时间',
   DELFLAG_             varchar(1) comment '逻辑删除标记',
   DISPLAY_ORDER_       int comment '显示序号',
   MODIFIED_BY_ID_      varchar(64) comment '修改人ID',
   MODIFIED_BY_NAME_    varchar(64) comment '修改人名称',
   MODIFIED_TIME_       timestamp comment '修改时间',
   VERSION_             int comment '版本',
   primary key (CODE_)
);

alter table SCH_JOB_ENTITY comment '调度任务管理';

/*==============================================================*/
/* Table: SCH_JOB_GROUP                                         */
/*==============================================================*/
create table SCH_JOB_GROUP
(
   CODE_                VARCHAR(64) not null comment '类别编码',
   NAME_                VARCHAR(256) comment '类别名称',
   PARAM_               VARCHAR(2000) comment '参数',
   REMARK_              VARCHAR(512) comment '描述',
   TENANT_ID_           varchar(64) comment '租户',
   CREATED_BY_ID_       varchar(64) comment '创建人ID',
   CREATED_BY_NAME_     varchar(64) comment '创建人名称',
   CREATED_TIME_        datetime comment '创建时间',
   DELFLAG_             varchar(1) comment '逻辑删除标记',
   DISPLAY_ORDER_       int comment '显示序号',
   MODIFIED_BY_ID_      varchar(64) comment '修改人ID',
   MODIFIED_BY_NAME_    varchar(64) comment '修改人名称',
   MODIFIED_TIME_       timestamp comment '修改时间',
   VERSION_             int comment '版本',
   primary key (CODE_)
);

alter table SCH_JOB_GROUP comment '调度类型';

/*==============================================================*/
/* Table: SCH_JOB_GROUP_CLUSTER                                 */
/*==============================================================*/
create table SCH_JOB_GROUP_CLUSTER
(
   ID_                  VARCHAR(64) not null comment 'ID',
   GROUP_CODE_          VARCHAR(64) not null comment '类别编码',
   CLUSTER_CODE_        VARCHAR(64) not null comment '服务器编码',
   TENANT_ID_           varchar(64) comment '租户',
   CREATED_BY_ID_       varchar(64) comment '创建人ID',
   CREATED_BY_NAME_     varchar(64) comment '创建人名称',
   CREATED_TIME_        datetime comment '创建时间',
   DELFLAG_             varchar(1) comment '逻辑删除标记',
   DISPLAY_ORDER_       int comment '显示序号',
   MODIFIED_BY_ID_      varchar(64) comment '修改人ID',
   MODIFIED_BY_NAME_    varchar(64) comment '修改人名称',
   MODIFIED_TIME_       timestamp comment '修改时间',
   VERSION_             int comment '版本',
   primary key (ID_)
);

alter table SCH_JOB_GROUP_CLUSTER comment '调度类型集群表';

/*==============================================================*/
/* Table: SCH_JOB_INST                                          */
/*==============================================================*/
create table SCH_JOB_INST
(
   ID_                  VARCHAR(64) not null comment 'ID',
   JOB_CODE_            VARCHAR(64) comment '任务编码',
   JOB_NAME_            VARCHAR(256) comment '任务名称',
   GROUP_CODE_          VARCHAR(64) comment '任务类型',
   PARAM_               VARCHAR(2000) comment '任务参数:JSON字符串。',
   NODE_CODE_           VARCHAR(64) comment '节点编码',
   CLUSTER_CODE_        VARCHAR(64) comment '集群编码',
   START_TIME           datetime comment '开始时间',
   END_TIME             datetime comment '结束时间',
   EXEC_TIME_           bigint comment '执行时间',
   STATUS_              VARCHAR(64) comment '状态:
            NEW - 新建
            NOTIFYING - 通知中
            EXECUTING  执行中
            DONE - 执行完成
            ERROR - 执行失败',
   TIMEOUT_TIMES_       int comment '超时次数',
   ERROR_TIMES_         int comment '错误次数',
   ERROR_MESSAGE_       VARCHAR(500) comment '错误原因',
   RECOVERED_           VARCHAR(64) comment '是否恢复',
   TRIGGER_KEY_         VARCHAR(256) comment '触发器',
   LAST_TRIGGER_KEY_    VARCHAR(256) comment '上次触发器',
   SCHEDULED_FIRE_TIME_ bigint comment '计划触发时间',
   SERVER_INSTANCE_ID_  VARCHAR(64) comment '实例ID',
   TENANT_ID_           varchar(64) comment '租户',
   CREATED_BY_ID_       varchar(64) comment '创建人ID',
   CREATED_BY_NAME_     varchar(64) comment '创建人名称',
   CREATED_TIME_        datetime comment '创建时间',
   DELFLAG_             varchar(1) comment '逻辑删除标记',
   DISPLAY_ORDER_       int comment '显示序号',
   MODIFIED_BY_ID_      varchar(64) comment '修改人ID',
   MODIFIED_BY_NAME_    varchar(64) comment '修改人名称',
   MODIFIED_TIME_       timestamp comment '修改时间',
   VERSION_             int comment '版本',
   primary key (ID_)
);

alter table SCH_JOB_INST comment '调度任务实例';

/*==============================================================*/
/* Table: SCH_JOB_LOG                                           */
/*==============================================================*/
create table SCH_JOB_LOG
(
   ID_                  VARCHAR(64) not null,
   JOB_CODE_            VARCHAR(64) comment '任务编码',
   JOB_INST_ID_         VARCHAR(64) comment '实例ID',
   LOG_TIME_            datetime comment '记录时间',
   CONTENT_             longtext comment '日志内容',
   TENANT_ID_           varchar(64) comment '租户',
   CREATED_BY_ID_       varchar(64) comment '创建人ID',
   CREATED_BY_NAME_     varchar(64) comment '创建人名称',
   CREATED_TIME_        datetime comment '创建时间',
   DELFLAG_             varchar(1) comment '逻辑删除标记',
   DISPLAY_ORDER_       int comment '显示序号',
   MODIFIED_BY_ID_      varchar(64) comment '修改人ID',
   MODIFIED_BY_NAME_    varchar(64) comment '修改人名称',
   MODIFIED_TIME_       timestamp comment '修改时间',
   VERSION_             int comment '版本',
   primary key (ID_)
);

alter table SCH_JOB_LOG comment '任务日志';

/*==============================================================*/
/* Table: SCH_NODE                                              */
/*==============================================================*/
create table SCH_NODE
(
   CODE_                VARCHAR(64) not null comment '节点编码',
   NAME_                VARCHAR(256) comment '节点名称',
   CLUSTER_CODE_        VARCHAR(64) not null comment '集群编码',
   IP_ADDR_             VARCHAR(64) comment 'IP地址',
   PORT_                int comment '端口',
   WEB_CONTEXT_         VARCHAR(64) comment '应用上下文',
   STATUS_              VARCHAR(32) comment '启用/停用',
   REMARK_              VARCHAR(512) comment '备注',
   TENANT_ID_           varchar(64) comment '租户',
   CREATED_BY_ID_       varchar(64) comment '创建人ID',
   CREATED_BY_NAME_     varchar(64) comment '创建人名称',
   CREATED_TIME_        datetime comment '创建时间',
   DELFLAG_             varchar(1) comment '逻辑删除标记',
   DISPLAY_ORDER_       int comment '显示序号',
   MODIFIED_BY_ID_      varchar(64) comment '修改人ID',
   MODIFIED_BY_NAME_    varchar(64) comment '修改人名称',
   MODIFIED_TIME_       timestamp comment '修改时间',
   VERSION_             int comment '版本',
   primary key (CODE_)
);

alter table SCH_NODE comment '集群节点';

/*==============================================================*/
/* Table: SCH_NODE_STATE                                        */
/*==============================================================*/
create table SCH_NODE_STATE
(
   CODE_                varchar(64) not null comment '节点编码',
   NAME_                VARCHAR(256) comment '节点名称',
   CLUSTER_CODE_        VARCHAR(64) comment '集群编码',
   CLUSTER_NAME_        VARCHAR(256) comment '集群名称',
   IP_ADDR_             VARCHAR(64) comment 'IP地址',
   PORT_                int comment '端口',
   WEB_CONTEXT_         VARCHAR(64) comment '应用上下文',
   STATUS_              VARCHAR(64) comment '状态:
            ONLINE - 在线
            CONNECTING - 连接中
            OFFLINE - 断开',
   RETRY_TIMES_         int comment '重试次数',
   JOB_COUNT_           int comment '运行任务数',
   TOTAL_JVM_MEMORY_    bigint comment 'JVM大小',
   FREE_JVM_MEMORY_     bigint comment '空闲JVM',
   MEMORY_USAGE_        numeric(22,8) comment '内存使用',
   DISK_USAGE_          numeric(22,8) comment '硬盘使用',
   CPU_USAGE_           numeric(22,8) comment 'CPU使用',
   LOCK_                VARCHAR(64) comment '锁定:Y锁定/N未锁定',
   LOCK_INSTANCE_ID_    VARCHAR(64) comment '实例ID',
   LOCK_TIME_           datetime comment '锁定时间',
   LOCK_EXPIRE_TIME_    datetime comment '超时时间',
   TENANT_ID_           varchar(64) comment '租户',
   CREATED_BY_ID_       varchar(64) comment '创建人ID',
   CREATED_BY_NAME_     varchar(64) comment '创建人名称',
   CREATED_TIME_        datetime comment '创建时间',
   DELFLAG_             varchar(1) comment '逻辑删除标记',
   DISPLAY_ORDER_       int comment '显示序号',
   MODIFIED_BY_ID_      varchar(64) comment '修改人ID',
   MODIFIED_BY_NAME_    varchar(64) comment '修改人名称',
   MODIFIED_TIME_       timestamp comment '修改时间',
   VERSION_             int comment '版本',
   primary key (CODE_)
);

alter table SCH_NODE_STATE comment '集群节点状态表';

alter table SCH_JOB_ENTITY add constraint FK_Reference_5_SCH_JEP  foreign key (GROUP_CODE_)
      references SCH_JOB_GROUP (CODE_);

alter table SCH_JOB_GROUP_CLUSTER add constraint FK_Reference_2_SCH_JEP  foreign key (CLUSTER_CODE_)
      references SCH_CLUSTER (CODE_);

alter table SCH_JOB_GROUP_CLUSTER add constraint FK_Reference_3_SCH_JEP  foreign key (GROUP_CODE_)
      references SCH_JOB_GROUP (CODE_);

alter table SCH_JOB_INST add constraint FK_Reference_6_SCH_JEP  foreign key (JOB_CODE_)
      references SCH_JOB_ENTITY (CODE_);

alter table SCH_JOB_LOG add constraint FK_Reference_7_SCH_JEP  foreign key (JOB_INST_ID_)
      references SCH_JOB_INST (ID_);

alter table SCH_NODE add constraint FK_Reference_1_SCH_JEP  foreign key (CLUSTER_CODE_)
      references SCH_CLUSTER (CODE_);

alter table SCH_NODE_STATE add constraint FK_Reference_4_SCH_JEP  foreign key (CODE_)
      references SCH_NODE (CODE_);

DROP TABLE IF EXISTS SCH_FIRED_TRIGGERS;
DROP TABLE IF EXISTS SCH_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS SCH_SCHEDULER_STATE;
DROP TABLE IF EXISTS SCH_LOCKS;
DROP TABLE IF EXISTS SCH_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS SCH_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS SCH_CRON_TRIGGERS;
DROP TABLE IF EXISTS SCH_BLOB_TRIGGERS;
DROP TABLE IF EXISTS SCH_TRIGGERS;
DROP TABLE IF EXISTS SCH_JOB_DETAILS;
DROP TABLE IF EXISTS SCH_CALENDARS;


CREATE TABLE SCH_JOB_DETAILS
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    JOB_NAME  VARCHAR(200) NOT NULL,
    JOB_GROUP VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(250) NULL,
    JOB_CLASS_NAME   VARCHAR(250) NOT NULL,
    IS_DURABLE VARCHAR(1) NOT NULL,
    IS_NONCONCURRENT VARCHAR(1) NOT NULL,
    IS_UPDATE_DATA VARCHAR(1) NOT NULL,
    REQUESTS_RECOVERY VARCHAR(1) NOT NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
);

CREATE TABLE SCH_TRIGGERS
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    JOB_NAME  VARCHAR(200) NOT NULL,
    JOB_GROUP VARCHAR(200) NOT NULL,
    DESCRIPTION VARCHAR(250) NULL,
    NEXT_FIRE_TIME BIGINT(13) NULL,
    PREV_FIRE_TIME BIGINT(13) NULL,
    PRIORITY INTEGER NULL,
    TRIGGER_STATE VARCHAR(16) NOT NULL,
    TRIGGER_TYPE VARCHAR(8) NOT NULL,
    START_TIME BIGINT(13) NOT NULL,
    END_TIME BIGINT(13) NULL,
    CALENDAR_NAME VARCHAR(200) NULL,
    MISFIRE_INSTR SMALLINT(2) NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
        REFERENCES SCH_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);

CREATE TABLE SCH_SIMPLE_TRIGGERS
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    REPEAT_COUNT BIGINT(7) NOT NULL,
    REPEAT_INTERVAL BIGINT(12) NOT NULL,
    TIMES_TRIGGERED BIGINT(10) NOT NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES SCH_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE SCH_CRON_TRIGGERS
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    CRON_EXPRESSION VARCHAR(200) NOT NULL,
    TIME_ZONE_ID VARCHAR(80),
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES SCH_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE SCH_SIMPROP_TRIGGERS
  (          
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INT NULL,
    INT_PROP_2 INT NULL,
    LONG_PROP_1 BIGINT NULL,
    LONG_PROP_2 BIGINT NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR(1) NULL,
    BOOL_PROP_2 VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP) 
    REFERENCES SCH_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE SCH_BLOB_TRIGGERS
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    BLOB_DATA BLOB NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
        REFERENCES SCH_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);

CREATE TABLE SCH_CALENDARS
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    CALENDAR_NAME  VARCHAR(200) NOT NULL,
    CALENDAR BLOB NOT NULL,
    PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
);

CREATE TABLE SCH_PAUSED_TRIGGER_GRPS
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_GROUP  VARCHAR(200) NOT NULL, 
    PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
);

CREATE TABLE SCH_FIRED_TRIGGERS
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    ENTRY_ID VARCHAR(95) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    INSTANCE_NAME VARCHAR(200) NOT NULL,
    FIRED_TIME BIGINT(13) NOT NULL,
    SCHED_TIME BIGINT(13) NOT NULL,
    PRIORITY INTEGER NOT NULL,
    STATE VARCHAR(16) NOT NULL,
    JOB_NAME VARCHAR(200) NULL,
    JOB_GROUP VARCHAR(200) NULL,
    IS_NONCONCURRENT VARCHAR(1) NULL,
    REQUESTS_RECOVERY VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME,ENTRY_ID)
);

CREATE TABLE SCH_SCHEDULER_STATE
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    INSTANCE_NAME VARCHAR(200) NOT NULL,
    LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
    CHECKIN_INTERVAL BIGINT(13) NOT NULL,
    PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
);

CREATE TABLE SCH_LOCKS
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    LOCK_NAME  VARCHAR(40) NOT NULL, 
    PRIMARY KEY (SCHED_NAME,LOCK_NAME)
);

INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sch', '调度管理', NULL, NULL, '#', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 40, NULL, NULL, '2017-10-27 16:32:11', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sch.100', '集群管理', NULL, 'sch', 'sch/cluster/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 10, NULL, NULL, '2017-10-27 16:35:06', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sch.200', '节点管理', NULL, 'sch', 'sch/node/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 20, NULL, NULL, '2017-10-27 16:35:15', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sch.300', '任务类型', NULL, 'sch', 'sch/jobGroup/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 30, NULL, NULL, '2017-11-29 10:40:40', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sch.400', '日历管理', NULL, 'sch', 'sch/calendarDetail/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 40, NULL, NULL, '2017-10-27 16:52:28', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sch.500', '任务调度', NULL, 'sch', 'sch/jobEntity/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 50, NULL, NULL, '2017-10-27 16:38:59', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sch.600', '监控中心', NULL, 'sch', '#', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 60, NULL, NULL, '2017-10-27 16:39:36', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sch.600.100', '节点监控', NULL, 'sch.600', 'sch/nodeState/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 10, NULL, NULL, '2017-11-3 09:42:31', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sch.600.200', '实例监控', NULL, 'sch.600', 'sch/jobInst/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 20, NULL, NULL, '2017-11-17 09:15:21', NULL);
