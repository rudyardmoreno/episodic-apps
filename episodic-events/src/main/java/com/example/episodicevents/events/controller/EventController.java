package com.example.episodicevents.events.controller;

import com.example.episodicevents.events.amqp.ProgressMessage;
import com.example.episodicevents.events.repository.EventRepository;
import com.example.episodicevents.events.types.Event;
import com.example.episodicevents.events.types.Progress;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rudyard Moreno on 5/22/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
@RestController
@RequestMapping("/")
public class EventController {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/recent")
    public List<Event> getRecentEvents() {

        List<Event> eventList = new ArrayList<Event>();
        eventList=eventRepository.findAll();

        return eventList;
    }

    @PostMapping("/")
    public Object createProduct(@RequestBody Event event) {
        if (event.getType().equals("progress")) {
            try {
                Progress progress = new Progress();
                progress=(Progress) event;
                ProgressMessage progressMessage= new ProgressMessage();
                progressMessage.setUserId(progress.getUserId());
                progressMessage.setEpisodeId(progress.getEpisodeId());
                progressMessage.setCreatedAt(progress.getCreatedAt());
                progressMessage.setOffset(progress.getData().getOffset());
                rabbitTemplate.convertAndSend("episodic-events", "episodic-progress", progressMessage);
            } catch (Exception e){
                System.out.println("EventController --> Post --> Push msg rabbit --> " + e.getMessage());
            }
        }
        return eventRepository.save(event);
    }
}
