package org.seckill.exception;

/**
 * Component:
 * Description:
 * Date: 16/6/9
 *
 * @author yue.zhang
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
