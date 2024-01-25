package com.assignment.availabilitymanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id")
  private Integer roleId;

  @Column(name = "role")
  private String role;

  @Setter
  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new HashSet<>();

  public Role(Integer id, String role) {
    this.roleId = id;
    this.role = role;
  }

  public void setRole(String role) {
    this.role = "ROLE_" + role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Role role1)) return false;

    if (getRoleId() != null ? !getRoleId().equals(role1.getRoleId()) : role1.getRoleId() != null) return false;
    if (getRole() != null ? !getRole().equals(role1.getRole()) : role1.getRole() != null) return false;
    return getUsers() != null ? getUsers().equals(role1.getUsers()) : role1.getUsers() == null;
  }

  @Override
  public int hashCode() {
    int result = getRoleId() != null ? getRoleId().hashCode() : 0;
    result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
    result = 31 * result + (getUsers() != null ? getUsers().hashCode() : 0);
    return result;
  }
}
