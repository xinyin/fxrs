-- ====================================================================================================================
-- ========================================复星保德信二期项目相关修改SQL汇总记录begin========================================
-- ====================================================================================================================

-- 修改机构基础数据表资本净额和总资产字段长度
ALTER TABLE bus_root_org_info MODIFY COLUMN net_captial DECIMAL (20,4) DEFAULT NULL COMMENT '资本净额';
ALTER TABLE bus_root_org_info MODIFY COLUMN total_capital DECIMAL (20,4) DEFAULT NULL COMMENT '总资产';
-- 金融产品添加投资者和底层资产
ALTER TABLE bus_rel_pro
    ADD COLUMN asset_desc VARCHAR(2000) DEFAULT NULL COMMENT '底层资产';
ALTER TABLE bus_rel_pro
    ADD COLUMN investors VARCHAR(2000) DEFAULT NULL COMMENT '投资者';

-- 金融产品字典码码值修改
DELETE
FROM sys_dict_item
WHERE DICT_CODE_ = 'BUS-pro';

INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`,
                           `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`,
                           `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`)
VALUES ('0a741990-9e64-11ea-960e-8cec4b4b2170', 'BUS-pro', 'SYS.BUS', '基金管理公司及其子公司资管产品', '04', NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-05-25 17:06:23', NULL);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`,
                           `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`,
                           `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`)
VALUES ('0c5f80a5-9e67-11ea-960e-8cec4b4b2170', 'BUS-pro', 'SYS.BUS', '期货公司及其子公司资管产品', '05', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-05-25 17:06:41', NULL);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`,
                           `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`,
                           `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`)
VALUES ('13d3b1c8-9e67-11ea-960e-8cec4b4b2170', 'BUS-pro', 'SYS.BUS', '保险资管产品', '06', NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, '2020-05-25 17:06:53', NULL);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`,
                           `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`,
                           `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`)
VALUES ('1b985de7-9e67-11ea-960e-8cec4b4b2170', 'BUS-pro', 'SYS.BUS', '金融资产投资公司资管产品', '07', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-05-25 17:07:07', NULL);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`,
                           `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`,
                           `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`)
VALUES ('23d5c123-9e67-11ea-960e-8cec4b4b2170', 'BUS-pro', 'SYS.BUS', '公募基金', '08', NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, '2020-05-25 17:07:20', NULL);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`,
                           `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`,
                           `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`)
VALUES ('32df8c3a-9e67-11ea-960e-8cec4b4b2170', 'BUS-pro', 'SYS.BUS', '私募机构私募基金', '09', NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, '2020-05-25 17:07:46', NULL);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`,
                           `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`,
                           `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`)
VALUES ('dee1de84-9e63-11ea-960e-8cec4b4b2170', 'BUS-pro', 'SYS.BUS', '银行非保本理财', '01', NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, '2020-05-25 17:05:29', NULL);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`,
                           `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`,
                           `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`)
VALUES ('e693200e-9e63-11ea-960e-8cec4b4b2170', 'BUS-pro', 'SYS.BUS', '信托公司资管产品', '02', NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, '2020-05-25 17:05:46', NULL);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`,
                           `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`,
                           `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`)
VALUES ('f0a7ea46-9e63-11ea-960e-8cec4b4b2170', 'BUS-pro', 'SYS.BUS', '证券公司及其子公司资管产品', '03', NULL, NULL,
        NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-05-25 17:06:02', NULL);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`,
                           `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`,
                           `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`)
VALUES ('696fd8a2-9e67-11ea-960e-8cec4b4b2170', 'BUS-pro', 'SYS.BUS', '其他', '99', NULL, NULL, NULL, NULL, NULL, NULL,
        NULL, NULL, NULL, NULL, '2020-05-25 17:09:17', NULL);

-- 更新自然人信息和法人或非法人组织表中创建人为创建人名称
-- UPDATE bus_rel_psn a, sys_staff b
-- SET a.CREATED_BY_NAME_ = b.STAFF_NAME_
-- WHERE a.CREATED_BY_NAME_ = b.STAFF_CODE_;
-- UPDATE bus_rel_org a, sys_staff b
-- SET a.CREATED_BY_NAME_ = b.STAFF_NAME_
-- WHERE a.CREATED_BY_NAME_ = b.STAFF_CODE_;

-- 更新自然人证件类型码值
UPDATE sys_dict_item SET ITEM_LABEL_ = '其他证件' WHERE ITEM_VALUE_ = '5' AND DICT_CODE_='BUS-IdentifyType';
UPDATE sys_dict_item SET ITEM_LABEL_ = '居民户口薄' WHERE ITEM_VALUE_ = '2' AND DICT_CODE_='BUS-IdentifyType';

-- 自然人关联方和法人关联方数据导入时需要新增字段
ALTER TABLE  bus_rel_org_history add column source_id INT4(32) DEFAULT NULL COMMENT '源id';

ALTER TABLE bus_rel_org ADD COLUMN glfbh VARCHAR(65) DEFAULT NULL COMMENT 'east报送相关字段';
ALTER TABLE bus_rel_psn ADD COLUMN glfbh VARCHAR(65) DEFAULT NULL COMMENT 'east报送相关字段';
ALTER TABLE sub_person ADD COLUMN glfbh VARCHAR(65) DEFAULT NULL COMMENT 'east报送相关字段';
ALTER TABLE sub_organ ADD COLUMN glfbh VARCHAR(65) DEFAULT NULL COMMENT 'east报送相关字段';
ALTER TABLE bus_rel_org MODIFY COLUMN reg_capital FLOAT (20,4) DEFAULT NULL COMMENT '注册资本(万元)';
ALTER TABLE bus_rel_org MODIFY COLUMN ORG_NATURE VARCHAR (1000) DEFAULT NULL COMMENT '经济性质或类型';
ALTER TABLE bus_rel_org MODIFY COLUMN reg_address VARCHAR (1000) DEFAULT NULL COMMENT '注册地址';

