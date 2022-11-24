package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaDao;
import ru.yandex.practicum.filmorate.storage.mapper.MpaRowMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class MpaDaoImpl implements MpaDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Mpa> get(int id) {
        String sql = "select * from MPA where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new MpaRowMapper(), id));
    }

    @Override
    public Collection<Mpa> getAll() {
        String sql = "select * from MPA";
        return jdbcTemplate.query(sql, new MpaRowMapper());
    }
}
