package ru.yandex.practicum.filmorate.storage.dao;

import java.util.List;
import java.util.Optional;

public interface FilmLikeDao {

    void addLike(long filmId, long userId);
    void removeLike(long filmId, long userId);
    List<Long> getAllFilmLikes(long filmId);
    List<Long> getAllUserLikes(long userId);
}
