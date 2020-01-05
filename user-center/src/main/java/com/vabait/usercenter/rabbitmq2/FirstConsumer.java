package com.vabait.usercenter.rabbitmq2;

//@Component
public class FirstConsumer {

//    @RabbitListener(queues = {"first-queue,second-queue"}, containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage(String message) throws Exception {
        // 处理消息
        System.out.println("FirstConsumer {} handleMessage :" + message);
    }

}