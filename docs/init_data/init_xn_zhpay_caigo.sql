/*
-- Query: SELECT `type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code` FROM xn_zhpay.tsys_config where system_code = 'CD-CCG000007'
LIMIT 0, 10000

-- Date: 2017-03-25 18:22
*/
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','hzbDistance','1000','admin',now(),'摇钱树摇出距离(米)','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','hzbMaxNum','100','admin',now(),'摇钱树摇出最大数量','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','userDayMaxCount','5','admin',now(),'用户每天摇最大次数','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','deviceDayMaxCount','5','admin',now(),'设备每天摇最大次数','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('B','hzbYyDayMaxCount','900','admin',now(),'一个摇钱树每天摇最大次数','CD-CCG000007','CD-CCG000007');

INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','ycCGB','0.33','admin','2017-03-24 15:43:19','摇出菜狗币','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','ycJF','','admin',now(),'摇出积分','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','ycCNY','','admin',now(),'摇出人民币','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','yyAmountMin','1','admin',now(),'摇出金额最小值','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('C','yyAmountMax','10','admin',now(),'摇出金额最大值','CD-CCG000007','CD-CCG000007');

INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','advTitle','小小心意','admin',now(),'广告语','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','dayNumber','5','admin',now(),'红包主人每天红包数','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','hzbOwnerCurrency','CGB','admin',now(),'红包主人得到币种','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','hzbOwnerAmount','5','admin',now(),'红包主人得到金额','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','hzbReceiveCurrency','XNB','admin',now(),'红包金额币种','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','hzbReceiveAmount','5','admin',now(),'红包金额','CD-CCG000007','CD-CCG000007');
INSERT INTO `tsys_config` (`type`,`ckey`,`cvalue`,`updater`,`update_datetime`,`remark`,`company_code`,`system_code`) VALUES ('M','dayReceiveNumber','1','admin',now(),'单人每天红包领取数量','CD-CCG000007','CD-CCG000007');

/*
-- Query: SELECT `type`,`parent_key`,`dkey`,`dvalue`,`updater`,now() as `update_datetime`,`remark`,'CD-CCG000007' as system_code FROM xn_zhpay.tsys_dict  where system_code = 'CD-CCG000007'
LIMIT 0, 10000
-- Date: 2017-03-25 12:57
*/
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('0',NULL,'biz_type','业务类型','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','biz_type','-36','购买摇钱树','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','biz_type','37','购买摇钱树分成','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','biz_type','38','摇钱树摇一摇奖励','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','biz_type','39','摇一摇分成','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','biz_type','60','发一发得红包','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','biz_type','61','领取红包','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','biz_type','-64','参与小目标','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','biz_type','65','小目标中奖','admin',now(),'','CD-CCG000007');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('0',NULL,'config_type','规则种类','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','config_type','B','摇钱树规则','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','config_type','C','摇一摇规则','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','config_type','X','虚拟币规则','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','config_type','M','定向红包','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','config_type','O','系统参数','admin',now(),'','CD-CCG000007');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('0',NULL,'dict_type','数据字典类型','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','dict_type','0','第一层','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','dict_type','1','第二层','admin',now(),'','CD-CCG000007');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('0',NULL,'hzb_status','摇钱树状态','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','hzb_status','0','待支付','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','hzb_status','1','激活','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','hzb_status','91','人为下架','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','hzb_status','92','正常耗尽','admin',now(),'','CD-CCG000007');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('0',NULL,'hzb_mgift_status','定向红包状态','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','hzb_mgift_status','0','待发送','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','hzb_mgift_status','1','已发送，待领取','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','hzb_mgift_status','2','已领取','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','hzb_mgift_status','3','已失效','admin',now(),'','CD-CCG000007');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('0',NULL,'jewel_template_status','宝贝模板状态','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','jewel_template_status','0','待上架','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','jewel_template_status','1','已上架','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','jewel_template_status','2','已下架','admin',now(),'','CD-CCG000007');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('0',NULL,'jewel_status','宝贝状态','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','jewel_status','0','募集中','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','jewel_status','1','已揭晓','admin',now(),'','CD-CCG000007');

INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('0',NULL,'jewel_record_status','宝贝参与记录状态','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','jewel_record_status','0','待支付','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','jewel_record_status','1','待开奖','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','jewel_record_status','2','已中奖','admin',now(),'','CD-CCG000007');
INSERT INTO `tsys_dict` (`type`,`parent_key`,`dkey`,`dvalue`,`updater`,`update_datetime`,`remark`,`system_code`) VALUES ('1','jewel_record_status','3','未中奖','admin',now(),'','CD-CCG000007');