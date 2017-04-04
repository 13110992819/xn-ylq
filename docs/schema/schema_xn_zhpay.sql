-- ----------------------------
--  Table structure for `tyydb_jewel_template`
-- ----------------------------
DROP TABLE IF EXISTS `tyydb_jewel_template`;
CREATE TABLE `tyydb_jewel_template` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `to_amount` bigint(20) DEFAULT NULL COMMENT '中奖金额',
  `to_currency` varchar(4) DEFAULT NULL COMMENT '中奖币种',
  `total_num` int(11) DEFAULT NULL COMMENT '总人次',
  `max_num` int(11) DEFAULT NULL COMMENT '单人最大投资次数',
  `from_amount` bigint(20) DEFAULT NULL COMMENT '人次单价',
  `from_currency` varchar(4) DEFAULT NULL COMMENT '单价币种',
  `slogan` varchar(255) DEFAULT NULL COMMENT '标语',
  `adv_pic` text DEFAULT NULL COMMENT '宣传图',
  `current_periods` int(11) DEFAULT NULL COMMENT '当前期数',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（0 待上架 1 已上架 2 已下架）',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '夺宝模板'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tyydb_jewel`
-- ----------------------------
DROP TABLE IF EXISTS `tyydb_jewel`;
CREATE TABLE `tyydb_jewel` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `template_code` varchar(32) DEFAULT NULL COMMENT '模板编号',
  `periods` int(11) DEFAULT NULL COMMENT '期号',
  `to_amount` bigint(20) DEFAULT NULL COMMENT '中奖金额',
  `to_currency` varchar(4) DEFAULT NULL COMMENT '中奖币种',
  `total_num` int(11) DEFAULT '0' COMMENT '总人次',
  `max_num` int(11) DEFAULT NULL COMMENT '单人最大投资次数',
  `invest_num` int(11) DEFAULT '0' COMMENT '已投资人次',
  `from_amount` bigint(20) DEFAULT NULL COMMENT '人次单价',
  `from_currency` varchar(4) DEFAULT NULL COMMENT '单价币种',
  `slogan` varchar(255) DEFAULT NULL COMMENT '标语',
  `adv_pic` text DEFAULT NULL COMMENT '宣传图',
  `start_datetime` datetime DEFAULT NULL COMMENT '开始时间',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（0 募集中，1 已揭晓）',
  `win_number` varchar(255) DEFAULT NULL COMMENT '中奖号码',
  `win_user` varchar(255) DEFAULT NULL COMMENT '中奖人编号',
  `win_datetime` datetime DEFAULT NULL COMMENT '中奖时间',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '夺宝标的'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
-- ----------------------------
--  Table structure for `tyydb_jewel_record`
-- ----------------------------
DROP TABLE IF EXISTS `tyydb_jewel_record`;
CREATE TABLE `tyydb_jewel_record` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `jewel_code` varchar(32) DEFAULT NULL COMMENT '标的编号',
  `invest_datetime` datetime DEFAULT NULL COMMENT '投资时间',
  `times` int(11) DEFAULT NULL COMMENT '参与次数',
  `ip` varchar(255) DEFAULT NULL COMMENT '购买IP地址',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `pay_group` varchar(32) DEFAULT NULL COMMENT '支付组号',
  `pay_code` varchar(32) DEFAULT NULL COMMENT '支付编号',
  `pay_amount` bigint(20) DEFAULT NULL COMMENT '支付金额',
  `pay_datetime` varchar(32) DEFAULT NULL COMMENT '支付时间(格式化到毫秒)',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '参与记录'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tyydb_jewel_record_number`
-- ----------------------------
DROP TABLE IF EXISTS `tyydb_jewel_record_number`;
CREATE TABLE `tyydb_jewel_record_number` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `jewel_code` varchar(32) DEFAULT NULL COMMENT '夺宝标的编号',
  `record_code` varchar(32) DEFAULT NULL COMMENT '夺宝记录编号',
  `number` varchar(32) DEFAULT NULL COMMENT '夺宝号码',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`) COMMENT '参与号码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tyqs_hzb_template`
