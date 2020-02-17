package mx.sjahl.avengers.repository;

import mx.sjahl.avengers.domain.Character;
import mx.sjahl.avengers.domain.Colaborator;
import mx.sjahl.avengers.domain.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Colaborator entity.
 */
@Repository
public interface ColaboratorRepository extends JpaRepository<Colaborator, Long> {

    Optional<Colaborator> findByMarvelId(Long id);
}
