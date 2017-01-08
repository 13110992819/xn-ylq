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
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('A','A1','包邮订单金额','byje','90','订单金额大于等于该配置，包邮',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('A','A1','运费','yunfei','120','默认订单运费',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','业务员','o2oYwy','0.1','消费分成10%',0,NULL,'CD-CZH000001');

INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','县','o2oArea','0.1','消费分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','市','o2oCity','0.1','消费分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','省','o2oProvince','0.1','消费分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','C用户','o2oCUser','0.1','消费分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','B用户','o2oBUser','0.1','消费分成10%',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B1','A用户','o2oAUser','0.1','消费分成10%',0,NULL,'CD-CZH000001');


INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','县(A1未买)','stAreaNotA1','0','A1未买福利月卡分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','市(A1未买)','stCityNotA1','0','A1未买福利月卡分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','省(A1未买)','stProvinceNotA1','0','A1未买福利月卡分成比例',0,NULL,'CD-CZH000001');

INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','县(A1已买)','stArea','0.03','A1已买福利月卡分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','市(A1已买)','stCity','0','A1已买福利月卡分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','省(A1已买)','stProvince','0','A1已买福利月卡分成比例',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','A1','stA10','0','A1未买福利月卡时',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','A1','stA12000','0.03','A1买福利月卡2000时',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','A1','stA110000','0.05','A1买福利月卡1万时',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','A1','stA130000','0.06','A1买福利月卡3万时',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('B','B2','A1','stA150000','0.08','A1买福利月卡5万时',0,NULL,'CD-CZH000001');

INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('O','O1','福利月卡返现期数','stBackNum','10','福利月卡返现期数',0,NULL,'CD-CZH000001');

INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('D','D1','红包兑分润','hb2Fr','10','1红包=1分润',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('D','D1','红包业绩兑分润','hbyj2Fr','10','1红包业绩=2分润',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('D','D1','红包业绩兑贡献奖励','hbyj2Fr','10','1红包业绩=1贡献奖励',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('D','D1','分润兑人民币','fr2Cny','10','1分润=1人民币',0,NULL,'CD-CZH000001');
INSERT INTO `tsys_config` (`category`,`type`,`cname`,`ckey`,`cvalue`,`remark`,`belong`,`company_code`,`system_code`) VALUES ('D','D1','贡献奖励兑人民币','gxjl2Cny','10','1贡献奖励=1人民币',0,NULL,'CD-CZH000001');


/*
-- Query: SELECT * FROM std_mall.tact_stock
-- Date: 2017-01-07 10:55
*/
INSERT INTO `tact_stock` (`code`,`name`,`type`,`pic`,`description`,`capital`,`price`,`currency`,`back_interval`,`back_count`,`welfare1`,`welfare2`,`status`,`system_code`) VALUES ('ST00000001','股份两千','A',NULL,'股份2000',200,2000,'CNY',30,10,200,200,'1','CD-CZH000001');
INSERT INTO `tact_stock` (`code`,`name`,`type`,`pic`,`description`,`capital`,`price`,`currency`,`back_interval`,`back_count`,`welfare1`,`welfare2`,`status`,`system_code`) VALUES ('ST00000002','股份一万','B',NULL,'股份10000',1000,10000,'CNY',30,10,1000,1000,'1','CD-CZH000001');
INSERT INTO `tact_stock` (`code`,`name`,`type`,`pic`,`description`,`capital`,`price`,`currency`,`back_interval`,`back_count`,`welfare1`,`welfare2`,`status`,`system_code`) VALUES ('ST00000003','股份三万','C',NULL,'股份30000',3000,30000,'CNY',30,10,3000,3000,'1','CD-CZH000001');
INSERT INTO `tact_stock` (`code`,`name`,`type`,`pic`,`description`,`capital`,`price`,`currency`,`back_interval`,`back_count`,`welfare1`,`welfare2`,`status`,`system_code`) VALUES ('ST00000004','股份五万','D',NULL,'股份50000',5000,50000,'CNY',30,10,5000,5000,'1','CD-CZH000001');



