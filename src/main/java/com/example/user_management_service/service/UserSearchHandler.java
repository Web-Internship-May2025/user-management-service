package com.example.user_management_service.service;

import com.example.user_management_service.dto.SubscriberInfoDTO;
import com.example.user_management_service.message.PolicySearchSubscriberRequestMessage;
import com.example.user_management_service.message.PolicySearchSubscriberResponseMessage;
import com.example.user_management_service.model.Subscriber;
import com.example.user_management_service.repository.SubscriberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSearchHandler {
    private static final Logger log = LoggerFactory.getLogger(UserSearchHandler.class);
    private final SubscriberService subscriberService;
    @Autowired
    private KafkaTemplate<String, String> kafka;
    private final String SUBSCRIBER_REQUEST_TOPIC = "policy.subscriber.request";
    private final String SUBSCRIBER_RESPONSE_TOPIC = "policy.subscriber.response";
    public UserSearchHandler(SubscriberService subscriberService){
        this.subscriberService = subscriberService;
    }

    @KafkaListener(topics = "policy.subscriber.request",
    groupId = "policy-service-group")
    public void onUserSearchRequest(byte[] data){
        try{
            PolicySearchSubscriberRequestMessage req = new ObjectMapper().readValue(data, PolicySearchSubscriberRequestMessage.class);

            System.out.println("User service received "+req);
            String firstName = req.getFirstName();
            String lastName = req.getLastName();
            String email = req.getEmail();

            List<Subscriber> found = Collections.emptyList();
            found = subscriberService.search(firstName, lastName, email);

            List<SubscriberInfoDTO> infos = found.stream()
                    .map(s-> new SubscriberInfoDTO(
                            s.getId(),
                            s.getFirstName(),
                            s.getLastName()
                    )).toList();


            PolicySearchSubscriberResponseMessage resp =
                    new PolicySearchSubscriberResponseMessage(req.getCorrelationId(), infos);
            String json = new ObjectMapper().writeValueAsString(resp);
            kafka.send(SUBSCRIBER_RESPONSE_TOPIC, req.getCorrelationId(), json);
        }catch(IOException e){
            log.error("Failed to parse search request", e);
        }
    }
}
