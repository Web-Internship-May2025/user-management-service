package com.example.user_management_service.service;

import com.example.user_management_service.dto.SubscriberDTO;
import com.example.user_management_service.exception.ResourceNotFoundException;
import com.example.user_management_service.mapper.SubscriberMapper;
import com.example.user_management_service.model.Subscriber;
import com.example.user_management_service.model.SubscriberRole;
import com.example.user_management_service.repository.SubscriberRepository;
import com.example.user_management_service.repository.SubscriberRoleRepository;
import com.example.user_management_service.spec.SubscriberSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;


@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberRoleRepository roleRepo;
    private final SubscriberMapper mapper;


    @Autowired
    public SubscriberService(SubscriberRepository subscriberRepository, SubscriberRoleRepository roleRepo, SubscriberMapper mapper) {
        this.subscriberRepository = subscriberRepository;
        this.roleRepo       = roleRepo;
        this.mapper         = mapper;
    }


    public SubscriberDTO addSubscriber(SubscriberDTO dto) {
        Subscriber subscriber = mapper.toEntity(dto);
        SubscriberRole role = roleRepo.findById(dto.getSubscriberRoleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "SubscriberRole with id=" + dto.getSubscriberRoleId() + " not found"));
        subscriber.setSubscriberRole(role);
        subscriber.setIsEnabled(false);
        subscriber.setIsActive(false);
        Subscriber saved = subscriberRepository.save(subscriber);
        return mapper.toDto(saved);
    }


    public List<Subscriber> search(String firstName, String lastName, String email){
        Specification<Subscriber> spec = SubscriberSpecification.filterBy(firstName, lastName, email);
        return subscriberRepository.findAll(spec);
    }


    public Page<Subscriber> findSubscribersBySearch(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return subscriberRepository.findAll(pageable);
        }
        return subscriberRepository.findSubscribersBySearch(keyword, pageable);
    }
}

