package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface FriendStatusDao {

    List<User> getAllUserFriends(long id);
    void addFriend(long userId, long friendId);
    void removeFriend(long userId, long friendId);
    Optional<Boolean> checkStatus(long userId, long friendId);
}
