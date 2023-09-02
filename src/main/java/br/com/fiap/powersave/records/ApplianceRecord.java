package br.com.fiap.powersave.records;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ApplianceRecord (

    @NotEmpty(message = "{name.required}") String name,
    @NotEmpty(message = "{model.required}") String model,
    @NotNull(message = "{potency.required}") Integer potency) {

}