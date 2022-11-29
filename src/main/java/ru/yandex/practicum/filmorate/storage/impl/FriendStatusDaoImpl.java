package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FriendStatusDao;

import java.util.List;
import java.util.Optional;

public class FriendStatusDaoImpl implements FriendStatusDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendStatusDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(long userId, long friendId) {
        String sql = "insert into friend_status values (?, ?, FALSE)";
        jdbcTemplate.update(sql, userId, friendId);

        sql = "select count(*) from friend_status where user_id = friendId and friend_id = userId";
        Integer checkFriendship = jdbcTemplate.queryForObject(sql, Integer.class, userId, friendId);
        if(checkFriendship > 1) {
            sql = "update friend_status set status = TRUE where user_id = ? and friend_id = ?";
            jdbcTemplate.update(sql, userId, friendId);
            jdbcTemplate.update(sql, friendId, userId);
        }
    }

    @Override
    public void removeFriend(long userId, long friendId) {
        String sql = "delete from friend_status where user_id = ?";
        jdbcTemplate.update(sql, userId);

        sql = "update friend_status set status = FALSE where friend_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    @Override
    public List<Long> getAllUserFriends(long userId) {
        String sql = "select from friend_status where user_id = ? and status = TRUE";
        return jdbcTemplate.queryForList(sql, Long.class, userId);
    }

    @Override
    public Optional<Boolean> checkStatus(long userId, long friendId) {
        String sql = "select status from friend_status where user_id = ? and friend_id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, Boolean.class, userId, friendId));
    }
}
