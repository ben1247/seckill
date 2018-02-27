package org.seckill.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * kafka消费者
 * Created by yuezhang on 17/12/3.
 */
@Component
public class KafkaConsumerHelper implements InitializingBean, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    KafkaConsumer<String,String> consumer;

    private ExecutorService messageProcessExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
//        autoCommitSync();
//        manualCommitSync();

    }

    @Override
    public void destroy() throws Exception {
        if (consumer != null){
            consumer.close();
        }

        if (messageProcessExecutor != null){
            messageProcessExecutor.shutdown();
        }
    }

    private void initThreadPool(){

        // 开启一个后台线程去处理消费
        messageProcessExecutor = Executors.newFixedThreadPool(1, new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                String name = "kafka message process thread-" + threadNumber.getAndIncrement();
                Thread ret = new Thread(r, name);
                ret.setDaemon(true);
                return ret;
            }
        });
    }

    /**
     * 自动提交偏移量
     */
    private void autoCommitSync(){
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConstant.bootstrap_servers);
        props.put("group.id","consumer2");
        props.put("enable.auto.commit","true");
        props.put("auto.commit.interval.ms","1000");
        props.put("session.timeout.ms","30000");
        // 设置如何把byte转成object类型
        props.put("key.deserializer", KafkaConstant.key_deserializer);
        props.put("value.deserializer", KafkaConstant.value_deserializer);

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("mytest"));

        initThreadPool();

        messageProcessExecutor.submit(new Runnable() {
            @Override
            public void run() {
                while (true){
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String,String> record : records){
                        logger.info("receive message offset = {} , key = {} , value = {}",record.offset(),record.key(),record.value());
                    }
                }

            }
        });

    }

    /**
     * 手动提交偏移量
     *
     * 在这个例子中，我们将消费一批消息并将它们存储在内存中。当我们积累足够多的消息后，我们再将它们批量插入到数据库中。
     * 如果我们设置offset自动提交（之前说的例子），消费将被认为是已消费的。这样会出现问题，
     * 我们的进程可能在批处理记录之后，但在它们被插入到数据库之前失败了。
     * 为了避免这种情况，我们将在相应的记录插入数据库之后再手动提交偏移量。这样我们可以准确控制消息是成功消费的。
     * 提出一个相反的可能性：在插入数据库之后，但是在提交之前，这个过程可能会失败（即使这可能只是几毫秒，这是一种可能性）。
     * 在这种情况下，进程将获取到已提交的偏移量，并会重复插入的最后一批数据。这种方式就是所谓的“至少一次”保证，在故障情况下，可以重复。
     */
    private void manualCommitSync(){
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaConstant.bootstrap_servers);
        props.put("group.id","consumer2");
        props.put("enable.auto.commit","false");
        props.put("auto.commit.interval.ms","1000");
        props.put("session.timeout.ms","30000");
        // 设置如何把byte转成object类型
        props.put("key.deserializer", KafkaConstant.key_deserializer);
        props.put("value.deserializer", KafkaConstant.value_deserializer);

        consumer = new KafkaConsumer<>(props);

        String topic = KafkaConstant.topic;
        consumer.subscribe(Collections.singletonList(topic));

        initThreadPool();

        messageProcessExecutor.submit(new Runnable() {

            final int minBatchSize = 10;
            List<ConsumerRecord<String,String>> buffer = new ArrayList<>();

            @Override
            public void run() {
                while (true){
                    ConsumerRecords<String,String> records = consumer.poll(100);
                    for (ConsumerRecord<String,String> record : records){
                        buffer.add(record);
                    }
                    if (buffer.size() >= minBatchSize){
                        insertIntoDb(buffer);
                        consumer.commitSync();
                        buffer.clear();
                    }
                }

            }
        });



    }

    private void insertIntoDb(List<ConsumerRecord<String,String>> buffer){
        logger.info("go to insertIntoDb");
        for (ConsumerRecord<String,String> record: buffer ){
            logger.info("receive message offset = {} , key = {} , value = {}",record.offset(),record.key(),record.value());
        }
    }

}
