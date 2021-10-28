package com.ttn.ecommerceApplication.ecommerceApplication.controller;

import com.ttn.ecommerceApplication.ecommerceApplication.dao.ForgotPasswordDao;
import com.ttn.ecommerceApplication.ecommerceApplication.dto.PasswordDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ForgotPasswordController
{
    @Autowired
    ForgotPasswordDao forgotPassword;

    @ApiOperation("uri for forget password in which token is sent to the user")
    @PostMapping("/forgotPassword/{email_id}")
    public ResponseEntity setForgotPasswordHandler(@PathVariable(name = "email_id") String email_id)
    {
        return forgotPassword.forgotPassword(email_id);
    }


    //to do
    @ApiOperation("uri for setting new password on entering token")
    @PutMapping("/setPassword/{token}")
    public ResponseEntity setForgotPassword(@PathVariable(name = "token") String token,@Valid  @RequestBody PasswordDTO password)
    {
        return  forgotPassword.setPassword(token,password);
    }
}
