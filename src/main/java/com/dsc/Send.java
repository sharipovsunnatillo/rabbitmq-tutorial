package com.dsc;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by: Sharipov Sunnatillo
 * Date: 11/3/22 18:40
 */
public class Send {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
            String queue = "tutorial-queue";
            channel.queueDeclare(queue, false, false, false, null);
            channel.basicPublish("", queue, null, "Hello World!".getBytes());
            channel.basicConsume(queue, true, (consumerTag, message) -> System.out.println(consumerTag + "==>" + new String(message.getBody())), System.out::println);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
