package br.com.fiap.powersave.model.dto;

import br.com.fiap.powersave.model.enums.KinshipType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter
@Setter
public class PersonRelationRequestDto {

    @NotNull
    private KinshipType kinshipType;

    @NotNull
    @NumberFormat
    private Long personId;

}