package com.example.user_management_service.mapper;

public interface IMapper <E, D>{
    D toDto(E entity);
    E toEntity(D dto);
}
