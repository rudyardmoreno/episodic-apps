package com.example.episodicshows.amqp;

import com.example.episodicshows.shows.Episode;
import com.example.episodicshows.shows.EpisodeRepository;
import com.example.episodicshows.shows.Show;
import com.example.episodicshows.shows.ShowRepository;
import com.example.episodicshows.users.User;
import com.example.episodicshows.users.UserRepository;
import com.example.episodicshows.users.Viewing;
import com.example.episodicshows.users.ViewingRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Rudyard Moreno on 5/23/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
@Configuration
public class AmqpListener implements RabbitListenerConfigurer {
    @Autowired
    EpisodeRepository episodeRepository;

    @Autowired
    ViewingRepository viewingRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    UserRepository userRepository;

    @RabbitListener(queues = "episodic-progress")
    @Transactional
    public void receiveMessage(final Progress progress) {
        System.out.println("************************************************");
        System.out.println("userId:" + progress.getUserId());
        System.out.println("episodeId:" + progress.getEpisodeId());
        System.out.println("createdAt:" + progress.getCreatedAt());
        System.out.println("offset:" + progress.getOffset());
        System.out.println("************************************************");

        Episode episode = new Episode();
        Viewing viewing = new Viewing();
        Show show = new Show();
        User user = new User();

        user=userRepository.findOne(progress.getUserId());
        if (user != null) {
            episode = episodeRepository.findOne(progress.getEpisodeId());
        }

        if (user!=null && episode!= null) {
            show=showRepository.findOne(episode.getShowId());

            if (show !=null ) {

                viewing = viewingRepository.findByIds(progress.getUserId(), show.getId(), episode.getId());

                if (viewing == null) {
                    viewing = new Viewing();
                }

                viewing.setEpisodeId(episode.getId());
                viewing.setShowId(episode.getShowId());
                viewing.setUserId(progress.getUserId());
                viewing.setUpdatedAt(progress.getCreatedAt());
                viewing.setTimecode(progress.getOffset());

                viewingRepository.save(viewing);
            }
        }
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new MappingJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}
