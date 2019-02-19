CREATE TABLE `merchant` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL COMMENT '商户名称',
  `logo_url` VARCHAR(256) NOT NULL COMMENT '商户 logo',
  `business_license_url` VARCHAR(256) NOT NULL COMMENT '商户营业执照',
  `phone` VARCHAR(64) NOT NULL COMMENT '商户联系电话',
  `address` VARCHAR(64) NOT NULL COMMENT '商户地址',
  `is_audit` BOOLEAN NOT NULL COMMENT '是否被审核',
  PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1  DEFAULT CHARSET=utf8;