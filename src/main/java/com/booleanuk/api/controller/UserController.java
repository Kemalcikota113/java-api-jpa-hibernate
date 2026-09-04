package com.booleanuk.api.controller;

import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<User> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("{id}")
    public User getById(@PathVariable("id") Integer id) {
        return this.findUser(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody User request) {
        User user = new User(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getPhone());
        return this.repository.save(user);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public User update(@PathVariable("id") Integer id, @RequestBody User request) {
        User user = this.findUser(id);
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        return this.repository.save(user);
    }

    @DeleteMapping("{id}")
    public User delete(@PathVariable("id") Integer id) {
        User user = this.findUser(id);
        this.repository.delete(user);
        return user;
    }

    private User findUser(Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id found"));
    }
}
