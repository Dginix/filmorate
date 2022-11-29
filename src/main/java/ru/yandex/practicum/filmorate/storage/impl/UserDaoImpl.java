package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.UserDao;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;

import java.util.Collection;
import java.util.Optional;

@Component
@Qualifier("dbStorage")
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> add(User user) {
        String sql = "insert into user values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        return Optional.of(user);
    }

    @Override
    public Optional<User> update(User user) {
        String sql = "merge into user key (?) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getId(), user.getEmail(), user.getLogin(), user.getName(),
                user.getBirthday());
        return Optional.of(user);
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from user where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<User> get(Long id) {
        String sql = "select * from user where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserRowMapper(), id));
    }

    @Override
    public boolean isContains(Long id) {
        String sql = "select * from user where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserRowMapper(), id)).isPresent();
    }

    @Override
    public Collection<User> getAll() {
        String sql = "select * from user";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }
}
