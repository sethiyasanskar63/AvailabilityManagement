package com.assignment.availabilitymanagement.serviceImpl;

import com.assignment.availabilitymanagement.entity.Role;
import com.assignment.availabilitymanagement.repository.RoleRepository;
import com.assignment.availabilitymanagement.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Role addRole(Role role) {
    return roleRepository.saveAndFlush(role);
  }

  @Override
  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }
}
