package br.com.fiap.powersave.model.entity;

import br.com.fiap.powersave.model.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@ToString(exclude = "addresses")
@Entity(name = "tb_person")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "registration_date", updatable = false)
    private LocalDateTime registrationDate;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "TB_PERSON_ADDRESS",
            joinColumns = @JoinColumn(name = "id_person"),
            inverseJoinColumns = @JoinColumn(name = "id_address")
    )
    private List<Address> addresses = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "TB_PERSON_APPLIANCE",
            joinColumns = @JoinColumn(name = "id_person"),
            inverseJoinColumns = @JoinColumn(name = "id_appliance")
    )
    private List<Appliance> appliances = new ArrayList<>();

    @OneToMany(mappedBy = "person")
    private Collection<Kinship> kinshipsAsPerson = new ArrayList<>();

    @OneToMany(mappedBy = "kinshipPerson")
    private Collection<Kinship> kinshipsAsKinshipPerson = new ArrayList<>();

    @PrePersist
    private void setRegistrationDate() {
        this.registrationDate = LocalDateTime.now();
    }

}