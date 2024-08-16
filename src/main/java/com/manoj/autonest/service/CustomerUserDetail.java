package com.manoj.autonest.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.manoj.autonest.model.User;

public class CustomerUserDetail implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User user;

    public CustomerUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return a list of authorities granted to the user. Assuming role-based access control.
        return List.of(() -> user.getRole());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Return true if the account is non-expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Return true if the account is non-locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Return true if the credentials (password) are non-expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Return true if the account is enabled
        return true;
    }

}
