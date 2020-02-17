package mx.sjahl.avengers.repository;

import mx.sjahl.avengers.domain.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Comic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComicRepository extends JpaRepository<Comic, Long> {

    @Query("select c from Comic c left join fetch c.characters  where c.marvelId =:marvelId")
    Optional<Comic> findByMarvelId(@Param("marvelId") Long marvelId);

    @Query("select c from Comic c left join fetch c.characters ch where ch.name =:name")
    List<Comic> findByCharacterName(@Param("name") String name);
}
