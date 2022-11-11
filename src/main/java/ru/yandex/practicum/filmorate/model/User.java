package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    @NotNull(message = "id cant be empty")
    @Positive(message = "id cant be less then zero or equal")
    private long id;

    @Email(message = "email not valid")
    private String email;

    @NotBlank(message = "login can't be blank")
    private String login;

    @EqualsAndHashCode.Exclude
    private String name;

    @EqualsAndHashCode.Exclude
    @Past(message = "birthday can't be in future")
    private LocalDate birthday;

    @EqualsAndHashCode.Exclude
    Set<Long> friends;
}
