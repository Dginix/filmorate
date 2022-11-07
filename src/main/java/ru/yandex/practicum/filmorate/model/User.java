package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    @NotNull
    private long id;
    @Email(message = "email not valid")
    private String email;
    @NotBlank(message = "login can't be blank")
    private String login;
    private String name;
    @Past(message = "birthday can't be in future")
    private LocalDate birthday;
}
