package mx.sjahl.avengers.service;

import mx.sjahl.avengers.domain.Creator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Creator}.
 */
public interface CreatorService {

    /**
     * Save a creator.
     *
     * @param creator the entity to save.
     * @return the persisted entity.
     */
    Creator save(Creator creator);

    /**
     * Get all the creators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Creator> findAll(Pageable pageable);


    /**
     * Get the "id" creator.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Creator> findOne(Long id);

    /**
     * Delete the "id" creator.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
