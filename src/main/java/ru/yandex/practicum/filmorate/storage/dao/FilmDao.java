package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmDao {
    Optional<Film> add(Film t);
    Optional<Film> update(Film t);
    Optional<Film> delete(Long id);
    Optional<Film> get(Long id);
    boolean isContains(Long id);
    List<Film> getAll();
}
