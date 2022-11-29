package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmDao {
    Optional<Film> add(Film film);
    Optional<Film> update(Film film);
    void delete(Long id);
    Optional<Film> get(Long id);
    boolean isContains(Long id);
    Collection<Film> getAll();
}
