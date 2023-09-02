package br.com.fiap.powersave.model.dto;

import br.com.fiap.powersave.model.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplianceResponseFullDto {

    private Long id;
    private String name;
    private String model;
    private Integer potency;
    private Collection<PersonResponseSimpleDto> persons = new ArrayList<>();
    private LocalDateTime registrationDate;

}