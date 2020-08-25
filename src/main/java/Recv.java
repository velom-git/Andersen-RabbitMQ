import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.ArrayList;

public class Recv {
    private final static String QUEUE_NAME = "mychannel";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("В ожидании сообщения...");

        ArrayList<String> list = new ArrayList<>();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> { // оповещает о пришедшем сообщении, использует лямбду через Consumer
            String message = new String(delivery.getBody(), "UTF-8");
            list.add(message);
            System.out.println("Сохранено: '" + message + "'");
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});  //  регистрирует получателя
    }
}
