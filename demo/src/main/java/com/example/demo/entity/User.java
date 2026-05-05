package com.example.demo.entity;

import jakarta.validation.constraints.Email;
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

    @NotBlank(message = "{error.email.empty}")
    @Email(message = "{error.email.invalid}")
    private String email;

    private boolean confirmed;

    private User() {}

    public static Builder builder() {
        return new Builder();
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public static final class Builder {
        private final User user = new User();

        public Builder lastname(String lastname) {
            user.lastname = lastname;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public Builder confirmed(boolean confirmed) {
            user.confirmed = confirmed;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
