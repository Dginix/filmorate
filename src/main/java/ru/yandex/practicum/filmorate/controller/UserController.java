package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    Map<Long, User> users = new HashMap<>();

    @PostMapping()
    public User addUser(@Valid @RequestBody User user) {
        if(user.getLogin().contains(" ")) {
            log.warn(user.getLogin());
            throw new ValidationException("Login can't contain any spaces");
        }
        log.debug(user.toString());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        users.put(user.getId(), user);
        log.debug(user.toString());
        return user;
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return users.values().stream().toList();
    }
}
