package br.com.fiap.powersave.model.dto;

import br.com.fiap.powersave.model.enums.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
public class PersonRequestDto {

    @NotBlank(message = "{name.required}")
    private String name;

    @NotNull(message = "{gender.required}")
    private Gender gender;

    @NotNull(message = "{birthDate.required}")
    private LocalDate birthDate;

    @Valid
    private Collection<PersonRelationRequestDto> personRelations = Collections.emptyList();

}