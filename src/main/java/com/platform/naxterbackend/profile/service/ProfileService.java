package com.platform.naxterbackend.profile.service;

import com.platform.naxterbackend.profile.model.Account;
import com.platform.naxterbackend.profile.model.Profile;
import com.platform.naxterbackend.profile.model.Visualization;
import com.platform.naxterbackend.user.model.User;

import java.util.List;

public interface ProfileService {

    List<Profile> getProfiles();

    User editAccount(String name, Account account);

    User visit(Visualization visualization);
}
