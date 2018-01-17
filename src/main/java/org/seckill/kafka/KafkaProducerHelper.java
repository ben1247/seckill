package org.seckill.kafka;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

/**
 * kafka 生产者帮助类
 * Created by yuezhang on 17/12/3.
 */
@Component
public class KafkaProducerHelper implements InitializingBean, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private Producer<String,String> producer;

    private String topic;

    @Override
    public void afterPropertiesSet() throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConstant.bootstrap_servers);
        // acks 是判别请求是否为完整的条件（就是是判断是不是成功发送了）。我们指定了“all”将会阻塞消息，这种设置性能最低，但是是最可靠的。
        props.put("acks", "all");
        // retries 如果请求失败，生产者会自动重试，我们指定是0次，如果启用重试，则会有重复消息的可能性。
        props.put("retries", 0);
        // 缓存的大小是通过 batch.size 配置指定的。值较大的话将会产生更大的批。并需要更多的内存（因为每个“活跃”的分区都有1个缓冲区）。
        props.put("batch.size", 16384);
        // 默认缓冲可立即发送，即遍缓冲空间还没有满，但是，如果你想减少请求的数量，可以设置linger.ms大于0。这将指示生产者发送请求之前等待一段时间，
        // 希望更多的消息填补到未满的批中。这类似于TCP的算法，例如上面的代码段，可能100条消息在一个请求发送，因为我们设置了linger(逗留)时间为1毫秒，
        // 然后，如果我们没有填满缓冲区，这个设置将增加1毫秒的延迟请求以等待更多的消息。需要注意的是，在高负载下，相近的时间一般也会组成批，
        // 即使是 linger.ms=0。在不处于高负载的情况下，如果设置比0大，以少量的延迟代价换取更少的，更有效的请求。
        props.put("linger.ms", 1);
        // 控制生产者可用的缓存总量，如果消息发送速度比其传输到服务器的快，将会耗尽这个缓存空间。当缓存空间耗尽，其他发送调用将被阻塞，
        // 阻塞时间的阈值通过max.block.ms设定，之后它将抛出一个TimeoutException。
        props.put("buffer.memory", 33554432); // 32M
        // 将用户提供的key和value对象ProducerRecord转换成字节，你可以使用附带的ByteArraySerializaer或StringSerializer处理简单的string或byte类型。
        props.put("key.serializer", KafkaConstant.key_serializer);
        props.put("value.serializer", KafkaConstant.value_serializer);

        producer = new KafkaProducer<>(props);

        topic = KafkaConstant.topic;
    }

    @Override
    public void destroy() throws Exception {
        if (producer != null){
            producer.close();
        }
    }

    public void setMessage(String key , String value){

        try {
//            producer.send(new ProducerRecord<>(topic,key,value));

            producer.send(new ProducerRecord<>(topic,key,value),new Callback(){

            @Override
            public void onCompletion(RecordMetadata metadata, Exception e) {
                if (e != null){
                    logger.error(String.format("send message onCompletion failure %s",e.getMessage()) ,e);
                }else{
                    long offset = metadata.offset();
                    String topic = metadata.topic();
                    int partition = metadata.partition();
                    long timestamp = metadata.timestamp();
                    Date date = new Date(timestamp);
                    logger.info(String.format("send message onCompletion success, topic: %s , partition: %s , offset : %s , timestamp: %s",topic,partition,offset,date));
                }

            }});
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }

    }


}
