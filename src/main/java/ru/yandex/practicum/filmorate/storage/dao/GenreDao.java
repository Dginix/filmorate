package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {

    Optional<Genre> get(int id);
    List<Genre> getAll();
}
