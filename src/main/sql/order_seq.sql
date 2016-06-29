DELIMITER $$ -- console ; 转换为 $$
-- 定义存储过程
CREATE PROCEDURE `seckill`.`seq_no`(OUT r_result VARCHAR(20))
  BEGIN
    DECLARE v_cnt INT;
    DECLARE v_timestr INT;
    DECLARE rowcount BIGINT;
    SET v_timestr = DATE_FORMAT(NOW(), '%Y%m%d');

    SELECT ROUND(RAND()*100,0) + 1 INTO v_cnt;

    START TRANSACTION ;
        UPDATE order_seq SET order_sn = order_sn + v_cnt WHERE timestr = v_timestr;
        IF ROW_COUNT() = 0 THEN
          INSERT INTO order_seq(timestr,order_sn) VALUES(v_timestr,v_cnt);
        END IF;
        set r_result = (SELECT CONCAT(v_timestr, LPAD(order_sn,7,0)) AS order_sn FROM order_seq WHERE timestr = v_timestr);
    COMMIT;
  END
$$
-- 存储过程定义结束
DELIMITER ;



set @r_result=0;
-- 执行存储过程
CALL seq_no(@r_result);
-- 获取结果
SELECT @r_result;