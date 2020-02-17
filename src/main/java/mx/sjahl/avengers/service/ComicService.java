package mx.sjahl.avengers.service;

import mx.sjahl.avengers.domain.Comic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Comic}.
 */
public interface ComicService {

    /**
     * Save a comic.
     *
     * @param comic the entity to save.
     * @return the persisted entity.
     */
    Comic save(Comic comic);

    /**
     * Get all the comics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Comic> findAll(Pageable pageable);


    /**
     * Get the "id" comic.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Comic> findOne(Long id);

    /**
     * Delete the "id" comic.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
