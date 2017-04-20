package com.junit;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;

/**
 *
 * http://blog.csdn.net/u012592062/article/details/51910955  博客详细解释
 * http://www.cnblogs.com/flat_peach/archive/2013/04/07/3004008.html 集群搭建
 * http://www.cnblogs.com/luxiaoxun/p/3918054.html rabbitmq 几种使用场景介绍
 * http://www.rabbitmq.com/tutorials/tutorial-one-java.html 官网
 * Created by kangkang on 2017/4/20.
 */
public class rabbitmqTest {
    final String QUEUE_NAME = "task_queue";

    @Test
    public void rabbitmqSend() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.89.139.135");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            QueueingConsumer consumer = new QueueingConsumer(channel);
            // 跟helloworld的不同点
            boolean durable = true;
            // 下面这个声明队列的队列名字改了，所以生产者和消费者两边的程序都要改成统一的队列名字。
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
            // 有了durable为true，我们可以保证名叫task_queue的队列即使在RabbitMQ重启的情况下也不会消失。
            String message = "hello";
            // 现在我们需要将消息标记成可持久化的。
            // 如果你需要更强大的保证消息传递，你可以将发布消息的代码打包到一个事务里。
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("s[" + message + "]");
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void rabbitmqRecv() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("118.89.139.135");
            Connection connection = factory.newConnection();
            Channel channel = null;
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            System.out.println("CRTL+C");
            // 这条语句告诉RabbitMQ在同一时间不要给一个worker一个以上的消息。
            // 或者换一句话说, 不要将一个新的消息分发给worker知道它处理完了并且返回了前一个消息的通知标志（acknowledged）
            // 替代的，消息将会分发给下一个不忙的worker。
            channel.basicQos(1);
            // 自动通知标志
            boolean autoAck = false;
            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Customer Received '" + message + "'");
                }
            };
            //自动回复队列应答 -- RabbitMQ中的消息确认机制
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 循环拉取信息
     * @param channel
     * @param autoAck
     */
    private  void getwhileMessage(Channel channel,boolean autoAck) {
        try {

            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
            //轮训获取消息模式
            while (true) {
                QueueingConsumer.Delivery delivery = null;

                delivery = consumer.nextDelivery();

                String message = new String(delivery.getBody());
                System.out.println("r[" + message + "]");
                try {
                    doWord(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("r[done]");
                // 发出通知标志
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doWord(String task) throws InterruptedException {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
