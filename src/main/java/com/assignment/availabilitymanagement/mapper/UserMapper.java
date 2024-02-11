package com.assignment.availabilitymanagement.mapper;

import com.assignment.availabilitymanagement.dto.UserDTO;
import com.assignment.availabilitymanagement.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between UserInfo entity and UserDTO.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDTO toDto(UserInfo userInfo);

  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "roles", ignore = true)
  UserInfo toEntity(UserDTO dto);
}
