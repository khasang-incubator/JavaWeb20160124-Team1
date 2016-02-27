package io.khasang.wlogs.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRegistrationForm {
    @NotNull
    @Size(min=3, max=50)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    private String username;
    @NotNull
    @Size(min=6, max=20)
    private String password;
    @NotNull
    @Size(min=6, max=20)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isPasswordConfirmedValid() {
        return password.equals(confirmPassword);
    }
}
