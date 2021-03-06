package mx.sjahl.avengers.web.rest;

import mx.sjahl.avengers.exception.MarvelException;
import mx.sjahl.avengers.service.ColaboratorService;
import mx.sjahl.avengers.service.dto.CharacterResponseDTO;
import mx.sjahl.avengers.service.dto.ColaboratorResponseDTO;
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
 * REST controller for managing {@link mx.sjahl.avengers.domain.Colaborator}.
 */
@RestController
public class ColaboratorResource {

    private final Logger log = LoggerFactory.getLogger(ColaboratorResource.class);

    private final ColaboratorService colaboratorService;

    public ColaboratorResource(ColaboratorService colaboratorService) {
        this.colaboratorService = colaboratorService;
    }

    /**
     * {@code GET  /colaborators/:name} : get the "name" colaborator.
     *
     * @param name the name of the colaborator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the colaborator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/colaborators/{name}")
    public ResponseEntity<ColaboratorResponseDTO> getCharacter(@PathVariable String name) throws MarvelException {

        log.debug("REST request to get Character : {}", name);
        Optional<ColaboratorResponseDTO> characterDTO = colaboratorService.getColaborators(name);
        return ResponseUtil.wrapOrNotFound(characterDTO);
    }

}
