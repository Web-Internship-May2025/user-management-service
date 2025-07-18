package com.example.user_management_service.controller;

import com.example.user_management_service.dto.SubscriberDTO;
import com.example.user_management_service.model.Subscriber;
import com.example.user_management_service.service.SubscriberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;

    @Autowired
    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PreAuthorize("hasRole('SALES_AGENT')")
    @GetMapping
    public Page<Subscriber> searchSubscribers(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return subscriberService.findSubscribersBySearch(keyword, pageable);
    }

    @PreAuthorize("hasRole('SALES_AGENT')")
    @PostMapping
    public ResponseEntity<SubscriberDTO> addSubscriber(
            @RequestBody @Valid SubscriberDTO dto) {

        SubscriberDTO created = subscriberService.addSubscriber(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }
}