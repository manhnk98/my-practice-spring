package com.nkm.mypracticespring.security.custom.i_model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class FakeUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Set<GrantedAuthority> customAuthorities;

    public FakeUserDetails(String username, String password, Set<GrantedAuthority> customAuthorities) {
        this.username = username;
        this.password = password;
        this.customAuthorities = customAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return customAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
