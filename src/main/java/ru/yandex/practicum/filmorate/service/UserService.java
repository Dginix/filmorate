package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserDAO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserDAO userInMemoryStorage;

    @Autowired
    public UserService(UserDAO userInMemoryStorage) {
        this.userInMemoryStorage = userInMemoryStorage;
    }

    public User addUser(User user) {
        return userInMemoryStorage.add(user).get();
    }

    public User updateUser(User user) {
        if (!userInMemoryStorage.isContains(user.getId())) {
            throw new NotFoundException("user not found");
        }
        return userInMemoryStorage.update(user).get();
    }

    public User getUserById(Long id) {
        return userInMemoryStorage.get(id).orElseThrow(() -> new NotFoundException("user not found"));
    }

    public List<User> getAllUsers() {
        return userInMemoryStorage.getAll();
    }

    public List<User> getAllFriends(Long userId) {
        return userInMemoryStorage.getAll().stream()
                .filter(user -> user.getFriends().stream()
                        .anyMatch(friend -> friend.equals(userId)))
                .collect(Collectors.toList());
    }

    public void addToFriendsList(Long userId, Long friendId) {
        userInMemoryStorage.get(userId).orElseGet(() -> {
            throw new NotFoundException("user not found");
        }).getFriends().add(friendId);

        userInMemoryStorage.get(friendId).orElseGet(() -> {
            throw new NotFoundException("user friend not found");
        }).getFriends().add(userId);
    }

    public void removeFromFriendsList(Long userId, Long friendId) {
        userInMemoryStorage.get(userId).orElseGet(() -> {
            throw new NotFoundException("user not found");
        }).getFriends().remove(friendId);

        userInMemoryStorage.get(friendId).orElseGet(() -> {
            throw new NotFoundException("user friend not found");
        }).getFriends().remove(userId);
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {

        if (!userInMemoryStorage.isContains(userId)) {
            throw new NotFoundException("user not found");
        }

        if (!userInMemoryStorage.isContains(friendId)) {
            throw new NotFoundException("user friend not found");
        }

        List<Long> friendsIds =  userInMemoryStorage.get(userId).get().getFriends().stream()
                .distinct()
                .filter(userInMemoryStorage.get(friendId).get().getFriends()::contains)
                .collect(Collectors.toList());

        return userInMemoryStorage.getAll().stream()
                .filter(user -> friendsIds.stream()
                        .anyMatch(id -> id.equals(user.getId())))
                .collect(Collectors.toList());
    }
}
