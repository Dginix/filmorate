package ru.yandex.practicum.filmorate.storage.inmemory_impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@Qualifier("collectionStorage")
public class InMemoryUserStorage implements UserDao {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public Optional<User> add(User user) {
        users.put(user.getId(), user);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> update(User user) {
        User storageUser = users.get(user.getId());
        storageUser.setName(user.getName());
        storageUser.setId(user.getId());
        storageUser.setBirthday(user.getBirthday());
        storageUser.setLogin(user.getLogin());
        storageUser.setEmail(user.getEmail());
        return Optional.ofNullable(storageUser);
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
    }

    @Override
    public Optional<User> get(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public boolean isContains(Long id) {
        return users.containsKey(id);
    }

    @Override
    public List<User> getAll() {
        return users.values().stream().toList();
    }
}
