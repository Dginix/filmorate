package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.interfaces.Storage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final Storage<User> userStorage;

    @Autowired
    public UserService(Storage<User> userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) {
        return userStorage.add(user);
    }

    public User updateUser(User user) {
        return userStorage.update(user);
    }

    public User getUserById(Long id) {
        if(!userStorage.isContains(id)) {
            throw new NotFoundException("user not found");
        }
        return userStorage.get(id);
    }

    public List<User> getAllUsers() {
        return userStorage.getAll();
    }

    public void addToFriendsList(Long userId, Long friendId) {
        if (!userStorage.isContains(userId)) {
            throw new NotFoundException("user not found");
        }

        if (!userStorage.isContains(friendId)) {
            throw new NotFoundException("user friend not found");
        }

        if (userStorage.get(userId).getFriends().stream()
                .filter(aLong -> aLong==friendId)
                .count() == 0) {
            userStorage.get(userId).getFriends().add(friendId);
        }

        if (userStorage.get(friendId).getFriends().stream()
                .filter(aLong -> aLong==userId)
                .count() == 0) {
            userStorage.get(friendId).getFriends().add(userId);
        }
    }

    public void removeFromFriendsList(Long userId, Long friendId) {
        if (!userStorage.isContains(userId)) {
            throw new NotFoundException("user not found");
        }

        if (!userStorage.isContains(friendId)) {
            throw new NotFoundException("user friend not found");
        }

        if (userStorage.get(userId).getFriends().stream()
                .filter(aLong -> aLong==friendId)
                .count() == 0) {
            userStorage.get(userId).getFriends().remove(friendId);
        }

        if (userStorage.get(friendId).getFriends().stream()
                .filter(aLong -> aLong==userId)
                .count() == 0) {
            userStorage.get(friendId).getFriends().remove(userId);
        }
    }

    public List<Long> getCommonFriends(Long userId, Long friendId) {
        return userStorage.get(userId).getFriends().stream()
                .distinct()
                .filter(userStorage.get(friendId).getFriends()::contains)
                .collect(Collectors.toList());
    }
}
