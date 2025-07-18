package com.example.user_management_service.service;

import com.example.user_management_service.aspect.Monitored;
import com.example.user_management_service.dto.CountryDTO;
import com.example.user_management_service.dto.UserDTO;
import com.example.user_management_service.mapper.CountryMapper;
import com.example.user_management_service.mapper.UserMapper;
import com.example.user_management_service.model.enums.UserRoleType;
import com.example.user_management_service.repository.CountryRepository;
import com.example.user_management_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.user_management_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Monitored
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }
    
    public UserDTO findById(Long id){
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()){
            User user = opt.get();
            return userMapper.toDto(user);
        }
        return null;
    }

    public UserDTO findByUsername(String username){
        Optional<User> opt = userRepository.findByUsername(username);
        if(opt.isPresent()){
            User user = opt.get();
            return userMapper.toDto(user);
        }
        return null;
    }
}
