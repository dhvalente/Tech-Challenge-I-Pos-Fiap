package br.com.fiap.powersave.model.dto;

import br.com.fiap.powersave.model.entity.Address;
import br.com.fiap.powersave.model.entity.Appliance;
import br.com.fiap.powersave.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseFullDto {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private Collection<Address> addresses;
    private Collection<Appliance> appliances;
    private LocalDateTime registrationDate;

}