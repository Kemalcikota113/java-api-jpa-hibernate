package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {
    private final GameRepository repository;

    public GameController(GameRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Game> getAll() {
        return this.repository.findAll();
    }

    @GetMapping("{id}")
    public Game getById(@PathVariable("id") Integer id) {
        return this.findGame(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Game create(@RequestBody Game request) {
        Game game = new Game(
                request.getTitle(),
                request.getGenre(),
                request.getPublisher(),
                request.getDeveloper(),
                request.getReleaseYear(),
                request.getIsEarlyAccess());
        return this.repository.save(game);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public Game update(@PathVariable("id") Integer id, @RequestBody Game request) {
        Game game = this.findGame(id);
        game.setTitle(request.getTitle());
        game.setGenre(request.getGenre());
        game.setPublisher(request.getPublisher());
        game.setDeveloper(request.getDeveloper());
        game.setReleaseYear(request.getReleaseYear());
        game.setIsEarlyAccess(request.getIsEarlyAccess());
        return this.repository.save(game);
    }

    @DeleteMapping("{id}")
    public Game delete(@PathVariable("id") Integer id) {
        Game game = this.findGame(id);
        this.repository.delete(game);
        return game;
    }

    private Game findGame(Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No game with that id found"));
    }
}
