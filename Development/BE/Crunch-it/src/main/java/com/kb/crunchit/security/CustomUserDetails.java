package com.kb.crunchit.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final String email;
    private final String password;

    @Getter
    private final String nickname;
    @Getter
    private final Integer userId;

    public CustomUserDetails(String email, String password, String nickname, Integer UserId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.userId = UserId;
    }

    ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
