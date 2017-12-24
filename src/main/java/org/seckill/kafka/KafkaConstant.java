package org.seckill.kafka;

/**
 * Created by yuezhang on 17/12/10.
 */
public class KafkaConstant {

    public final static String bootstrap_servers = "127.0.0.1:9095,127.0.0.1:9096,127.0.0.1:9097";

    public final static String key_serializer = "org.apache.kafka.common.serialization.StringSerializer";

    public final static String value_serializer = "org.apache.kafka.common.serialization.StringSerializer";

    public final static String key_deserializer = "org.apache.kafka.common.serialization.StringDeserializer";

    public final static String value_deserializer = "org.apache.kafka.common.serialization.StringDeserializer";

}
