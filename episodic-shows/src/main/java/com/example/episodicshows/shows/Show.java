package com.example.episodicshows.shows;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Rudyard Moreno on 5/17/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */

@Entity(name ="shows")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }
    public String getName() { return name;}

    public void setId(Long value) {id = value;}
    public void setName(String value) {
        name = value;
    }
}
