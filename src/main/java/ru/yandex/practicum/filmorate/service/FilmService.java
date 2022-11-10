package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.Storage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final Storage<Film> filmStorage;

    @Autowired
    public FilmService(Storage<Film> filmStorage) {
        this.filmStorage = filmStorage;
    }

    public boolean addLike(Long filmId, Long userId) {
        return filmStorage.get(filmId).getLikes().add(userId);
    }

    public boolean removeLike(Long filmId, Long userId) {
        return filmStorage.get(filmId).getLikes().remove(userId);
    }

    public List<Film> getTopFilmsByUserLikes(Long count) {
        return filmStorage.getAll().stream()
                .sorted((Comparator.comparingInt(o -> o.getLikes().size())))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film addFilm(Film film) {
        Film result = filmStorage.add(film);
        log.debug("from service: " + result.toString());
        return result;
    }

    public Film getFilmById(Long id) {
        return filmStorage.get(id);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }
}
