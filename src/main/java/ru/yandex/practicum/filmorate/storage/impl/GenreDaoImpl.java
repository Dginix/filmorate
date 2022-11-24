package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;
import ru.yandex.practicum.filmorate.storage.mapper.GenreRowMapper;

import java.util.Collection;
import java.util.Optional;

@Component
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Genre> get(int id) {
        String sql = "select * from genre where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new GenreRowMapper(), id));
    }

    @Override
    public Collection<Genre> getAll() {
        String sql = "select * from genre";
        return jdbcTemplate.query(sql, new GenreRowMapper());
    }
}
