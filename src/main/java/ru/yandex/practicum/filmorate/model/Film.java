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

    @NotNull(message = "id cant be empty")
    @Positive(message = "id cant be less then zero or equal")
    private long id;

    @NotBlank(message = "name cant be blank")
    private String name;

    @Size(max = 200, message = "max size for description is 200")
    @EqualsAndHashCode.Exclude
    private String description;

    private LocalDate releaseDate;

    @Positive(message = "duration cant be less then zero")
    @EqualsAndHashCode.Exclude
    private int duration;

    @EqualsAndHashCode.Exclude
    private Set<Long> likes;
}
