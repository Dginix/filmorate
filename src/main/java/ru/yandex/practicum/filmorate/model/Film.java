package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Film {
    @NotNull
    private long id;

    @NotBlank
    private String name;

    @Size(max = 200)
    @EqualsAndHashCode.Exclude
    private String description;

    private LocalDate releaseDate;

    @Positive
    @EqualsAndHashCode.Exclude
    private int duration;

    @EqualsAndHashCode.Exclude
    private Set<Long> likes = new HashSet<>();
}
