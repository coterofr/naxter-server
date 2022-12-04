package com.platform.naxterbackend.auth.service;

import com.platform.naxterbackend.auth.model.UserDetailsImpl;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userService.getUserByName(username);

        return UserDetailsImpl.build(user);
    }
}
