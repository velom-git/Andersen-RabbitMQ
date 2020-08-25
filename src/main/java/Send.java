import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private final static String QUEUE_NAME = "mychannel";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null); // queue, durable, exclusive, autoDelete, arguments
            // queue — название очереди, которую мы хотим создать. Название должно быть уникальным и не может совпадать с системным именем очереди
            // durable — если true, то очередь будет сохранять свое состояние и восстанавливается после перезапуска сервера/брокера
            // exclusive — если true, то очередь будет разрешать подключаться только одному потребителю
            // autoDelete — если true, то очередь обретает способность автоматически удалять себя
            // arguments — необязательные аргументы.
            for (int i = 1; i < 4; i++) {
                String message = "сообщение номер " + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println("Отправил '" + message + "'");
            }
        }

    }
}
