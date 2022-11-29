package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface FriendStatusDao {

    void addFriend(long userId, long friendId);
    void removeFriend(long userId, long friendId);
    List<Long> getAllUserFriends(long userId);
    Optional<Boolean> checkStatus(long userId, long friendId);
}
