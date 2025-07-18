package com.example.user_management_service.mapper;

import com.example.user_management_service.model.User;
import com.example.user_management_service.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IMapper<User, UserDTO> {

    @Override
    public UserDTO toDto(User entity) {
        if (entity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        if(entity.getId()!=null){
            dto.setId(entity.getId());
        }

        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setJmbg(entity.getJmbg());
        dto.setBirthDate(entity.getBirthDate());
        dto.setIsDeleted(entity.getIsDeleted());
        dto.setGender(entity.getGender());
        dto.setMaritalStatus(entity.getMaritalStatus());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        dto.setIsEnabled(entity.getIsEnabled());
        dto.setIsActive(entity.getIsActive());
        dto.setUserRoleType(entity.getUserRole());
        dto.setIcon(entity.getIcon());

        return dto;
    }

    @Override
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User entity = new User();
        if(dto.getId()!=null){
            entity.setId(dto.getId());
        }

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setJmbg(dto.getJmbg());
        entity.setBirthDate(dto.getBirthDate());
        entity.setIsDeleted(dto.getIsDeleted());
        entity.setGender(dto.getGender());
        entity.setMaritalStatus(dto.getMaritalStatus());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setIsEnabled(dto.getIsEnabled());
        entity.setIsActive(dto.getIsActive());
        entity.setUserRole(dto.getUserRoleType());

        return entity;
    }
}