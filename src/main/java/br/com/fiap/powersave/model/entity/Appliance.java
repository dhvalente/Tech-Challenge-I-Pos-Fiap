package br.com.fiap.powersave.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity(name = "tb_appliance")
@AllArgsConstructor
@NoArgsConstructor
public class Appliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String model;
    private Integer potency;
    private LocalDateTime registrationDate;

    @PrePersist
    private void setRegistrationDate(){
        setRegistrationDate(LocalDateTime.now());
    }

}
