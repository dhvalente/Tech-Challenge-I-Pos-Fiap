package br.com.fiap.powersave.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_address")
public class Address{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Long id;
    private String street;
    private String number;
    private String city;
    private String district;
    private String state;

    public Address(String street, String number, String city, String district, String state) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.district = district;
        this.state = state;
    }
}