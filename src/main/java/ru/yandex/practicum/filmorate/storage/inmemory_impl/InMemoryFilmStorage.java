package ru.yandex.practicum.filmorate.storage.inmemory_impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@Qualifier("collectionStorage")
public class InMemoryFilmStorage implements FilmDao {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Optional<Film> add(Film film) {
        films.put(film.getId(), film);
        return Optional.ofNullable(film);
    }

    @Override
    public Optional<Film> update(Film film) {
        Film storageFilm = films.get(film.getId());
        storageFilm.setName(film.getName());
        storageFilm.setDuration(film.getDuration());
        storageFilm.setReleaseDate(film.getReleaseDate());
        storageFilm.setDescription(film.getDescription());
        return Optional.ofNullable(storageFilm);
    }

    @Override
    public void delete(Long id) {
        films.remove(id);
    }

    @Override
    public Optional<Film> get(Long id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public boolean isContains(Long id) {
        return films.containsKey(id);
    }

    @Override
    public List<Film> getAll() {
        return films.values().stream().toList();
    }
}
