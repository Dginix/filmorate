package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmControllerTest {

    @Autowired
    FilmController filmController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addFilm_NormalTest() {
        Film film1 = Film.builder()
                .id(1)
                .name("Big Fish")
                .duration(140)
                .description("Bla bla")
                .releaseDate(LocalDate.of(2000,1,1))
                .likes(Stream.of(1l, 2l).collect(Collectors.toSet()))
                .build();

        filmController.addFilm(film1);
        assertEquals(film1, filmController.getFilmById(1l), "Фильм не попал в storage");
    }

    @Test
    void addFilm_NameValidationTest() {
        Film film1 = Film.builder()
                .id(1)
                .name("Big Fish")
                .duration(140)
                .description("Bla bla")
                .releaseDate(LocalDate.of(1895, Month.DECEMBER, 26))
                .likes(Stream.of(1l, 2l).collect(Collectors.toSet()))
                .build();

        assertThrows(ValidationException.class, () -> filmController.addFilm(film1));
    }
}