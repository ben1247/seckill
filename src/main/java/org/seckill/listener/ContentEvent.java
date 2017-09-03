package org.seckill.listener;

import org.springframework.context.ApplicationEvent;

/**
 * Component: 定义事件
 * Description:
 * Date: 17/9/3
 *
 * @author yue.zhang
 */
public class ContentEvent extends ApplicationEvent{

    public ContentEvent(final String content) {

        super(content);
    }
}
