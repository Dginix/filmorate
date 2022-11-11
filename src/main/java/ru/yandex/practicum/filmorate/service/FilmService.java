package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.interfaces.Storage;

import java.time.LocalDate;
import java.time.Month;
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
        validateFilm(film);
        Film result = filmStorage.add(film);
        return result;
    }

    public Film getFilmById(Long id) {
        return filmStorage.get(id);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    public void validateFilm(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ValidationException("release date cant be before 28.12.1895");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("duration cant be equal or less then zero");
        }
    }
}
