package br.com.fiap.powersave.repository;

import br.com.fiap.powersave.model.entity.Kinship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KinshipRepository extends JpaRepository<Kinship, Long> {

}
