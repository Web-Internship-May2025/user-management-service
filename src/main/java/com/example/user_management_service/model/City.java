package com.example.user_management_service.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "is_deleted", nullable = false , columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;


}
