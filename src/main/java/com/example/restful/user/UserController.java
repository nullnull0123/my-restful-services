package com.example.restful.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveAllUsers(@PathVariable int id) {
        return service.findOne(id);
    }

    @PostMapping(path = "/users")
    public void createUser(@RequestBody User user){
        User savedUser = service.save(user);
    }
}