CREATE TABLE `bus_del_rel_history`
(
    `PID`                 int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`                varchar(300) DEFAULT NULL COMMENT '姓名',
    `domestic_or_foreign` varchar(30)  DEFAULT NULL COMMENT '境内外标识',
    `mger_org`            varchar(30)  DEFAULT NULL COMMENT '管户机构',
    `identify_type`       varchar(90)  DEFAULT NULL COMMENT '证件类型',
    `identify_number`     varchar(60)  DEFAULT NULL COMMENT '证件号码',
    `regulator`           varchar(60)  DEFAULT NULL COMMENT '解除口径',
    `type`                varchar(30)  DEFAULT NULL COMMENT '关联方类型',
    `rel_pid`             int(11) DEFAULT NULL COMMENT '关联方主键',
    `transaction_dep`     varchar(300) DEFAULT NULL COMMENT '经办部门',
    `operator`            varchar(300) DEFAULT NULL COMMENT '经办人',
    `source_id`           int(11) DEFAULT NULL COMMENT '源id',
    `TENANT_ID_`          varchar(64)  DEFAULT NULL COMMENT '租户',
    `CREATED_BY_ID_`      varchar(64)  DEFAULT NULL COMMENT '创建人ID',
    `CREATED_BY_NAME_`    varchar(64)  DEFAULT NULL COMMENT '创建人名称',
    `CREATED_TIME_`       datetime     DEFAULT NULL COMMENT '创建时间',
    `DELFLAG_`            varchar(1)   DEFAULT NULL COMMENT '逻辑删除标记',
    `DISPLAY_ORDER_`      int(11) DEFAULT NULL COMMENT '显示序号',
    `MODIFIED_BY_ID_`     varchar(64)  DEFAULT NULL COMMENT '修改人ID',
    `MODIFIED_BY_NAME_`   varchar(64)  DEFAULT NULL COMMENT '修改人名称',
    `MODIFIED_TIME_`      datetime     DEFAULT NULL COMMENT '修改时间',
    `VERSION_`            int(11) DEFAULT NULL COMMENT '版本',
    PRIMARY KEY (`PID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


-- 金融产品相关
ALTER TABLE  bus_rel_pro MODIFY COLUMN pro_type VARCHAR (2) DEFAULT NULL COMMENT '产品分类';
ALTER TABLE  bus_rel_pro MODIFY COLUMN ad_credit_code VARCHAR (18) DEFAULT NULL COMMENT '管理人统一社会信用代码';
ALTER TABLE  bus_rel_pro MODIFY COLUMN care_credit_code VARCHAR (18) DEFAULT NULL COMMENT '托管人统一社会信用代码';

ALTER TABLE sub_product ADD COLUMN asset_desc VARCHAR(2000) DEFAULT NULL COMMENT '底层资产';
ALTER TABLE sub_product ADD COLUMN investors VARCHAR(2000) DEFAULT NULL COMMENT '投资者';

ALTER TABLE sub_product_view ADD COLUMN asset_desc VARCHAR(2000) DEFAULT NULL COMMENT '底层资产';
ALTER TABLE sub_product_view ADD COLUMN investors VARCHAR(2000) DEFAULT NULL COMMENT '投资者';
-- 关联法人相关
ALTER TABLE  bus_rel_org MODIFY COLUMN org_name VARCHAR (200) DEFAULT NULL COMMENT '企业名称';
ALTER TABLE  bus_rel_org MODIFY COLUMN domestic_or_foreign VARCHAR (1) DEFAULT NULL COMMENT '境内外标识(1-境内，2-境外)';
ALTER TABLE  bus_rel_org MODIFY COLUMN identify_type VARCHAR (2) DEFAULT NULL COMMENT '证件类型';
ALTER TABLE  bus_rel_org MODIFY COLUMN unisc_code VARCHAR (100) DEFAULT NULL COMMENT '证件号码';


ALTER TABLE  bus_rel_org_history MODIFY COLUMN org_name VARCHAR (200) DEFAULT NULL COMMENT '企业名称';
ALTER TABLE  bus_rel_org_history MODIFY COLUMN domestic_or_foreign VARCHAR (1) DEFAULT NULL COMMENT '境内外标识(1-境内，2-境外)';
ALTER TABLE  bus_rel_org_history MODIFY COLUMN identify_type VARCHAR (2) DEFAULT NULL COMMENT '证件类型';
ALTER TABLE  bus_rel_org_history MODIFY COLUMN unisc_code VARCHAR (100) DEFAULT NULL COMMENT '证件号码';

ALTER TABLE sub_organ MODIFY COLUMN identity_no VARCHAR (100) DEFAULT NULL COMMENT '证件号码';
ALTER TABLE sub_organ ADD COLUMN ORG_NATURE VARCHAR(1000) DEFAULT NULL COMMENT '经济性质或类型';
ALTER TABLE sub_organ MODIFY COLUMN business_scope VARCHAR(2000) DEFAULT NULL COMMENT '主营业务或经营范围';
ALTER TABLE sub_organ MODIFY COLUMN reg_address VARCHAR(1000) DEFAULT NULL COMMENT '注册地';
ALTER TABLE sub_organ MODIFY COLUMN reg_capital DECIMAL (20,4) DEFAULT NULL COMMENT '注册资本';

ALTER TABLE sub_organ_view ADD COLUMN ORG_NATURE VARCHAR(1000) DEFAULT NULL COMMENT '经济性质或类型';
ALTER TABLE sub_organ_view ADD COLUMN business_scope VARCHAR(2000) DEFAULT NULL COMMENT '主营业务或经营范围';
ALTER TABLE sub_organ_view ADD COLUMN legal_person VARCHAR(100) DEFAULT NULL COMMENT '法定代表人';
ALTER TABLE sub_organ_view ADD COLUMN reg_address VARCHAR(1000) DEFAULT NULL COMMENT '注册地';
ALTER TABLE sub_organ_view ADD COLUMN reg_capital DECIMAL (20,4) DEFAULT NULL COMMENT '注册资本';

-- 关联自然人相关
ALTER TABLE  bus_rel_psn MODIFY COLUMN name_ VARCHAR (200) DEFAULT NULL COMMENT '姓名';
ALTER TABLE  bus_rel_psn MODIFY COLUMN domestic_or_foreign VARCHAR (1) DEFAULT NULL COMMENT '境内外标识';
ALTER TABLE  bus_rel_psn MODIFY COLUMN identify_type VARCHAR (1) DEFAULT NULL COMMENT '证件类型';
ALTER TABLE  bus_rel_psn MODIFY COLUMN identify_number VARCHAR (100) DEFAULT NULL COMMENT '证件号码';

-- 关联关系相关
ALTER TABLE  bus_rel MODIFY COLUMN from_identify_number VARCHAR (100) DEFAULT NULL COMMENT '本层关联方代码';
ALTER TABLE  bus_rel MODIFY COLUMN to_identify_number VARCHAR (100) DEFAULT NULL COMMENT '前一层关联方代码';
ALTER TABLE  bus_rel MODIFY COLUMN rel_psn_org VARCHAR (3) DEFAULT NULL COMMENT '与前一层的关联关系';
ALTER TABLE  bus_rel MODIFY COLUMN equity_share VARCHAR (1) DEFAULT NULL COMMENT '与前一层是否有权益份额是，对前一层持有是，被前一层持有否';
ALTER TABLE bus_rel MODIFY COLUMN shareholding_ratio FLOAT (6,2) DEFAULT NULL COMMENT '持有比例';
ALTER TABLE bus_rel MODIFY COLUMN pledge_ratio FLOAT (6,2) DEFAULT NULL COMMENT '质押股权比例';
ALTER TABLE bus_rel MODIFY COLUMN relStatement VARCHAR (1000) DEFAULT NULL COMMENT '关联关系说明';
ALTER TABLE bus_rel MODIFY COLUMN isValid VARCHAR (1) DEFAULT NULL COMMENT '该关系是否仍然有效';


ALTER TABLE  bus_rel_history MODIFY COLUMN from_identify_number VARCHAR (100) DEFAULT NULL COMMENT '本层关联方代码';
ALTER TABLE  bus_rel_history MODIFY COLUMN to_identify_number VARCHAR (100) DEFAULT NULL COMMENT '前一层关联方代码';
ALTER TABLE  bus_rel_history MODIFY COLUMN rel_psn_org VARCHAR (3) DEFAULT NULL COMMENT '与前一层的关联关系';
ALTER TABLE  bus_rel_history MODIFY COLUMN equity_share VARCHAR (1) DEFAULT NULL COMMENT '与前一层是否有权益份额是，对前一层持有是，被前一层持有否';
ALTER TABLE bus_rel_history MODIFY COLUMN shareholding_ratio FLOAT (6,2) DEFAULT NULL COMMENT '持有比例';
ALTER TABLE bus_rel_history MODIFY COLUMN pledge_ratio FLOAT (6,2) DEFAULT NULL COMMENT '质押股权比例';
ALTER TABLE bus_rel_history MODIFY COLUMN relStatement VARCHAR (1000) DEFAULT NULL COMMENT '关联关系说明';
ALTER TABLE bus_rel_history MODIFY COLUMN isValid VARCHAR (1) DEFAULT NULL COMMENT '该关系是否仍然有效';


ALTER TABLE sub_related_relation MODIFY COLUMN entity_type VARCHAR (1) DEFAULT NULL COMMENT '关联方类型';
ALTER TABLE sub_related_relation MODIFY COLUMN super_entity_type VARCHAR (1) DEFAULT NULL COMMENT '上级关联方类型';
ALTER TABLE sub_related_relation MODIFY COLUMN relation_type VARCHAR (3) DEFAULT NULL COMMENT '与前一层的关联关系类型';
ALTER TABLE sub_related_relation MODIFY COLUMN equity_share VARCHAR (1) DEFAULT NULL COMMENT '与前一层是否有权益份额';

ALTER TABLE sub_related_relation_view MODIFY COLUMN entity_type VARCHAR (1) DEFAULT NULL COMMENT '关联方类型';
ALTER TABLE sub_related_relation_view MODIFY COLUMN super_entity_type VARCHAR (1) DEFAULT NULL COMMENT '上级关联方类型';
ALTER TABLE sub_related_relation_view MODIFY COLUMN relation_type VARCHAR (3) DEFAULT NULL COMMENT '与前一层的关联关系类型';
ALTER TABLE sub_related_relation_view MODIFY COLUMN equity_share VARCHAR (1) DEFAULT NULL COMMENT '与前一层是否有权益份额';

ALTER TABLE east_glgxb MODIFY COLUMN GLF_GLFZJJDLX VARCHAR (1) NOT NULL COMMENT '关联方/关联方中间节点类型' ;
ALTER TABLE east_glgxb MODIFY COLUMN QYCGLF_GLFZJJDLX VARCHAR (1) NOT NULL COMMENT '前一层关联方/关联方中间节点类型';
ALTER TABLE east_glgxb MODIFY COLUMN YQYCDGLGX VARCHAR (3) NOT NULL COMMENT '与前一层的关联关系';
ALTER TABLE east_glgxb MODIFY COLUMN YQYCZJSFYQYFE VARCHAR (1) NOT NULL COMMENT '与前一层之间是否有权益份额';
ALTER TABLE east_glgxb MODIFY COLUMN GLGXSM VARCHAR (1000) NOT NULL COMMENT '关联关系说明';
ALTER TABLE east_glgxb MODIFY COLUMN GXYXBZ VARCHAR (1) NOT NULL COMMENT '关系有效标志';

ALTER TABLE east_glgxb_view MODIFY COLUMN GLF_GLFZJJDLX VARCHAR (1)  DEFAULT  NULL COMMENT '关联方/关联方中间节点类型';
ALTER TABLE east_glgxb_view MODIFY COLUMN QYCGLF_GLFZJJDLX VARCHAR (1) DEFAULT  NULL COMMENT '前一层关联方/关联方中间节点类型';
ALTER TABLE east_glgxb_view MODIFY COLUMN YQYCDGLGX VARCHAR (3) DEFAULT  NULL  COMMENT '与前一层的关联关系';
ALTER TABLE east_glgxb_view MODIFY COLUMN YQYCZJSFYQYFE VARCHAR (1) DEFAULT  NULL COMMENT '与前一层之间是否有权益份额';
ALTER TABLE east_glgxb_view MODIFY COLUMN GLGXSM VARCHAR (1000) DEFAULT  NULL COMMENT '关联关系说明';
ALTER TABLE east_glgxb_view MODIFY COLUMN GXYXBZ VARCHAR (1) DEFAULT  NULL COMMENT '关系有效标志';

-- 金融产品报送
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (600, 4, 27, 'UnderlyingAsset', 'assetDesc', 2000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (601, 4, 27, 'Investors', 'investors', 2000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- 法人报送
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (602, 1, 2, 'EconomicNature', 'orgNature', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (603, 1, 2, 'EconomicScope', 'businessScope', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (604, 1, 2, 'Representative', 'legalPerson', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (605, 1, 2, 'Registered', 'regAddress', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (606, 1, 2, 'RegisteredCapital', 'regCapital', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- 资金运用管理相关sql
ALTER TABLE bus_funds_investment_balance ADD COLUMN balance_investment DECIMAL (20,4) DEFAULT NULL COMMENT '权益类资产投资余额';
ALTER TABLE bus_funds_investment_balance ADD COLUMN balance_investment_limit DECIMAL (20,4) DEFAULT NULL COMMENT '权益类资产投资限额';
ALTER TABLE bus_funds_investment_balance ADD COLUMN financial_products DECIMAL (20,4) DEFAULT NULL COMMENT '金融产品投资限额';

ALTER TABLE bus_funds_investment_balance modify COLUMN mobility_balance DECIMAL (20,4) DEFAULT NULL COMMENT '流动性资产投资余额';
ALTER TABLE bus_funds_investment_balance modify COLUMN real_estate_balance DECIMAL (20,4) DEFAULT NULL COMMENT '不动产类资产投资余额';
ALTER TABLE bus_funds_investment_balance modify COLUMN real_estate_limit DECIMAL (20,4) DEFAULT NULL COMMENT '不动产类资产投资限额';
ALTER TABLE bus_funds_investment_balance modify COLUMN foreign_balance DECIMAL (20,4) DEFAULT NULL COMMENT '境外投资余额';
ALTER TABLE bus_funds_investment_balance modify COLUMN foreign_limit DECIMAL (20,4) DEFAULT NULL COMMENT '境外投资限额';
ALTER TABLE bus_funds_investment_balance modify COLUMN fixed_income_balance DECIMAL (20,4) DEFAULT NULL COMMENT '固定收益类资产投资余额';
ALTER TABLE bus_funds_investment_balance modify COLUMN insurance_management_balance DECIMAL (20,4) DEFAULT NULL COMMENT '保险资产管理产品投资余额';
ALTER TABLE bus_funds_investment_balance modify COLUMN bank_financial_balance DECIMAL (20,4) DEFAULT NULL COMMENT '商业银行理财产品投资余额';
ALTER TABLE bus_funds_investment_balance modify COLUMN bank_negotiable_securities_balance DECIMAL (20,4) DEFAULT NULL COMMENT '银行业金融机构信资产支持证券投资余额';
ALTER TABLE bus_funds_investment_balance modify COLUMN trust_scheme_balance DECIMAL (20,4) DEFAULT NULL COMMENT '信托公司集合资金信托计划投资余额';
ALTER TABLE bus_funds_investment_balance modify COLUMN negotiable_Securities_Special_Plan_Balance DECIMAL (20,4) DEFAULT NULL COMMENT '证券公司专项资产管理计划投资余额';
ALTER TABLE bus_funds_investment_balance modify COLUMN insurance_funds_support_plan_balance DECIMAL (20,4) DEFAULT NULL COMMENT '保险资管公司发行资产支持计划投资余额';

ALTER TABLE bus_funds_investment_balance modify COLUMN other_investment_balance DECIMAL (20,4) DEFAULT NULL COMMENT '其他投资余额';
ALTER TABLE bus_funds_investment_balance modify COLUMN all_investment_balance DECIMAL (20,4) DEFAULT NULL COMMENT '全部投资余额';

ALTER table bus_rel_funds MODIFY COLUMN rel_name VARCHAR(200)  COMMENT '关联方名称';
ALTER table bus_rel_funds MODIFY COLUMN rel_identify_number VARCHAR(100)  COMMENT '关联方证件号码';
ALTER TABLE bus_rel_funds modify COLUMN funds_investment_balance DECIMAL (20,4) DEFAULT NULL COMMENT '资金运用投资余额';

ALTER TABLE sub_ins_allfunds modify COLUMN mobility_balance DECIMAL (20,4) DEFAULT NULL COMMENT '流动性资产投资余额';
ALTER TABLE sub_ins_allfunds modify COLUMN FixedProfitBalance DECIMAL (20,4) DEFAULT NULL COMMENT '固定收益类资产投资余额';
ALTER TABLE sub_ins_allfunds modify COLUMN InvestAmount DECIMAL (20,4) DEFAULT NULL COMMENT '权益类资产投资余额';
ALTER TABLE sub_ins_allfunds modify COLUMN ImvbsInvestBalance DECIMAL (20,4) DEFAULT NULL COMMENT '不动产类资产投资余额';
ALTER TABLE sub_ins_allfunds modify COLUMN AbroadFund DECIMAL (20,4) DEFAULT NULL COMMENT '境外投资余额';
ALTER TABLE sub_ins_allfunds modify COLUMN InsManageProFund DECIMAL (20,4) DEFAULT NULL COMMENT '保险资产管理产品';
ALTER TABLE sub_ins_allfunds modify COLUMN ComBankFinFund DECIMAL (20,4) DEFAULT NULL COMMENT '商业银行理财产品';
ALTER TABLE sub_ins_allfunds modify COLUMN BankCreditFund DECIMAL (20,4) DEFAULT NULL COMMENT '银行业金融机构信贷资产支持证券';
ALTER TABLE sub_ins_allfunds modify COLUMN TrustFund DECIMAL (20,4) DEFAULT NULL COMMENT '信托公司集合资金信托计划';
ALTER TABLE sub_ins_allfunds modify COLUMN BondSpecialFund DECIMAL (20,4) DEFAULT NULL COMMENT '证券公司专项资产管理计划';
ALTER TABLE sub_ins_allfunds modify COLUMN InsCompanyPlanFund DECIMAL (20,4) DEFAULT NULL COMMENT '保险资管公司发行资产支持计划';
ALTER TABLE sub_ins_allfunds modify COLUMN OtherTotalAmount DECIMAL (20,4) DEFAULT NULL COMMENT '其他';
ALTER TABLE sub_ins_allfunds modify COLUMN all_investment_balance DECIMAL (20,4) DEFAULT NULL COMMENT '全部投资余额';
ALTER TABLE sub_ins_allfunds modify COLUMN current_asset_investment_balance DECIMAL (20,4) DEFAULT NULL COMMENT '流动性资产投资余额-投向关联方';
ALTER TABLE sub_ins_allfunds modify COLUMN AllTotalFund DECIMAL (20,4) DEFAULT NULL COMMENT '全部投资余额-投资关联方';


ALTER TABLE sub_ins_allfunds_view modify COLUMN current_asset_investment_balance DECIMAL (20,4) DEFAULT NULL COMMENT '流动性资产投资余额';
ALTER TABLE sub_ins_allfunds_view modify COLUMN FixedProfitBalance DECIMAL (20,4) DEFAULT NULL COMMENT '固定收益类资产投资余额';
ALTER TABLE sub_ins_allfunds_view modify COLUMN InvestAmount DECIMAL (20,4) DEFAULT NULL COMMENT '权益类资产投资余额';
ALTER TABLE sub_ins_allfunds_view modify COLUMN ImvbsInvestBalance DECIMAL (20,4) DEFAULT NULL COMMENT '不动产类资产投资余额';
ALTER TABLE sub_ins_allfunds_view modify COLUMN AbroadFund DECIMAL (20,4) DEFAULT NULL COMMENT '境外投资余额';
ALTER TABLE sub_ins_allfunds_view modify COLUMN InsManageProFund DECIMAL (20,4) DEFAULT NULL COMMENT '保险资产管理产品';
ALTER TABLE sub_ins_allfunds_view modify COLUMN ComBankFinFund DECIMAL (20,4) DEFAULT NULL COMMENT '商业银行理财产品';
ALTER TABLE sub_ins_allfunds_view modify COLUMN BankCreditFund DECIMAL (20,4) DEFAULT NULL COMMENT '银行业金融机构信贷资产支持证券';
ALTER TABLE sub_ins_allfunds_view modify COLUMN TrustFund DECIMAL (20,4) DEFAULT NULL COMMENT '信托公司集合资金信托计划';
ALTER TABLE sub_ins_allfunds_view modify COLUMN BondSpecialFund DECIMAL (20,4) DEFAULT NULL COMMENT '证券公司专项资产管理计划';
ALTER TABLE sub_ins_allfunds_view modify COLUMN InsCompanyPlanFund DECIMAL (20,4) DEFAULT NULL COMMENT '保险资管公司发行资产支持计划';
ALTER TABLE sub_ins_allfunds_view modify COLUMN OtherTotalAmount DECIMAL (20,4) DEFAULT NULL COMMENT '其他';
ALTER TABLE sub_ins_allfunds_view modify COLUMN AllTotalFund DECIMAL (20,4) DEFAULT NULL COMMENT '全部投资余额';



ALTER TABLE sub_ins_relfunds ADD COLUMN rel_type VARCHAR(1) DEFAULT NULL COMMENT '关联方类别';
ALTER TABLE sub_ins_relfunds modify COLUMN CorpInvestFund DECIMAL (20,4) DEFAULT NULL COMMENT '资金运用投资余额';

ALTER table sub_ins_relfunds_view ADD COLUMN rel_type VARCHAR(1)  COMMENT '关联方类别';
ALTER table sub_ins_relfunds_view MODIFY COLUMN REL_NAME VARCHAR(200)  COMMENT '关联方名称';
ALTER TABLE sub_ins_relfunds_view modify COLUMN CorpInvestFund DECIMAL (20,4) DEFAULT NULL COMMENT '资金运用投资余额';

DELETE from sub_template_details where element_name = 'ListedInvestBalance';
DELETE from sub_template_details where element_name = 'UnlistedInvestBalance';
DELETE from sub_template_details where element_name = 'CorpTotalFund';
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (382, 16, 380, 'InvestBalance', 'investAmount', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (405, 17, 401, 'RelatedPartyType', 'relType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

INSERT INTO `sys_dict`(`DICT_CODE_`, `DICT_NAME_`, `DICT_DESC_`, `DICT_CATE_CODE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('INS-SingleRelatedTranType', '单笔关联交易类型', NULL, 'SYS', NULL, 'FXAdmin', '复星保德信管理员', '2022-10-27 14:20:05', '1', NULL, NULL, NULL, '2022-10-27 14:20:05', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('0fab65933e8c4e1ebc679ac19ef16528', 'INS-SingleRelatedTranType', 'SYS', '统一交易协议', '2', '统一交易协议', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-10-27 14:20:58', '1', NULL, NULL, NULL, '2022-10-27 14:20:58', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('3050697311bb47ea8f0a5d48e3e72b24', 'INS-SingleRelatedTranType', 'SYS', '银保监会要求报告的其他交易', '3', '银保监会要求报告的其他交易', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-10-27 14:21:12', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-10-27 14:21:24', 1);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('b564a995dde24b2e8eb3bb333f54c642', 'INS-SingleRelatedTranType', 'SYS', '重大关联交易', '1', '重大关联交易', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-10-27 14:20:48', '1', NULL, NULL, NULL, '2022-10-27 14:20:48', 0);

INSERT INTO `sys_dict` (`dict_code_`, `dict_name_`, `dict_desc_`, `dict_cate_code_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('INS-FundsEachReport', '是否需逐笔报告', '资金运用类-是否需逐笔报告', 'TRA', NULL, 'jepAdmin', 'jepAdmin', '2022-07-19 19:12:42', '1', NULL, NULL, NULL, '2022-07-19 19:12:42', 0);
INSERT INTO `sys_dict` (`dict_code_`, `dict_name_`, `dict_desc_`, `dict_cate_code_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('INS-FundsEachDisclosure', '是否需逐笔披露', '资金御用类-是否需逐笔披露', 'TRA', NULL, 'jepAdmin', 'jepAdmin', '2022-07-19 19:15:31', '1', NULL, NULL, NULL, '2022-07-19 19:15:31', 0);
INSERT INTO `sys_dict_item` (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('5835652b2970494d8ff10ff3e19a3845', 'INS-FundsEachReport', 'TRA', '否', '0', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2022-07-19 19:14:45', '1', NULL, NULL, NULL, '2022-07-19 19:14:45', 0);
INSERT INTO `sys_dict_item` (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('0b654b4fd1864913bf513a85914811ab', 'INS-FundsEachDisclosure', 'TRA', '否', '0', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2022-07-19 19:16:09', '1', NULL, NULL, NULL, '2022-07-19 19:16:09', 0);
INSERT INTO `sys_dict_item` (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('f4671b3844394c57835d45fe82d12624', 'INS-FundsEachReport', 'TRA', '是，应当在签订交易协议后15个工作日内逐笔向银保监会报告', '1', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2022-07-19 19:14:10', '1', NULL, NULL, NULL, '2022-07-19 19:14:10', 0);
INSERT INTO `sys_dict_item` (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('88d77695854c4c00b265ae3520c064b7', 'INS-FundsEachDisclosure', 'TRA', '是，应当在交易协议签署后10个工作日内披露', '1', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2022-07-19 19:15:58', '1', NULL, NULL, NULL, '2022-07-19 19:15:58', 0);

-- ============================================================关联交易新增字段============================================================================
-- 资金运用类
ALTER TABLE  ins_tra_funds add column single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';
ALTER TABLE  ins_tra_funds add column each_report VARCHAR (30) DEFAULT NULL COMMENT '是否需逐笔报告';
ALTER TABLE  ins_tra_funds add column each_disclosure VARCHAR (30) DEFAULT NULL COMMENT '是否需逐笔披露';
ALTER TABLE  ins_tra_funds add column is_collect_related VARCHAR (1) DEFAULT NULL COMMENT '是否需要补录关联方信息';
ALTER TABLE  ins_tra_funds add column is_consider_situation VARCHAR (50) DEFAULT NULL COMMENT '是否可免于审议';
ALTER TABLE  ins_tra_funds add column consider_situation VARCHAR (255) DEFAULT NULL COMMENT '免于审议情形';
ALTER TABLE  ins_tra_funds add column dept_name VARCHAR (100) DEFAULT NULL COMMENT '部门';

ALTER TABLE ins_tra_funds MODIFY COLUMN REL_TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '关联交易金额';
ALTER TABLE ins_tra_funds MODIFY COLUMN TRAN_TYPE VARCHAR (1) DEFAULT NULL COMMENT '交易类型';
ALTER TABLE ins_tra_funds MODIFY COLUMN TRADE_PRICE VARCHAR (300) DEFAULT NULL COMMENT '交易价格';
ALTER TABLE ins_tra_funds MODIFY COLUMN TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';
ALTER TABLE ins_tra_funds MODIFY COLUMN FINANCIAL_ASSSTS_NAME VARCHAR (100) DEFAULT NULL COMMENT '金融产品名称';
ALTER TABLE ins_tra_funds MODIFY COLUMN FINANCIAL_ASSSTS_NUM VARCHAR (100) DEFAULT NULL COMMENT '产品批准/注册/备案号';
ALTER TABLE ins_tra_funds add column bus_type VARCHAR (2) DEFAULT NULL COMMENT '业务类型';
ALTER TABLE ins_tra_funds MODIFY COLUMN PRAC_TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '关联交易实际金额';

-- 资金运用类报送
ALTER TABLE  sub_ins_funds add column bus_type VARCHAR (2) DEFAULT NULL COMMENT '业务类型';
ALTER TABLE  sub_ins_funds add column TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';
ALTER TABLE  sub_ins_funds add column single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';

ALTER TABLE sub_ins_funds MODIFY COLUMN TRAN_TYPE VARCHAR (1) DEFAULT NULL COMMENT '交易类型';
ALTER TABLE sub_ins_funds MODIFY COLUMN is_Overseasinvestment VARCHAR (1) DEFAULT NULL COMMENT '是否境外投资';
ALTER TABLE sub_ins_funds MODIFY COLUMN INVESTMENT_METHOD VARCHAR (1) DEFAULT NULL COMMENT '投资方式';
ALTER TABLE sub_ins_funds MODIFY COLUMN Major_Type VARCHAR (1) DEFAULT NULL COMMENT '达到重大关联交易的情形';
ALTER TABLE sub_ins_funds MODIFY COLUMN trade_Amount DECIMAL (20,4) DEFAULT NULL COMMENT '关联交易金额（万元)';
ALTER TABLE sub_ins_funds MODIFY COLUMN FINANCIAL_PRO_RATIO DECIMAL (6,2) DEFAULT NULL COMMENT '投资金融产品份额占比（%）';

ALTER TABLE  sub_ins_funds_view add column bus_type VARCHAR (2) DEFAULT NULL COMMENT '业务类型';
ALTER TABLE  sub_ins_funds_view add column TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';
ALTER TABLE  sub_ins_funds_view add column single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';

ALTER TABLE sub_ins_funds_view MODIFY COLUMN TRAN_TYPE VARCHAR (1) DEFAULT NULL COMMENT '交易类型';
ALTER TABLE sub_ins_funds_view MODIFY COLUMN is_Overseasinvestment VARCHAR (1) DEFAULT NULL COMMENT '是否境外投资';
ALTER TABLE sub_ins_funds_view MODIFY COLUMN INVESTMENT_METHOD VARCHAR (1) DEFAULT NULL COMMENT '投资方式';
ALTER TABLE sub_ins_funds_view MODIFY COLUMN Major_Type VARCHAR (1) DEFAULT NULL COMMENT '达到重大关联交易的情形';
ALTER TABLE sub_ins_funds_view MODIFY COLUMN trade_Amount DECIMAL (20,4) DEFAULT NULL COMMENT '关联交易金额（万元)';
ALTER TABLE sub_ins_funds_view MODIFY COLUMN FINANCIAL_PRO_RATIO DECIMAL (6,2) DEFAULT NULL COMMENT '投资金融产品份额占比（%）';

DELETE FROM sub_template_details where template_id = '10';
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (260, 10, 220, 'TransactionOverview', 'tranName', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (261, 10, 220, 'Remark', 'remark', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (219, 10, NULL, 'SingleReport', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (220, 10, 219, 'Fund', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (221, 10, 220, 'TransType', 'tranTypeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (222, 10, 220, 'BuzType', 'busType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (223, 10, 220, 'ContractNo', 'contractNo', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (224, 10, 220, 'SignDate', 'tranDateSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (225, 10, 220, 'TransStartDate', 'busStartTimeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (226, 10, 220, 'TransEndDate', 'busEndTimeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (227, 10, 220, 'TransAmount', 'tradeAmount', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (228, 10, 220, 'TransPrice', 'tradePrice', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (229, 10, 220, 'PricingMark', 'pricingPolicy', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (231, 10, 220, 'TransSubject', 'tradeSubject', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (232, 10, 220, 'RelatedParty', '', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (233, 10, 232, 'RelatedPartyType', 'relTypeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (234, 10, 232, 'RelatedPartyName', 'relName', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (235, 10, 232, 'RelatedPartyCertNo', 'relNumber', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (236, 10, 232, 'DeclareType', 'majorType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (237, 10, 232, 'TransactionType', 'singleRelatedTranType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (238, 10, 220, 'OverseaFlag', 'isOverseasinvestment', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (242, 10, 220, 'InvestType', 'investmentMethodSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (244, 10, 220, 'FinancialProductName', 'financialAssstsName', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (245, 10, 220, 'FinancialProductNo', 'financialAssstsNum', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details`(`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (247, 10, 220, 'FinancialProductRatio', 'financialProRatio', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
-- 资金运用类业务类型
DELETE FROM sys_dict_item WHERE DICT_CODE_ = 'INS-FundsType';
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('912213bf865149dc94639870e6ad1fd7', 'INS-FundsType', 'TRA', '投资其他资产', '99', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-08 13:40:57', '1', NULL, NULL, NULL, '2022-11-08 13:40:57', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('9269638f-3088-11eb-b87e-005056be60bc', 'INS-FundsType', 'TRA', '投资股权', '01', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2020-11-27 16:13:53', '1', NULL, NULL, NULL, '2020-11-27 16:13:53', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('a0022c40-3088-11eb-b87e-005056be60bc', 'INS-FundsType', 'TRA', '投资不动产', '02', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2020-11-27 16:14:16', '1', NULL, NULL, NULL, '2020-11-27 16:14:16', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('aa497b69-3088-11eb-b87e-005056be60bc', 'INS-FundsType', 'TRA', '办理银行存款', '03', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2020-11-27 16:14:34', '1', NULL, NULL, NULL, '2020-11-27 16:14:34', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('b4e7013b-3088-11eb-b87e-005056be60bc', 'INS-FundsType', 'TRA', '投资金融产品', '04', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2020-11-27 16:14:51', '1', NULL, NULL, NULL, '2020-11-27 16:14:51', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('bee6f4e1-3088-11eb-b87e-005056be60bc', 'INS-FundsType', 'TRA', '买卖债券', '05', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2020-11-27 16:15:08', '1', NULL, NULL, NULL, '2020-11-27 16:15:08', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('c655cce6-3088-11eb-b87e-005056be60bc', 'INS-FundsType', 'TRA', '买卖股票', '06', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2020-11-27 16:15:21', '1', NULL, NULL, NULL, '2020-11-27 16:15:21', 0);

-- 服务类
ALTER TABLE  ins_tra_service add column single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';
ALTER TABLE  ins_tra_service add column each_report VARCHAR (30) DEFAULT NULL COMMENT '是否需逐笔报告';
ALTER TABLE  ins_tra_service add column each_disclosure VARCHAR (30) DEFAULT NULL COMMENT '是否需逐笔披露';
ALTER TABLE  ins_tra_service add column is_collect_related VARCHAR (1) DEFAULT NULL COMMENT '是否需要补录关联方信息';
ALTER TABLE  ins_tra_service add column is_consider_situation VARCHAR (50) DEFAULT NULL COMMENT '是否可免于审议';
ALTER TABLE  ins_tra_service add column consider_situation VARCHAR (255) DEFAULT NULL COMMENT '免于审议情形';
ALTER TABLE  ins_tra_service add column trade_agreement_type VARCHAR (1) DEFAULT NULL COMMENT '统一交易协议类型';
ALTER TABLE  ins_tra_service add column dept_name VARCHAR (100) DEFAULT NULL COMMENT '部门';
ALTER TABLE  ins_tra_service MODIFY COLUMN PRAC_TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '关联交易实际金额';


ALTER TABLE ins_tra_service MODIFY COLUMN REL_TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '关联交易金额';
ALTER TABLE ins_tra_service MODIFY COLUMN TRAN_TYPE VARCHAR (1) DEFAULT NULL COMMENT '交易类型';
ALTER TABLE ins_tra_service MODIFY COLUMN TRADE_PRICE VARCHAR (300) DEFAULT NULL COMMENT '交易价格';
ALTER TABLE ins_tra_service MODIFY COLUMN TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';

-- 服务类关联交易报送
ALTER TABLE sub_ins_service MODIFY COLUMN TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '交易金额';
ALTER TABLE sub_ins_service add COLUMN single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';
ALTER TABLE sub_ins_service add COLUMN TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';
ALTER TABLE sub_ins_service add COLUMN Trading_direction VARCHAR (1) DEFAULT NULL COMMENT '交易方向';
ALTER TABLE sub_ins_service_view ADD COLUMN single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';
ALTER TABLE sub_ins_service_view add COLUMN TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';
ALTER TABLE sub_ins_service_view add COLUMN Trading_direction VARCHAR (1) DEFAULT NULL COMMENT '交易方向';
ALTER TABLE sub_ins_service_view MODIFY COLUMN TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '交易金额';

DELETE FROM sub_template_details WHERE template_id = '13';
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (415, 13, 328, 'TransactionType', 'singleRelatedTranType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (414, 13, 316, 'TransFlag', 'tradingDirection', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (338, 13, 316, 'Remark', 'remark', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (332, 13, 328, 'DeclareType', 'majorType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (331, 13, 328, 'RelatedPartyCertNo', 'relNumber', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (330, 13, 328, 'RelatedPartyName', 'relName', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (329, 13, 328, 'RelatedPartyType', 'relTypeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (328, 13, 316, 'RelatedParty', '', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (327, 13, 316, 'TransSubject', 'tradeSubject', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (326, 13, 316, 'TransactionOverview', 'tranName', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (325, 13, 316, 'PricingMark', 'pricingPolicy', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (324, 13, 316, 'TransPrice', 'tradePrice', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (323, 13, 316, 'TransAmount', 'tradeAmount', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (322, 13, 316, 'TransEndDate', 'busEndTimeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (321, 13, 316, 'TransStartDate', 'busStartTimeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (320, 13, 316, 'SignDate', 'tranDateSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (319, 13, 316, 'ContractNo', 'contractNo', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (318, 13, 316, 'BuzType', 'busType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (317, 13, 316, 'TransType', 'tranType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (316, 13, 315, 'Service', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (315, 13, NULL, 'SingleReport', NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- 服务类业务类型
DELETE FROM sys_dict_item WHERE DICT_CODE_ = 'INS-ServiceType';
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('012a2a49-b20b-11ea-ab1d-8cec4b488362', 'INS-ServiceType', 'TRA', '资产评估', '04', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 17:01:13', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('10eec7ec-b20b-11ea-ab1d-8cec4b488362', 'INS-ServiceType', 'TRA', '咨询顾问', '05', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 17:01:27', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('1f2707b1-b20b-11ea-ab1d-8cec4b488362', 'INS-ServiceType', 'TRA', '技术和基础设施', '06', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 17:01:33', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('28ff7930-b20b-11ea-ab1d-8cec4b488362', 'INS-ServiceType', 'TRA', '委托或受托管理资产', '07', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 17:01:39', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('332c5a0a-b20b-11ea-ab1d-8cec4b488362', 'INS-ServiceType', 'TRA', '租赁资产', '08', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 17:01:47', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('3c758ee0-b20b-11ea-ab1d-8cec4b488362', 'INS-ServiceType', 'TRA', '其他', '99', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 17:01:54', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('e0301426-b20a-11ea-ab1d-8cec4b488362', 'INS-ServiceType', 'TRA', '审计', '01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 17:02:01', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('e859e7dd-b20a-11ea-ab1d-8cec4b488362', 'INS-ServiceType', 'TRA', '精算', '02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 17:02:12', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('ef1a4a41-b20a-11ea-ab1d-8cec4b488362', 'INS-ServiceType', 'TRA', '法律', '03', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 17:02:19', NULL);

INSERT INTO `sys_dict`(`DICT_CODE_`, `DICT_NAME_`, `DICT_DESC_`, `DICT_CATE_CODE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('INS-Tra-Direction', '交易方向', '交易方向', 'SYS.BUS', NULL, 'FXAdmin', '复星保德信管理员', '2022-10-31 15:22:17', '1', NULL, NULL, NULL, '2022-10-31 15:22:17', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('3559f8bd7d68405881035c1e8c065a8a', 'INS-Tra-Direction', 'SYS.BUS', '提供服务', '3', '提供服务', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-10-31 15:22:51', '1', NULL, NULL, NULL, '2022-10-31 15:22:51', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('73e2fd09b0494f7d8e50a8e93156c1f1', 'INS-Tra-Direction', 'SYS.BUS', '接受服务', '4', '接受服务', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-10-31 15:23:08', '1', NULL, NULL, NULL, '2022-10-31 15:23:08', 0);
-- 利益转移类
ALTER TABLE  ins_tra_interest add column single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';
ALTER TABLE  ins_tra_interest add column each_report VARCHAR (30) DEFAULT NULL COMMENT '是否需逐笔报告';
ALTER TABLE  ins_tra_interest add column each_disclosure VARCHAR (30) DEFAULT NULL COMMENT '是否需逐笔披露';
ALTER TABLE  ins_tra_interest add column is_collect_related VARCHAR (1) DEFAULT NULL COMMENT '是否需要补录关联方信息';
ALTER TABLE  ins_tra_interest add column is_consider_situation VARCHAR (50) DEFAULT NULL COMMENT '是否可免于审议';
ALTER TABLE  ins_tra_interest add column consider_situation VARCHAR (255) DEFAULT NULL COMMENT '免于审议情形';
ALTER TABLE  ins_tra_interest add column dept_name VARCHAR (100) DEFAULT NULL COMMENT '部门';
ALTER TABLE  ins_tra_interest MODIFY COLUMN PRAC_TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '关联交易实际金额';


ALTER TABLE ins_tra_interest MODIFY COLUMN REL_TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '关联交易金额';
ALTER TABLE ins_tra_interest MODIFY COLUMN TRAN_TYPE VARCHAR (1) DEFAULT NULL COMMENT '交易类型';
ALTER TABLE ins_tra_interest MODIFY COLUMN TRADE_PRICE VARCHAR (300) DEFAULT NULL COMMENT '交易价格';
ALTER TABLE ins_tra_interest MODIFY COLUMN TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';
ALTER TABLE ins_tra_interest add column Trading_direction VARCHAR (1) DEFAULT NULL COMMENT '交易方向';

-- 利益转移类关联交易报送
ALTER TABLE sub_ins_interest MODIFY COLUMN TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '交易金额';
ALTER TABLE sub_ins_interest add COLUMN single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';
ALTER TABLE sub_ins_interest add COLUMN TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';
ALTER TABLE sub_ins_interest add COLUMN Trading_direction VARCHAR (1) DEFAULT NULL COMMENT '交易方向';
ALTER TABLE sub_ins_interest_view ADD COLUMN single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';
ALTER TABLE sub_ins_interest_view add COLUMN TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';
ALTER TABLE sub_ins_interest_view add COLUMN Trading_direction VARCHAR (1) DEFAULT NULL COMMENT '交易方向';
ALTER TABLE sub_ins_interest_view MODIFY COLUMN TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '交易金额';

DELETE FROM sub_template_details WHERE template_id = '12';
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (314, 12, 287, 'Remark', 'remark', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (309, 12, 287, 'Guarantee', 'guaranteeConditions', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (304, 12, 299, 'TransactionType', 'singleRelatedTranType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (303, 12, 299, 'DeclareType', 'majorType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (302, 12, 299, 'RelatedPartyCertNo', 'relNumber', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (301, 12, 299, 'RelatedPartyName', 'relName', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (300, 12, 299, 'RelatedPartyType', 'relTypeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (299, 12, 287, 'RelatedParty', '', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (298, 12, 287, 'TransSubject', 'tradeSubject', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (297, 12, 287, 'TransactionOverview', 'tranName', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (296, 12, 287, 'PricingMark', 'pricingPolicy', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (295, 12, 287, 'TransPrice', 'tradePrice', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (294, 12, 287, 'TransAmount', 'tradeAmount', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (293, 12, 287, 'TransEndDate', 'busEndTimeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (292, 12, 287, 'TransStartDate', 'busStartTimeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (291, 12, 287, 'SignDate', 'tranDateSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (290, 12, 287, 'ContractNo', 'contractNo', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (289, 12, 287, 'BuzType', 'busType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (288, 12, 287, 'TransType', 'tranType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (287, 12, 286, 'Transfer', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (286, 12, NULL, 'SingleReport', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO  `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (416, 12, 287, 'TransFlag', 'tradingDirection', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- 利益转移类业务类型
INSERT INTO `sys_dict` (`DICT_CODE_`, `DICT_NAME_`, `DICT_DESC_`, `DICT_CATE_CODE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('INS-InterestType', '利益转移类业务类型', NULL, 'TRA', NULL, 'FXAdmin', '复星保德信管理员', '2022-11-08 11:48:02', '1', NULL, NULL, NULL, '2022-11-08 11:48:02', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('73e639a68d074f3fbfaa3e4c5822c883', 'INS-InterestType', 'TRA', '权利转让', '02', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-08 11:50:01', '1', NULL, NULL, NULL, '2022-11-08 11:50:01', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('7e2b56d3546243e6bcb0d97bf5907306', 'INS-InterestType', 'TRA', '担保', '03', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-08 11:50:14', '1', NULL, NULL, NULL, '2022-11-08 11:50:14', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('7fc7217e447e47ac93f6d6c0c14d182b', 'INS-InterestType', 'TRA', '赠与、给予或接受财务资助', '01', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-08 11:48:43', '1', NULL, NULL, NULL, '2022-11-08 11:48:43', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('95685105633348b98c7adb8a65c85e83', 'INS-InterestType', 'TRA', '放弃优先受让权、同比例增资权或其他权利', '05', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-08 11:50:35', '1', NULL, NULL, NULL, '2022-11-08 11:50:35', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('e0f8c0fea0c944aca86382afed6fb973', 'INS-InterestType', 'TRA', '债权债务转移', '04', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-08 11:50:24', '1', NULL, NULL, NULL, '2022-11-08 11:50:24', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('faf9a94c97fd493ab08616ab5620ce03', 'INS-InterestType', 'TRA', '其他', '99', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-08 11:50:50', '1', NULL, NULL, NULL, '2022-11-08 11:50:50', 0);


INSERT INTO `sys_dict`(`DICT_CODE_`, `DICT_NAME_`, `DICT_DESC_`, `DICT_CATE_CODE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('INS-Tra-Directions', '交易方向（利益转移）', '交易方向（利益转移）', 'SYS.BUS', NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 10:53:36', '1', NULL, NULL, NULL, '2022-11-02 10:53:36', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('5289394ebad84d0b8dfdbb2f550aa254', 'INS-Tra-Directions', 'SYS.BUS', '利益转入', '5', '利益转入', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 10:54:33', '1', NULL, NULL, NULL, '2022-11-02 10:54:33', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('5f5bbce5b7c64604bca261c7b0e0e9e9', 'INS-Tra-Directions', 'SYS.BUS', '利益转出', '6', '利益转出', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 10:54:48', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 10:55:50', 1);
INSERT INTO `sys_dict`(`DICT_CODE_`, `DICT_NAME_`, `DICT_DESC_`, `DICT_CATE_CODE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('INT-BusinessTypes', '利益转移类字典', '利益转移类字典（新规）', 'SYS.BUS', NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 11:15:27', '1', NULL, NULL, NULL, '2022-11-02 11:15:27', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('0f1b4402dc14434a8fa6f802faeed26e', 'INT-BusinessTypes', 'SYS.BUS', '债权债务转移', '04', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 11:16:29', '1', NULL, NULL, NULL, '2022-11-02 11:16:29', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('1b69d891b2d54c76ae1e317c1286d8f8', 'INT-BusinessTypes', 'SYS.BUS', '其他', '99', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 11:16:55', '1', NULL, NULL, NULL, '2022-11-02 11:16:55', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('8bbf03d750b54233a75e2e1fa9cdbadc', 'INT-BusinessTypes', 'SYS.BUS', '赠与、给予或接受财务资助', '01', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 11:15:47', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 11:15:59', 1);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('a46615be4ae149e283fab37bc575bb6c', 'INT-BusinessTypes', 'SYS.BUS', '担保', '03', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 11:16:20', '1', NULL, NULL, NULL, '2022-11-02 11:16:20', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('a801cb556d45428292afe91b7cbe33ed', 'INT-BusinessTypes', 'SYS.BUS', '权利转让', '02', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 11:16:10', '1', NULL, NULL, NULL, '2022-11-02 11:16:10', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('cd90769913f14417b49b8679f4a9ac5a', 'INT-BusinessTypes', 'SYS.BUS', '放弃优先受让权、同比例增资权或其他权利', '05', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2022-11-02 11:16:41', '1', NULL, NULL, NULL, '2022-11-02 11:16:41', 0);

-- 保险业务及其他类
ALTER TABLE  ins_tra_business add column single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';
ALTER TABLE  ins_tra_business add column each_report VARCHAR (30) DEFAULT NULL COMMENT '是否需逐笔报告';
ALTER TABLE  ins_tra_business add column each_disclosure VARCHAR (30) DEFAULT NULL COMMENT '是否需逐笔披露';
ALTER TABLE  ins_tra_business add column is_collect_related VARCHAR (1) DEFAULT NULL COMMENT '是否需要补录关联方信息';
ALTER TABLE  ins_tra_business add column is_consider_situation VARCHAR (50) DEFAULT NULL COMMENT '是否可免于审议';
ALTER TABLE  ins_tra_business add column consider_situation VARCHAR (255) DEFAULT NULL COMMENT '免于审议情形';
ALTER TABLE  ins_tra_business add column trade_agreement_type VARCHAR (1) DEFAULT NULL COMMENT '统一交易协议类型';
ALTER TABLE  ins_tra_business add column dept_name VARCHAR (100) DEFAULT NULL COMMENT '部门';
ALTER TABLE ins_tra_business MODIFY COLUMN REL_NAME VARCHAR (200) DEFAULT NULL COMMENT '关联方名称';
ALTER TABLE ins_tra_business MODIFY COLUMN REL_TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '关联交易金额';
ALTER TABLE ins_tra_business MODIFY COLUMN PRAC_TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '关联交易实际金额';
ALTER TABLE ins_tra_business MODIFY COLUMN TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';
-- 报送库字段调整
ALTER TABLE sub_ins_business MODIFY COLUMN TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '交易金额';
ALTER TABLE sub_ins_business_view MODIFY COLUMN TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '交易金额';
ALTER TABLE  sub_ins_business add column single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';
ALTER TABLE  sub_ins_business add COLUMN TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';
ALTER TABLE  sub_ins_business_view add column single_related_trantype VARCHAR (1) DEFAULT NULL COMMENT '单笔关联交易类型';
ALTER TABLE  sub_ins_business_view add COLUMN TRAN_NAME VARCHAR (1000) DEFAULT NULL COMMENT '交易概述';


-- 保险业务及其他类关联交易报送
DELETE FROM sub_template_details WHERE template_id = '11';

INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (285, 11, 263, 'Remark', 'remark', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (280, 11, 275, 'TransactionType', 'singleRelatedTranType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (279, 11, 275, 'DeclareType', 'majorType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (278, 11, 275, 'RelatedPartyCertNo', 'relNumber', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (277, 11, 275, 'RelatedPartyName', 'relName', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (276, 11, 275, 'RelatedPartyType', 'relTypeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (275, 11, 263, 'RelatedParty', '', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (274, 11, 263, 'TransSubject', 'tradeSubject', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (273, 11, 263, 'TransactionType', 'tranName', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (272, 11, 263, 'PricingMark', 'pricingPolicy', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (271, 11, 263, 'TransPrice', 'tradePrice', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (270, 11, 263, 'TransAmount', 'tradeAmount', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (269, 11, 263, 'TransEndDate', 'busEndTimeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (268, 11, 263, 'TransStartDate', 'busStartTimeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (267, 11, 263, 'SignDate', 'tranDateSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (266, 11, 263, 'ContractNo', 'contractNo', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (265, 11, 263, 'BuzType', 'busType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (264, 11, 263, 'TransType', 'tranType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (263, 11, 262, 'Insurance', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (262, 11, NULL, 'SingleReport', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


-- 保险业务类业务类型
DELETE FROM sys_dict_item WHERE DICT_CODE_ = 'INT-InsBusinessType';
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('2a5e25b6-b2ae-11ea-ab1d-8cec4b488362', 'INT-InsBusinessType', 'TRA', '保险业务', '01', '保险业务', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-20 12:26:09', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('35df12bd-b2ae-11ea-ab1d-8cec4b488362', 'INT-InsBusinessType', 'TRA', '其他', '02', '保险代理业务', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-20 12:26:28', NULL);
DELETE FROM sys_dict_item WHERE DICT_CODE_ = 'INS-majorType' AND ITEM_VALUE_ = '3';



-- 更新关联关系码值
UPDATE sys_dict_item SET ITEM_LABEL_ = '法人或非法人组织' WHERE ITEM_ID_ = '0983b6cc-772c-11ea-a153-8cec4b488362';
DELETE FROM sys_dict_item WHERE DICT_CODE_ = 'BUS-RelZZD';
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('07fa3260-45c0-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '对前一层控制', '106', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:28:10', '1', NULL, NULL, NULL, '2020-12-24 16:28:10', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('1b1708a4-45c0-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '对前一层共同控制', '104', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:28:42', '1', NULL, NULL, NULL, '2020-12-24 16:28:42', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('2d60d6fe-45c0-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '对前一层施加重大影响', '107', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:29:13', '1', NULL, NULL, NULL, '2020-12-24 16:29:13', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('7767c268-45c2-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '被前一层共同控制', '100', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:45:36', '1', NULL, NULL, NULL, '2020-12-24 16:45:36', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('97ad3d9c-45c2-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '被前一层控制', '102', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:46:30', '1', NULL, NULL, NULL, '2020-12-24 16:46:30', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('a62db746-45c2-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '被前一层施加重大影响', '103', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:46:55', '1', NULL, NULL, NULL, '2020-12-24 16:46:55', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('0358fd7c-45c1-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '监事', '112', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:35:12', '1', NULL, NULL, NULL, '2020-12-24 16:35:12', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('21489fb3-45c1-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '高级管理人员', '111', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:36:02', '1', NULL, NULL, NULL, '2020-12-24 16:36:02', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('47a29e37-45c1-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '一致行动人', '108', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:37:07', '1', NULL, NULL, NULL, '2020-12-24 16:37:07', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('94abdb54-45bf-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '董事', '109', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:24:57', '1', NULL, NULL, NULL, '2020-12-24 16:24:57', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('ce205d8f-45c2-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '最终受益人', '116', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:48:02', '1', NULL, NULL, NULL, '2020-12-24 16:48:02', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('ce205d9f-45c2-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '按照实质重于形式和穿透的原则认定的关联方', '118', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2022-05-11 16:42:02', '1', NULL, NULL, NULL, '2022-05-11 16:42:02', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('ce205c8f-45c2-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '其他具有大额授信、资产转移、保险资金运用等核心业务审批或决策权的人员', '119', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2022-05-11 16:43:02', '1', NULL, NULL, NULL, '2022-05-11 16:43:02', 0);
INSERT INTO sys_dict_item (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('5418330d-45bf-11eb-acea-8cec4b488312', 'BUS-RelZZD', 'SYS.BUS', '前一层的配偶、父母、成年子女及兄弟姐妹', '113', NULL, NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2020-12-24 16:23:09', '1', NULL, NULL, NULL, '2020-12-24 16:23:09', 0);

DELETE FROM sys_dict_item WHERE DICT_CODE_ = 'BUS-RelApplystatus' AND ITEM_VALUE_ IN ('3','4','5');
INSERT INTO sys_dict_item (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('f154b2d1-a7d0-11eb-b9af-0242ac110002', 'BUS-RelApplystatus', 'SYS.BUS', '待归档', '7', '待归档', NULL, NULL, 'ZZAdmin', 'ZZAdmin', '2021-04-28 11:22:21', '1', NULL, NULL, NULL, '2021-04-28 11:22:21', 0);

-- 未归类
UPDATE bus_product SET pro_name = '资金运用类' WHERE pro_id = 1;
UPDATE bus_product SET pro_name = '服务类' WHERE pro_id = 2;
UPDATE bus_product SET pro_name = '利益转移类' WHERE pro_id = 3;
UPDATE bus_product SET pro_name = '保险业务和其他类' WHERE pro_id = 4;
UPDATE bus_product SET DELFLAG_ = '0' WHERE pro_id = 5;
UPDATE bus_product SET DELFLAG_ = '0' WHERE pro_id = 6;
ALTER TABLE  ins_un_sort_out add column dept_name VARCHAR (100) DEFAULT NULL COMMENT '部门';





-- 调整菜单
UPDATE sys_menu SET MENU_NAME_ = '法人或非法人组织' WHERE MENU_CODE_ = 'bus.200';
UPDATE sys_menu SET MENU_NAME_ = '法人或非法人组织' WHERE MENU_CODE_ = 'preSub.200.200';
-- 删除关联交易管理和单笔报送中投资入股类和其他类菜单
UPDATE sys_menu SET ENABLED_ = 'N' WHERE MENU_CODE_ = 'instra.100';
UPDATE sys_menu SET ENABLED_ = 'N' WHERE MENU_CODE_ = 'instra.600';
UPDATE sys_menu SET ENABLED_ = 'N' WHERE MENU_CODE_ = 'preSub.300.100';
UPDATE sys_menu SET ENABLED_ = 'N' WHERE MENU_CODE_ = 'preSub.300.600';
-- 删除报告数据管理：关联交易控制委员会季度报告管理、关联交易控制委员会季度报告管理
UPDATE sys_menu SET ENABLED_ = 'N' WHERE MENU_CODE_ = 'sys.300.600';
UPDATE sys_menu SET ENABLED_ = 'N' WHERE MENU_CODE_ = 'sys.300.400';
-- 调整菜单顺序
DELETE FROM sys_menu WHERE MENU_CODE_ IN ('preSub.300.300','preSub.300.500','instra.300','instra.500');
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('preSub.300.300', '服务类', NULL, 'preSub.300', 'sub/sublog/traServiceList', 'BIZ', NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, 'jepAdmin', 'jepAdmin', '2020-08-27 00:00:00', NULL, 300, 'jepAdmin', 'jepAdmin', '2021-01-11 10:13:54', 1);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('preSub.300.500', '保险业务和其他类', NULL, 'preSub.300', 'sub/sublog/traBusinessList', 'BIZ', NULL, NULL, 'Y', 'Y', 'N', NULL, 'Y', NULL, NULL, 'jepAdmin', 'jepAdmin', '2020-08-27 00:00:00', '1', 500, NULL, NULL, '2020-08-27 00:00:00', 0);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('instra.300', '服务类', NULL, 'instra', 'ins/traService/list', 'BIZ', 'fa fa-sheqel', NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 300, 'jepAdmin', 'jepAdmin', '2021-01-11 10:13:33', NULL);
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('instra.500', '保险业务和其他类', NULL, 'instra', 'ins/traBusiness/list', 'BIZ', 'fa fa-krw', NULL, 'Y', 'Y', 'N', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 500, 'jepAdmin', 'jepAdmin', '2020-11-11 09:17:09', NULL);
-- 添加关联交易经办人管理菜单
-- INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sys.200.1000', '关联交易经办人配置', NULL, 'sys.200', '#', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'N', NULL, NULL, 'jepAdmin', '超级管理员', '2022-11-16 10:57:20', '1', 1000, 'jepAdmin', '超级管理员', '2022-11-16 11:08:22', 1);
-- INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sys.200.1000.100', '资金运用类', NULL, 'sys.200.1000', 'ins/tran/traFunds', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, NULL, NULL, NULL, 'jepAdmin', '超级管理员', '2022-11-16 11:08:22', NULL, 100, 'jepAdmin', '超级管理员', '2022-11-16 11:11:23', 1);
-- INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sys.200.1000.200', '服务类', NULL, 'sys.200.1000', 'ins/tran/traService', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, NULL, NULL, NULL, 'jepAdmin', '超级管理员', '2022-11-16 11:08:58', NULL, 200, 'jepAdmin', '超级管理员', '2022-11-16 11:11:11', 1);
-- INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sys.200.1000.300', '利益转移类', NULL, 'sys.200.1000', 'ins/tran/traInterest', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, 'jepAdmin', '超级管理员', '2022-11-16 11:10:58', '1', 300, NULL, NULL, '2022-11-16 11:10:58', 0);
-- INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sys.200.1000.400', '保险业务和其他类', NULL, 'sys.200.1000', 'ins/tran/traBusiness', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, 'jepAdmin', '超级管理员', '2022-11-16 11:11:57', '1', 400, NULL, NULL, '2022-11-16 11:11:57', 0);

-- 添加免于审议情形字典值
UPDATE sys_dict_item SET ITEM_LABEL_ = '法人或非法人组织' WHERE DICT_CODE_ = 'INS-relType' AND ITEM_VALUE_ = 'ORG';
INSERT INTO `sys_dict` (`dict_code_`, `dict_name_`, `dict_desc_`, `dict_cate_code_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('INS-considerSituation', '免于审议情形', NULL, 'TRA', NULL, 'jepAdmin', 'jepAdmin', '2022-05-17 15:46:51', '1', NULL, NULL, NULL, '2022-05-17 15:46:51', 0);
INSERT INTO `sys_dict_item` (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('db5842244d83492a97e37be58eebff79', 'INS-considerSituation', 'TRA', '一方以现金认购另一方公开发行的股票、公司债券或企业债券、可转换债券或其他衍生品种', '1', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2022-05-17 15:49:19', '1', NULL, NULL, NULL, '2022-05-17 15:49:19', 0);
INSERT INTO `sys_dict_item` (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('3e0bbb9c1276484dbab331fedcad7bd4', 'INS-considerSituation', 'TRA', '同一自然人同时担任银行保险机构和其他法人的独立董事且不存在其他构成关联方情形的，该法人与银行保险机构进行的交易', '2', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2022-05-17 15:50:17', '1', NULL, NULL, NULL, '2022-05-17 15:50:17', 0);
INSERT INTO `sys_dict_item` (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('16bbb3d2f2f943f9a5380539735573a9', 'INS-considerSituation', 'TRA', '与关联法人单笔交易金额小于500万', '3', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2022-05-17 15:50:32', '1', NULL, NULL, NULL, '2022-05-17 15:50:32', 0);
INSERT INTO `sys_dict_item` (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('8e53337f6e2f456ba254f081c87937e0', 'INS-considerSituation', 'TRA', '与关联自然人单笔交易金额小于50万', '4', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2022-05-17 15:50:43', '1', NULL, NULL, NULL, '2022-05-17 15:50:43', 0);
INSERT INTO `sys_dict_item` (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('ac7a87142b634fdc92ae42acc79d1156', 'INS-considerSituation', 'TRA', '活期存款业务', '5', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2022-05-17 15:50:56', '1', NULL, NULL, NULL, '2022-05-17 15:50:56', 0);
INSERT INTO `sys_dict_item` (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('a84f55f4f121433f9769a6b71c267d96', 'INS-considerSituation', 'TRA', '交易的定价为国家规定的', '6', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2022-05-17 15:51:06', '1', NULL, NULL, NULL, '2022-05-17 15:51:06', 0);
INSERT INTO `sys_dict_item` (`item_id_`, `dict_code_`, `dict_cate_code_`, `item_label_`, `item_value_`, `item_desc_`, `filter_`, `tenant_id_`, `created_by_id_`, `created_by_name_`, `created_time_`, `delflag_`, `display_order_`, `modified_by_id_`, `modified_by_name_`, `modified_time_`, `version_`) VALUES ('2e5ea9df3a2c4951b12adcb08ba75d95', 'INS-considerSituation', 'TRA', '银保监会认可的其他情形', '7', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2022-05-17 15:51:18', '1', NULL, NULL, NULL, '2022-05-17 15:51:18', 0);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('ede83b33-b2c8-11nn-ab1d-8cec4b488311', 'INS-relType', 'TRA', '金融产品', 'PRO', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-20 15:37:44', NULL);
DELETE FROM  sys_dict_item WHERE DICT_CODE_ = 'INS-tranType';
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('35592133-b208-11ea-ab1d-8cec4b488362', 'INS-tranType', 'TRA', '资金运用类', '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 16:38:11', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('3f639d55-b208-11ea-ab1d-8cec4b488362', 'INS-tranType', 'TRA', '保险业务及其他类', '4', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 16:38:28', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('48c3a8d5-b208-11ea-ab1d-8cec4b488362', 'INS-tranType', 'TRA', '利益转移类', '3', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-19 16:38:43', NULL);
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('5278bafa-b208-11ea-ab1d-8cec4b488362', 'INS-tranType', 'TRA', '服务类', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2021-01-15 16:19:18', NULL);

-- 关联交易明细表
ALTER TABLE sub_ins_allbustraninfo add COLUMN Trading_direction VARCHAR (1) DEFAULT NULL COMMENT '交易方向';
ALTER TABLE sub_ins_allbustraninfo_view add COLUMN Trading_direction VARCHAR (1) DEFAULT NULL COMMENT '交易方向';
ALTER TABLE sub_ins_allbustraninfo add COLUMN BUS_TYPE VARCHAR (2) DEFAULT NULL COMMENT '业务类型';
ALTER TABLE sub_ins_allbustraninfo_view add COLUMN BUS_TYPE VARCHAR (2) DEFAULT NULL COMMENT '业务类型';
ALTER TABLE sub_ins_allbustraninfo add COLUMN FINANCIAL_ASSSTS_NUM VARCHAR (100) DEFAULT NULL COMMENT '产品批准/注册/备案号';
ALTER TABLE sub_ins_allbustraninfo MODIFY COLUMN TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '交易金额';
ALTER TABLE sub_ins_allbustraninfo_view add COLUMN FINANCIAL_ASSSTS_NUM VARCHAR (100) DEFAULT NULL COMMENT '产品批准/注册/备案号';
ALTER TABLE sub_ins_allbustraninfo_view add COLUMN is_agreement VARCHAR (2) DEFAULT NULL COMMENT '是否统一交易协议';
ALTER TABLE sub_ins_allbustraninfo_view MODIFY COLUMN TRADE_AMOUNT DECIMAL (20,4) DEFAULT NULL COMMENT '交易金额';
ALTER TABLE ins_tra_service MODIFY COLUMN is_Agreement_Impl VARCHAR(2000) DEFAULT NULL COMMENT '统一交易协议执行情况';
ALTER TABLE ins_tra_business MODIFY COLUMN is_Agreement_Impl VARCHAR(2000) DEFAULT NULL COMMENT '统一交易协议执行情况';
ALTER TABLE sub_ins_allbustraninfo MODIFY COLUMN is_Agreement_Impl VARCHAR(2000) DEFAULT NULL COMMENT '统一交易协议执行情况';
ALTER TABLE sub_ins_allbustraninfo_view ADD COLUMN is_Agreement_Impl VARCHAR(2000) DEFAULT NULL COMMENT '统一交易协议执行情况';
DELETE FROM sub_template_details WHERE template_id = '14';

INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (362, 14, NULL, 'InsQuarterReport', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (363, 14, 362, 'InsTransDtl', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (367, 14, 363, 'CreditType', 'busType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (368, 14, 363, 'TransFund', 'tradeAmount', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (370, 14, 363, 'PricePolicy', 'pricingPolicy', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (371, 14, 363, 'ContractNo', 'contractNo', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (372, 14, 363, 'LongTermAgreementFlag', 'isAgreement', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (373, 14, 610, 'AgreementTransactionImplementation', 'isAgreementImpl', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (374, 14, 610, 'RelatedPartyType', 'relTypeSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (375, 14, 363, 'TransTarget', 'tradeSubject', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (376, 14, 363, 'TransType', 'tranType', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (377, 14, 363, 'TransFlag', 'tradingDirection', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (378, 14, 363, 'Remark', 'remark', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (407, 14, 363, 'TransPrice', 'tradePrice', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (408, 14, 363, 'ProductIssueNo', 'financialAssstsNum', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (409, 14, 363, 'TransTime', 'tranDateSub', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (410, 14, 363, 'ProductRatio', 'financialProRatio', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (607, 14, 610, 'RelatedPartyCertNo', 'relNumber', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (608, 14, 610, 'RelatedPartyName', 'relName', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (610, 14, 363, 'RelatedParty', '', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sub_template_details` (`pid`, `template_id`, `parent_id`, `element_name`, `col_name`, `col_length`, `col_must`, `condition_col`, `condition_value`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`, `match_name`) VALUES (611, 14, 363, 'ProductName', 'financialAssstsName', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


-- 部门负责人兼职多个部门实现配置化
CREATE TABLE `sys_approve_map`
(
    `PID`               int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `STAFF_CODE_`       varchar(64)                    NOT NULL COMMENT '用户编码',
    `STAFF_NAME_`       varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名称',
    `DEPT_CODE_`        varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '所属部门',
    `DEPT_NAME_`        varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '所属部门',
    `ROLE_CODE_`        varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '角色编码',
    `ROLE_NAME_`        varchar(128) CHARACTER SET utf8 DEFAULT NULL COMMENT '角色名称',
    `ROLE_STD_CODE_`    varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '标准角色编码',
    `TENANT_ID_`        varchar(64) CHARACTER SET utf8  DEFAULT NULL COMMENT '租户',
    `CREATED_BY_ID_`    varchar(64) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人ID',
    `CREATED_BY_NAME_`  varchar(64) CHARACTER SET utf8  DEFAULT NULL COMMENT '创建人名称',
    `CREATED_TIME_`     datetime                        DEFAULT NULL COMMENT '创建时间',
    `DELFLAG_`          varchar(1) CHARACTER SET utf8   DEFAULT NULL COMMENT '逻辑删除标记',
    `DISPLAY_ORDER_`    int(11) DEFAULT NULL COMMENT '显示序号',
    `MODIFIED_BY_ID_`   varchar(64) CHARACTER SET utf8  DEFAULT NULL COMMENT '修改人ID',
    `MODIFIED_BY_NAME_` varchar(64) CHARACTER SET utf8  DEFAULT NULL COMMENT '修改人名称',
    `MODIFIED_TIME_`    timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `VERSION_`          int(11) DEFAULT NULL COMMENT '版本',
    PRIMARY KEY (`PID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='员工、部门、角色映射表';


-- 扩展日志记录表日志说明长度
ALTER TABLE sys_log_record MODIFY COLUMN log_content VARCHAR (3000)  DEFAULT NULL;
-- 新增菜单
INSERT INTO `sys_menu` (`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('sys.200.1010', '多部门配制化管理', NULL, 'sys.200', 'sys/approveMap/list', 'ADM', NULL, NULL, 'Y', 'Y', 'Y', NULL, 'Y', NULL, NULL, NULL, NULL, NULL, NULL, 920, NULL, NULL, '2022-04-18 16:38:48', NULL);


-- 调整资金运用类投资方式字段值
UPDATE sys_dict_item SET ITEM_VALUE_ = '1' WHERE DICT_CODE_ = 'INS-FundsinvestmentMethod' AND ITEM_VALUE_ = '01';
UPDATE sys_dict_item SET ITEM_VALUE_ = '2' WHERE DICT_CODE_ = 'INS-FundsinvestmentMethod' AND ITEM_VALUE_ = '02';
UPDATE sys_dict_item SET ITEM_LABEL_ = '法人或非法人组织' WHERE DICT_CODE_ = 'Rel_POP' AND ITEM_VALUE_ = '1';


ALTER TABLE ins_tra_funds ADD COLUMN trad_time datetime DEFAULT NULL COMMENT '交易时间';

ALTER TABLE ins_un_sort_out ADD COLUMN trad_time datetime DEFAULT NULL COMMENT '交易时间';



-- 新需求内容

-- 关联方和关联关系新增备案号


ALTER TABLE bus_rel_org ADD COLUMN record_number VARCHAR(20) DEFAULT NULL COMMENT '自然人关联方版本号';

ALTER TABLE bus_rel ADD COLUMN record_number VARCHAR(20) DEFAULT NULL COMMENT '自然人关联方版本号';



-- 自然人关联方表添加版本号
ALTER TABLE bus_rel_psn ADD COLUMN record_number VARCHAR(20) DEFAULT NULL COMMENT '自然人关联方版本号';
-- 备份自然人历史表
ALTER TABLE bus_rel_psn_history RENAME TO bus_rel_psn_history_bak;
-- 创建自然人备份表
CREATE TABLE `bus_rel_psn_history`
(
    `pid`                      int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长主键',
    `reg_code`                 varchar(32)        DEFAULT NULL COMMENT '登记编码',
    `orignal_org`              varchar(20)        DEFAULT NULL COMMENT '源主体银行机构',
    `orignal_org_name`         varchar(100)       DEFAULT NULL COMMENT '源主体银行机构名称',
    `name_`                    varchar(200)       DEFAULT NULL COMMENT '姓名',
    `sex`                      varchar(10)        DEFAULT NULL COMMENT '性別',
    `nationality`              varchar(30)        DEFAULT NULL COMMENT '国别/地区',
    `domestic_or_foreign`      varchar(1)         DEFAULT NULL COMMENT '境内外标识',
    `race`                     varchar(20)        DEFAULT NULL COMMENT '民族',
    `identify_type`            varchar(1)         DEFAULT NULL COMMENT '证件类型',
    `identify_number`          varchar(100)       DEFAULT NULL COMMENT '证件号码',
    `birthdate`                varchar(11)        DEFAULT NULL COMMENT '出生日期',
    `mobile`                   varchar(20)        DEFAULT NULL COMMENT '移动电话',
    `email`                    varchar(32)        DEFAULT NULL COMMENT '邮箱',
    `duty_code`                varchar(255)       DEFAULT NULL COMMENT '职位',
    `status`                   varchar(255)       DEFAULT NULL COMMENT '审批状态    未提交/已提交/未审批',
    `status_comment`           varchar(255)       DEFAULT NULL COMMENT '审批状态说明',
    `marital_status`           varchar(32)        DEFAULT NULL COMMENT '婚姻状况',
    `valid_status`             varchar(10)        DEFAULT NULL COMMENT '是否有效   0|无效1|有效',
    `active_time`              datetime(6) DEFAULT NULL COMMENT '生效时间',
    `invalid_time`             datetime(6) DEFAULT NULL COMMENT '失效时间',
    `mger_org`                 varchar(50)        DEFAULT NULL COMMENT '管户机构(默认是总行)',
    `up_approval_department`   varchar(50)        DEFAULT NULL COMMENT '上一级审批部门',
    `next_approval_department` varchar(50)        DEFAULT NULL COMMENT '下一级审批部门',
    `up_approval_people`       varchar(50)        DEFAULT NULL COMMENT '上一级审批人',
    `next_approval_people`     varchar(50)        DEFAULT NULL COMMENT '下一级审批人',
    `regulator_ybj`            varchar(50)        DEFAULT NULL COMMENT '银保监口径\r\n\r\n 0|否\r\n1|是',
    `regulator_zjh`            varchar(50)        DEFAULT NULL COMMENT '证监会口径（0|否1|是）',
    `regulator_czb`            varchar(50)        DEFAULT NULL COMMENT '财政部口径（0|否1|是）',
    `regulator_oth1`           varchar(50)        DEFAULT NULL COMMENT '其他口径1（0|否1|是）',
    `regulator_oth2`           varchar(50)        DEFAULT NULL COMMENT '其他口径2（0|否1|是）',
    `is_black`                 varchar(4)         DEFAULT NULL COMMENT '是否黑名单（0|否1|是）',
    `black_reason`             varchar(1024)      DEFAULT NULL COMMENT '加入/移出黑名单原因',
    `is_shareholder`           varchar(16)        DEFAULT NULL COMMENT '是否为股东',
    `shareholders_code`        varchar(100)       DEFAULT NULL COMMENT '股东代码（账户号）',
    `open_bank`                varchar(100)       DEFAULT NULL COMMENT '交易账户开户行',
    `trade_account`            varchar(50)        DEFAULT NULL COMMENT '交易账户账号',
    `in_time`                  varchar(11)        DEFAULT NULL COMMENT '入股时间',
    `in_price`                 varchar(20)        DEFAULT NULL COMMENT '入股价格',
    `hole_share_amount`        varchar(50)        DEFAULT NULL COMMENT '持股数量（股）',
    `shareholding_proportion`  varchar(10)        DEFAULT NULL COMMENT '持股占比',
    `frozen_share_amount`      varchar(20)        DEFAULT NULL COMMENT '总冻结股数（股）',
    `pledged_share_amount`     varchar(20)        DEFAULT NULL COMMENT '总质押股数（股）',
    `pledge_ratio`             varchar(10)        DEFAULT NULL COMMENT '质押股权比例',
    `rel_type`                 varchar(20)        DEFAULT NULL COMMENT '关联方类型 (\r\n0-股东\r\n1-关联方\r\n2-股东&关联方）',
    `politics_status`          varchar(64)        DEFAULT NULL COMMENT '政治面貌',
    `education_background`     varchar(64)        DEFAULT NULL COMMENT '学历',
    `residential_address`      varchar(64)        DEFAULT NULL COMMENT '居住地址',
    `work_units`               varchar(64)        DEFAULT NULL COMMENT '工作单位',
    `unit_address`             varchar(64)        DEFAULT NULL COMMENT '单位地址',
    `rel_serial`               varchar(64)        DEFAULT NULL COMMENT '流水号',
    `alig_code`                varchar(100)       DEFAULT NULL COMMENT '定位码',
    `TENANT_ID_`               varchar(64)        DEFAULT NULL COMMENT '租户',
    `CREATED_BY_ID_`           varchar(64)        DEFAULT NULL COMMENT '创建人ID',
    `CREATED_BY_NAME_`         varchar(64)        DEFAULT NULL COMMENT '创建人名称',
    `CREATED_TIME_`            datetime(6) DEFAULT NULL COMMENT '创建时间',
    `DELFLAG_`                 varchar(1)         DEFAULT '1' COMMENT '逻辑删除标记（1|否0|是）',
    `DISPLAY_ORDER_`           int(11) DEFAULT NULL COMMENT '显示序号',
    `MODIFIED_BY_ID_`          varchar(64)        DEFAULT NULL COMMENT '修改人ID',
    `MODIFIED_BY_NAME_`        varchar(64)        DEFAULT NULL COMMENT '修改人名称',
    `MODIFIED_TIME_`           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `VERSION_`                 int(11) DEFAULT NULL COMMENT '版本',
    `dj_status`                varchar(1)         DEFAULT '0' COMMENT '冻结状态',
    `data_source`              varchar(64)        DEFAULT NULL,
    `is_main_gd`               varchar(16)        DEFAULT NULL,
    `capital_number`           varchar(64)        DEFAULT NULL,
    `is_no_share_vote`         varchar(16)        DEFAULT NULL,
    `vote_proportion`          varchar(20)        DEFAULT NULL,
    `limit_vote_situation`     varchar(200)       DEFAULT NULL,
    `trade_balance`            decimal(10, 0)     DEFAULT NULL COMMENT '交易余额',
    `is_Iterate`               varchar(1)         DEFAULT NULL COMMENT '是否需要遍历  0 否 1 是',
    `is_assoparty`             varchar(1)         DEFAULT NULL COMMENT '是否为关联方 1否 0是',
    `AMEND_ID`                 varchar(50)        DEFAULT NULL COMMENT '操作人id',
    `AMEND_NAME`               varchar(50)        DEFAULT NULL COMMENT '操作人',
    `effect_relation`          varchar(6)         DEFAULT '0' COMMENT '是否存在有效关系  0 无  1 有',
    `relate_number`            varchar(20)        DEFAULT NULL COMMENT '关联方自然人编号',
    `TRANSACTION_DEP`          varchar(50)        DEFAULT NULL COMMENT '经办部门',
    `is_change`                varchar(1)         DEFAULT NULL,
    `OPERATOR`                 varchar(100)       DEFAULT NULL COMMENT '经办人',
    `glfbh`                    varchar(65)        DEFAULT NULL COMMENT 'east报送相关字段',
    `record_number`            varchar(20)        DEFAULT NULL COMMENT '自然人关联方版本号',
    `record_type`              varchar(1)         DEFAULT NULL COMMENT '备案类型\n1-新增、2-变更、3-删除',
    `source_id`                int(32) DEFAULT NULL COMMENT '源id',
    PRIMARY KEY (`pid`) USING BTREE,
    KEY                        `psn_index` (`name_`,`identify_type`,`identify_number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=759 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='关联关系自然人备份表';


ALTER TABLE bus_rel_org_history RENAME TO bus_rel_org_history_bak;


CREATE TABLE `bus_rel_org_history` (
                                       `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
                                       `reg_code` varchar(32) DEFAULT NULL COMMENT '登记编码',
                                       `orignal_org` varchar(20) DEFAULT NULL COMMENT '源主体银行机构',
                                       `orignal_org_name` varchar(100) DEFAULT NULL COMMENT '源主体银行机构名称',
                                       `org_name` varchar(200) DEFAULT NULL COMMENT '企业名称',
                                       `org_type` varchar(100) DEFAULT NULL COMMENT '企业类型',
                                       `domestic_or_foreign` varchar(1) DEFAULT NULL COMMENT '境内外标识(1-境内，2-境外)',
                                       `identify_type` varchar(2) DEFAULT NULL COMMENT '证件类型',
                                       `unisc_code` varchar(100) DEFAULT NULL COMMENT '证件号码',
                                       `reg_capital` float(20,4) DEFAULT NULL COMMENT '注册资本(万元)',
  `reg_capital_currency` varchar(10) DEFAULT NULL COMMENT '注册资本币种代码',
  `business_scope` varchar(2000) DEFAULT NULL COMMENT '营业范围',
  `status` varchar(10) DEFAULT NULL COMMENT '机构状态（1-正常，2-停业）',
  `legal_person` varchar(100) DEFAULT NULL COMMENT '法定代表人',
  `reg_address` varchar(1000) DEFAULT NULL COMMENT '注册地址',
  `valid_status` varchar(10) DEFAULT NULL COMMENT '是否有效 0-无效，1-有效',
  `active_time` datetime DEFAULT NULL COMMENT '生效时间',
  `invalid_time` datetime DEFAULT NULL COMMENT '失效时间',
  `mger_org` varchar(50) DEFAULT NULL COMMENT '管户机构(默认是总行)',
  `approval_status` varchar(255) NOT NULL COMMENT '审批状态    未提交/已提交/未审批',
  `status_comment` varchar(255) DEFAULT NULL COMMENT '审批状态说明',
  `up_approval_department` varchar(50) DEFAULT NULL COMMENT '上一级审批部门',
  `next_approval_department` varchar(50) DEFAULT NULL COMMENT '下一级审批部门',
  `up_approval_people` varchar(50) DEFAULT NULL COMMENT '上一级审批人',
  `next_approval_people` varchar(50) DEFAULT NULL COMMENT '下一级审批人',
  `regulator_ybj` varchar(16) DEFAULT NULL COMMENT '银保监口径（0|否1|是）',
  `regulator_zjh` varchar(50) DEFAULT NULL COMMENT '证监会口径（0|否1|是）',
  `regulator_czb` varchar(50) DEFAULT NULL COMMENT '财政部口径（0|否1|是）',
  `regulator_oth1` varchar(50) DEFAULT NULL COMMENT '其他口径1（0|否1|是）',
  `regulator_oth2` varchar(50) DEFAULT NULL COMMENT '其他口径2（0|否1|是）',
  `is_black` varchar(4) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '是否黑名单（0|否1|是）',
  `in_time` varchar(11) DEFAULT NULL,
  `in_price` varchar(20) DEFAULT NULL,
  `black_reason` varchar(1024) DEFAULT NULL COMMENT '加入/移出黑名单原因',
  `is_shareholder` varchar(16) DEFAULT NULL COMMENT '是否为股东',
  `shareholders_code` varchar(100) DEFAULT NULL COMMENT '股东代码（账户号）',
  `legal_identify_type` varchar(10) DEFAULT NULL COMMENT '法定人证件类型',
  `legal_identify_number` varchar(20) DEFAULT NULL COMMENT '法定人证件号码',
  `state_owned` varchar(10) DEFAULT NULL COMMENT '是否国有企业（\r\n\r\n0|否\r\n1|是）',
  `open_bank` varchar(100) DEFAULT NULL COMMENT '账户关联银行账户开户行',
  `trade_account` varchar(50) DEFAULT NULL COMMENT '关联银行户账号',
  `hole_share_amount` varchar(50) DEFAULT NULL COMMENT '持股数量',
  `shareholding_proportion` varchar(10) DEFAULT NULL COMMENT '持股占比',
  `frozen_share` varchar(50) DEFAULT NULL COMMENT '总冻结股数（股）',
  `pledged_share` varchar(50) DEFAULT NULL COMMENT '总质押股数（股）',
  `rel_type` varchar(20) DEFAULT NULL COMMENT '关联方类型',
  `rel_serial` varchar(64) DEFAULT NULL,
  `data_source` varchar(64) DEFAULT NULL COMMENT '数据来源  1|手工录入2|数据采集',
  `is_main_gd` varchar(16) DEFAULT NULL COMMENT '是否本行主要股东  0否1是',
  `capital_number` varchar(64) DEFAULT NULL,
  `is_no_share_vote` varchar(16) DEFAULT NULL COMMENT '是否具有非持股表决权  0是1否',
  `vote_proportion` varchar(20) DEFAULT NULL COMMENT '表决权占比',
  `limit_vote_situation` varchar(200) DEFAULT NULL,
  `alig_code` varchar(100) DEFAULT NULL COMMENT '定位码',
  `TENANT_ID_` varchar(64) DEFAULT NULL COMMENT '租户',
  `CREATED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '创建人ID',
  `CREATED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '创建人名称',
  `CREATED_TIME_` datetime DEFAULT NULL COMMENT '创建时间',
  `DELFLAG_` varchar(1) DEFAULT '1' COMMENT '逻辑删除标记',
  `DISPLAY_ORDER_` int(11) DEFAULT NULL COMMENT '显示序号',
  `MODIFIED_BY_ID_` varchar(64) DEFAULT NULL COMMENT '修改人ID',
  `MODIFIED_BY_NAME_` varchar(64) DEFAULT NULL COMMENT '修改人名称',
  `MODIFIED_TIME_` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `VERSION_` int(11) DEFAULT NULL COMMENT '版本',
  `dj_status` varchar(1) DEFAULT '0' COMMENT '冻结状态',
  `is_insider` varchar(1) DEFAULT NULL COMMENT '是否内部人 1是 0否',
  `warn_leve` varchar(1) DEFAULT NULL COMMENT '预警级别',
  `preset_limit_currency` varchar(11) DEFAULT NULL COMMENT '预设额度币种',
  `preset_limit` varchar(16) DEFAULT NULL COMMENT '预设额度',
  `available_limit` varchar(16) DEFAULT NULL COMMENT '可用额度',
  `mem_group` varchar(64) DEFAULT NULL COMMENT '集团证件号',
  `group_name` varchar(10) DEFAULT NULL COMMENT ' 所属集团名称',
  `effective_not` varchar(20) DEFAULT NULL COMMENT '内部人是否生效  0否  1是',
  `credit_balance` varchar(16) DEFAULT NULL COMMENT '授信余额',
  `is_Iterate` varchar(1) DEFAULT NULL COMMENT '是否需要遍历  0 否  1是',
  `pledge_ratio` varchar(10) DEFAULT NULL COMMENT '质押股权比例',
  `is_assoparty` varchar(1) DEFAULT NULL COMMENT '是否为关联方 1否 0是',
  `AMEND_ID` varchar(50) DEFAULT NULL COMMENT '操作人id',
  `AMEND_NAME` varchar(50) DEFAULT NULL COMMENT '操作人',
  `effect_relation` varchar(6) DEFAULT '0' COMMENT '是否存在有效关系  0 无  1 有',
  `relate_number` varchar(20) DEFAULT NULL COMMENT '关联方法人编号',
  `TRANSACTION_DEP` varchar(50) DEFAULT NULL COMMENT '经办部门',
  `is_change` varchar(50) DEFAULT NULL COMMENT 'ischange',
  `ORG_NATURE` varchar(1000) DEFAULT NULL COMMENT '经济性质或类型',
  `OPERATOR` varchar(50) DEFAULT NULL COMMENT '经办人',
  `is_subsidiary` varchar(50) DEFAULT NULL COMMENT '是否控股子公司',
  `glfbh` varchar(65) DEFAULT NULL COMMENT 'east报送相关字段',
  `record_number` varchar(20) DEFAULT NULL COMMENT '自然人关联方版本号',
	`record_type`              varchar(1)         DEFAULT NULL COMMENT '备案类型\n1-新增、2-变更、3-删除',
   `source_id`                int(32) DEFAULT NULL COMMENT '源id',
  PRIMARY KEY (`pid`,`approval_status`) USING BTREE,
  KEY `org_index` (`org_name`,`org_type`,`unisc_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5333 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='关联关系机构历史表';





-- 删除关联交易中待确认状态
DELETE FROM `sys_dict_item` WHERE DICT_CODE_ = 'BUS-Applystatus' AND ITEM_VALUE_ = '2';
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('5278bafa-b208-11ea-ab1d-8cec4b4211114', 'BUS-Applystatus', 'TRA', '待确认', '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2021-01-15 16:19:18', NULL);
-- INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('5278bafa-b208-11ea-ab1d-8cec4b4222224', 'BUS-Applystatus', 'TRA', '已驳回', '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2021-01-15 16:19:18', NULL);


-- 修改文件上传记录表，字段大小
alter table ins_tra_insenclosure modify  TRANS_NUM varchar(100);

-- 附件上传相关改造
ALTER TABLE ins_tra_insenclosure ADD COLUMN upload_file_type VARCHAR(20) DEFAULT NULL COMMENT '上传文件字典类型';

-- 添加字典 ，使用字典代替生产PDF时查询会签部门
INSERT INTO `sys_dict`(`DICT_CODE_`, `DICT_NAME_`, `DICT_DESC_`, `DICT_CATE_CODE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('BUS-ApprovalToPdf', '关联交易办公室成员部门', NULL, 'SYS.BUS', NULL, 'jepAdmin', '超级管理员', '2023-02-17 15:10:42', '1', NULL, NULL, NULL, '2023-02-17 15:10:42', 0);

-- 修改ITEM_LABEL_ 为会签部门编码
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('1b906cfe9fae43879f95f16ed861905b', 'BUS-ApprovalToPdf', 'SYS.BUS', 'FX00001_1_37', 'asset', '资产管理中心组合管理部负责人', NULL, NULL, 'jepAdmin', '超级管理员', '2023-02-17 15:15:01', NULL, NULL, 'jepAdmin', '超级管理员', '2023-02-17 15:21:01', 1);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('4f1531dc52db4dc2883faee614c789c0', 'BUS-ApprovalToPdf', 'SYS.BUS', 'FX00001_1_28', 'risk', '风险管理部负责人', NULL, NULL, 'jepAdmin', '超级管理员', '2023-02-17 15:15:39', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2023-03-14 16:59:56', 2);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('a5d0052c1fca41fd9cc44fd041ccaacb', 'BUS-ApprovalToPdf', 'SYS.BUS', 'FX00001_1_8', 'compliance', '合规负责人', NULL, NULL, 'jepAdmin', '超级管理员', '2023-02-17 15:13:27', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2023-03-13 13:59:09', 2);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('dab806bc1a2949b48955c6d065027dc1', 'BUS-ApprovalToPdf', 'SYS.BUS', 'FX00001_1_29', 'secretary', '董事会秘书', NULL, NULL, 'jepAdmin', '超级管理员', '2023-02-17 15:14:20', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2023-03-13 13:59:32', 2);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('dca9f1b03b054d1eadc14d3143f59048', 'BUS-ApprovalToPdf', 'SYS.BUS', 'FX00001_1_26', 'finance', '财务部负责人', NULL, NULL, 'jepAdmin', '超级管理员', '2023-02-17 15:16:25', NULL, NULL, 'FXAdmin', '复星保德信管理员', '2023-03-14 17:00:09', 2);

--关联交易上传文件  14个
INSERT INTO `sys_dict`(`DICT_CODE_`, `DICT_NAME_`, `DICT_DESC_`, `DICT_CATE_CODE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('BUS-AttachmentBusType', '关联交易上传文件', NULL, 'SYS.BUS', NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 14:08:34', '1', NULL, NULL, NULL, '2023-01-11 14:08:34', 0);
--
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('16b3a0df8949416ca05429243918fe43', 'BUS-AttachmentBusType', 'SYS.BUS', '填报机构控股股东及其关联方上季度末征信评级报告', 'Z03', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 15:07:40', NULL, NULL, 'jepAdmin', '超级管理员', '2023-02-14 10:49:05', 1);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('18151a45701f40a79bfd4e48be67836b', 'BUS-AttachmentBusType', 'SYS.BUS', '穿透交易架构图', 'Z01', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 15:04:43', '1', NULL, NULL, NULL, '2023-01-11 15:04:43', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('4d48ff960a884c95a72f6524cdd2b7ad', 'BUS-AttachmentBusType', 'SYS.BUS', '交易涉及的有关审批文件', 'Y02', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 14:59:22', '1', NULL, NULL, NULL, '2023-01-11 14:59:22', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('530d50beeb1443c1bd732f6af46c04bc', 'BUS-AttachmentBusType', 'SYS.BUS', '股东（大会）、董事会决议', 'Y03', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 15:00:10', '1', NULL, NULL, NULL, '2023-01-11 15:00:10', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('55cb3c75f2b34afeb1dc277ecde84c2e', 'BUS-AttachmentBusType', 'SYS.BUS', '资金运用类交易的协议', 'Y01', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 14:19:36', '1', NULL, NULL, NULL, '2023-01-11 14:19:36', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('60465af5ba8e4e819b1f6282db6fa576', 'BUS-AttachmentBusType', 'SYS.BUS', '中介服务机构出具的专业报告', 'Z05', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 15:09:24', '1', NULL, NULL, NULL, '2023-01-11 15:09:24', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('6a0c49a4c93e4314a6b7a4863a0b46f9', 'BUS-AttachmentBusType', 'SYS.BUS', '总经理室会议纪要', 'Z06', NULL, NULL, NULL, 'jepAdmin', '超级管理员', '2023-03-01 15:22:33', NULL, NULL, 'jepAdmin', '超级管理员', '2023-03-01 15:29:45', 1);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('88b1aae394c2439dab038b4f8862392b', 'BUS-AttachmentBusType', 'SYS.BUS', '关联交易管理办公室会议纪要', 'Z07', NULL, NULL, NULL, 'jepAdmin', '超级管理员', '2023-03-01 15:22:07', '1', NULL, NULL, NULL, '2023-03-01 15:22:07', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('8aba6661bde14135a920a3d496fcf232', 'BUS-AttachmentBusType', 'SYS.BUS', '独立董事发表意见情况', 'Y05', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 15:04:07', '1', NULL, NULL, NULL, '2023-01-11 15:04:07', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('abfe153aabc84e339060333eaba3daca', 'BUS-AttachmentBusType', 'SYS.BUS', '董事会上会材料', 'Y06', NULL, NULL, NULL, 'jepAdmin', '超级管理员', '2023-03-01 15:21:15', '1', NULL, NULL, NULL, '2023-03-01 15:21:15', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('c128f3405a01402cb71c410b9fd2707b', 'BUS-AttachmentBusType', 'SYS.BUS', '关联交易控制委员会的意见或决议', 'Y04', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 15:03:28', '1', NULL, NULL, NULL, '2023-01-11 15:03:28', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('c9a1516ce07248e196d599d15e367442', 'BUS-AttachmentBusType', 'SYS.BUS', '交易基础资产涉及的相关法律文件', 'Z04', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 15:08:39', '1', NULL, NULL, NULL, '2023-01-11 15:08:39', 0);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('dce4b5bc0a734c29b3cbb85f4cf4b4c7', 'BUS-AttachmentBusType', 'SYS.BUS', '其他', 'Z08', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 15:10:05', NULL, NULL, 'jepAdmin', '超级管理员', '2023-03-01 15:29:32', 1);
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('fdf2fe807cac480097a75802f4702b97', 'BUS-AttachmentBusType', 'SYS.BUS', '填报机构控股股东及其关联方上年度经审计的财务报表', 'Z02', NULL, NULL, NULL, 'jepAdmin', 'jepAdmin', '2023-01-11 15:06:23', '1', NULL, NULL, NULL, '2023-01-11 15:06:23', 0);

--  关联交易上传文件  1个sys_dict
INSERT INTO `sys_dict`(`DICT_CODE_`, `DICT_NAME_`, `DICT_DESC_`, `DICT_CATE_CODE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('BUS-AttachmentBusType1', '关联交易上传附件-其他', NULL, 'SYS.BUS', NULL, 'FXAdmin', '复星保德信管理员', '2023-03-08 16:55:38', '1', NULL, NULL, NULL, '2023-03-08 16:55:38', 0);
--  sys_dict_item
INSERT INTO `sys_dict_item`(`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('0a805fe27b044d8ba535fa906905e568', 'BUS-AttachmentBusType1', 'SYS.BUS', '其他', 'Z08', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2023-03-08 16:56:08', '1', NULL, NULL, NULL, '2023-03-08 16:56:08', 0);


-- ALTER TABLE bus_rel_import ADD COLUMN item_description VARCHAR(100) DEFAULT '0' COMMENT '0新增 1修改';
-- ALTER TABLE bus_rel_import ADD COLUMN record_number VARCHAR(20) DEFAULT NULL COMMENT '关联关系备份时版本号';

ALTER TABLE bus_rel_history ADD COLUMN proc_inst_id VARCHAR(200) DEFAULT null COMMENT '关联流程表';
ALTER TABLE bus_rel_history ADD COLUMN failure_status VARCHAR(20) DEFAULT NULL COMMENT '失效确认状态 1待确认2已确认';
ALTER TABLE bus_rel_history ADD COLUMN item_description VARCHAR(100) DEFAULT '0' COMMENT '0新增 1修改';
ALTER TABLE bus_rel_history ADD COLUMN record_number VARCHAR(20) DEFAULT NULL COMMENT '关联关系备份时版本号';
ALTER TABLE bus_rel_history ADD COLUMN source_id VARCHAR(32) DEFAULT NULL COMMENT '关联关系备份原pid';

-- 自然人关联方导入是否启用证件号码校验
INSERT INTO `sys_appconfig` (`CONFIG_CODE_`, `CONFIG_NAME_`, `CONFIG_DESC_`, `CONFIG_VALUE_`, `DEFAULT_VALUE_`, `CONFIG_LEVEL_`, `VALUE_TYPE_`, `INPUT_TYPE_`, `PARENT_ID_`, `UNIT_`, `VALUE_RANGE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('WHETHER_VERIFY_IDNUMBER', '自然人关联方导入是否启用证件号码校验', 'Y-开启，N-关闭', 'N', NULL, 'APP', NULL, NULL, NULL, NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2023-03-08 18:15:35', '1', NULL, NULL, NULL, '2023-03-08 18:15:35', 0);
INSERT INTO `sys_appconfig` VALUES ('tran_file_upload_path', '关联交易合规管理平台访问地址', '平台访问地址', 'http://gljy.pflife.com.cn:8080/rptrs/login', NULL, 'APP', NULL, NULL, NULL, NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2023-03-08 18:15:35', '1', NULL, NULL, NULL, '2023-03-08 18:15:35', 0);

--  备份字典添加 （备份，所有）
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('ea396e672fbf48c39e311e44f57fafa9', 'INS-operationType', 'TRA', '备份', '4', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2023-03-08 13:26:49', '1', NULL, NULL, NULL, '2023-03-08 13:26:49', '0');
INSERT INTO `sys_dict_item` (`ITEM_ID_`, `DICT_CODE_`, `DICT_CATE_CODE_`, `ITEM_LABEL_`, `ITEM_VALUE_`, `ITEM_DESC_`, `FILTER_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('f0bc3b50f25b4514a0c31e15779656e5', 'INS-operationType', 'TRA', '所有', '5', NULL, NULL, NULL, 'FXAdmin', '复星保德信管理员', '2023-03-08 13:27:10', '1', NULL, NULL, NULL, '2023-03-08 13:27:10', '0');
-- ====================================================================================================================
-- =========================================复星保德信二期项目相关修改SQL汇总记录end=========================================
-- ====================================================================================================================
--  查询条件是否添加删除标识

-- 参数表新增数据
INSERT INTO sys_appconfig (`CONFIG_CODE_`, `CONFIG_NAME_`, `CONFIG_DESC_`, `CONFIG_VALUE_`, `DEFAULT_VALUE_`, `CONFIG_LEVEL_`, `VALUE_TYPE_`, `INPUT_TYPE_`, `PARENT_ID_`, `UNIT_`, `VALUE_RANGE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('EMAIL_TO_SEND', '未归类业务邮件发送人', '未归类业务邮件发送人', '李华治|Huazhi.Li@pflife.com.cn,陈佳倩|Jiaqian.Chen@pflife.com.cn', NULL, 'APP', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, NULL, '2023-03-22 05:01:03', 0);

-- 机构视图表 字段长度变更
alter table sub_organ_view modify  identify_type varchar(2);

--  关联方历史 新增菜单
INSERT INTO `sys_menu`(`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('bus.710', '自然人历史关联方', NULL, 'bus', 'bus/relPsn/history/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, 'jepAdmin', '超级管理员', '2023-03-16 18:53:35', '1', 710, NULL, NULL, '2023-03-16 18:53:35', 0);
INSERT INTO `sys_menu`(`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('bus.810', '法人或非法人组织历史关联方', NULL, 'bus', 'bus/relOrg/history/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, 'jepAdmin', '超级管理员', '2023-03-16 18:53:55', '1', 810, NULL, NULL, '2023-03-16 18:53:55', 0);
INSERT INTO `sys_menu`(`MENU_CODE_`, `MENU_NAME_`, `MODULE_CODE_`, `PARENT_ID_`, `URL_`, `MENU_LEVEL_`, `MENU_IMAGE_`, `MENU_TARGET_`, `ENABLED_`, `VISIBLED_`, `PUBLIC_MENU_`, `TIPS_`, `LEAF_NODE_`, `CONTEXT_RELATIVE_`, `TENANT_ID_`, `CREATED_BY_ID_`, `CREATED_BY_NAME_`, `CREATED_TIME_`, `DELFLAG_`, `DISPLAY_ORDER_`, `MODIFIED_BY_ID_`, `MODIFIED_BY_NAME_`, `MODIFIED_TIME_`, `VERSION_`) VALUES ('relation.501', '关联关系历史表', NULL, 'relation', 'bus/rel/history/list', NULL, NULL, NULL, 'Y', 'Y', NULL, NULL, 'Y', NULL, NULL, 'jepAdmin', '超级管理员', '2023-03-16 18:52:18', '1', 501, NULL, NULL, '2023-03-16 18:52:18', 0);

-- 关联关系导入中间表字段长度修改
alter table bus_rel_import MODIFY to_identify_number VARCHAR(100);
alter table bus_rel_import MODIFY from_identify_number VARCHAR(100);