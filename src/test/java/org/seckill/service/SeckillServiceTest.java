package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entry.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Component:
 * Description:
 * Date: 16/6/9
 *
 * @author yue.zhang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void testGetById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

    /**
     * 测试秒杀逻辑，先读取是否能够秒杀，然后再进行秒杀
     * @throws Exception
     */
    @Test
    public void testExportSeckillLogic() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(1000);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long userPhone = 13918648193L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id,userPhone,md5);
                logger.info("execution={}",execution);
            }catch (RepeatKillException e){
                logger.error(e.getMessage(),e);
            }catch (SeckillCloseException e){
                logger.error(e.getMessage(),e);
            }
        }else{
            // 秒杀未开启
            logger.warn("exposer={}",exposer);
        }
    }


}