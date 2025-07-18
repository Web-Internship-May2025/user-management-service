package com.example.user_management_service.model;
import jakarta.persistence.*;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("DRIVER")
public class Driver extends User {

   @NotNull
   @Column(name = "licence_num", nullable = false, unique = true)
   private String licenceNum;

   @NotNull
   @Column(name = "licence_obtained", nullable = false)
   private String licenceObtained;

   @NotNull
   @Column(name = "years_insured", nullable = false)
   private Integer yearsInsured;

   @ManyToMany
   @JoinTable(
           name = "driver_risk",
           joinColumns = @JoinColumn(name = "driver_id"),
           inverseJoinColumns = @JoinColumn(name = "risk_id")
   )
   private List<Risk> risk;


}
