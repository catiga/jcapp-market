/*2018-09-11 ***/
ALTER TABLE `ma_coupon_code` ADD  COLUMN  validate_type VARCHAR(4);
ALTER TABLE `ma_coupon_code` ADD   COLUMN   validate_period VARCHAR(255);
/**2019-03-15**/
ALTER TABLE `ma_movie_coupon_rule` ADD   COLUMN   halls VARCHAR(255);


