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

