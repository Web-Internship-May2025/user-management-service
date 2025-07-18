package com.example.user_management_service.message;

import com.example.user_management_service.dto.SubscriberInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicySearchSubscriberResponseMessage {
    private String correlationId;
    private List<SubscriberInfoDTO> subscriberInfos;
}
