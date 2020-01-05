package com.vabait.usercenter.rabbitmq2;

//@Component
public class ThirdConsumer {

//    @RabbitListener(queues = {"third-queue"}, containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage(String message) throws Exception {
        // 处理消息
        System.out.println("ThirdConsumer {} handleMessage :" + message);
    }

}