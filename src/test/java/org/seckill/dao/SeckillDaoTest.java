package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entry.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Component: intellij idea快捷键： 选中SeckillDao , shift + command + t
 * Description:
 * Date: 16/6/7
 *
 * @author yue.zhang
 */
// 配置spring和junit整合,junit启动时加载springIOC容器
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    // 注入Dao实现类依赖
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckillList = seckillDao.queryAll(0,100);
        for(Seckill seckill : seckillList){
            System.out.println(seckill);
        }
    }

    @Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L,killTime);
        System.out.println("updateCount=" + updateCount);
    }


}