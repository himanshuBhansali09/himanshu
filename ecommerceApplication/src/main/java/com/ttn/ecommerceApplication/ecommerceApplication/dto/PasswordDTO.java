package com.ttn.ecommerceApplication.ecommerceApplication.dto;

import com.ttn.ecommerceApplication.ecommerceApplication.passwordvalidation.ValidPassword;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Component
public class PasswordDTO
{
    @Column(nullable=false)
    @NotEmpty(message = "Password cant be null")
    @Size(min=8)
    @ValidPassword
    private String password;

    @Column(nullable = false)
    private String confirmPassword;

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
}
