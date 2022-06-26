package com.eucossa.notification_service.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author christopherochiengotieno@gmail.com
 * @version 1.0.0
 * @since Saturday, 25/06/2022
 */
@Configuration
public class AmqpConfig {
    public final static String ORDER_CONFIRMATION_QUEUE = "ORDER_CONFIRMATION_QUEUE";
    public final static String PROMOTIONAL_NOTIFICATION_QUEUE = "PROMOTIONAL_NOTIFICATION_QUEUE";
    public final static String EUCOSSA_MESSAGE_EXCHANGE = "EUCOSSA_MESSAGE_EXCHANGE";
    public final static String ROUTING_KEY = "ROUTING_KEY";

    @Bean
    public Queue orderConfirmationQueue() {
        return new Queue(ORDER_CONFIRMATION_QUEUE);
    }

    @Bean
    public Queue promotionalNotificationQueue() {
        return new Queue(PROMOTIONAL_NOTIFICATION_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EUCOSSA_MESSAGE_EXCHANGE);
    }

    @Bean
    public Binding orderConfirmationQueueBinding(@Qualifier("orderConfirmationQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding promotionalNotificationQueueBinding(@Qualifier("promotionalNotificationQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
