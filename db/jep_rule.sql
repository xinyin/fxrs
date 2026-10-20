/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50704
Source Host           : localhost:3306
Source Database       : jep

Target Server Type    : MYSQL
Target Server Version : 50704
File Encoding         : 65001

Date: 2018-03-14 09:23:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rul_case
-- ----------------------------
DROP TABLE IF EXISTS `rul_case`;
CREATE TABLE `rul_case` (
  `ID_` varchar(64) NOT NULL,
  `CASE_NO_` varchar(64) DEFAULT NULL COMMENT '案件编号',
  `WTF_` varchar(64) DEFAULT NULL COMMENT '委托方',
  `CUS_NAME_` varchar(64) DEFAULT NULL COMMENT '客户姓名',
  `CUS_PHONE_` varchar(64) DEFAULT NULL COMMENT '手机号',
  `CUS_PROVINCE_` varchar(64) DEFAULT NULL COMMENT '省份',
  `CUS_CITY_` varchar(64) DEFAULT NULL COMMENT '城市',
  `CUS_DATE_` int(11) DEFAULT NULL COMMENT '委托日期',
  `EXCEED_DAYS_` varchar(64) DEFAULT NULL COMMENT '逾期天数',
  `AMONT_` decimal(16,2) DEFAULT NULL COMMENT '案件金额',
  `SCORE_` decimal(5,2) DEFAULT NULL COMMENT '评分',
  `DEAL_MAN_` varchar(64) DEFAULT NULL COMMENT '处理人',
  `STATE_` varchar(64) DEFAULT NULL COMMENT '状态',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='DEMO_RULE_CASE';

-- ----------------------------
-- Records of rul_case
-- ----------------------------
INSERT INTO `rul_case` VALUES ('1', '1', '中国银行', '李涛', '187212131231', '四川', '成都', '2017', '123', '10000.00', '110.00', '催收员1', '0', null, null, null, null, null, null, 'jepAdmin', 'jepAdmin', '2018-03-07 21:58:17.000000', null);
INSERT INTO `rul_case` VALUES ('22ef2e9d-2443-11e8-a746-00ffaa1f36b9', '3', '城投1', '张三', '13409568753', '四川', '成都', '20170819', '6', '120.00', '0.00', '催收员2', 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:44.608000', null);
INSERT INTO `rul_case` VALUES ('22fe2fde-2443-11e8-a746-00ffaa1f36b9', '4', '城投2', '李四', '13409568754', '四川', '成都', '20170820', '101', '1111.00', '0.00', '催收员1', 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:44.717000', null);
INSERT INTO `rul_case` VALUES ('230dde91-2443-11e8-a746-00ffaa1f36b9', '5', '城投3', '刘三', '13409568755', '四川', '成都', '20170821', '102', '4000.00', '0.00', '催收员1', 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:44.810000', null);
INSERT INTO `rul_case` VALUES ('23199dee-2443-11e8-a746-00ffaa1f36b9', '6', '城投4', '王五', '13409568756', '四川', '成都', '20170822', '8', '8000.00', '0.00', '催收员1', 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:44.888000', null);
INSERT INTO `rul_case` VALUES ('2323aabe-2443-11e8-a746-00ffaa1f36b9', '7', '城投5', '张德', '13409568757', '四川', '成都', '20170823', '12', '12356.00', '0.00', null, 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:44.952224', null);
INSERT INTO `rul_case` VALUES ('232c3ad3-2443-11e8-a746-00ffaa1f36b9', '8', '城投6', '陈坤', '13409568758', '四川', '成都', '20170824', '105', '4567.00', '0.00', '催收员1', 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:45.010000', null);
INSERT INTO `rul_case` VALUES ('2334bf73-2443-11e8-a746-00ffaa1f36b9', '9', '城投7', '刘三姐', '13409568759', '四川', '成都', '20170825', '10', '120006.00', '0.00', null, 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:45.063254', null);
INSERT INTO `rul_case` VALUES ('233b77a6-2443-11e8-a746-00ffaa1f36b9', '10', '城投8', '张麻子', '13409568760', '四川', '成都', '20170826', '107', '6753.00', '0.00', '催收员1', 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:45.107000', null);
INSERT INTO `rul_case` VALUES ('23423fa5-2443-11e8-a746-00ffaa1f36b9', '11', '城投9', '李小二', '13409568761', '四川', '成都', '20170827', '42', '500.00', '0.00', null, 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:45.155000', null);
INSERT INTO `rul_case` VALUES ('234c4090-2443-11e8-a746-00ffaa1f36b9', '12', '城投10', '刘四五', '13409568762', '四川', '成都', '20170828', '21', '679.00', '0.00', null, 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:45.220000', null);
INSERT INTO `rul_case` VALUES ('235395b5-2443-11e8-a746-00ffaa1f36b9', '13', '城投11', '陈八九', '13409568763', '四川', '成都', '20170829', '110', '764.00', '0.00', null, 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:45.265000', null);
INSERT INTO `rul_case` VALUES ('235d45a0-2443-11e8-a746-00ffaa1f36b9', '14', '城投12', '六七十', '13409568764', '四川', '成都', '20170830', '111', '2532.00', '0.00', '催收员1', 'DRAFT', null, null, null, null, null, null, null, null, '2018-03-10 17:12:45.328000', null);

-- ----------------------------
-- Table structure for rul_functions
-- ----------------------------
DROP TABLE IF EXISTS `rul_functions`;
CREATE TABLE `rul_functions` (
  `ID_` varchar(64) NOT NULL,
  `CODE_` varchar(64) DEFAULT NULL COMMENT '英文名称',
  `NAME_` varchar(128) DEFAULT NULL COMMENT '名称',
  `CONTENT_` varchar(1024) DEFAULT NULL COMMENT '内容',
  `REMARK_` varchar(512) DEFAULT NULL COMMENT '描述',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义函数库';

-- ----------------------------
-- Records of rul_functions
-- ----------------------------

-- ----------------------------
-- Table structure for rul_model_rule_score
-- ----------------------------
DROP TABLE IF EXISTS `rul_model_rule_score`;
CREATE TABLE `rul_model_rule_score` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `PKG_ID_` varchar(64) DEFAULT NULL COMMENT '包ID',
  `MODEL_ID_` varchar(64) DEFAULT NULL COMMENT '评分模型ID',
  `RULE_ID_` varchar(64) DEFAULT NULL COMMENT '规则ID',
  `RULE_CODE_` varchar(64) DEFAULT NULL COMMENT '编码',
  `RULE_NAME_` varchar(64) DEFAULT NULL COMMENT '规则名称',
  `MAX_VALUE_` decimal(10,2) DEFAULT NULL COMMENT '最大阈值',
  `MIN_VALUE_` decimal(10,2) DEFAULT NULL COMMENT '最小阈值',
  `NAME_` varchar(64) DEFAULT NULL COMMENT '名称',
  `SCORE_` decimal(10,2) DEFAULT NULL COMMENT '分值',
  `REMARK_` varchar(256) DEFAULT NULL COMMENT '备注',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`),
  KEY `FK_REFERENCE_8` (`MODEL_ID_`),
  CONSTRAINT `FK_REFERENCE_8` FOREIGN KEY (`MODEL_ID_`) REFERENCES `rul_score_model` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模型规则评分表';

-- ----------------------------
-- Records of rul_model_rule_score
-- ----------------------------
INSERT INTO `rul_model_rule_score` VALUES ('3611b540-21ec-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', 'ceb68ff7-21e7-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', 'R001', 'R001', '100.00', '10.00', null, '60.00', '10天至100天等分60', null, null, null, null, null, null, null, null, '2018-03-07 18:30:49.877890', null);
INSERT INTO `rul_model_rule_score` VALUES ('58eedf8b-21ec-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', 'ceb68ff7-21e7-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', 'R002', 'R002', '5000.00', '1000.00', null, '20.00', '1000-5000得分20', null, null, null, null, null, null, null, null, '2018-03-07 18:33:04.341396', null);
INSERT INTO `rul_model_rule_score` VALUES ('6efd058e-21ec-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', 'ceb68ff7-21e7-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', 'R002', 'R002', '10000.00', '5001.00', null, '30.00', '5000-10000得分30', null, null, null, null, null, null, null, null, '2018-03-07 18:33:05.687289', null);
INSERT INTO `rul_model_rule_score` VALUES ('7e7a66bb-21f2-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', 'ceb68ff7-21e7-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', 'R001', '逾期天数大于3天', '999.00', '100.00', null, '70.00', '大于100天分值为70分', null, null, null, null, null, null, null, null, '2018-03-07 18:29:01.523548', null);
INSERT INTO `rul_model_rule_score` VALUES ('802b3537-21ec-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', 'ceb68ff7-21e7-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', 'R002', 'R002', '999999.00', '10000.00', null, '40.00', '10000以上得分40', null, null, null, null, null, null, null, null, '2018-03-07 18:33:09.295799', null);
INSERT INTO `rul_model_rule_score` VALUES ('ceb8497e-21e7-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', 'ceb68ff7-21e7-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', 'R001', null, '10.00', '4.00', null, '50.00', '4-10天得分50', null, null, null, null, null, null, null, null, '2018-03-07 18:31:23.749325', null);

-- ----------------------------
-- Table structure for rul_objects
-- ----------------------------
DROP TABLE IF EXISTS `rul_objects`;
CREATE TABLE `rul_objects` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `CODE_` varchar(64) DEFAULT NULL COMMENT '对象名称英文',
  `NAME_` varchar(128) DEFAULT NULL COMMENT '对象名称中文',
  `DATA_TYPE_` varchar(64) DEFAULT NULL COMMENT '数据类型：String、Int等等',
  `OBJ_TYPE_` varchar(64) DEFAULT NULL COMMENT '实体表、虚拟表',
  `IN_OUT_` varchar(64) DEFAULT NULL COMMENT '传递方向：仅传入、仅传出、传入传出',
  `GROUP_` varchar(64) DEFAULT NULL COMMENT '分组',
  `REMARK_` varchar(512) DEFAULT NULL COMMENT '描述',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对象库';

-- ----------------------------
-- Records of rul_objects
-- ----------------------------
INSERT INTO `rul_objects` VALUES ('0f100bf8-21e2-11e8-a25c-0a0027000006', 'cusDate', '委托日期', 'STRING', null, 'INPUT', '案件管理', '委托日期', null, null, null, null, null, null, null, null, '2018-03-07 16:31:22.651100', null);
INSERT INTO `rul_objects` VALUES ('293915ab-21e1-11e8-a25c-0a0027000006', 'exceedDays', '逾期天数', 'INTEGER', 'ENTITY', 'INPUT', '案件管理', '逾期天数', null, null, null, null, null, null, null, null, '2018-03-07 16:24:57.046218', null);
INSERT INTO `rul_objects` VALUES ('437f21c1-21e2-11e8-a25c-0a0027000006', 'amont', '案件金额', 'DOUBLE', null, 'INPUT', '案件管理', '案件金额', null, null, null, null, null, null, null, null, '2018-03-07 16:32:50.620425', null);
INSERT INTO `rul_objects` VALUES ('5d8589ff-21e2-11e8-a25c-0a0027000006', 'score', '评分', 'DOUBLE', null, 'OUTPUT', '案件管理', '评分', null, null, null, null, null, null, null, null, '2018-03-07 16:33:34.283239', null);
INSERT INTO `rul_objects` VALUES ('716b22fe-21e2-11e8-a25c-0a0027000006', 'dealMan', '处理人', 'STRING', null, 'ALL', '案件管理', '处理人', null, null, null, null, null, null, null, null, '2018-03-07 16:34:07.664439', null);
INSERT INTO `rul_objects` VALUES ('9a7d57db-21e2-11e8-a25c-0a0027000006', 'state', '状态', 'STRING', null, 'INPUT', '案件管理', '状态', null, null, null, null, null, null, null, null, '2018-03-07 16:35:16.570415', null);
INSERT INTO `rul_objects` VALUES ('c4b6d623-21e1-11e8-a25c-0a0027000006', 'caseNo', '案件编号', 'STRING', null, 'INPUT', '案件管理', '案件编号', null, null, null, null, null, null, null, null, '2018-03-07 16:29:17.915401', null);
INSERT INTO `rul_objects` VALUES ('d38b36ae-21e1-11e8-a25c-0a0027000006', 'cusName', '客户姓名', 'STRING', null, 'INPUT', '案件管理', '客户姓名', null, null, null, null, null, null, null, null, '2018-03-07 16:29:42.794626', null);
INSERT INTO `rul_objects` VALUES ('def54848-21e1-11e8-a25c-0a0027000006', 'cusPhone', '手机号', 'STRING', null, 'INPUT', '案件管理', '客户手机号', null, null, null, null, null, null, null, null, '2018-03-07 16:30:01.944782', null);

-- ----------------------------
-- Table structure for rul_packages
-- ----------------------------
DROP TABLE IF EXISTS `rul_packages`;
CREATE TABLE `rul_packages` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `CODE_` varchar(64) DEFAULT NULL COMMENT '英文名称',
  `NAME_` varchar(256) DEFAULT NULL COMMENT '中文名称',
  `TYPE_` varchar(64) DEFAULT NULL COMMENT '是否评分',
  `SCORE_MODEL_` varchar(64) DEFAULT NULL COMMENT '评分模型',
  `STATE_` varchar(64) DEFAULT NULL COMMENT '状态',
  `REMARK_` varchar(512) DEFAULT NULL COMMENT '备注',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则集';

-- ----------------------------
-- Records of rul_packages
-- ----------------------------
INSERT INTO `rul_packages` VALUES ('29af18ae-21e5-11e8-a25c-0a0027000006', 'CASE_SCORE', '案件评分模型', 'Y', 'ceb68ff7-21e7-11e8-a25c-0a0027000006', 'DEPLOY', '案件评分', null, null, null, null, null, null, 'jepAdmin', 'jepAdmin', '2018-03-09 10:04:15.000000', null);
INSERT INTO `rul_packages` VALUES ('442045e0-243a-11e8-a746-00ffaa1f36b9', 'CASE_ASSIGN', '按金额进行分派', 'N', null, 'DEPLOY', '按金额进行分派', null, 'jepAdmin', 'jepAdmin', '2018-03-10 16:09:13', 'N', null, 'jepAdmin', 'jepAdmin', '2018-03-10 16:28:43.000000', '3');

-- ----------------------------
-- Table structure for rul_pkg_content
-- ----------------------------
DROP TABLE IF EXISTS `rul_pkg_content`;
CREATE TABLE `rul_pkg_content` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `PKG_ID_` varchar(64) DEFAULT NULL COMMENT '模型ID',
  `PKG_CODE_` varchar(64) DEFAULT NULL COMMENT '模型编码',
  `PKG_NAME_` varchar(64) DEFAULT NULL COMMENT '模型名称',
  `CONTENT_` longtext COMMENT '模型内容',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则模型内容表';

-- ----------------------------
-- Records of rul_pkg_content
-- ----------------------------
INSERT INTO `rul_pkg_content` VALUES ('1f3b157c-21e6-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', 'CASE_SCORE', '案件评分模型', '/**\n * 案件评分模型\n * 案件评分\n */\npackage com.yzj.rules.CASE_SCORE;\n\nimport java.util.*;\nimport com.yzj.jep.rules.model.ScoreResult;\nimport function com.yzj.jep.rules.drools5.functions.Conversion.toInt;\nimport function com.yzj.jep.rules.drools5.functions.Conversion.toStr;\nimport function com.yzj.jep.rules.drools5.functions.Conversion.toDate;\nimport function com.yzj.jep.rules.drools5.functions.Conversion.toDouble;\nimport function com.yzj.jep.rules.drools5.functions.Conversion.toLong;\nglobal java.util.ArrayList resultList;\nglobal java.util.HashMap resultMap;\n\n/**\n * 逾期天数大于3天\n * null\n */\nrule \"R001\"\nwhen\n	$map:Map();\n	eval( toInt($map.get(\"exceedDays\")) > 3)\nthen\n  	resultList.add(new ScoreResult(\"R001\",toStr($map.get(\"exceedDays\")),\"0\"));\nend\n\n/**\n * 金额大于1000\n * null\n */\nrule \"R002\"\nwhen\n  $map:Map();\n  and eval(toDouble($map.get(\"amont\")) >= 1000)\nthen\n  resultList.add(new ScoreResult(\"R002\",toStr($map.get(\"amont\")),\"0\"));\nend\n\n', null, null, null, null, null, null, 'jepAdmin', 'jepAdmin', '2018-03-09 10:04:15.000000', null);
INSERT INTO `rul_pkg_content` VALUES ('5b1a848e-243a-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', 'CASE_ASSIGN', '按金额进行分派', '/**\n * 按金额进行分派\n * 按金额进行分派\n */\npackage com.yzj.rules.CASE_ASSIGN;\n\nimport java.util.*;\nimport com.yzj.jep.rules.model.ScoreResult;\nimport function com.yzj.jep.rules.drools5.functions.Conversion.toInt;\nimport function com.yzj.jep.rules.drools5.functions.Conversion.toStr;\nimport function com.yzj.jep.rules.drools5.functions.Conversion.toDate;\nimport function com.yzj.jep.rules.drools5.functions.Conversion.toDouble;\nimport function com.yzj.jep.rules.drools5.functions.Conversion.toLong;\nglobal java.util.ArrayList resultList;\nglobal java.util.HashMap resultMap;\n\n/**\n * 按案件金额分配案件\n * null\n */\nrule \"R003\"\nwhen\n$map:Map();\n eval( toDouble($map.get(\"amont\")) >= 1000)\nthen\n $map.put(\"dealMan\",\"催收员1\");\n\nend\n\n/**\n * 按金额分配案件（金额小于1000）\n * null\n */\nrule \"R004\"\nwhen\n$map:Map();\neval(toDouble($map.get(\"amont\")) <= 1000)\nthen\n\n$map.put(\"dealMan\",\"催收员2\");\n/*resultList.add($map);*/\n\nend\n\n', null, 'jepAdmin', 'jepAdmin', '2018-03-10 16:09:51', 'N', null, 'jepAdmin', 'jepAdmin', '2018-03-10 16:28:43.000000', '1');

-- ----------------------------
-- Table structure for rul_pkg_rule_map
-- ----------------------------
DROP TABLE IF EXISTS `rul_pkg_rule_map`;
CREATE TABLE `rul_pkg_rule_map` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `PKG_ID_` varchar(64) DEFAULT NULL COMMENT '规则集ID',
  `RULE_ID_` varchar(64) DEFAULT NULL COMMENT '规则ID',
  `RULE_NAME_` varchar(255) DEFAULT NULL,
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`),
  KEY `FK_REFERENCE_4` (`PKG_ID_`),
  KEY `FK_REFERENCE_5` (`RULE_ID_`),
  CONSTRAINT `FK_REFERENCE_4` FOREIGN KEY (`PKG_ID_`) REFERENCES `rul_packages` (`ID_`),
  CONSTRAINT `FK_REFERENCE_5` FOREIGN KEY (`RULE_ID_`) REFERENCES `rul_rules` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则集和规则映射';

-- ----------------------------
-- Records of rul_pkg_rule_map
-- ----------------------------
INSERT INTO `rul_pkg_rule_map` VALUES ('2d737046-21e5-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', '逾期天数大于3天', null, null, null, null, null, null, null, null, '2018-03-07 16:53:42.123779', null);
INSERT INTO `rul_pkg_rule_map` VALUES ('2d740bc5-21e5-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', '金额大于1000', null, null, null, null, null, null, null, null, '2018-03-07 16:53:42.127786', null);
INSERT INTO `rul_pkg_rule_map` VALUES ('4bdbef39-243a-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', '1c6d478e-2432-11e8-a746-00ffaa1f36b9', '按案件金额分配案件', null, 'jepAdmin', 'jepAdmin', '2018-03-10 16:09:26', 'N', null, null, null, '2018-03-10 16:09:27.803234', '0');
INSERT INTO `rul_pkg_rule_map` VALUES ('4be36ddd-243a-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', '64a0f0dd-2436-11e8-a746-00ffaa1f36b9', '按金额分配案件（金额小于1000）', null, 'jepAdmin', 'jepAdmin', '2018-03-10 16:09:26', 'N', null, null, null, '2018-03-10 16:09:27.863393', '0');

-- ----------------------------
-- Table structure for rul_rule_object_map
-- ----------------------------
DROP TABLE IF EXISTS `rul_rule_object_map`;
CREATE TABLE `rul_rule_object_map` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `RULE_ID_` varchar(64) DEFAULT NULL COMMENT '规则主键',
  `OBJ_ID_` varchar(64) DEFAULT NULL COMMENT '对像主键',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`),
  KEY `FK_REFERENCE_6` (`RULE_ID_`),
  KEY `FK_REFERENCE_7` (`OBJ_ID_`),
  CONSTRAINT `FK_REFERENCE_6` FOREIGN KEY (`RULE_ID_`) REFERENCES `rul_rules` (`ID_`),
  CONSTRAINT `FK_REFERENCE_7` FOREIGN KEY (`OBJ_ID_`) REFERENCES `rul_objects` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则对象映射表';

-- ----------------------------
-- Records of rul_rule_object_map
-- ----------------------------
INSERT INTO `rul_rule_object_map` VALUES ('62b459ae-2432-11e8-a746-00ffaa1f36b9', '1c6d478e-2432-11e8-a746-00ffaa1f36b9', '0f100bf8-21e2-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:12:48', 'N', null, null, null, '2018-03-10 15:12:48.119148', '0');
INSERT INTO `rul_rule_object_map` VALUES ('62bbfeea-2432-11e8-a746-00ffaa1f36b9', '1c6d478e-2432-11e8-a746-00ffaa1f36b9', '293915ab-21e1-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:12:48', 'N', null, null, null, '2018-03-10 15:12:48.170320', '0');
INSERT INTO `rul_rule_object_map` VALUES ('62beb73b-2432-11e8-a746-00ffaa1f36b9', '1c6d478e-2432-11e8-a746-00ffaa1f36b9', '437f21c1-21e2-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:12:48', 'N', null, null, null, '2018-03-10 15:12:48.186837', '0');
INSERT INTO `rul_rule_object_map` VALUES ('62c131ee-2432-11e8-a746-00ffaa1f36b9', '1c6d478e-2432-11e8-a746-00ffaa1f36b9', '5d8589ff-21e2-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:12:48', 'N', null, null, null, '2018-03-10 15:12:48.204671', '0');
INSERT INTO `rul_rule_object_map` VALUES ('62d290c3-2432-11e8-a746-00ffaa1f36b9', '1c6d478e-2432-11e8-a746-00ffaa1f36b9', '716b22fe-21e2-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:12:48', 'N', null, null, null, '2018-03-10 15:12:48.316573', '0');
INSERT INTO `rul_rule_object_map` VALUES ('62d56946-2432-11e8-a746-00ffaa1f36b9', '1c6d478e-2432-11e8-a746-00ffaa1f36b9', '9a7d57db-21e2-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:12:48', 'N', null, null, null, '2018-03-10 15:12:48.336167', '0');
INSERT INTO `rul_rule_object_map` VALUES ('62d829d3-2432-11e8-a746-00ffaa1f36b9', '1c6d478e-2432-11e8-a746-00ffaa1f36b9', 'c4b6d623-21e1-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:12:48', 'N', null, null, null, '2018-03-10 15:12:48.352851', '0');
INSERT INTO `rul_rule_object_map` VALUES ('62dacd01-2432-11e8-a746-00ffaa1f36b9', '1c6d478e-2432-11e8-a746-00ffaa1f36b9', 'd38b36ae-21e1-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:12:48', 'N', null, null, null, '2018-03-10 15:12:48.372499', '0');
INSERT INTO `rul_rule_object_map` VALUES ('62ddad64-2432-11e8-a746-00ffaa1f36b9', '1c6d478e-2432-11e8-a746-00ffaa1f36b9', 'def54848-21e1-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:12:48', 'N', null, null, null, '2018-03-10 15:12:48.390025', '0');
INSERT INTO `rul_rule_object_map` VALUES ('6c68a73d-2436-11e8-a746-00ffaa1f36b9', '64a0f0dd-2436-11e8-a746-00ffaa1f36b9', '0f100bf8-21e2-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:41:42', 'N', null, null, null, '2018-03-10 15:41:42.387830', '0');
INSERT INTO `rul_rule_object_map` VALUES ('6c6d176d-2436-11e8-a746-00ffaa1f36b9', '64a0f0dd-2436-11e8-a746-00ffaa1f36b9', '293915ab-21e1-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:41:42', 'N', null, null, null, '2018-03-10 15:41:42.418250', '0');
INSERT INTO `rul_rule_object_map` VALUES ('6c700925-2436-11e8-a746-00ffaa1f36b9', '64a0f0dd-2436-11e8-a746-00ffaa1f36b9', '437f21c1-21e2-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:41:42', 'N', null, null, null, '2018-03-10 15:41:42.433969', '0');
INSERT INTO `rul_rule_object_map` VALUES ('6c72816c-2436-11e8-a746-00ffaa1f36b9', '64a0f0dd-2436-11e8-a746-00ffaa1f36b9', '5d8589ff-21e2-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:41:42', 'N', null, null, null, '2018-03-10 15:41:42.458309', '0');
INSERT INTO `rul_rule_object_map` VALUES ('6c762c39-2436-11e8-a746-00ffaa1f36b9', '64a0f0dd-2436-11e8-a746-00ffaa1f36b9', '716b22fe-21e2-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:41:42', 'N', null, null, null, '2018-03-10 15:41:42.475184', '0');
INSERT INTO `rul_rule_object_map` VALUES ('6c824411-2436-11e8-a746-00ffaa1f36b9', '64a0f0dd-2436-11e8-a746-00ffaa1f36b9', '9a7d57db-21e2-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:41:42', 'N', null, null, null, '2018-03-10 15:41:42.558401', '0');
INSERT INTO `rul_rule_object_map` VALUES ('6c85ccf0-2436-11e8-a746-00ffaa1f36b9', '64a0f0dd-2436-11e8-a746-00ffaa1f36b9', 'c4b6d623-21e1-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:41:43', 'N', null, null, null, '2018-03-10 15:41:42.577227', '0');
INSERT INTO `rul_rule_object_map` VALUES ('6c884b57-2436-11e8-a746-00ffaa1f36b9', '64a0f0dd-2436-11e8-a746-00ffaa1f36b9', 'd38b36ae-21e1-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:41:43', 'N', null, null, null, '2018-03-10 15:41:42.594917', '0');
INSERT INTO `rul_rule_object_map` VALUES ('6c8afcd5-2436-11e8-a746-00ffaa1f36b9', '64a0f0dd-2436-11e8-a746-00ffaa1f36b9', 'def54848-21e1-11e8-a25c-0a0027000006', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:41:43', 'N', null, null, null, '2018-03-10 15:41:42.614491', '0');
INSERT INTO `rul_rule_object_map` VALUES ('711e8f06-21e4-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', '0f100bf8-21e2-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:48:26.156031', null);
INSERT INTO `rul_rule_object_map` VALUES ('711f2f0a-21e4-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', '293915ab-21e1-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:48:26.160042', null);
INSERT INTO `rul_rule_object_map` VALUES ('711fafda-21e4-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', '437f21c1-21e2-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:48:26.162550', null);
INSERT INTO `rul_rule_object_map` VALUES ('7120114a-21e4-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', '5d8589ff-21e2-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:48:26.166066', null);
INSERT INTO `rul_rule_object_map` VALUES ('71215357-21e4-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', '716b22fe-21e2-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:48:26.174081', null);
INSERT INTO `rul_rule_object_map` VALUES ('7121ffcf-21e4-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', '9a7d57db-21e2-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:48:26.178592', null);
INSERT INTO `rul_rule_object_map` VALUES ('7122aea0-21e4-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', 'c4b6d623-21e1-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:48:26.198143', null);
INSERT INTO `rul_rule_object_map` VALUES ('71257949-21e4-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', 'd38b36ae-21e1-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:48:26.201151', null);
INSERT INTO `rul_rule_object_map` VALUES ('7125f7ec-21e4-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', 'def54848-21e1-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:48:26.204159', null);
INSERT INTO `rul_rule_object_map` VALUES ('f12d3f2e-21e2-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', '0f100bf8-21e2-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:37:42.007484', null);
INSERT INTO `rul_rule_object_map` VALUES ('f1306692-21e2-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', '293915ab-21e1-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:37:42.028038', null);
INSERT INTO `rul_rule_object_map` VALUES ('f131513b-21e2-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', '437f21c1-21e2-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:37:42.034054', null);
INSERT INTO `rul_rule_object_map` VALUES ('f131d78d-21e2-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', '5d8589ff-21e2-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:37:42.037076', null);
INSERT INTO `rul_rule_object_map` VALUES ('f1326866-21e2-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', '716b22fe-21e2-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:37:42.040572', null);
INSERT INTO `rul_rule_object_map` VALUES ('f132cfaf-21e2-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', '9a7d57db-21e2-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:37:42.043080', null);
INSERT INTO `rul_rule_object_map` VALUES ('f1335101-21e2-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', 'c4b6d623-21e1-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:37:42.047591', null);
INSERT INTO `rul_rule_object_map` VALUES ('f133e73d-21e2-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', 'd38b36ae-21e1-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:37:42.050598', null);
INSERT INTO `rul_rule_object_map` VALUES ('f1345a05-21e2-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', 'def54848-21e1-11e8-a25c-0a0027000006', null, null, null, null, null, null, null, null, '2018-03-07 16:37:42.053106', null);

-- ----------------------------
-- Table structure for rul_rules
-- ----------------------------
DROP TABLE IF EXISTS `rul_rules`;
CREATE TABLE `rul_rules` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `CODE_` varchar(64) DEFAULT NULL COMMENT '规则编码',
  `NAME_` varchar(256) DEFAULT NULL COMMENT '规则名称',
  `TYPE_` varchar(64) DEFAULT NULL COMMENT '规则类型：评分规则、一般规则',
  `CONTENT_` varchar(2048) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL COMMENT '规则优先级',
  `SALIENCE_` varchar(3) DEFAULT NULL,
  `NO_LOOP_` varchar(1) DEFAULT NULL COMMENT '是否循环执行',
  `DATE_EXPIRES_` varchar(22) DEFAULT NULL COMMENT '有效期',
  `DATE_EFFECTIVE_` varchar(22) DEFAULT NULL COMMENT '启用时间',
  `ENABLE_` varchar(1) DEFAULT NULL COMMENT '是否启用',
  `LOCK_ON_ACTIVE_` varchar(1) DEFAULT NULL COMMENT '确认只执行一次',
  `ACTIVATION_GROUP_` varchar(64) DEFAULT NULL COMMENT '互斥执行组',
  `AGENDA_GROUP_` varchar(64) DEFAULT NULL COMMENT '分组（设置分组后需要显示调用）',
  `AUTO_FOCUS_` varchar(1) DEFAULT NULL COMMENT '自动获取焦点',
  `REMARK_` varchar(512) DEFAULT NULL COMMENT '备注',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则表';

-- ----------------------------
-- Records of rul_rules
-- ----------------------------
INSERT INTO `rul_rules` VALUES ('1c6d478e-2432-11e8-a746-00ffaa1f36b9', 'R003', '按案件金额分配案件', null, null, '100', null, null, null, null, null, null, null, null, null, '案件金额大于1000', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:10:50', 'N', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:32:53.000000', '1');
INSERT INTO `rul_rules` VALUES ('64a0f0dd-2436-11e8-a746-00ffaa1f36b9', 'R004', '按金额分配案件（金额小于1000）', null, null, '99', null, null, null, null, null, null, null, null, null, '案件金额小于1000', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:41:29', 'N', null, null, null, '2018-03-10 15:41:29.341148', '0');
INSERT INTO `rul_rules` VALUES ('c952a041-21e2-11e8-a25c-0a0027000006', 'R001', '逾期天数大于3天', null, null, '100', null, null, null, null, null, null, null, null, null, '逾期天数大于3天开始评分', null, null, null, null, null, null, null, null, '2018-03-07 16:36:35.143158', null);
INSERT INTO `rul_rules` VALUES ('e61ad5d5-21e2-11e8-a25c-0a0027000006', 'R002', '金额大于1000', null, null, '100', null, null, null, null, null, null, null, null, null, '金额大于1000开始评分', null, null, null, null, null, null, null, null, '2018-03-07 16:37:23.432137', null);

-- ----------------------------
-- Table structure for rul_rules_content
-- ----------------------------
DROP TABLE IF EXISTS `rul_rules_content`;
CREATE TABLE `rul_rules_content` (
  `ID_` varchar(64) NOT NULL COMMENT '交易ID',
  `RULE_ID_` varchar(64) NOT NULL,
  `CONTENT_` longtext COMMENT '规则内容',
  `HTML_LHS_` longtext COMMENT 'HTML内容',
  `HTML_RHS_` longtext COMMENT 'RHS',
  `LHS_INDEX_` int(11) DEFAULT NULL COMMENT 'LHS行索引',
  `RHS_INDEX_` int(11) DEFAULT NULL COMMENT 'RHS行数',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`),
  KEY `FK_RULE_CONTENT_RULEID` (`RULE_ID_`),
  CONSTRAINT `FK_RULE_CONTENT_RULEID` FOREIGN KEY (`RULE_ID_`) REFERENCES `rul_rules` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易规则文件';

-- ----------------------------
-- Records of rul_rules_content
-- ----------------------------
INSERT INTO `rul_rules_content` VALUES ('07a1def3-2437-11e8-a746-00ffaa1f36b9', '64a0f0dd-2436-11e8-a746-00ffaa1f36b9', 'rule \"R004\"\nwhen\n$map:Map();\neval(toDouble($map.get(\"amont\")) <= 1000)\nthen\n\n$map.put(\"dealMan\",\"催收员2\");\n/*resultList.add($map);*/\n\nend', '<div id=\"row_1\" rowindex=\"1\"><a id=\"logic_1\" class=\"logic\" code=\"\" onclick=\"showLogic(this)\" data-original-title=\"\" title=\"\">并且</a><a id=\"obj_1\" class=\"obj\" code=\"\" onclick=\"showObjects(this)\" data-original-title=\"\" title=\"\">案件金额</a><a id=\"operate1\" class=\"operate\" code=\"==\" onclick=\"showOperate(this)\" data-original-title=\"\" title=\"\">小于等于</a><a id=\"value1\" class=\"vals\" code=\"\" onclick=\"showValue(this)\" data-original-title=\"\" title=\"\">1000</a><a class=\"remove\" code=\"\" onclick=\"deleteRow(\'\')\"><i class=\"fa fa-times\" title=\"删除这个条件\"></i></a></div>', '<div id=\"rhsRow_1\" rowindex=\"1\"><a id=\"action_1\" class=\"logic\" code=\"\" onclick=\"showActions(this)\" data-original-title=\"\" title=\"\">对象赋值</a><a id=\"obj_2\" class=\"obj\" code=\"\" onclick=\"showObjects(this)\" data-original-title=\"\" title=\"\">处理人</a><a id=\"operate2\" class=\"operate\" code=\"==\" onclick=\"showOperate(this)\" data-original-title=\"\" title=\"\">等于</a><a id=\"value2\" class=\"vals\" code=\"\" onclick=\"showValue(this)\" data-original-title=\"\" title=\"\">催收员2</a><a class=\"remove\" code=\"\" onclick=\"deleteRow(\'rhs\')\"><i class=\"fa fa-times\" title=\"删除这个条件\"></i></a></div>', '1', '1', null, null, null, null, null, null, null, null, '2018-03-10 17:22:13.751172', null);
INSERT INTO `rul_rules_content` VALUES ('12339337-21e5-11e8-a25c-0a0027000006', 'e61ad5d5-21e2-11e8-a25c-0a0027000006', 'rule \"R002\"\nwhen\n  $map:Map();\n  and eval(toDouble($map.get(\"amont\")) >= 1000)\nthen\n  resultList.add(new ScoreResult(\"R002\",toStr($map.get(\"amont\")),\"0\"));\nend', '<div id=\"row_1\" rowindex=\"1\"><a id=\"logic_1\" class=\"logic\" code=\"\" onclick=\"showLogic(this)\" data-original-title=\"\" title=\"\">并且</a><a id=\"obj_1\" class=\"obj\" code=\"\" onclick=\"showObjects(this)\" data-original-title=\"\" title=\"\">案件金额</a><a id=\"operate1\" class=\"operate\" code=\"==\" onclick=\"showOperate(this)\" data-original-title=\"\" title=\"\">大于等于</a><a id=\"value1\" class=\"vals\" code=\"\" onclick=\"showValue(this)\" data-original-title=\"\" title=\"\">1000</a><a class=\"remove\" code=\"\" onclick=\"deleteRow(\'\')\"><i class=\"fa fa-times\" title=\"删除这个条件\"></i></a></div>', null, '2', '1', null, null, null, null, null, null, null, null, '2018-03-07 16:52:56.408189', null);
INSERT INTO `rul_rules_content` VALUES ('1e4a7d07-2433-11e8-a746-00ffaa1f36b9', '1c6d478e-2432-11e8-a746-00ffaa1f36b9', 'rule \"R003\"\nwhen\n$map:Map();\n eval( toDouble($map.get(\"amont\")) >= 1000)\nthen\n $map.put(\"dealMan\",\"催收员1\");\n\nend', '<div id=\"row_1\" rowindex=\"1\"><a id=\"logic_1\" class=\"logic\" code=\"\" onclick=\"showLogic(this)\" data-original-title=\"\" title=\"\">(</a><a id=\"obj_1\" class=\"obj\" code=\"\" onclick=\"showObjects(this)\" data-original-title=\"\" title=\"\">案件金额</a><a id=\"operate1\" class=\"operate\" code=\"==\" onclick=\"showOperate(this)\" data-original-title=\"\" title=\"\">大于等于</a><a id=\"value1\" class=\"vals\" code=\"\" onclick=\"showValue(this)\" data-original-title=\"\" title=\"\">1000</a><a class=\"remove\" code=\"\" onclick=\"deleteRow(\'\')\"><i class=\"fa fa-times\" title=\"删除这个条件\"></i></a></div>', '<div id=\"rhsRow_1\" rowindex=\"1\"><a id=\"action_1\" class=\"logic\" code=\"\" onclick=\"showActions(this)\" data-original-title=\"\" title=\"\">对象赋值</a><a id=\"obj_2\" class=\"obj\" code=\"\" onclick=\"showObjects(this)\" data-original-title=\"\" title=\"\">处理人</a><a id=\"operate2\" class=\"operate\" code=\"==\" onclick=\"showOperate(this)\" data-original-title=\"\" title=\"\">等于</a><a id=\"value2\" class=\"vals\" code=\"\" onclick=\"showValue(this)\" data-original-title=\"\" title=\"\">催收员1</a><a class=\"remove\" code=\"\" onclick=\"deleteRow(\'rhs\')\"><i class=\"fa fa-times\" title=\"删除这个条件\"></i></a></div>', '2', '2', null, 'jepAdmin', 'jepAdmin', '2018-03-10 15:18:03', 'N', null, null, null, '2018-03-10 15:18:02.840319', '0');
INSERT INTO `rul_rules_content` VALUES ('4ec2593b-21e4-11e8-a25c-0a0027000006', 'c952a041-21e2-11e8-a25c-0a0027000006', 'rule \"R001\"\nwhen\n	$map:Map();\n	eval( toInt($map.get(\"exceedDays\")) > 3)\nthen\n  	resultList.add(new ScoreResult(\"R001\",toStr($map.get(\"exceedDays\")),\"0\"));\nend', '<div id=\"row_1\" rowindex=\"1\"><a id=\"logic_1\" class=\"logic\" code=\"\" onclick=\"showLogic(this)\" data-original-title=\"\" title=\"\">并且</a><a id=\"obj_1\" class=\"obj\" code=\"\" onclick=\"showObjects(this)\" data-original-title=\"\" title=\"\">逾期天数</a><a id=\"operate1\" class=\"operate\" code=\"==\" onclick=\"showOperate(this)\" data-original-title=\"\" title=\"\">大于等于</a><a id=\"value1\" class=\"vals\" code=\"\" onclick=\"showValue(this)\" data-original-title=\"\" title=\"\">3</a><a class=\"remove\" code=\"\" onclick=\"deleteRow(\'\')\"><i class=\"fa fa-times\" title=\"删除这个条件\"></i></a></div>', null, '2', '1', null, null, null, null, null, null, null, null, '2018-03-07 16:47:28.509143', null);

-- ----------------------------
-- Table structure for rul_rules_log
-- ----------------------------
DROP TABLE IF EXISTS `rul_rules_log`;
CREATE TABLE `rul_rules_log` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `PKG_ID_` varchar(64) DEFAULT NULL COMMENT '模型ID',
  `PKG_MODEL_` varchar(64) DEFAULT NULL COMMENT '模型名称',
  `START_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '交易时间',
  `EXE_TIME_` int(11) DEFAULT NULL COMMENT '执行耗时(MS)',
  `REQ_DATA_` varchar(1024) DEFAULT NULL COMMENT '请求内容',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NULL DEFAULT NULL,
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='规则引擎的调用日志';

-- ----------------------------
-- Records of rul_rules_log
-- ----------------------------
INSERT INTO `rul_rules_log` VALUES ('0271ae8a-243d-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 16:28:52', '163', '{\"cusDate\":\"123\",\"exceedDays\":\"312\",\"amont\":\"123\",\"score\":\"123\",\"dealMan\":\"312\",\"state\":\"123\",\"caseNo\":\"123\",\"cusName\":\"123\",\"cusPhone\":\"123\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-10 16:28:51', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('05b8fb65-243d-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 16:28:57', '4', '{\"cusDate\":\"123\",\"exceedDays\":\"312\",\"amont\":\"123213\",\"score\":\"123\",\"dealMan\":\"312\",\"state\":\"123\",\"caseNo\":\"123\",\"cusName\":\"123\",\"cusPhone\":\"123\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-10 16:28:57', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('07439fa1-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:19:06', '2', '{\"amont\":679.0,\"caseNo\":\"12\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170828,\"cusName\":\"刘四五\",\"cusPhone\":\"13409568762\",\"cusProvince\":\"四川\",\"exceedDays\":\"21\",\"id\":\"234c4090-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673165220,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投10\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('115ef826-21f4-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:40:17', '103588', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('14b00c10-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:12:19', '2270', '{\"amont\":10000.0,\"caseNo\":\"1\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":2017,\"cusName\":\"李涛\",\"cusPhone\":\"187212131231\",\"cusProvince\":\"四川\",\"exceedDays\":\"123\",\"id\":\"1\",\"modifiedById\":\"jepAdmin\",\"modifiedByName\":\"jepAdmin\",\"modifiedTime\":1520431097000,\"modifiedTimeString\":\"2018-03-07\",\"score\":110.0,\"state\":\"0\",\"wtf\":\"中国银行\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('1ab17ab4-21f0-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:11:55', '3440', '{\"cusDate\":\"\",\"exceedDays\":\"13\",\"amont\":\"10000\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('1def66f6-2158-11e8-a25c-0a0027000006', '87879677-1eb5-11e8-a25c-0a0027000006', null, '2018-03-07 00:03:57', '26299', '{\"sex\":\"123\",\"age\":\"132\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('22811a6d-21f3-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:33:37', '7048', '{\"cusDate\":\"\",\"exceedDays\":\"12\",\"amont\":\"123\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('249651f4-21e6-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 17:00:37', '53', '{\"cusDate\":\"1\",\"exceedDays\":\"123\",\"amont\":\"123\",\"score\":\"123\",\"dealMan\":\"123\",\"state\":\"13\",\"caseNo\":\"123\",\"cusName\":\"123\",\"cusPhone\":\"123\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('25bab806-2158-11e8-a25c-0a0027000006', '87879677-1eb5-11e8-a25c-0a0027000006', null, '2018-03-07 00:04:10', '12', '{\"sex\":\"123\",\"age\":\"132\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('2a1197e7-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:12:55', '3', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('2b3108e4-233e-11e8-a9f3-00ffaa1f36b9', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-09 10:04:54', '109', '{\"cusDate\":\"321\",\"exceedDays\":\"32\",\"amont\":\"312312\",\"score\":\"123123\",\"dealMan\":\"1213\",\"state\":\"123\",\"caseNo\":\"123\",\"cusName\":\"32\",\"cusPhone\":\"321\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-09 10:04:54', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('2db8470f-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:13:01', '2', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('2df7c647-233e-11e8-a9f3-00ffaa1f36b9', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-09 10:04:59', '107', '{\"cusDate\":\"321\",\"exceedDays\":\"13\",\"amont\":\"312312\",\"score\":\"123123\",\"dealMan\":\"1213\",\"state\":\"123\",\"caseNo\":\"123\",\"cusName\":\"32\",\"cusPhone\":\"321\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-09 10:04:58', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('2ebac148-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:13:03', '2', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('2ed81aeb-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:13:03', '2', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('2ef56058-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:13:03', '2', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('2f837bad-21f0-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:12:30', '16229', '{\"cusDate\":\"\",\"exceedDays\":\"13\",\"amont\":\"10000\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('30ac2ecc-21e6-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 17:00:57', '32', '{\"cusDate\":\"1\",\"exceedDays\":\"123\",\"amont\":\"123\",\"score\":\"\",\"dealMan\":\"123\",\"state\":\"13\",\"caseNo\":\"123\",\"cusName\":\"123\",\"cusPhone\":\"123\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('32bf8a26-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:13:10', '4', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('32dc5e26-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:13:10', '1', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('330664be-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:13:10', '3', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('33369bc4-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:13:10', '2', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('33514daf-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:13:11', '2', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('351bf55a-21f4-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:41:17', '38', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('368e0bb1-2158-11e8-a25c-0a0027000006', '87879677-1eb5-11e8-a25c-0a0027000006', null, '2018-03-07 00:04:38', '38', '{\"sex\":\"123\",\"age\":\"132\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('39512d69-21f3-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:34:15', '21329', '{\"cusDate\":\"\",\"exceedDays\":\"12\",\"amont\":\"123\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('3984559b-2158-11e8-a25c-0a0027000006', '87879677-1eb5-11e8-a25c-0a0027000006', null, '2018-03-07 00:04:43', '28', '{\"sex\":\"123\",\"age\":\"132\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('3b0fd424-2158-11e8-a25c-0a0027000006', '87879677-1eb5-11e8-a25c-0a0027000006', null, '2018-03-07 00:04:46', '4', '{\"sex\":\"123\",\"age\":\"132\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('3bd726cc-2158-11e8-a25c-0a0027000006', '87879677-1eb5-11e8-a25c-0a0027000006', null, '2018-03-07 00:04:47', '2', '{\"sex\":\"123\",\"age\":\"132\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('3c0a118f-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:20:35', '4', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('40db0cd0-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:20:43', '2', '{\"amont\":4000.0,\"caseNo\":\"5\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170821,\"cusName\":\"刘三\",\"cusPhone\":\"13409568755\",\"cusProvince\":\"四川\",\"exceedDays\":\"102\",\"id\":\"230dde91-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164810,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投3\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('43118dbf-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:13:37', '2467', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('4afea89b-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:21:00', '32', '{\"cusDate\":\"\",\"exceedDays\":\"\",\"amont\":\"132\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('4dda687c-21f0-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:13:21', '48470', '{\"cusDate\":\"\",\"exceedDays\":\"13\",\"amont\":\"10000\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('4f6ca83d-2441-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 16:59:39', '156', '{\"cusDate\":\"123\",\"exceedDays\":\"123\",\"amont\":\"123\",\"score\":\"123\",\"dealMan\":\"213\",\"state\":\"132\",\"caseNo\":\"123\",\"cusName\":\"123\",\"cusPhone\":\"123\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('53e9ab43-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:14:05', '1', '{\"amont\":6753.0,\"caseNo\":\"10\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170826,\"cusName\":\"张麻子\",\"cusPhone\":\"13409568760\",\"cusProvince\":\"四川\",\"exceedDays\":\"107\",\"id\":\"233b77a6-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673165107,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投8\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('5964dc50-220f-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 21:55:35', '175', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"123\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-07 21:55:34', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('5bc494ef-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:14:18', '2', '{\"amont\":8000.0,\"caseNo\":\"6\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170822,\"cusName\":\"王五\",\"cusPhone\":\"13409568756\",\"cusProvince\":\"四川\",\"exceedDays\":\"8\",\"id\":\"23199dee-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164888,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投4\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('60d8c1cc-243a-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 16:10:01', '285', '{\"cusDate\":\"213\",\"exceedDays\":\"123\",\"amont\":\"12\",\"score\":\"123\",\"dealMan\":\"123\",\"state\":\"213\",\"caseNo\":\"132\",\"cusName\":\"123\",\"cusPhone\":\"321\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-10 16:10:01', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('60e66fb7-21f8-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 19:11:09', '7162', '{\"cusDate\":\"\",\"exceedDays\":\"1\",\"amont\":\"1\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('6231f659-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:21:39', '2', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('6446bfa1-21f8-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 19:11:15', '36', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"1\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('669ad034-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:14:37', '1', '{\"amont\":500.0,\"caseNo\":\"11\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170827,\"cusName\":\"李小二\",\"cusPhone\":\"13409568761\",\"cusProvince\":\"四川\",\"exceedDays\":\"42\",\"id\":\"23423fa5-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673165155,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投9\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('695acd8f-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:14:41', '3', '{\"amont\":500.0,\"caseNo\":\"11\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170827,\"cusName\":\"李小二\",\"cusPhone\":\"13409568761\",\"cusProvince\":\"四川\",\"exceedDays\":\"42\",\"id\":\"23423fa5-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673165155,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投9\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('6d445703-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:14:48', '2', '{\"amont\":4567.0,\"caseNo\":\"8\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170824,\"cusName\":\"陈坤\",\"cusPhone\":\"13409568758\",\"cusProvince\":\"四川\",\"exceedDays\":\"105\",\"id\":\"232c3ad3-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673165010,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投6\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('7ad278d1-220f-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 21:56:31', '12709', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"123\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-07 21:56:30', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('7cfe2313-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:22:24', '47', '{\"cusDate\":\"\",\"exceedDays\":\"\",\"amont\":\"132\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('80d73534-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:22:30', '3', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('838e66b9-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:22:35', '2', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('89177ac6-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:22:44', '3', '{\"amont\":10000.0,\"caseNo\":\"1\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":2017,\"cusName\":\"李涛\",\"cusPhone\":\"187212131231\",\"cusProvince\":\"四川\",\"dealMan\":\"催收员1\",\"exceedDays\":\"123\",\"id\":\"1\",\"modifiedById\":\"jepAdmin\",\"modifiedByName\":\"jepAdmin\",\"modifiedTime\":1520431097000,\"modifiedTimeString\":\"2018-03-07\",\"score\":110.0,\"state\":\"0\",\"wtf\":\"中国银行\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('8ba964f8-21f3-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:36:33', '28520', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('8d8f611b-21ec-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 17:46:30', '262', '{\"cusDate\":\"\",\"exceedDays\":\"5\",\"amont\":\"15000\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('8f45cf7b-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:22:54', '1', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('90781608-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:15:47', '3', '{\"amont\":500.0,\"caseNo\":\"11\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170827,\"cusName\":\"李小二\",\"cusPhone\":\"13409568761\",\"cusProvince\":\"四川\",\"exceedDays\":\"42\",\"id\":\"23423fa5-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673165155,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投9\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('94a95305-21f3-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:36:48', '12832', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('97f21f5b-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:15:59', '3', '{\"amont\":764.0,\"caseNo\":\"13\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170829,\"cusName\":\"陈八九\",\"cusPhone\":\"13409568763\",\"cusProvince\":\"四川\",\"exceedDays\":\"110\",\"id\":\"235395b5-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673165265,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投11\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('9ae2721e-2210-11e8-a9f3-00ffaa1f36b9', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 22:06:08', '159', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"123\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-07 22:06:08', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('a08eebf6-220f-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 21:57:34', '4017', '{\"amont\":10000.0,\"caseNo\":\"1\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":2017,\"cusName\":\"李涛\",\"cusPhone\":\"187212131231\",\"cusProvince\":\"四川\",\"exceedDays\":\"123\",\"id\":\"1\",\"modifiedTime\":1520430600927,\"modifiedTimeString\":\"2018-03-07\",\"score\":0.0,\"state\":\"0\",\"wtf\":\"中国银行\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-07 21:57:34', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('a0e1bb18-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:16:14', '2', '{\"amont\":679.0,\"caseNo\":\"12\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170828,\"cusName\":\"刘四五\",\"cusPhone\":\"13409568762\",\"cusProvince\":\"四川\",\"exceedDays\":\"21\",\"id\":\"234c4090-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673165220,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投10\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('a4959694-2442-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:09:11', '37', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"132\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('a966b093-21f4-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:44:33', '44', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('b1200c48-21ae-11e8-a25c-0a0027000006', 'SCORE_1', null, '2018-03-07 10:23:41', '3156', '{\"sex\":\"123\",\"age\":\"123\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('b2803c20-220f-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 21:58:04', '36', '{\"amont\":10000.0,\"caseNo\":\"1\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":2017,\"cusName\":\"李涛\",\"cusPhone\":\"187212131231\",\"cusProvince\":\"四川\",\"exceedDays\":\"123\",\"id\":\"1\",\"modifiedById\":\"jepAdmin\",\"modifiedByName\":\"jepAdmin\",\"modifiedTime\":1520431054000,\"modifiedTimeString\":\"2018-03-07\",\"score\":110.0,\"state\":\"0\",\"wtf\":\"中国银行\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-07 21:58:04', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('b6039971-21f4-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:44:54', '50', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('b64314e1-220f-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 21:58:11', '28', '{\"amont\":10000.0,\"caseNo\":\"1\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":2017,\"cusName\":\"李涛\",\"cusPhone\":\"187212131231\",\"cusProvince\":\"四川\",\"exceedDays\":\"123\",\"id\":\"1\",\"modifiedById\":\"jepAdmin\",\"modifiedByName\":\"jepAdmin\",\"modifiedTime\":1520431084000,\"modifiedTimeString\":\"2018-03-07\",\"score\":110.0,\"state\":\"0\",\"wtf\":\"中国银行\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-07 21:58:10', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('b6d32ebd-243a-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 16:12:26', '35', '{\"cusDate\":\"\",\"exceedDays\":\"213\",\"amont\":\"123\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-10 16:12:25', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('bb7f3168-21b4-11e8-a25c-0a0027000006', 'SCORE_1', null, '2018-03-07 11:06:55', '185', '{\"sex\":\"qweq\",\"age\":\"qwe\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('bf9be3cc-21f4-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:45:10', '52', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('c2bfedee-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:24:21', '93', '{\"amont\":120.0,\"caseNo\":\"3\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170819,\"cusName\":\"张三\",\"cusPhone\":\"13409568753\",\"cusProvince\":\"四川\",\"exceedDays\":\"6\",\"id\":\"22ef2e9d-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164608,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投1\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('c503b3a5-2444-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:24:24', '4', '{\"amont\":1111.0,\"caseNo\":\"4\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170820,\"cusName\":\"李四\",\"cusPhone\":\"13409568754\",\"cusProvince\":\"四川\",\"exceedDays\":\"101\",\"id\":\"22fe2fde-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673164717,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投2\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('c5e1a457-21ed-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 17:55:14', '29545', '{\"cusDate\":\"13123\",\"exceedDays\":\"9\",\"amont\":\"10000\",\"score\":\"\",\"dealMan\":\"123\",\"state\":\"123\",\"caseNo\":\"312\",\"cusName\":\"321\",\"cusPhone\":\"123\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('cadc600e-21f7-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 19:06:57', '46', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"211\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('ce0bf4d5-21f3-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:38:25', '68789', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('cfd7b008-21f7-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 19:07:06', '50', '{\"cusDate\":\"\",\"exceedDays\":\"10\",\"amont\":\"211\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('d0145c22-21f4-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:45:37', '75', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('d44c7a6e-21f7-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 19:07:13', '23', '{\"cusDate\":\"\",\"exceedDays\":\"2\",\"amont\":\"11111\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('d7b0eedb-21f7-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 19:07:19', '39', '{\"cusDate\":\"\",\"exceedDays\":\"1\",\"amont\":\"11111\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('d9530d42-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:17:49', '4', '{\"amont\":679.0,\"caseNo\":\"12\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170828,\"cusName\":\"刘四五\",\"cusPhone\":\"13409568762\",\"cusProvince\":\"四川\",\"exceedDays\":\"21\",\"id\":\"234c4090-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673165220,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投10\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('d984608d-21f4-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:45:53', '47', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('db1bd235-21f4-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:45:56', '6', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('db4a3909-21ee-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:02:59', '435970', '{\"cusDate\":\"13123\",\"exceedDays\":\"9\",\"amont\":\"10000\",\"score\":\"\",\"dealMan\":\"123\",\"state\":\"123\",\"caseNo\":\"312\",\"cusName\":\"321\",\"cusPhone\":\"123\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('dc51c55a-21f4-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:45:58', '35', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('dddabcb7-21f4-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:46:01', '23', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('df0985b5-21f4-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:46:03', '6', '{\"cusDate\":\"\",\"exceedDays\":\"123\",\"amont\":\"23\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('dfbd798c-2208-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 21:09:14', '199', '{\"cusDate\":\"\",\"exceedDays\":\"2313\",\"amont\":\"123\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, 'jepAdmin', 'jepAdmin', '2018-03-07 21:09:13', 'N', null, null, null, null, '0');
INSERT INTO `rul_rules_log` VALUES ('e1898f25-21ec-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 17:48:51', '61', '{\"cusDate\":\"\",\"exceedDays\":\"5\",\"amont\":\"15000\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('e5cf9881-2443-11e8-a746-00ffaa1f36b9', '442045e0-243a-11e8-a746-00ffaa1f36b9', null, '2018-03-10 17:18:10', '1', '{\"amont\":2532.0,\"caseNo\":\"14\",\"createTimeString\":\"\",\"cusCity\":\"成都\",\"cusDate\":20170830,\"cusName\":\"六七十\",\"cusPhone\":\"13409568764\",\"cusProvince\":\"四川\",\"exceedDays\":\"111\",\"id\":\"235d45a0-2443-11e8-a746-00ffaa1f36b9\",\"modifiedTime\":1520673165328,\"modifiedTimeString\":\"2018-03-10\",\"score\":0.0,\"state\":\"DRAFT\",\"wtf\":\"城投12\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('f3a98925-21f0-11e8-a25c-0a0027000006', '29af18ae-21e5-11e8-a25c-0a0027000006', null, '2018-03-07 18:17:59', '160106', '{\"cusDate\":\"\",\"exceedDays\":\"12\",\"amont\":\"23123\",\"score\":\"\",\"dealMan\":\"\",\"state\":\"\",\"caseNo\":\"\",\"cusName\":\"\",\"cusPhone\":\"\"}', null, null, null, null, null, null, null, null, null, null);
INSERT INTO `rul_rules_log` VALUES ('f3dfbea9-21a2-11e8-a25c-0a0027000006', '87879677-1eb5-11e8-a25c-0a0027000006', null, '2018-03-07 08:59:39', '204', '{\"sex\":\"123\",\"age\":\"12\"}', null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for rul_score_model
-- ----------------------------
DROP TABLE IF EXISTS `rul_score_model`;
CREATE TABLE `rul_score_model` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `CODE_` varchar(64) DEFAULT NULL COMMENT '交易ID',
  `NAME_` varchar(64) DEFAULT NULL COMMENT '交易编码',
  `RUL_PKG_ID_` varchar(64) DEFAULT NULL COMMENT '规则集ID',
  `RUL_PKG_NAME_` varchar(64) DEFAULT NULL COMMENT '规则集名称',
  `ALGORITHM_` varchar(64) DEFAULT NULL COMMENT '评分算法',
  `FUNCTION_ID_` varchar(64) DEFAULT NULL COMMENT '自定义算法ID',
  `REMARK_` varchar(256) DEFAULT NULL COMMENT '备注',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT NULL COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(10) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `VERSION_` int(10) DEFAULT NULL COMMENT '版本',
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易分值表';

-- ----------------------------
-- Records of rul_score_model
-- ----------------------------
INSERT INTO `rul_score_model` VALUES ('ceb68ff7-21e7-11e8-a25c-0a0027000006', 'PF001', '评分模型1', '29af18ae-21e5-11e8-a25c-0a0027000006', '案件评分模型', 'AVG', null, '案件评分计算模型1', null, null, null, null, null, null, null, null, '2018-03-07 17:12:31.670339', null);
