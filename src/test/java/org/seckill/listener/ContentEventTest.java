package org.seckill.listener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Component:
 * Description:
 * Date: 17/9/3
 *
 * @author yue.zhang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-listener.xml"})
public class ContentEventTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testPublishEvent(){
        applicationContext.publishEvent(new ContentEvent("亲乖m上幼儿园咯"));
    }

}
