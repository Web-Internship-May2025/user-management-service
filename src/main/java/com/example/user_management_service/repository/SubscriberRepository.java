package com.example.user_management_service.repository;
import com.example.user_management_service.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.user_management_service.model.enums.UserRoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>, JpaSpecificationExecutor<Subscriber> {


    @Query("SELECT s FROM Subscriber s WHERE " +
            "LOWER(s.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Subscriber> findSubscribersBySearch(@Param("search") String search, Pageable pageable);
}
