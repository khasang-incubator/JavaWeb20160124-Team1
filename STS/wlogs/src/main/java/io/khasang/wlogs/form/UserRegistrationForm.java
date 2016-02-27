package io.khasang.wlogs.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;

public class UserRegistrationForm {
    @NotBlank
    @Length(min=3, max=50)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    private String username;
    @NotBlank
    @Length(min=6, max=20)
    private String password;
    @NotBlank
    @Length(min=6, max=20)
    private String confirmPassword;
    @NotBlank
    @Email
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @AssertTrue(message = "Password should be equals with verify field password.")
    public boolean isPasswordConfirmedValid() {
        return password.equals(confirmPassword);
    }
}
