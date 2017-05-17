package com.example.episodicshows.users;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

/**
 * Created by Rudyard Moreno on 5/17/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */

@Entity(name ="users")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;

    public Long getId() {
        return id;
    }
    public String getEmail() { return email;}

    public void setId(Long value) {id = value;}
    public void setEmail(String value) {
        email = value;
    }
}
