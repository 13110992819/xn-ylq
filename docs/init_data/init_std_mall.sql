/*
-- Query: SELECT * FROM std_mall.tmall_category where parent_code = '0'
LIMIT 0, 5000

-- Date: 2016-12-17 17:23
*/
INSERT INTO `tmall_category` (`code`,`parent_code`,`type`,`name`,`pic`,`order_no`,`belong`,`company_code`,`system_code`) VALUES ('FL201600000000000001','0','1','剁手合集','',1,NULL,NULL,'CD-CZH000001');
INSERT INTO `tmall_category` (`code`,`parent_code`,`type`,`name`,`pic`,`order_no`,`belong`,`company_code`,`system_code`) VALUES ('FL201600000000000002','0','1','一元夺宝',NULL,2,NULL,NULL,'CD-CZH000001');
INSERT INTO `tmall_category` (`code`,`parent_code`,`type`,`name`,`pic`,`order_no`,`belong`,`company_code`,`system_code`) VALUES ('FL201600000000000003','0','1','0元试购','',3,NULL,NULL,'CD-CZH000001');


INSERT INTO `tmall_category` (`code`,`parent_code`,`type`,`name`,`pic`,`order_no`,`belong`,`company_code`,`system_code`) VALUES ('FL201600000000000004','FL201600000000000001','1','小类1','',1,NULL,NULL,'CD-CZH000001');
INSERT INTO `tmall_category` (`code`,`parent_code`,`type`,`name`,`pic`,`order_no`,`belong`,`company_code`,`system_code`) VALUES ('FL201600000000000005','FL201600000000000001','1','小类2','',1,NULL,NULL,'CD-CZH000001');
INSERT INTO `tmall_category` (`code`,`parent_code`,`type`,`name`,`pic`,`order_no`,`belong`,`company_code`,`system_code`) VALUES ('FL201600000000000006','FL201600000000000001','1','小类3','',1,NULL,NULL,'CD-CZH000001');
INSERT INTO `tmall_category` (`code`,`parent_code`,`type`,`name`,`pic`,`order_no`,`belong`,`company_code`,`system_code`) VALUES ('FL201600000000000007','FL201600000000000001','1','小类4','',1,NULL,NULL,'CD-CZH000001');

INSERT INTO `tmall_category` (`code`,`parent_code`,`type`,`name`,`pic`,`order_no`,`belong`,`company_code`,`system_code`) VALUES ('FL201600000000000008','FL201600000000000003','1','小类1','',1,NULL,NULL,'CD-CZH000001');
INSERT INTO `tmall_category` (`code`,`parent_code`,`type`,`name`,`pic`,`order_no`,`belong`,`company_code`,`system_code`) VALUES ('FL201600000000000009','FL201600000000000003','1','小类2','',1,NULL,NULL,'CD-CZH000001');
INSERT INTO `tmall_category` (`code`,`parent_code`,`type`,`name`,`pic`,`order_no`,`belong`,`company_code`,`system_code`) VALUES ('FL201600000000000010','FL201600000000000003','1','小类3','',1,NULL,NULL,'CD-CZH000001');
INSERT INTO `tmall_category` (`code`,`parent_code`,`type`,`name`,`pic`,`order_no`,`belong`,`company_code`,`system_code`) VALUES ('FL201600000000000011','FL201600000000000003','1','小类4','',1,NULL,NULL,'CD-CZH000001');

/*
-- Query: SELECT * FROM std_mall.tsys_config
-- Date: 2016-12-29 13:57
*/
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('O','O1','包邮订单金额','spByje','90','订单金额大于等于该配置，包邮',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('O','O1','运费','spYunfei','120','默认订单运费',0,NULL,'CD-CZH000001');

INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','业务员','o2oYwy','0.1','消费分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','县','o2oArea','0.1','消费分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','市','o2oCity','0.1','消费分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','省','o2oProvince','0.1','消费分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','消费用户','o2oCUser','0.35','消费获得钱包币分成占比',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','一级推荐人','o2oBUser','0.1','消费分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','二级推荐人','o2oAUser','0.1','消费分成10%',0,NULL,'CD-CZH000001');

INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','县(推荐人未买)','stAreaNotA1','0','推荐人未买福利月卡分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','市(推荐人未买)','stCityNotA1','0','推荐人未买福利月卡分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','省(推荐人未买)','stProvinceNotA1','0','推荐人未买福利月卡分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','县(推荐人已买)','stArea','0.03','推荐人已买福利月卡分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','市(推荐人已买)','stCity','0','推荐人已买福利月卡分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','省(推荐人已买)','stProvince','0','推荐人已买福利月卡分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','推荐人未买','stA10','0','推荐人未买福利月卡时',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','推荐人已买第一档','stA12000','0.03','推荐人买福利月卡第一档(2000)时,推荐人分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','推荐人已买第二档','stA110000','0.05','推荐人买福利月卡第二档(10000)时,推荐人分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','推荐人已买第三档','stA130000','0.06','推荐人买福利月卡第三档(30000)时,推荐人分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','推荐人已买第四档','stA150000','0.08','推荐人买福利月卡第四档(50000)时,推荐人分成比例',0,NULL,'CD-CZH000001');

INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B3','县','hzbArea','0.1','购买汇赚宝分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B3','市','hzbCity','0.1','购买汇赚宝分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B3','省','hzbProvince','0.1','购买汇赚宝分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B3','一级推荐人','hzbCUser','0.1','购买汇赚宝分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B3','二级推荐人','hzbBUser','0.1','购买汇赚宝分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B3','三级推荐人','hzbAUser','0.1','购买汇赚宝分成10%',0,NULL,'CD-CZH000001');

INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B4','县','yyArea','0.23','摇一摇红包业绩分成值',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B4','市','yyCity','0','摇一摇红包业绩分成值',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B4','省','yyProvince','0','摇一摇红包业绩分成值',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B4','汇赚宝主人','yyCUser','0.45','摇一摇红包业绩分成值',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B4','汇赚宝主人一级推荐人','yyBUser','0.45','摇一摇红包业绩分成值',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B4','汇赚宝主人二级推荐人','yyAUser','0.45','摇一摇红包业绩分成值',0,NULL,'CD-CZH000001');

INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('C','C1','摇出红包概率','ycHBB','0.33','3次里面必定有1次',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('C','C1','摇出购物币概率','ycGWB','','随机',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('C','C1','摇出钱包币概率','ycQBB','','随机',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('C','C1','金额下限','yyAmountMin','1','无',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('C','C1','金额上限','yyAmountMax','10','无',0,NULL,'CD-CZH000001');


INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('D','D1','红包兑分润','hb2Fr','1','1红包=1分润',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('D','D1','红包业绩兑分润','hbyj2Fr','2','1红包业绩=2分润',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('D','D1','红包业绩兑贡献奖励','hbyj2Gxjl','1','1红包业绩=1贡献奖励',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('D','D1','分润兑人民币','fr2Cny','1','1分润=1人民币',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('D','D1','贡献奖励兑人民币','gxjl2Cny','1','1贡献奖励=1人民币',0,NULL,'CD-CZH000001');

INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('E','E1','近距离店铺数','storeNum','10','近距离店铺数',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('E','E2','汇赚宝摇出距离','hzbDistance','1000','汇赚宝摇出距离(米)',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('E','E2','汇赚宝摇出最大数量','hzbMaxNum','100','汇赚宝摇出最大数量',0,NULL,'CD-CZH000001');

INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('O','O1','福利月卡返现期数','stBackNum','10','福利月卡返现期数',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('C','C1','用户每天摇最大次数','userDayMaxCount','5','用户每天摇最大次数',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('C','C1','设备每天摇最大次数','deviceDayMaxCount','5','设备每天摇最大次数',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('C','C1','一个汇赚宝每天摇最大次数','hzbYyDayMaxCount','900','一个汇赚宝每天摇最大次数',0,NULL,'CD-CZH000001');

/*
-- Query: SELECT * FROM std_mall.tact_hzb
-- Date: 2017-01-08 19:32
*/
INSERT INTO `tact_hzb` (`code`,`name`,`pic`,`description`,`price`,`currency`,`system_code`) VALUES ('HZB001','汇赚宝','http://123.33.33.33/default.jpg','汇赚宝玩法，摇一摇，摇出你的美',2000000,'CNY','CD-CZH000001');

/*
-- Query: SELECT * FROM std_mall.tact_stock
-- Date: 2017-01-07 10:55
*/
INSERT INTO `tact_stock` (`code`,`name`,`type`,`pic`,`description`,`capital`,`price`,`currency`,`back_interval`,`back_count`,`welfare1`,`welfare2`,`status`,`system_code`) VALUES ('ST00000001','股份两千','A',NULL,'股份2000',200,2000000,'CNY',30,10,200000,20000,'1','CD-CZH000001');
INSERT INTO `tact_stock` (`code`,`name`,`type`,`pic`,`description`,`capital`,`price`,`currency`,`back_interval`,`back_count`,`welfare1`,`welfare2`,`status`,`system_code`) VALUES ('ST00000002','股份一万','B',NULL,'股份10000',1000,10000000,'CNY',30,10,1000000,100000,'1','CD-CZH000001');
INSERT INTO `tact_stock` (`code`,`name`,`type`,`pic`,`description`,`capital`,`price`,`currency`,`back_interval`,`back_count`,`welfare1`,`welfare2`,`status`,`system_code`) VALUES ('ST00000003','股份三万','C',NULL,'股份30000',3000,30000000,'CNY',30,10,3000000,300000,'1','CD-CZH000001');
INSERT INTO `tact_stock` (`code`,`name`,`type`,`pic`,`description`,`capital`,`price`,`currency`,`back_interval`,`back_count`,`welfare1`,`welfare2`,`status`,`system_code`) VALUES ('ST00000004','股份五万','D',NULL,'股份50000',5000,50000000,'CNY',30,10,5000000,500000,'1','CD-CZH000001');



