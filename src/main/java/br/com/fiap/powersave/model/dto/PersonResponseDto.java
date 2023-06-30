package br.com.fiap.powersave.model.dto;

import br.com.fiap.powersave.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponseDto {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private LocalDateTime registrationDate;

}