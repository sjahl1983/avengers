package mx.sjahl.avengers.repository;

import mx.sjahl.avengers.domain.Character;
import mx.sjahl.avengers.domain.Comic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Character entity.
 */
@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    @Query(value = "select distinct character from Character character left join fetch character.colaborators left join fetch character.comics",
        countQuery = "select count(distinct character) from Character character")
    Page<Character> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct character from Character character left join fetch character.colaborators left join fetch character.comics")
    List<Character> findAllWithEagerRelationships();

    @Query("select character from Character character left join fetch character.colaborators left join fetch character.comics where character.id =:id")
    Optional<Character> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select character from Character character left join fetch character.colaborators left join fetch character.comics where character.name =:name")
    Optional<Character> findByNameEagerRelationships(@Param("name") String name);

    @Query("select distinct character from Character character" +
        " left join fetch character.comics c" +
        " where c in (:comics)")
    List<Character> findCharactersFetchComicsBy(@Param("comics") List<Comic> comics);
}
