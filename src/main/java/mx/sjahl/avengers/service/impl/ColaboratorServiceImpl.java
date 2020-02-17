package mx.sjahl.avengers.service.impl;

import mx.sjahl.avengers.domain.Character;
import mx.sjahl.avengers.domain.Colaborator;
import mx.sjahl.avengers.domain.Creator;
import mx.sjahl.avengers.exception.MarvelException;
import mx.sjahl.avengers.repository.CharacterRepository;
import mx.sjahl.avengers.repository.ColaboratorRepository;
import mx.sjahl.avengers.repository.CreatorRepository;
import mx.sjahl.avengers.service.CharacterService;
import mx.sjahl.avengers.service.ColaboratorService;
import mx.sjahl.avengers.service.dto.ColaboratorResponseDTO;
import mx.sjahl.avengers.util.MarvelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Colaborator}.
 */
@Service
public class ColaboratorServiceImpl implements ColaboratorService {

    private final Logger log = LoggerFactory.getLogger(ColaboratorServiceImpl.class);

    private final ColaboratorRepository colaboratorRepository;

    private final CreatorRepository creatorRepository;

    private final CharacterRepository characterRepository;

    private final CharacterService characterService;

    public ColaboratorServiceImpl(ColaboratorRepository colaboratorRepository, CreatorRepository creatorRepository, CharacterRepository characterRepository, CharacterService characterService) {
        this.colaboratorRepository = colaboratorRepository;
        this.creatorRepository = creatorRepository;
        this.characterRepository = characterRepository;
        this.characterService = characterService;
    }

    /**
     * Get all the colaborators (inkers, editors, writers...) for the character.
     *
     * @param name the name of the character.
     * @return the list of colabarators.
     */
    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public Optional<ColaboratorResponseDTO> getColaborators(String name) throws MarvelException {
        log.debug("Request to get all Colaborators");
        characterService.syncCharacter(name);

        Optional<Character> oCharacter = characterRepository.findByNameEagerRelationships(name);
        if (oCharacter.isPresent()) {
            ColaboratorResponseDTO responseDTO = new ColaboratorResponseDTO();

            List<Creator> creators = creatorRepository.findCreatorsByCharacterName(name);

            for (Creator creator : creators) {
                String key = creator.getType() + ( creator.getType().contains(" ") ? "(s)" : "s" );
                List<String> list = (List) responseDTO.get(key);
                if (list == null) {
                    list = new ArrayList<>();
                    responseDTO.put(key, list);
                }
                list.add(creator.getColaborator().getName());

            }
            responseDTO.setLastSync(MarvelUtil.getLastSyncString(oCharacter.get().getLastSync()));
            return Optional.of(responseDTO);
        }

        return Optional.empty();
    }
}
