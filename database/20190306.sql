alter table `cbpub_item` modify column `ccid` VARCHAR(255)

ALTER TABLE cbpub_info ADD COLUMN cbpt CHAR(4) NOT NULL DEFAULT '0000' AFTER `ss`;