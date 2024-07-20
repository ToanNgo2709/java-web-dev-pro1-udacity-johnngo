package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * User Id
     */
    private int userid;

    /**
     * Username
     */
    private String username;

    /**
     * Password
     */
    private String password;

    /**
     * First Name
     */
    private String firstname;

    /**
     * Last Name
     */
    private String lastname;

    /**
     * Salt
     */
    private String salt = "";

    /**
     * Enable Flag
     */
    private boolean enableFlg = true;

    /**
     * Role
     */
    private String role = "USER";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enableFlg;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enableFlg;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enableFlg;
    }

    @Override
    public boolean isEnabled() {
        return enableFlg;
    }
}
