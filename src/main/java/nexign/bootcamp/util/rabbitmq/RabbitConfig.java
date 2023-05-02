package nexign.bootcamp.util.rabbitmq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private final CachingConnectionFactory cachingConnectionFactory;

    public RabbitConfig(CachingConnectionFactory cachingConnectionFactory) {
        this.cachingConnectionFactory = cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(){
        return new RabbitAdmin(cachingConnectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(cachingConnectionFactory);
    }

    @Bean
    public Queue queue(){
        return new Queue("abonentCache");
    }
}