-- ----------------------------
DROP TABLE IF EXISTS `tyqs_hzb_template`;
CREATE TABLE `tyqs_hzb_template` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `pic` varchar(255) DEFAULT NULL COMMENT '照片',
  `price` bigint(20) DEFAULT NULL COMMENT '价格',
  `currency` varchar(4) DEFAULT NULL COMMENT '价格币种',
  
  `period_rock_num` int(11) DEFAULT NULL COMMENT '周期内可摇总次数',
  `total_rock_num` int(11) DEFAULT NULL COMMENT '可摇总次数',
  `back_amount1` bigint(20) DEFAULT NULL COMMENT '价值1',
  `back_amount2` bigint(20) DEFAULT NULL COMMENT '价值2',
  `back_amount3` bigint(20) DEFAULT NULL COMMENT '价值3',
  
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '汇赚宝模板'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tyqs_hzb`
-- ----------------------------
DROP TABLE IF EXISTS `tyqs_hzb`;
CREATE TABLE `tyqs_hzb` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `template_code` varchar(32) DEFAULT NULL COMMENT '汇赚宝编号',
  `price` bigint(20) DEFAULT NULL COMMENT '购买时价格',
  `currency` varchar(4) DEFAULT NULL COMMENT '购买时价格币种',
 
  `period_rock_num` int(11) DEFAULT NULL COMMENT '周期内已被摇总次数',
  `total_rock_num` int(11) DEFAULT NULL COMMENT '已摇总次数',
  `back_amount1` bigint(20) DEFAULT NULL COMMENT '已价值1',
  `back_amount2` bigint(20) DEFAULT NULL COMMENT '已价值2',
  `back_amount3` bigint(20) DEFAULT NULL COMMENT '已价值3',
  
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',

  `pay_group` varchar(32) DEFAULT NULL COMMENT '支付组号',
  `pay_code` varchar(32) DEFAULT NULL COMMENT '支付编号',
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_amount1` bigint(20) DEFAULT NULL COMMENT '支付人民币',
  `pay_amount2` bigint(20) DEFAULT NULL COMMENT '支付虚拟币1',
  `pay_amount3` bigint(20) DEFAULT NULL COMMENT '支付虚拟币2',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '汇赚宝'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tyqs_hzb_yy`
-- ----------------------------
DROP TABLE IF EXISTS `tyqs_hzb_yy`;
CREATE TABLE `tyqs_hzb_yy` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `hzb_code` varchar(32) DEFAULT NULL COMMENT '汇赚宝',
  `yy_currency` varchar(32) DEFAULT NULL COMMENT '摇出币种',
  `yy_amount` bigint(20) DEFAULT NULL COMMENT '摇出金额',
  `user_id` varchar(32) DEFAULT NULL COMMENT '摇的人',
  `device_no` varchar(255) DEFAULT NULL COMMENT '设备编号',
  `owner_fc_currency` varchar(32) DEFAULT NULL COMMENT '树主人分成币种',
  `owner_fc_amount` bigint(20) DEFAULT NULL COMMENT '树主人分成金额',
  `create_datetime` datetime DEFAULT NULL COMMENT '产生时间',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '汇赚宝摇一摇'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tyqs_hzb_mgift`
-- ----------------------------
DROP TABLE IF EXISTS `tyqs_hzb_mgift`;
CREATE TABLE `tyqs_hzb_mgift` (
  `code` varchar(32) NOT NULL COMMENT '红包编号',
   `hzb_code` varchar(32) DEFAULT NULL COMMENT '汇赚宝',
  `slogan` varchar(255) DEFAULT NULL COMMENT '标语',
 `owner` varchar(32) DEFAULT NULL COMMENT '树主人编号',
 
  `owner_currency` varchar(4) DEFAULT NULL COMMENT '树主人领取币种',
  `owner_amount` bigint(20) DEFAULT NULL COMMENT '树主人领取金额',
   `receive_currency` varchar(4) DEFAULT NULL COMMENT '领取人币种',
  `receive_amount` bigint(20) DEFAULT NULL COMMENT '红包领取金额',
  
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) DEFAULT NULL,
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  
  `receiver` varchar(32) DEFAULT NULL COMMENT '红包领取人编号',
  `receive_datetime` datetime DEFAULT NULL COMMENT '被领取时间',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '定向红包'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `tsys_config`;
CREATE TABLE `tsys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `ckey` varchar(32) DEFAULT NULL COMMENT 'key值',
  `cvalue` text COMMENT '值',
  
  `updater` varchar(32) NOT NULL COMMENT '更新人',
  `update_datetime` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`) COMMENT '系统参数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tsys_dict`;
CREATE TABLE `tsys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号（自增长）',
  `type` char(1) NOT NULL COMMENT '类型（0=下拉框意义 1=下拉框选项）',
  `parent_key` varchar(32) DEFAULT NULL COMMENT '父key',
  `dkey` varchar(32) NOT NULL COMMENT 'key',
  `dvalue` varchar(64) NOT NULL COMMENT '值',
  
  `updater` varchar(32) NOT NULL COMMENT '更新人',
  `update_datetime` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`) COMMENT '数据字典'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;