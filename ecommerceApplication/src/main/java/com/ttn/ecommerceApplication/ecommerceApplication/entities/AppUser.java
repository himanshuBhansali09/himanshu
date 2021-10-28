package com.ttn.ecommerceApplication.ecommerceApplication.entities;
import com.ttn.ecommerceApplication.ecommerceApplication.dao.UserDao;
import com.ttn.ecommerceApplication.ecommerceApplication.daoImpl.GrantAuthorityImpl;
import com.ttn.ecommerceApplication.ecommerceApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;
import java.util.List;

public class AppUser implements UserDetails {

    @Autowired
    AppUserDetailsService appUserDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDao userDao;

    private String username;
    private String password;
    private boolean isEnabled;
    private boolean isAccountNonLocked;
    private boolean isAccountNonExpired;
    List<GrantAuthorityImpl> grantAuthorities;

    public AppUser(String username, String password, List<GrantAuthorityImpl> grantAuthorities,boolean isEnabled,boolean isAccountNonLocked,boolean isAccountNonExpired) {
        this.username = username;
        this.password = password;
        this.grantAuthorities = grantAuthorities;
        this.isEnabled=isEnabled;
        this.isAccountNonLocked=isAccountNonLocked;
        this.isAccountNonExpired=isAccountNonExpired;
        System.out.println(isEnabled);
        System.out.println(isAccountNonLocked);
        System.out.println("app user ka constructor");
        System.out.println(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("get authorities");

        return grantAuthorities;

    }

    @Override
    public String getPassword()
    {
        System.out.println(password);
        return password;
    }



    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}