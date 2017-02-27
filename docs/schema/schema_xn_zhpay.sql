/*
 Navicat MySQL Data Transfer

 Source Server         : 148
 Source Server Version : 50545
 Source Host           : 121.43.101.148
 Source Database       : xn_zhpay

 Target Server Version : 50545
 File Encoding         : utf-8

 Date: 02/24/2017 20:31:24 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tact_hzb`
-- ----------------------------
DROP TABLE IF EXISTS `tact_hzb`;
CREATE TABLE `tact_hzb` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `pic` varchar(255) DEFAULT NULL COMMENT '照片',
  `description` text COMMENT '玩法描述',
  `price` bigint(20) DEFAULT NULL COMMENT '价格',
  `currency` varchar(4) DEFAULT NULL COMMENT '价格币种',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tact_hzb_hold`
-- ----------------------------
DROP TABLE IF EXISTS `tact_hzb_hold`;
CREATE TABLE `tact_hzb_hold` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `hzb_code` varchar(32) DEFAULT NULL COMMENT '汇赚宝编号',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（未激活，激活，下架）',
  `price` bigint(20) DEFAULT NULL COMMENT '购买价格',
  `currency` varchar(4) DEFAULT NULL COMMENT '价格币种',
  `period_rock_num` int(11) DEFAULT NULL COMMENT '周期内被摇总次数',
  `total_rock_num` int(11) DEFAULT NULL COMMENT '已摇总次数',
  `pay_amount1` bigint(20) DEFAULT NULL COMMENT '支付人民币',
  `pay_amount2` bigint(20) DEFAULT NULL COMMENT '支付虚拟币1',
  `pay_amount3` bigint(20) DEFAULT NULL COMMENT '支付虚拟币2',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip',
  `pay_group` varchar(32) DEFAULT NULL COMMENT '支付组号',
  `pay_code` varchar(32) DEFAULT NULL COMMENT '支付编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tact_hzb_mgift`
-- ----------------------------
DROP TABLE IF EXISTS `tact_hzb_mgift`;
CREATE TABLE `tact_hzb_mgift` (
  `code` varchar(32) NOT NULL COMMENT '红包编号',
  `adv_title` varchar(255) DEFAULT NULL COMMENT '广告语',
  `owner` varchar(32) DEFAULT NULL COMMENT '树主人编号',
  `owner_currency` varchar(4) DEFAULT NULL COMMENT '树主人领取币种',
  `owner_amount` bigint(20) DEFAULT NULL COMMENT '树主人领取金额',
  `receive_currency` varchar(4) DEFAULT NULL COMMENT '领取人币种',
  `receive_amount` bigint(20) DEFAULT NULL COMMENT '红包领取人编号',
  `receiver` varchar(32) DEFAULT NULL COMMENT '红包领取人编号',
  `receive_datetime` datetime DEFAULT NULL COMMENT '红包被领取时间',
  `status` varchar(4) DEFAULT NULL COMMENT '红包状态',
  `create_datetime` datetime DEFAULT NULL COMMENT '红包创建时间',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tact_hzb_yy`
-- ----------------------------
DROP TABLE IF EXISTS `tact_hzb_yy`;
CREATE TABLE `tact_hzb_yy` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `hzb_hold_id` bigint(32) DEFAULT NULL COMMENT '汇赚宝持有Id',
  `type` varchar(32) DEFAULT NULL COMMENT '类型（1 红包 2 钱包 3 购物币）',
  `quantity` int(11) DEFAULT NULL COMMENT '摇出数量',
  `user_id` varchar(32) DEFAULT NULL,
  `device_no` varchar(255) DEFAULT NULL COMMENT '设备编号',
  `create_datetime` datetime DEFAULT NULL COMMENT '产生时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tact_jewel`
-- ----------------------------
DROP TABLE IF EXISTS `tact_jewel`;
CREATE TABLE `tact_jewel` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `template_code` varchar(32) DEFAULT NULL COMMENT '模板编号',
  `periods` int(11) DEFAULT NULL COMMENT '期号',
  `currency` varchar(4) DEFAULT NULL COMMENT '中奖币种',
  `amount` bigint(20) DEFAULT NULL COMMENT '中奖金额',
  `total_num` int(11) DEFAULT '0' COMMENT '总人次',
  `price` bigint(20) DEFAULT NULL COMMENT '人次单价',
  `max_invest_num` int(11) DEFAULT NULL COMMENT '单人最大投资次数',
  `adv_text` text COMMENT '宣传文字',
  `adv_pic` varchar(255) DEFAULT NULL COMMENT '宣传图',
  `invest_num` int(11) DEFAULT '0' COMMENT '已投资人次',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `win_number` varchar(255) DEFAULT NULL COMMENT '中奖号码',
  `win_user` varchar(255) DEFAULT NULL COMMENT '中奖人编号',
  `win_datetime` datetime DEFAULT NULL COMMENT '中奖时间',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（0 募集中，1 已揭晓）',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tact_jewel_record`
-- ----------------------------
DROP TABLE IF EXISTS `tact_jewel_record`;
CREATE TABLE `tact_jewel_record` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `jewel_code` varchar(32) DEFAULT NULL COMMENT '标的编号',
  `invest_datetime` datetime DEFAULT NULL COMMENT '投资时间',
  `times` int(11) DEFAULT NULL COMMENT '参与次数',
  `pay_amount` bigint(20) DEFAULT NULL COMMENT '支付金额',
  `pay_datetime` varchar(32) DEFAULT NULL COMMENT '支付时间(格式化到毫秒)',
  `status` varchar(4) DEFAULT NULL COMMENT '状态(0待开奖，1已中奖，2未中奖)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ip` varchar(255) DEFAULT NULL COMMENT '购买IP地址',
  `pay_group` varchar(32) DEFAULT NULL COMMENT '支付组号',
  `pay_code` varchar(32) DEFAULT NULL COMMENT '支付编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tact_jewel_record_number`
-- ----------------------------
DROP TABLE IF EXISTS `tact_jewel_record_number`;
CREATE TABLE `tact_jewel_record_number` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `jewel_code` varchar(32) DEFAULT NULL COMMENT '夺宝标的编号',
  `record_code` varchar(32) DEFAULT NULL COMMENT '夺宝记录编号',
  `number` varchar(32) DEFAULT NULL COMMENT '夺宝号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tact_jewel_template`
-- ----------------------------
DROP TABLE IF EXISTS `tact_jewel_template`;
CREATE TABLE `tact_jewel_template` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `currency` varchar(4) DEFAULT NULL COMMENT '中奖币种',
  `amount` bigint(20) DEFAULT NULL COMMENT '中奖金额',
  `total_num` int(11) DEFAULT NULL COMMENT '总人次',
  `price` bigint(20) DEFAULT NULL COMMENT '人次单价',
  `max_invest_num` int(11) DEFAULT NULL COMMENT '单人最大投资次数',
  `adv_text` text COMMENT '宣传文字',
  `adv_pic` varchar(255) DEFAULT NULL COMMENT '宣传图',
  `current_periods` int(11) DEFAULT NULL COMMENT '当前期数',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（0 待上架 1 已上架 2 已下架）',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tsys_config`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_config`;
CREATE TABLE `tsys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `category` varchar(32) DEFAULT NULL COMMENT '大类',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  `cname` varchar(255) DEFAULT NULL COMMENT '配置名',
  `ckey` varchar(32) DEFAULT NULL COMMENT 'key值',
  `cvalue` varchar(255) DEFAULT NULL COMMENT '值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `belong` int(11) DEFAULT NULL COMMENT '属于谁',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tsys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `tsys_dict`;
CREATE TABLE `tsys_dict` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号（自增长）',
  `type` char(1) DEFAULT NULL COMMENT '类型（第一层/第二层）',
  `parent_key` varchar(32) DEFAULT NULL COMMENT '父key',
  `dkey` varchar(32) DEFAULT NULL COMMENT 'key',
  `dvalue` varchar(255) DEFAULT NULL COMMENT '值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
