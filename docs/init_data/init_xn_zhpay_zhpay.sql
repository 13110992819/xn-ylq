/*
-- Query: SELECT * FROM xn_zhpay.tyqs_hzb_template where code = 'HT201703241545109884'
LIMIT 0, 10000

-- Date: 2017-03-31 15:34
*/
INSERT INTO `tyqs_hzb_template` (`code`,`name`,`pic`,`price`,`currency`,`period_rock_num`,`total_rock_num`,`back_amount1`,`back_amount2`,`back_amount3`,`status`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('HT20170000000000001','汇赚宝模板','tree_big.png',600000,'CNY',1000,2000,0,0,0,'1','admin',now(),NULL,'CD-CZH000001','CD-CZH000001');

/*
-- Query: SELECT `type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code` FROM xn_zhpay.tsys_config
LIMIT 0, 10000

-- Date: 2017-03-22 15:51
*/
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','hzbArea','0.01','admin',now(),'购买汇赚宝县辖区合伙人分成1%','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','hzbCity','0','admin',now(),'购买汇赚宝市辖区合伙人分成0%','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','hzbProvince','0','admin',now(),'购买汇赚宝省辖区合伙人分成0%','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','hzbCUser','0.16','admin',now(),'购买汇赚宝一级推荐人分成16%','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','hzbBUser','0.08','admin',now(),'购买汇赚宝二级推荐人分成8%','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','hzbAUser','0.08','admin',now(),'购买汇赚宝三级推荐人分成8%','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','hzbDistance','1000','admin',now(),'汇赚宝摇出最大距离(米)','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','hzbMaxNum','100','admin',now(),'汇赚宝摇出最大数量','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','userDayMaxCount','5','admin',now(),'用户每天摇最大次数','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','deviceDayMaxCount','5','admin',now(),'设备每天摇最大次数','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','yyArea','0.23','admin',now(),'摇一摇红包业绩分成值','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','yyCity','0','admin',now(),'摇一摇红包业绩分成值','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','yyProvince','0','admin',now(),'摇一摇红包业绩分成值','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','yyCUser','0.45','admin',now(),'摇一摇红包业绩分成值','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','yyBUser','0.45','admin',now(),'摇一摇红包业绩分成值','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','yyAUser','0.45','admin',now(),'摇一摇红包业绩分成值','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','ycHBB','0.33','admin',now(),'3次里面必定有1次','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','ycGWB','','admin',now(),'摇出购物币','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','ycQBB','','admin',now(),'摇出钱包币','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','yyAmountMin','1','admin',now(),'摇出金额最小值(>=)','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','yyAmountMax','10','admin',now(),'摇出金额最大值(<)','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','advTitle','小小心意','admin',now(),'广告语','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','dayNumber','5','admin',now(),'红包主人每天红包数','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','hzbOwnerCurrency','HBYJ','admin',now(),'红包主人得到币种','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','hzbOwnerAmount','5','admin',now(),'红包主人得到金额','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','hzbReceiveCurrency','GXJL','admin',now(),'红包金额币种','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','hzbReceiveAmount','5','admin',now(),'红包金额','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','dayReceiveNumber','1','admin',now(),'单人每天红包领取数量','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('O','iosDownUrl','www.baidu.com','admin',now(),'ios链接','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('O','androidDownUrl','www.sina.com','admin',now(),'android链接','CD-CZH000001','CD-CZH000001');

/*
-- Query: SELECT `type`,`parent_key`,`dkey`,`dvalue`,`updater`,now() as `update_datetime`,`remark`,'CD-CZH000001' as system_code FROM xn_zhpay.tsys_dict  where system_code = 'CD-CZH000001'
LIMIT 0, 10000
-- Date: 2017-03-25 12:57
*/
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'config_type','规则种类','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','config_type','B','摇钱树规则','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','config_type','C','摇一摇规则','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','config_type','X','虚拟币规则','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','config_type','M','定向红包','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','config_type','D','分享规则','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','config_type','O','系统参数','admin',now(),'','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'dict_type','数据字典类型','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','dict_type','0','第一层','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','dict_type','1','第二层','admin',now(),'','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'hzb_status','摇钱树状态','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','hzb_status','0','待支付','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','hzb_status','1','激活','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','hzb_status','91','人为下架','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','hzb_status','92','正常耗尽','admin',now(),'','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'hzb_mgift_status','定向红包状态','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','hzb_mgift_status','0','待发送','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','hzb_mgift_status','1','已发送，待领取','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','hzb_mgift_status','2','已领取','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','hzb_mgift_status','3','已失效','admin',now(),'','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'jewel_template_status','宝贝模板状态','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','jewel_template_status','0','待上架','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','jewel_template_status','1','已上架','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','jewel_template_status','2','已下架','admin',now(),'','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'jewel_status','宝贝状态','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','jewel_status','0','募集中','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','jewel_status','1','已揭晓','admin',now(),'','CD-CZH000001','CD-CZH000001');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('0',NULL,'jewel_record_status','宝贝参与记录状态','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','jewel_record_status','0','待支付','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','jewel_record_status','1','待开奖','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','jewel_record_status','2','已中奖','admin',now(),'','CD-CZH000001','CD-CZH000001');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('1','jewel_record_status','3','未中奖','admin',now(),'','CD-CZH000001','CD-CZH000001');
