package com.platform.naxterbackend.user.service;

import com.platform.naxterbackend.user.model.Role;
import com.platform.naxterbackend.user.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> getRoles() {
        return this.roleRepository.findAll();
    }
}
