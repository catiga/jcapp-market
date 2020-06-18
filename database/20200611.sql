
alter table ma_figure_data add column `content` TEXT DEFAULT NULL;
alter table ma_figure_data add column `jump_type` CHAR(2) DEFAULT '00';
alter table ma_figure_data add column `jump_id` BIGINT DEFAULT NULL;
alter table ma_figure_data add column `jump_name` VARCHAR(255) DEFAULT NULL;
alter table ma_figure_data add column `jump_img` VARCHAR(255) DEFAULT NULL;
alter table ma_figure_data add column `jump_info` VARCHAR(255) DEFAULT NULL;
