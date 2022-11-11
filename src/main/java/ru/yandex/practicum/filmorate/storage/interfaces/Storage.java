package ru.yandex.practicum.filmorate.storage.interfaces;

import java.util.List;

public interface Storage<T> {
    T add(T t);
    T update(T t);
    T delete(Long id);
    T get(Long id);
    boolean isContain(Long id);
    List<T> getAll();
}
