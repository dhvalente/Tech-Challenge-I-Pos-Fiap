package br.com.fiap.powersave.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplianceResponseSimpleDto {

    private Long id;
    private String name;
    private String model;
    private Integer potency;

}