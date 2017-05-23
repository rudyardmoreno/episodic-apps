package com.example.episodicevents.events.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Rudyard Moreno on 5/23/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
@Configuration
public class AmqpPublisher {
    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange("episodic-events");
    }

    @Bean
    public Queue queue() {
        return new Queue("#{'${queue}'}");
    }

    @Bean
    public Binding declareBinding() {
        return BindingBuilder.bind(queue()).to(appExchange()).with("#{'${queue}'}");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
