package com.example.demo.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class User extends AbstractEntity {
    @NotBlank(message = "{error.login.empty}")
    @Size(min = 3, max = 20, message = "{error.login.size}")
    private String lastname;
    @NotBlank(message = "{error.password.empty}")
    @Size(min = 8, message = "{error.password.size}")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "{error.password.pattern}")
    private String password;


    public User(String lastname, String password) {
        this.lastname = lastname;
        this.password = password;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
