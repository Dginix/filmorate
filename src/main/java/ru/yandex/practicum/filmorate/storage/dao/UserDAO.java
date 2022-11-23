package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    Optional<User> add(User t);
    Optional<User> update(User t);
    Optional<User> delete(Long id);
    Optional<User> get(Long id);
    boolean isContains(Long id);
    List<User> getAll();
}
