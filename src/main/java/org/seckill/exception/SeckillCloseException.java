package org.seckill.exception;

/**
 * Component: 秒杀关闭异常
 * Description:
 * Date: 16/6/9
 *
 * @author yue.zhang
 */
public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
