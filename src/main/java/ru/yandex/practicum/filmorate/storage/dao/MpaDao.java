package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MpaDao {

    Optional<Mpa> get(int id);
    Collection<Mpa> getAll();
}
