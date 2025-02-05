package ru.example.redis_key_listener.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private Integer age;
}
