package com.ttn.ecommerceApplication.ecommerceApplication.daoImpl;
import org.springframework.security.core.GrantedAuthority;
public class GrantAuthorityImpl implements GrantedAuthority
{
    String  authority;
    public GrantAuthorityImpl(String  authority) {
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        System.out.println("authority" + authority);
        System.out.println(String.valueOf(authority));
        return String.valueOf(authority);
    }
}