package org.seckill.service.impl;

import org.seckill.kafka.KafkaProducerHelper;
import org.seckill.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yuezhang on 17/12/3.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private KafkaProducerHelper kafkaProducerHelper;

    @Override
    public void sendMessage(String message) {
        kafkaProducerHelper.setMessage(String.valueOf(message.hashCode()),message);
    }
}
