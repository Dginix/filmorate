package ru.yandex.practicum.filmorate.storage.dao;

import java.util.List;
import java.util.Optional;

public interface Storage<T> {
    Optional<T> add(T t);
    Optional<T> update(T t);
    Optional<T> delete(Long id);
    Optional<T> get(Long id);
    boolean isContains(Long id);
    List<T> getAll();
}
