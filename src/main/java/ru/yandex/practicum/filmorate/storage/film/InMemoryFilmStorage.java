package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.Storage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements Storage<Film> {
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public Film add(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        Film storageFilm = films.get(film.getId());
        storageFilm.setName(film.getName());
        storageFilm.setDuration(film.getDuration());
        storageFilm.setReleaseDate(film.getReleaseDate());
        storageFilm.setDescription(film.getDescription());
        return storageFilm;
    }

    @Override
    public Film delete(Long id) {
        return films.remove(id);
    }

    @Override
    public Film get(Long id) {
        return films.get(id);
    }

    @Override
    public boolean isContain(Long id) {
        return films.containsKey(id);
    }

    @Override
    public List<Film> getAll() {
        return films.values().stream().toList();
    }
}
