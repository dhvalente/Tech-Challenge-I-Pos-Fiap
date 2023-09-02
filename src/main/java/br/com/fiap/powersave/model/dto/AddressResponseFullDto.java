package br.com.fiap.powersave.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseFullDto {

    private Long id;
    private String type;
    private String street;
    private String number;
    private String city;
    private String district;
    private String state;
    private Collection<PersonResponseSimpleDto> persons;

}