package com.vabait.usercenter.rabbitmq2;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

//@Component
public class FirstProduct {
//    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * DirectExchange 生产者 发送消息
     *
     * @param uuid
     * @param message 消息
     */
    public void send(String uuid, Object message) {
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTINGKEY2,
                message, correlationId);
    }


    /**
     * TopicExchange 生产者
     *
     * @param uuid
     * @param message
     */
    public void sendThird(String uuid, Object message) {
        CorrelationData correlationId = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_TOPIC, "hhhj.topic.bbb",
                message, correlationId);
    }


}