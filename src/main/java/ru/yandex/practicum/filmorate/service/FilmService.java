package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.Storage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final Storage<Film> filmStorage;

    @Autowired
    public FilmService(Storage<Film> filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(Long filmId, Long userId) {
        filmStorage.get(filmId).getLikes().add(userId);
    }

    public void removeLike(Long filmId, Long userId) {
        filmStorage.get(filmId).getLikes().remove(userId);
    }

    public List<Film> getTop10FilmsByUserLikes() {
        return filmStorage.getAll().stream()
                .sorted((Comparator.comparingInt(o -> o.getLikes().size())))
                .limit(10)
                .collect(Collectors.toList());
    }

    public Film addFilm(Film film) {
        return filmStorage.add(film);
    }

    public Film getFilmById(Film film) {
        return filmStorage.get(film.getId());
    }

    public List<Film> getAllFilm(Film film) {
        return filmStorage.getAll();
    }
}
