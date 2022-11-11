package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
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

    public List<User> getAllFriends(Long userId) {
        return userStorage.getAll().stream()
                .filter(user -> user.getFriends().stream()
                        .anyMatch(friend -> friend.equals(userId)))
                .collect(Collectors.toList());
    }

    public void addToFriendsList(Long userId, Long friendId) {

        if (!userStorage.isContains(userId)) {
            throw new NotFoundException("user not found");
        }

        if (!userStorage.isContains(friendId)) {
            throw new NotFoundException("user friend not found");
        }

        if (!userStorage.get(userId).getFriends().contains(friendId)) {
            userStorage.get(userId).getFriends().add(friendId);
        }

        if (!userStorage.get(friendId).getFriends().contains(userId)) {
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

        if (userStorage.get(userId).getFriends().contains(friendId)) {
            userStorage.get(userId).getFriends().remove(friendId);
        }

        if (userStorage.get(friendId).getFriends().contains(userId)) {
            userStorage.get(friendId).getFriends().remove(userId);
        }
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {

        if (!userStorage.isContains(userId)) {
            throw new NotFoundException("user not found");
        }

        if (!userStorage.isContains(friendId)) {
            throw new NotFoundException("user friend not found");
        }

        List<Long> friendsIds =  userStorage.get(userId).getFriends().stream()
                .distinct()
                .filter(userStorage.get(friendId).getFriends()::contains)
                .collect(Collectors.toList());

        return userStorage.getAll().stream()
                .filter(user -> friendsIds.stream()
                        .anyMatch(id -> id.equals(user.getId())))
                .collect(Collectors.toList());
    }
}
