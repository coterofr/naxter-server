package com.platform.naxterbackend.theme.service;

import com.platform.naxterbackend.theme.model.Theme;
import com.platform.naxterbackend.theme.model.UserTheme;
import com.platform.naxterbackend.user.model.User;

import java.util.List;

public interface ThemeService {

    Theme getTheme(String name);

    Boolean exitsThemeByName(String name);

    Boolean exitsOtherThemeByName(String oldName, String newName);

    List<Theme> getThemes();

    List<Theme> getThemesByUser(String name);

    List<Theme> searchThemes(String idUser, String name);

    Theme createTheme(UserTheme userTheme);

    Theme editTheme(String name, UserTheme userTheme);
}
