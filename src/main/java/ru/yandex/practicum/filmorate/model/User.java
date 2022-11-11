package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    @NotNull(message = "id cant be empty")
    private long id;
    @Email(message = "email not valid")
    private String email;
    @NotBlank(message = "login can't be blank")
    private String login;
    private String name;
    @Past(message = "birthday can't be in future")
    private LocalDate birthday;
}
