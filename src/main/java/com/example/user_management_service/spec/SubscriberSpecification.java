package com.example.user_management_service.spec;

import com.example.user_management_service.model.Subscriber;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SubscriberSpecification {
    public static Specification<Subscriber> filterBy(final String firstName,
                                                     final String lastName,
                                                     final String email){
        return (root, query, cb)->{
            List<Predicate> predicates = new ArrayList<>();

            if(firstName!=null && !firstName.isBlank()){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("firstName")),
                                "%" + firstName.toLowerCase() + "%"
                        )
                );
            }

            if (lastName != null && !lastName.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("lastName")),
                                "%" + lastName.toLowerCase() + "%"
                        )
                );
            }

            if (email != null && !email.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("email")),
                                "%" + email.toLowerCase() + "%"
                        )
                );
            }

            if (predicates.isEmpty()) {
                return cb.conjunction();
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
