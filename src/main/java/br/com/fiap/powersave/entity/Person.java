package br.com.fiap.powersave.entity;

import br.com.fiap.powersave.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "tb_person")
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Long id;
    private String name;
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDateTime registrationDate = LocalDateTime.now();

}