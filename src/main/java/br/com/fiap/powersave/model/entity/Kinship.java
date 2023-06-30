package br.com.fiap.powersave.model.entity;

import br.com.fiap.powersave.model.enums.KinshipType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "tb_kinship")
@AllArgsConstructor
@NoArgsConstructor
public class Kinship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private KinshipType kinshipType;

    private Long kinshipPersonId;

    private Long personId;

}