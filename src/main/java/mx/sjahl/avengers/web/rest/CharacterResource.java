package mx.sjahl.avengers.web.rest;

import mx.sjahl.avengers.exception.MarvelException;
import mx.sjahl.avengers.service.CharacterService;
import mx.sjahl.avengers.service.dto.CharacterResponseDTO;
import mx.sjahl.avengers.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller for managing {@link mx.sjahl.avengers.domain.Character}.
 */
@RestController
public class CharacterResource {

    private final Logger log = LoggerFactory.getLogger(CharacterResource.class);

    private final CharacterService characterService;

    public CharacterResource(CharacterService characterService) {
        this.characterService = characterService;
    }

    /**
     * {@code GET  /characters/:name} : get the "name" character.
     *
     * @param name the name of the character to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the character, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/characters/{name}")
    public ResponseEntity<CharacterResponseDTO> getCharacter(@PathVariable String name) {
        log.debug("REST request to get Character : {}", name);
        try {
            Optional<CharacterResponseDTO> characterDTO = characterService.getCharacters(name);
            return ResponseUtil.wrapOrNotFound(characterDTO);
        } catch (MarvelException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
