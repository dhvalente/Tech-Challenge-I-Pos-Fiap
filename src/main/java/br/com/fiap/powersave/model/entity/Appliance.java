package br.com.fiap.powersave.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "tb_appliance")
@AllArgsConstructor
@NoArgsConstructor
public class Appliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_appliance")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "potency")
    private Integer potency;

    @ManyToMany(mappedBy = "appliances")
    private List<Person> persons = new ArrayList<>();

    @Column(name = "registration_date", updatable = false)
    private LocalDateTime registrationDate;

    @PrePersist
    private void setRegistrationDate() {
        this.registrationDate = LocalDateTime.now();
    }


}
