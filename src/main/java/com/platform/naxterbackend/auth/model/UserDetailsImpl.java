package com.platform.naxterbackend.auth.model;

import com.platform.naxterbackend.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsImpl implements UserDetails {

    private String name;
    private String email;
    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public UserDetailsImpl() { }

    public UserDetailsImpl(String name, String email, String userName, String password, Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                                                 .stream()
                                                 .map(role -> new SimpleGrantedAuthority(String.format("ROLE_%s", role.getType())))
                                                 .collect(Collectors.toList());

        return new UserDetailsImpl(user.getName(), user.getUserName(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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

    public String getEmail() {
        return email;
    }
}
