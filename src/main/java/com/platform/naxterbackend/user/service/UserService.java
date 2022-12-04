package com.platform.naxterbackend.user.service;

import com.platform.naxterbackend.auth.model.JwtToken;
import com.platform.naxterbackend.auth.model.LoginUser;
import com.platform.naxterbackend.auth.model.RegisterUser;
import com.platform.naxterbackend.user.model.ConfigUser;
import com.platform.naxterbackend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    User getUser(String name);

    User getUserByName(String name);

    String getUserPassword(String name);

    Boolean exitsUserByName(String name);

    Boolean exitsOtherUserByName(String oldName, String newName);

    Boolean exitsUserByEmail(String email);

    Boolean exitsOtherUserByEmail(String oldEmail, String newEmail);

    Boolean isUserBlock(String name);

    List<User> getUsers();

    Page<User> getUsers(Pageable pageable);

    List<User> getSubscribedAuthors(String name);

    List<User> searchUsers(String name);

    User register(RegisterUser registerUser, String passwordEncoded);

    JwtToken login(LoginUser loginUser, Authentication authentication);

    User edit(String name, ConfigUser configUser);

    User block(String name);

    String delete(String name);

    User changeGenericRole(String name);
}
