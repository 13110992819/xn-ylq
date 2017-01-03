ALTER TABLE `gateway_idauth_log` 
ADD COLUMN `card_no` VARCHAR(255) NULL COMMENT '银行卡号' AFTER `realName`,
ADD COLUMN `bind_mobile` VARCHAR(32) NULL COMMENT '绑定手机号' AFTER `card_no`;

ALTER TABLE `tjc_idauth` 
ADD COLUMN `card_no` VARCHAR(255) NULL COMMENT '银行卡号' AFTER `realName`,
ADD COLUMN `bind_mobile` VARCHAR(32) NULL COMMENT '绑定手机号' AFTER `card_no`;