package com.example.episodicevents.events.repository;

/**
 * Created by Rudyard Moreno on 5/22/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */

import com.example.episodicevents.events.types.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository <Event, String>{

}
