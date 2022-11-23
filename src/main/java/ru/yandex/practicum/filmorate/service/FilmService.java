package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.Storage;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final Storage<Film> filmStorage;
    private final Storage<User> userStorage;

    @Autowired
    public FilmService(Storage<Film> filmStorage, Storage<User> userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(Long filmId, Long userId) {
        if (!userStorage.isContains(userId)) {
            throw new NotFoundException("user not found");
        }

        filmStorage.get(filmId).ifPresentOrElse(
                film -> film.getLikes().add(userId),
                () -> {
                    throw new NotFoundException("film not found");
                }
        );
    }

    public void removeLike(Long filmId, Long userId) {
        filmStorage.get(filmId).ifPresentOrElse(
                film -> film.getLikes().remove(userId),
                () -> {
                    throw new NotFoundException("film not found");
                }
        );
    }

    public List<Film> getTopFilmsByUserLikes(Long count) {
        return filmStorage.getAll().stream()
                .sorted((Comparator.comparingInt(o -> o.getLikes().size())))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film addFilm(Film film) {
        validateFilmFields(film);
        return filmStorage.add(film).get();
    }

    public Film updateFilm(Film film) {
        validateFilmFields(film);
        if (!filmStorage.isContains(film.getId())) {
            throw new NotFoundException("film not found");
        }
        return filmStorage.update(film).get();
    }

    public Film getFilmById(Long id) {
        return filmStorage.get(id).orElseGet(() -> {
            throw new NotFoundException("film not found");
        });
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    public void validateFilmFields(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ValidationException("release date cant be before 28.12.1895");
        }
    }
}
