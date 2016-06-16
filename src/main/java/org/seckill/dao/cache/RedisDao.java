package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entry.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Component:
 * Description:
 * Date: 16/6/16
 *
 * @author yue.zhang
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip,int port){
        jedisPool = new JedisPool(ip,port);
    }

    public Seckill getSeckill(long seckillId){
        try {
            Jedis jedis = jedisPool.getResource();
            try{
                String key = "seckill:"+ seckillId;
                // 没有实现内部序列化操作
                // get -> byte[] -> 反序列化 -> Object(Seckill)
                // 采用自定义序列化
                byte [] bytes = jedis.get(key.getBytes());
                if(bytes != null){
                    // 空对象
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    // seckill 被反序列化
                    return seckill;
                }
            }finally {
                jedis.close();
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill){
        // set Object(seckill) -> 序列化 -> byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte [] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        // 缓存器，用于对象特别大的时候做一个缓冲的作用
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                // 超时缓存
                int timeout = 60 * 60;  // 1小时
                return jedis.setex(key.getBytes(),timeout,bytes);
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

        return null;
    }

}
