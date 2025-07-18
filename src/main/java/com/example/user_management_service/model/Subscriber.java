package com.example.user_management_service.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("SUBSCRIBER")
public class Subscriber extends User {

    @ManyToOne
    @JoinColumn(name= "subscriber_role_id")
    private SubscriberRole subscriberRole;


}
