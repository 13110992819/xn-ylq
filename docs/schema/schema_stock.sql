
-- ----------------------------
--  Table structure for `tact_stock`
-- ----------------------------
DROP TABLE IF EXISTS `tact_stock`;
CREATE TABLE `tact_stock` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `type` varchar(4) DEFAULT NULL COMMENT '类型(A A档，B B档，C C档，D D档)',
  `pic` varchar(255) DEFAULT NULL COMMENT '图片',
  `description` text COMMENT '玩法描述',
  `capital` int(11) DEFAULT NULL COMMENT '股本',
  `price` bigint(20) DEFAULT NULL COMMENT '价格',
  `currency` varchar(4) DEFAULT NULL COMMENT '价格币种',
  `back_interval` int(11) DEFAULT NULL COMMENT '返还间隔',
  `back_count` int(11) DEFAULT NULL COMMENT '返还总次数',
  `welfare1` bigint(20) DEFAULT NULL COMMENT '返还福利1',
  `welfare2` bigint(20) DEFAULT NULL COMMENT '返还福利2',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（1 使用中 0 假删除）',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tact_stock_back`
-- ----------------------------
DROP TABLE IF EXISTS `tact_stock_back`;
CREATE TABLE `tact_stock_back` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `stock_code` varchar(32) DEFAULT NULL COMMENT '股份编号',
  `back_datetime` datetime DEFAULT NULL COMMENT '返还时间',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tact_stock_hold`
-- ----------------------------
DROP TABLE IF EXISTS `tact_stock_hold`;
CREATE TABLE `tact_stock_hold` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `stock_code` varchar(32) DEFAULT NULL COMMENT '股份编号',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（未清算/已清算）',
  `back_num` int(11) DEFAULT NULL COMMENT '已经返还次数',
  `back_welfare1` bigint(20) DEFAULT NULL COMMENT '已返福利1',
  `back_welfare2` bigint(20) DEFAULT NULL COMMENT '已返福利2',
  `pay_amount1` bigint(20) DEFAULT NULL COMMENT '支付人民币',
  `pay_amount2` bigint(20) DEFAULT NULL COMMENT '支付虚拟币1',
  `pay_amount3` bigint(20) DEFAULT NULL COMMENT '支付虚拟币2',
  `next_back` datetime DEFAULT NULL COMMENT '下次返还时间',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  `pay_code` varchar(32) DEFAULT NULL COMMENT '第三方支付编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;