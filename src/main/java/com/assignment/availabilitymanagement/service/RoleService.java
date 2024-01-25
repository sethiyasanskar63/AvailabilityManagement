package com.assignment.availabilitymanagement.service;

import com.assignment.availabilitymanagement.entity.Role;

import java.util.List;

public interface RoleService {

  Role addRole(Role role);

  List<Role> getAllRoles();
}
