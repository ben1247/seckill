package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entry.SuccessKilled;

/**
 * Component:
 * Description:
 * Date: 16/6/6
 *
 * @author yue.zhang
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId")long seckillId , @Param("userPhone")long userPhone);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId , @Param("userPhone")long userPhone);
}
