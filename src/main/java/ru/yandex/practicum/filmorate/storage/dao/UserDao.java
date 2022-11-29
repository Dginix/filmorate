package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {

    Optional<User> add(User user);
    Optional<User> update(User user);
    void delete(Long id);
    Optional<User> get(Long id);
    boolean isContains(Long id);
    Collection<User> getAll();
}
