package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.Storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements Storage<User> {
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User add(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        User storageUser = users.get(user.getId());
        storageUser.setName(user.getName());
        storageUser.setId(user.getId());
        storageUser.setBirthday(user.getBirthday());
        storageUser.setLogin(user.getLogin());
        storageUser.setEmail(user.getEmail());
        return storageUser;
    }

    @Override
    public User delete(Long id) {
        return users.remove(id);
    }

    @Override
    public User get(Long id) {
        return users.get(id);
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
