package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("dbStorage") UserDao userDao) {
        this.userDao = userDao;
    }

    public User addUser(User user) {
        return userDao.add(user).get();
    }

    public User updateUser(User user) {
        if (!userDao.isContains(user.getId())) {
            throw new NotFoundException("user not found");
        }
        return userDao.update(user).get();
    }

    public User getUserById(Long id) {
        return userDao.get(id).orElseThrow(() -> new NotFoundException("user not found"));
    }

    public List<User> getAllUsers() {
        return userDao.getAll().stream().toList();
    }

    public List<User> getAllFriends(Long userId) {
        return userDao.getAll().stream()
                .filter(user -> user.getFriends().stream()
                        .anyMatch(friend -> friend.equals(userId)))
                .collect(Collectors.toList());
    }

    public void addToFriendsList(Long userId, Long friendId) {
        userDao.get(userId).orElseThrow(() -> new NotFoundException("user not found"))
                .getFriends().add(friendId);

        userDao.get(friendId).orElseThrow(() -> new NotFoundException("user friend not found")).
                getFriends().add(userId);
    }

    public void removeFromFriendsList(Long userId, Long friendId) {
        userDao.get(userId).orElseGet(() -> {
            throw new NotFoundException("user not found");
        }).getFriends().remove(friendId);

        userDao.get(friendId).orElseGet(() -> {
            throw new NotFoundException("user friend not found");
        }).getFriends().remove(userId);
    }

    public List<User> getCommonFriends(Long userId, Long friendId) {

        if (!userDao.isContains(userId)) {
            throw new NotFoundException("user not found");
        }

        if (!userDao.isContains(friendId)) {
            throw new NotFoundException("user friend not found");
        }

        List<Long> friendsIds =  userDao.get(userId).get().getFriends().stream()
                .distinct()
                .filter(userDao.get(friendId).get().getFriends()::contains)
                .collect(Collectors.toList());

        return userDao.getAll().stream()
                .filter(user -> friendsIds.stream()
                        .anyMatch(id -> id.equals(user.getId())))
                .collect(Collectors.toList());
    }
}
