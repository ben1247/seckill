package org.seckill.exception;

/**
 * Component: 重复秒杀异常(运行期异常)
 * Description:
 * Date: 16/6/9
 *
 * @author yue.zhang
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
