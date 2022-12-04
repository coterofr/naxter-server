package com.platform.naxterbackend.theme.service;

import com.platform.naxterbackend.theme.model.Theme;
import com.platform.naxterbackend.theme.model.UserTheme;
import com.platform.naxterbackend.theme.repository.ThemeRepository;
import com.platform.naxterbackend.user.model.User;
import com.platform.naxterbackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ThemeServiceImpl implements ThemeService {

    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Theme getTheme(String name) {
        return this.themeRepository.findByNameIgnoreCase(name);
    }

    @Override
    public Boolean exitsThemeByName(String name) {
        return this.themeRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public Boolean exitsOtherThemeByName(String oldName, String newName) {
        return Boolean.TRUE.equals(!oldName.toLowerCase().equals(newName.toLowerCase()) && this.themeRepository.existsByNameIgnoreCase(newName));
    }

    @Override
    public List<Theme> getThemes() {
        return this.themeRepository.findAllByOrderByNameAsc();
    }

    @Override
    public List<Theme> getThemesByUser(String name) {
        User user = this.userRepository.findByNameIgnoreCase(name);

        return this.themeRepository.findAllByUser(user);
    }

    @Override
    public List<Theme> searchThemes(String idUser, String name) {
        return this.themeRepository.findAllByNameAndUser(name, idUser);
    }

    @Override
    @Transactional(readOnly = false)
    public Theme createTheme(UserTheme userTheme) {
        Theme theme = new Theme();
        theme.setName(userTheme.getName());
        theme.setDescription(userTheme.getDescription());

        User user = this.userRepository.findByNameIgnoreCase(userTheme.getUser());
        theme.setUser(user);

        return this.themeRepository.save(theme);
    }

    @Override
    @Transactional(readOnly = false)
    public Theme editTheme(String name, UserTheme userTheme) {
        Theme theme = this.themeRepository.findByNameIgnoreCase(name);

        theme.setDescription(userTheme.getDescription());

        return this.themeRepository.save(theme);
    }
}
