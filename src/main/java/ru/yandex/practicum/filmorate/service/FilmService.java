package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.FilmDao;
import ru.yandex.practicum.filmorate.storage.dao.UserDao;

import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final FilmDao filmDao;
    private final UserDao userDao;

    @Autowired
    public FilmService(@Qualifier("dbStorage") FilmDao filmDao, @Qualifier("dbStorage") UserDao userDao) {
        this.filmDao = filmDao;
        this.userDao = userDao;
    }

    public void addLike(Long filmId, Long userId) {
        if (!userDao.isContains(userId)) {
            throw new NotFoundException("user not found");
        }

        filmDao.get(filmId).ifPresentOrElse(
                film -> film.getLikes().add(userId),
                () -> {
                    throw new NotFoundException("film not found");
                }
        );
    }

    public void removeLike(Long filmId, Long userId) {
        if (!userDao.isContains(userId)) {
            throw new NotFoundException("user not found");
        }

        filmDao.get(filmId).ifPresentOrElse(
                film -> film.getLikes().remove(userId),
                () -> {
                    throw new NotFoundException("film not found");
                }
        );
    }

    public List<Film> getTopFilmsByUserLikes(Long count) {
        return filmDao.getAll().stream()
                .sorted((Comparator.comparingInt(o -> o.getLikes().size())))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film addFilm(Film film) {
        validateFilmFields(film);
        return filmDao.add(film).get();
    }

    public Film updateFilm(Film film) {
        validateFilmFields(film);
        if (!filmDao.isContains(film.getId())) {
            throw new NotFoundException("film not found");
        }
        return filmDao.update(film).get();
    }

    public Film getFilmById(Long id) {
        return filmDao.get(id).orElseGet(() -> {
            throw new NotFoundException("film not found");
        });
    }

    public List<Film> getAllFilms() {
        return filmDao.getAll().stream().toList();
    }

    public void validateFilmFields(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
            throw new ValidationException("release date cant be before 28.12.1895");
        }
    }
}
