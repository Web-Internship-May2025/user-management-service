package com.example.user_management_service.model;
import com.example.user_management_service.model.enums.TaskStatusType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatusType status;

    @NotNull
    @Column(name = "assign_date")
    private LocalDateTime assignDate;

    @NotNull
    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;




}
