package com.ttn.ecommerceApplication.ecommerceApplication.dto;
import com.ttn.ecommerceApplication.ecommerceApplication.passwordvalidation.ValidPassword;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Component
public class UserDTO {


    @Column(nullable=false,unique = true)
    @NotBlank(message = "Enter the UserName")
    @Email(message="invalid_email")
    private String username;


    @Column(nullable=false)
    @NotEmpty(message = "Enter the First Name")
    private String firstName;

    @Column(nullable=true)
    private String middleName;

    @Column(nullable=false)
    @NotEmpty(message = "Enter the Last Name")
    private String lastName;

    @Column(nullable=false)
    @NotEmpty(message = "Password cant be null")
    @Size(min=8)
    @ValidPassword
    private String password;

    @Column(nullable = false)
    private String confirmPassword;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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



}
