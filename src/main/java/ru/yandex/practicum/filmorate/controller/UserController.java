package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    Map<Long, User> users = new HashMap<>();

    @PostMapping()
    public User addUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) {
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return users.values().stream().toList();
    }
}
