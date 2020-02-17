package mx.sjahl.avengers.repository;

import mx.sjahl.avengers.domain.Colaborator;
import mx.sjahl.avengers.domain.Comic;
import mx.sjahl.avengers.domain.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Creator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {

    List<Creator> findByComic(Comic comic);

    @Query("select distinct c from Creator c" +
            " left join fetch c.colaborator co" +
            " left join co.characters ch" +
            " where ch.name = :name")
    List<Creator> findCreatorsByCharacterName(@Param("name") String name);
}
