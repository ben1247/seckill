package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entry.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Component:
 * Description:
 * Date: 16/6/7
 *
 * @author yue.zhang
 */
// 配置spring和junit整合,junit启动时加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {
        long seckillId = 1001L;
        long userPhone = 13618647117L;
        int insertCount = successKilledDao.insertSuccessKilled(seckillId,userPhone);
        System.out.println("insertCount=" + insertCount);
    }

    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        long seckillId = 1001L;
        long userPhone = 13618647117L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}