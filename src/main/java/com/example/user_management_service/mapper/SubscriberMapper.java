package com.example.user_management_service.mapper;

import com.example.user_management_service.dto.SubscriberDTO;
import com.example.user_management_service.model.Subscriber;
import org.springframework.stereotype.Component;

@Component
public class SubscriberMapper implements  IMapper<Subscriber, SubscriberDTO> {

    @Override
    public Subscriber toEntity(SubscriberDTO dto) {
        if (dto == null) return null;
        Subscriber s = new Subscriber();
        s.setFirstName(dto.getFirstName());
        s.setLastName(dto.getLastName());
        s.setJmbg(dto.getJmbg());
        s.setBirthDate(dto.getBirthDate());
        s.setGender(dto.getGender());
        s.setMaritalStatus(dto.getMaritalStatus());
        s.setEmail(dto.getEmail());
        s.setUsername(dto.getUsername());
        s.setPassword(dto.getPassword());
        s.setUserRole(dto.getUserRoleType());
        return s;
    }

    @Override
    public SubscriberDTO toDto(Subscriber entity) {
        if (entity == null) return null;
        SubscriberDTO dto = new SubscriberDTO();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setJmbg(entity.getJmbg());
        dto.setBirthDate(entity.getBirthDate());
        dto.setGender(entity.getGender());
        dto.setMaritalStatus(entity.getMaritalStatus());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setUserRoleType(entity.getUserRole());
        if (entity.getSubscriberRole() != null) {
            dto.setSubscriberRoleId(entity.getSubscriberRole().getId());
        }
        return dto;
    }
}