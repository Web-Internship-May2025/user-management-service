package com.example.user_management_service.repository;

import com.example.user_management_service.model.Zip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipRepository extends JpaRepository<Zip, Long> {
}
