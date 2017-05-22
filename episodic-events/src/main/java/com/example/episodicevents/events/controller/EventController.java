package com.example.episodicevents.events.controller;

import com.example.episodicevents.events.repository.EventRepository;
import com.example.episodicevents.events.types.Event;
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

    @GetMapping("/recent")
    public List<Event> getRecentEvents() {

        List<Event> eventList = new ArrayList<Event>();
        eventList=eventRepository.findAll();

        return eventList;
    }
    
    @PostMapping("/")
    public Object createProduct(@RequestBody Event event) {
        return eventRepository.save(event);
    }
}
