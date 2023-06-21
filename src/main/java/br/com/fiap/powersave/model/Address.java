package br.com.fiap.powersave.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
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
    private String zipcode;

    public Address(String street, String number, String city, String district, String state, String zipcode) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.district = district;
        this.state = state;
        this.zipcode = zipcode;
    }
}