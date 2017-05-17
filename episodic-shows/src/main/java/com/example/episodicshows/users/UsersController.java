package com.example.episodicshows.users;

import org.springframework.web.bind.annotation.*;

/**
 * Created by Rudyard Moreno on 5/17/17
 * DISH NETWORK - Galvanize Training
 * CNE-002 (Dish)
 * Unit
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserRepository repository;

    public UsersController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<User> GetUsers() {
        Iterable<User> users = this.repository.findAll();
        return users;
    }

    @PostMapping("")
    public User CreateUser(@RequestBody User user) {
        return this.repository.save(user);
    }
}
