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
    public List<Event> getProducts() {

        List<Event> eventList = new ArrayList<Event>();
        eventList=eventRepository.findAll();

        return eventList;
        /*return Arrays.asList(
                new Play(52,987,456,null,new DataDefault(0)),
                new Pause(52,987,456,null,new DataDefault(1023)),
                new FastForward(52,987,456,null,new ForwardData(4,408,2.5)),
                new Rewind(52,987,456,null,new ForwardData(4,408,2.5)),
                new Progress(52,987,456,null,new DataDefault(4)),
                new Scrub(52,987,456,null,new ScrubData(4,408))
        );*/
    }

    @PostMapping("/")
    public Object createProduct(@RequestBody Event event) {
        return eventRepository.save(event);
    }
}
