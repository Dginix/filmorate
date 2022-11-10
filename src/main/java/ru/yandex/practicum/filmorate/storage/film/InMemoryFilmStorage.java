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
        return films.put(film.getId(), film);
    }

    @Override
    public Film update(Film film) {
        return films.put(film.getId(), film);
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
    public List<Film> getAll() {
        return films.values().stream().toList();
    }
}
