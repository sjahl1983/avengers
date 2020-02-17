package mx.sjahl.avengers.service;

import mx.sjahl.avengers.domain.Colaborator;
import mx.sjahl.avengers.exception.MarvelException;
import mx.sjahl.avengers.service.dto.ColaboratorResponseDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link Colaborator}.
 */
public interface ColaboratorService {

    /**
     * Get all the colaborators.
     *
     * @param name the name of the character.
     * @return the list of colabarators.
     */
    Optional<ColaboratorResponseDTO> getColaborators(String name) throws MarvelException;
}
