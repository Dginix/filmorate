package ru.yandex.practicum.filmorate.storage.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmDao;
import ru.yandex.practicum.filmorate.storage.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.storage.mapper.GenreRowMapper;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("dbStorage")
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Film> add(Film film) {
        String sql = "insert into film values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpaId());

        return Optional.of(film);
    }

    @Override
    public Optional<Film> update(Film film) {
        String sql = "merge into film key (?) values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, film.getId(), film.getId(), film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpaId());

        return Optional.of(film);
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from film where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Film> get(Long id) {
        String sql = "select * from film where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new FilmRowMapper(), id));
    }

    @Override
    public boolean isContains(Long id) {
        String sql = "select * from film where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new FilmRowMapper(), id)).isPresent();
    }

    @Override
    public Collection<Film> getAll() {
        String sql = "select * from film";
        return jdbcTemplate.query(sql, new FilmRowMapper());
    }
}
