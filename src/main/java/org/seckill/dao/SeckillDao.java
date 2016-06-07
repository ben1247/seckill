package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entry.Seckill;

import java.util.Date;
import java.util.List;

/**
 * Component:
 * Description:
 * Date: 16/6/6
 *
 * @author yue.zhang
 */
public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId")long seckillId , @Param("killTime")Date killTime);

    /**
     * 根据id查询
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset")int offset , @Param("limit")int limit);
}
