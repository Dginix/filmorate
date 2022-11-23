package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.Storage;

import java.util.List;
import java.util.Optional;
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
        return userStorage.add(user).get();
    }

    public User updateUser(User user) {
        if (!userStorage.isContains(user.getId())) {
            throw new NotFoundException("user not found");
        }
        return userStorage.update(user).get();
    }

    public User getUserById(Long id) {
        return userStorage.get(id).orElseThrow(() -> new NotFoundException("user not found"));
    }

    public List<User> getAllUsers() {
        return userStorage.getAll();
    }

    public List<User> getAllFriends(Long userId) {
        return userStorage.getAll().stream()
                .filter(user -> user.getFriends().stream()
                        .anyMatch(friend -> friend.equals(userId)))
                .collect(Collectors.toList());
    }

    public void addToFriendsList(Long userId, Long friendId) {
        userStorage.get(userId).orElseGet(() -> {
            throw new NotFoundException("user not found");
        }).getFriends().add(friendId);

        userStorage.get(friendId).orElseGet(() -> {
            throw new NotFoundException("user friend not found");
        }).getFriends().add(userId);
    }

    public void removeFromFriendsList(Long userId, Long friendId) {
        userStorage.get(userId).orElseGet(() -> {
            throw new NotFoundException("user not found");
        }).getFriends().remove(friendId);

        userStorage.get(friendId).orElseGet(() -> {
            throw new NotFoundException("user friend not found");
        }).getFriends().remove(userId);
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {

        if (!userStorage.isContains(userId)) {
            throw new NotFoundException("user not found");
        }

        if (!userStorage.isContains(friendId)) {
            throw new NotFoundException("user friend not found");
        }

        List<Long> friendsIds =  userStorage.get(userId).get().getFriends().stream()
                .distinct()
                .filter(userStorage.get(friendId).get().getFriends()::contains)
                .collect(Collectors.toList());

        return userStorage.getAll().stream()
                .filter(user -> friendsIds.stream()
                        .anyMatch(id -> id.equals(user.getId())))
                .collect(Collectors.toList());
    }
}
