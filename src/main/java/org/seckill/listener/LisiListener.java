package org.seckill.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Component: 定义无序监听器
 * Description:
 * Date: 17/9/3
 *
 * @author yue.zhang
 */
@Component
public class LisiListener implements ApplicationListener<ContentEvent> {

    @Override
    public void onApplicationEvent(ContentEvent event) {
        System.out.println("李四收到了新的内容：" + event.getSource());
    }
}
