/*
 Navicat MySQL Data Transfer

 Source Server         : 148
 Source Server Version : 50545
 Source Host           : 121.43.101.148
 Source Database       : std_mall

 Target Server Version : 50545
 File Encoding         : utf-8

 Date: 01/13/2017 11:26:40 AM
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
  `pay_code` varchar(32) DEFAULT NULL COMMENT '支付编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`)
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
  `store_code` varchar(32) DEFAULT NULL COMMENT '商家编号',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `slogan` varchar(255) DEFAULT NULL COMMENT '标语',
  `adv_pic` text COMMENT '广告图',
  `description_text` text COMMENT '详情文字',
  `description_pic` text COMMENT '详情图片',
  `price1` bigint(20) DEFAULT NULL COMMENT '单价1',
  `price2` bigint(20) DEFAULT NULL COMMENT '单价2',
  `price3` bigint(20) DEFAULT NULL COMMENT '单价3',
  `total_num` int(11) DEFAULT '0' COMMENT '所需总人次',
  `invest_num` int(11) DEFAULT '0' COMMENT '已投资人次',
  `start_datetime` datetime DEFAULT NULL COMMENT '夺宝开始时间',
  `raise_days` int(11) DEFAULT NULL COMMENT '募集天数',
  `win_number` varchar(255) DEFAULT NULL COMMENT '中奖号码',
  `win_user_id` varchar(255) DEFAULT NULL COMMENT '中奖人编号',
  `lot_alg` varchar(255) DEFAULT NULL COMMENT '抽奖算法',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（0 待审批，1 审批通过，2 审批不通过，3 夺宝进行中，4 强制下架，5 到期流标，6 夺宝成功，待开奖 ，7 已开奖，待发货， 8 已发货，待收货， 9 已收货）',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  `approver` varchar(32) DEFAULT NULL COMMENT '审批人',
  `approve_datetime` datetime DEFAULT NULL COMMENT '审批时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `lottery_datetime` datetime DEFAULT NULL COMMENT '开奖时间',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tact_jewel_interact`
-- ----------------------------
DROP TABLE IF EXISTS `tact_jewel_interact`;
CREATE TABLE `tact_jewel_interact` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `type` varchar(4) DEFAULT NULL COMMENT '类型（1 好评）',
  `evaluate_type` varchar(32) DEFAULT NULL COMMENT '评价类型(A 好评 B 中评 C 差评)',
  `interacter` varchar(32) DEFAULT NULL COMMENT '互动人',
  `interact_datetime` datetime DEFAULT NULL COMMENT '互动时间',
  `jewel_code` varchar(32) DEFAULT NULL COMMENT '标的编号',
  `order_code` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tact_jewel_record`
-- ----------------------------
DROP TABLE IF EXISTS `tact_jewel_record`;
CREATE TABLE `tact_jewel_record` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `jewel_code` varchar(32) DEFAULT NULL COMMENT '宝贝编号',
  `create_datetime` datetime DEFAULT NULL COMMENT '标语',
  `times` int(11) DEFAULT NULL COMMENT '参与次数',
  `pay_amount1` bigint(20) DEFAULT NULL COMMENT '支付金额',
  `pay_amount2` bigint(20) DEFAULT NULL,
  `pay_amount3` bigint(20) DEFAULT NULL,
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付时间',
  `receiver` varchar(255) DEFAULT NULL COMMENT '收件人姓名',
  `re_mobile` varchar(32) DEFAULT NULL COMMENT '收件人电话',
  `re_address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `logistics_code` varchar(32) DEFAULT NULL COMMENT '物流单号',
  `logistics_company` varchar(32) DEFAULT NULL COMMENT '物流公司编号',
  `status` varchar(4) DEFAULT NULL COMMENT '状态(0待开奖，1已中奖，2未中奖)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
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

-- ----------------------------
--  Table structure for `tmall_cart`
-- ----------------------------
DROP TABLE IF EXISTS `tmall_cart`;
CREATE TABLE `tmall_cart` (
  `code` varchar(32) NOT NULL COMMENT '购物车编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `product_code` varchar(32) DEFAULT NULL COMMENT '产品编号',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tmall_category`
-- ----------------------------
DROP TABLE IF EXISTS `tmall_category`;
CREATE TABLE `tmall_category` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `parent_code` varchar(32) DEFAULT NULL COMMENT '父节点',
  `type` varchar(4) DEFAULT NULL COMMENT '类型（1 产品类别 2 产品位置）',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `pic` varchar(255) DEFAULT NULL COMMENT '图片',
  `order_no` int(11) DEFAULT NULL COMMENT '序号',
  `belong` varchar(32) DEFAULT NULL COMMENT '属于谁',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tmall_order`
-- ----------------------------
DROP TABLE IF EXISTS `tmall_order`;
CREATE TABLE `tmall_order` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(4) DEFAULT NULL COMMENT '类型(散买，批发)',
  `receiver` varchar(255) DEFAULT NULL COMMENT '收件人姓名',
  `re_mobile` varchar(32) DEFAULT NULL COMMENT '收件人电话',
  `re_address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `receipt_type` varchar(4) DEFAULT NULL COMMENT '发票类型(1 个人，2 企业)',
  `receipt_title` varchar(32) DEFAULT NULL COMMENT '发票抬头',
  `apply_user` varchar(32) DEFAULT NULL COMMENT '下单人',
  `apply_note` varchar(255) DEFAULT NULL COMMENT '下单备注',
  `apply_datetime` datetime DEFAULT NULL COMMENT '下单时间',
  `amount1` bigint(20) DEFAULT NULL COMMENT '金额1',
  `amount2` bigint(20) DEFAULT NULL COMMENT '金额2',
  `amount3` bigint(20) DEFAULT NULL COMMENT '金额3',
  `pay_amount1` bigint(20) DEFAULT NULL COMMENT '支付金额1',
  `pay_amount2` bigint(20) DEFAULT NULL COMMENT '支付金额2',
  `pay_amount3` bigint(20) DEFAULT NULL COMMENT '支付金额3',
  `yunfei` bigint(20) DEFAULT NULL COMMENT '运费',
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付时间',
  `prompt_times` int(11) DEFAULT NULL COMMENT '催货次数',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `logistics_code` varchar(32) DEFAULT NULL COMMENT '物流单号',
  `logistics_company` varchar(32) DEFAULT NULL COMMENT '物流公司编号',
  `deliverer` varchar(32) DEFAULT NULL COMMENT '发货人编号',
  `delivery_datetime` datetime DEFAULT NULL COMMENT '发货时间',
  `pdf` varchar(255) DEFAULT NULL COMMENT '物流单',
  `company_code` varchar(32) DEFAULT NULL COMMENT '公司编号',
  `pay_code` varchar(32) DEFAULT NULL COMMENT '第三方支付编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tmall_product`
-- ----------------------------
DROP TABLE IF EXISTS `tmall_product`;
CREATE TABLE `tmall_product` (
  `code` varchar(32) NOT NULL COMMENT '商品编号',
  `category` varchar(32) DEFAULT NULL COMMENT '大类',
  `type` varchar(32) DEFAULT NULL COMMENT '小类',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `adv_title` varchar(255) DEFAULT NULL COMMENT '广告语',
  `adv_pic` varchar(255) DEFAULT NULL COMMENT '广告图',
  `pic1` varchar(255) DEFAULT NULL COMMENT 'pic1',
  `pic2` varchar(255) DEFAULT NULL COMMENT 'pic2',
  `pic3` varchar(255) DEFAULT NULL COMMENT 'pic3',
  `pic4` varchar(255) DEFAULT NULL COMMENT 'pic4',
  `description` text COMMENT '图文描述',
  `cost_price` bigint(20) DEFAULT NULL COMMENT '进货价',
  `quantity` int(11) DEFAULT NULL COMMENT '库存量',
  `status` varchar(4) DEFAULT NULL COMMENT '产品状态(上架，下架)',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `price1` bigint(20) DEFAULT NULL COMMENT '价格1（人民币）',
  `price2` bigint(20) DEFAULT NULL COMMENT '价格2（虚拟币1）',
  `price3` bigint(20) DEFAULT NULL COMMENT '价格3（虚拟币2）',
  `location` varchar(32) DEFAULT NULL COMMENT '位置(0 普通 1 热门)',
  `order_no` int(11) DEFAULT NULL COMMENT '相对位置编号',
  `company_code` varchar(32) DEFAULT NULL COMMENT '所属公司',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `tmall_product_order`
-- ----------------------------
DROP TABLE IF EXISTS `tmall_product_order`;
CREATE TABLE `tmall_product_order` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `order_code` varchar(32) DEFAULT NULL COMMENT '订单编号',
  `product_code` varchar(32) DEFAULT NULL COMMENT '商品编号',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `price1` bigint(20) DEFAULT NULL COMMENT '价格1',
  `price2` bigint(20) DEFAULT NULL COMMENT '价格2',
  `price3` bigint(20) DEFAULT NULL COMMENT '价格3',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `to2o_store`
-- ----------------------------
DROP TABLE IF EXISTS `to2o_store`;
CREATE TABLE `to2o_store` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `type` varchar(32) DEFAULT NULL COMMENT '类型(1 美食 2 KTV 3 电影 4 酒店 5 休闲娱乐 6 汽车 7 周边游 8 足疗按摩 9 生活服务 10 旅游)',
  `legal_person_name` varchar(64) DEFAULT NULL COMMENT '法人姓名',
  `user_referee` varchar(32) DEFAULT NULL COMMENT '推荐人',
  `rate1` decimal(18,8) DEFAULT NULL COMMENT '费率1',
  `rate2` decimal(18,8) DEFAULT NULL COMMENT '费率2',
  `slogan` varchar(255) DEFAULT NULL COMMENT '广告语',
  `ad_pic` varchar(255) DEFAULT NULL COMMENT '店铺缩略图',
  `pic` text COMMENT '商家图片,多张用逗号隔开',
  `description` text COMMENT '商家描述',
  `province` varchar(32) DEFAULT NULL COMMENT '省',
  `city` varchar(32) DEFAULT NULL COMMENT '市',
  `area` varchar(32) DEFAULT NULL COMMENT '区',
  `address` varchar(255) DEFAULT NULL COMMENT '具体地址',
  `longitude` varchar(255) DEFAULT NULL COMMENT '经度(预留)',
  `latitude` varchar(255) DEFAULT NULL COMMENT '维度(预留)',
  `book_mobile` varchar(32) DEFAULT NULL COMMENT '预定联系电话',
  `sms_mobile` varchar(32) DEFAULT NULL COMMENT '短信手机号',
  `pdf` varchar(255) DEFAULT NULL COMMENT '附件',
  `is_default` char(1) DEFAULT NULL COMMENT '是否默认',
  `ui_location` varchar(32) DEFAULT NULL COMMENT 'UI位置',
  `ui_order` int(11) DEFAULT NULL COMMENT 'UI序号',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `approver` varchar(32) DEFAULT NULL COMMENT '审核人',
  `approve_datetime` datetime DEFAULT NULL COMMENT '审核时间',
  `updater` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `owner` varchar(32) DEFAULT NULL COMMENT '店铺主人',
  `total_jf_num` bigint(20) DEFAULT '0' COMMENT '累计积分数量',
  `total_dz_num` int(11) DEFAULT '0' COMMENT '累计点赞数',
  `contract_no` varchar(255) DEFAULT NULL COMMENT '合同编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`) COMMENT '商户表'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `to2o_store_action`
-- ----------------------------
DROP TABLE IF EXISTS `to2o_store_action`;
CREATE TABLE `to2o_store_action` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `type` varchar(4) DEFAULT NULL COMMENT '互动类型(1 点赞 2 收藏)',
  `action_user` varchar(32) DEFAULT NULL COMMENT '互动人',
  `action_datetime` datetime DEFAULT NULL COMMENT '互动时间',
  `store_code` varchar(32) DEFAULT NULL COMMENT '商户编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `to2o_store_purchase`
-- ----------------------------
DROP TABLE IF EXISTS `to2o_store_purchase`;
CREATE TABLE `to2o_store_purchase` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `pay_type` varchar(4) DEFAULT NULL COMMENT '支付方式',
  `purchase_amount` bigint(20) DEFAULT NULL COMMENT '消费金额',
  `amount1` bigint(20) DEFAULT NULL COMMENT '人民币',
  `amount2` bigint(20) DEFAULT NULL COMMENT '虚拟币1',
  `amount3` bigint(20) DEFAULT NULL COMMENT '虚拟币2',
  `back_amount` bigint(20) DEFAULT NULL COMMENT '返现金额',
  `ticket_code` varchar(32) DEFAULT NULL COMMENT '折扣券编号',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
  `create_datetime` datetime DEFAULT NULL COMMENT '消费提交时间',
  `pay_datetime` datetime DEFAULT NULL COMMENT '支付时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `store_code` varchar(32) DEFAULT NULL COMMENT '商家编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  `jour_code` varchar(32) DEFAULT NULL COMMENT '橙账本对应流水编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `to2o_store_ticket`
-- ----------------------------
DROP TABLE IF EXISTS `to2o_store_ticket`;
CREATE TABLE `to2o_store_ticket` (
  `code` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `type` varchar(4) DEFAULT NULL COMMENT '类型(1 满减 2 返现)',
  `key1` bigint(20) DEFAULT NULL COMMENT 'key1',
  `key2` bigint(20) DEFAULT NULL COMMENT 'key2',
  `description` text COMMENT '使用详情',
  `price` bigint(20) DEFAULT NULL COMMENT '销售价格',
  `currency` varchar(4) DEFAULT NULL COMMENT '价格币种',
  `validate_start` datetime DEFAULT NULL,
  `validate_end` datetime DEFAULT NULL COMMENT '有效期止',
  `create_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `status` varchar(4) DEFAULT NULL COMMENT '状态（待上架，已上架，已下架，期满作废）',
  `store_code` varchar(32) DEFAULT NULL COMMENT '所属商家编号',
  `system_code` varchar(32) DEFAULT NULL COMMENT '系统编号',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `to2o_user_ticket`
-- ----------------------------
DROP TABLE IF EXISTS `to2o_user_ticket`;
CREATE TABLE `to2o_user_ticket` (
  `code` varchar(32) NOT NULL DEFAULT '' COMMENT '编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `ticket_code` varchar(32) DEFAULT NULL COMMENT '折扣券编号',
  `create_datetime` datetime DEFAULT NULL COMMENT '购买时间',
  `status` varchar(4) DEFAULT NULL COMMENT '状态',
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
