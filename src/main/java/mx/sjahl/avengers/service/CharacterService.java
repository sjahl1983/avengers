package mx.sjahl.avengers.service;

import mx.sjahl.avengers.domain.Character;
import mx.sjahl.avengers.exception.MarvelException;
import mx.sjahl.avengers.service.dto.CharacterResponseDTO;
import mx.sjahl.avengers.service.marvel.dto.MCharacterDTO;

import java.util.Optional;

/**
 * Service Interface for managing {@link mx.sjahl.avengers.domain.Character}.
 */
public interface CharacterService {

    /**
     * Resync or create Character with the name
     * @param name the name of the entity.
     * @return the entity.
     */
    Optional<Character> syncCharacter(String name) throws MarvelException;

    /**
     * get the characters interact with the character (name)
     * @param name of character
     * @return characterResponseDTO
     * @throws MarvelException
     */
    Optional<CharacterResponseDTO> getCharacters(String name) throws MarvelException;
}
