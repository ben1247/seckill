package org.seckill.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Component: 有序监听器
 * Description:
 * Date: 17/9/3
 *
 * @author yue.zhang
 */
@Component
public class SunliuListener implements SmartApplicationListener {

    /**
     * 用于指定支持的事件类型，只有支持的才调用onApplicationEvent
     * @param eventType
     * @return
     */
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == ContentEvent.class;
    }

    /**
     * 支持的目标类型，只有支持的才调用onApplicationEvent
     * @param sourceType
     * @return
     */
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType == String.class;
    }

    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("孙六在王五之后收到新的内容：" + event.getSource());
    }

    /**
     * 即顺序，越小优先级越高
     */
    public int getOrder() {
        return 2;
    }
}
