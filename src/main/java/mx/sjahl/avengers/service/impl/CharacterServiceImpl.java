package mx.sjahl.avengers.service.impl;

import mx.sjahl.avengers.domain.Character;
import mx.sjahl.avengers.domain.Colaborator;
import mx.sjahl.avengers.domain.Comic;
import mx.sjahl.avengers.domain.Creator;
import mx.sjahl.avengers.exception.MarvelException;
import mx.sjahl.avengers.repository.CharacterRepository;
import mx.sjahl.avengers.repository.ColaboratorRepository;
import mx.sjahl.avengers.repository.ComicRepository;
import mx.sjahl.avengers.repository.CreatorRepository;
import mx.sjahl.avengers.service.CharacterService;
import mx.sjahl.avengers.service.dto.CharacterResponseDTO;
import mx.sjahl.avengers.service.marvel.MarvelService;
import mx.sjahl.avengers.service.marvel.dto.MCharacterDTO;
import mx.sjahl.avengers.service.marvel.dto.MComicDTO;
import mx.sjahl.avengers.service.marvel.dto.MCreatorDTO;
import mx.sjahl.avengers.util.MarvelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Character}.
 */
@Service
public class CharacterServiceImpl implements CharacterService {

    private final Logger log = LoggerFactory.getLogger(CharacterServiceImpl.class);

    private final MarvelService marvelService;

    private final CharacterRepository characterRepository;

    private final ComicRepository comicRepository;

    private final ColaboratorRepository colaboratorRepository;

    private final CreatorRepository creatorRepository;

    public CharacterServiceImpl(MarvelService marvelService, CharacterRepository characterRepository, ComicRepository comicRepository, ColaboratorRepository colaboratorRepository, CreatorRepository creatorRepository) {
        this.marvelService = marvelService;
        this.characterRepository = characterRepository;
        this.comicRepository = comicRepository;
        this.colaboratorRepository = colaboratorRepository;
        this.creatorRepository = creatorRepository;
    }

    /**
     * sync Character with the name. if the character last sync less than ten minutes then return saved data
     * @param name of character
     * @return the entity.
     */
    @Override
    @Transactional
    public Optional<Character> syncCharacter(String name) throws MarvelException {

        Instant resync = Instant.now().minus(15, ChronoUnit.MINUTES);

        Optional<Character> oCharacter = characterRepository.findByNameEagerRelationships(name);

        if (oCharacter.isPresent()) {
            if (oCharacter.get().getLastSync().isAfter(resync)) {
                return oCharacter;
            } else {

                Character character = oCharacter.get();
                character.setComics(new HashSet<>());

                return syncAllData(oCharacter.get());
            }
        } else {
            MCharacterDTO mCharacterDTO = marvelService.findCharacterByName(name);
            if (mCharacterDTO == null) {
                return Optional.empty();
            }
            else {
                Character character = createCharacter(mCharacterDTO);
                return syncAllData(character);
            }
        }
    }


    /**
     * Create character with mCharacterDTO.
     *
     * @param mCharacterDTO the mCharacterDTO.
     * @return the entity.
     */
    private Character createCharacter(MCharacterDTO mCharacterDTO) {

        Character character = new Character();
        character.setName(mCharacterDTO.getName());
        character.setMarvelId(mCharacterDTO.getId());
        character.setLastSync(Instant.now());

        return characterRepository.save(character);
    }

    /**
     *
     * @param character the character for the entity
     * @param mComic the mComic
     * @return comic
     */
    private Comic createComic(Character character, MComicDTO mComic) {
        Comic comic = new Comic();
        comic.setName(mComic.getTitle());
        comic.setMarvelId(mComic.getId());
        comic.addCharacters(character);
        return comicRepository.save(comic);
    }

    /**
     * Sync all data for character
     * @param character character to sync
     * @return character
     * @throws MarvelException
     */
    private Optional<Character> syncAllData(Character character) throws MarvelException {

        List<MComicDTO> mComics = marvelService.findComicsByCharacterId(character.getMarvelId());
        for (MComicDTO mComic : mComics) {
            Optional<Comic> oComic = comicRepository.findByMarvelId(mComic.getId());
            Comic comic;
            if (!oComic.isPresent()) {
                comic = createComic(character, mComic);
            } else {
                comic = oComic.get();
                character.getComics().add(comic);
            }
            syncComicCharacters(comic);
            syncCreators(comic, character, mComic);
        }

        character.setLastSync(Instant.now());
        character = characterRepository.save(character);
        return Optional.of(character);
    }

    /**
     *  Sync creators for character
     *
     * @param comic the comic for the entity
     * @param character the character for the entity
     * @param mComic the mComic for creator
     */
    private void syncCreators(Comic comic, Character character, MComicDTO mComic) {

        if (mComic.getCreators() != null) {
            List<Creator> remCreators = new ArrayList<>();
            List<Creator> creators = creatorRepository.findByComic(comic);
            main: for (int i = 0; i < mComic.getCreators().getAvailable(); i++) {
                MCreatorDTO mCreatorDTO = mComic.getCreators().getItems().get(i);
                Long colaboratorId = MarvelUtil.getIdFromUri(mCreatorDTO.getResourceURI());
                log.info("colaboratorId: {}", colaboratorId);
                if (colaboratorId != null) {
                    Optional<Colaborator> oColaborator = colaboratorRepository.findByMarvelId(colaboratorId);
                    Colaborator colaborator;
                    if (!oColaborator.isPresent()) {
                        colaborator = createColaborator(character, mCreatorDTO.getName(), colaboratorId);
                    } else {
                        colaborator = oColaborator.get();
                        character.getColaborators().add(colaborator);
                    }
                    for (Creator creator : creators) {

                        if (creator.getColaborator().getMarvelId().equals(colaboratorId)) {
                            creators.remove(creator);
                            continue main;
                        }
                    }
                    createCreator(comic, colaborator, mCreatorDTO.getRole());
                }
            }
            main: for (Creator creator : creators) {
                for (int i = 0; i < mComic.getCreators().getAvailable(); i++) {

                    MCreatorDTO mCreatorDTO = mComic.getCreators().getItems().get(i);
                    Long colaboratorId = MarvelUtil.getIdFromUri(mCreatorDTO.getResourceURI());
                    if (creator.getColaborator().getMarvelId().equals(colaboratorId)) {
                        continue main;
                    }
                }
                remCreators.add(creator);
            }
            creatorRepository.deleteAll(remCreators);
        }
    }

    /**
     *
     * @param character the character for the entity
     * @param name the name of colaborator
     * @param colaboratorId the marvel_id of colaborator
     * @return colaborator
     */
    private Colaborator createColaborator(Character character, String name, Long colaboratorId) {
        Colaborator colaborator = new Colaborator();
        colaborator.setName(name);
        colaborator.setMarvelId(colaboratorId);
        colaborator.addCharacters(character);
        return colaboratorRepository.save(colaborator);
    }

    /**
     * create the creator depends of role
     *
     * @param comic the comic for the entity
     * @param colaborator the colaborator for the entity
     * @param type type o role of Creator
     */
    private void createCreator(Comic comic, Colaborator colaborator, String type) {
        Creator creator = new Creator();
        creator.setType(type);
        creator.setColaborator(colaborator);
        creator.setComic(comic);
        creatorRepository.save(creator);
    }

    /**
     * Sync the character of  the comic
     * @param comic for shearch characters
     * @throws MarvelException
     */
    private void syncComicCharacters(Comic comic) throws MarvelException {
        List<MCharacterDTO> mCharacters = marvelService.findCharactersByComicId(comic.getMarvelId());
        main: for (MCharacterDTO mCharacter : mCharacters) {
            for (Character character : comic.getCharacters()) {
                if (character.getMarvelId().equals(mCharacter.getId())) {
                    continue main;
                }
            }
            Optional<Character> oCharacter = characterRepository.findByNameEagerRelationships(mCharacter.getName());
            Character character;
            if (!oCharacter.isPresent()) {
                character = createCharacter(mCharacter);
            } else {
                character = oCharacter.get();
                character.setLastSync(Instant.now());
            }
            comic.addCharacters(character);
            characterRepository.save(character);
        }
    }

    /**
     * get the characters interact with the character (name)
     * @param name of character
     * @return characterResponseDTO
     * @throws MarvelException
     */
    @Override
    @Transactional
    public Optional<CharacterResponseDTO> getCharacters(String name) throws MarvelException {
        syncCharacter(name);

        Optional<Character> oCharacter = characterRepository.findByNameEagerRelationships(name);
        if (oCharacter.isPresent()) {

            List<Comic> comics = comicRepository.findByCharacterName(name);

            List<Character> characters = characterRepository.findCharactersFetchComicsBy(comics);
            CharacterResponseDTO responseDTO = new CharacterResponseDTO();
            responseDTO.setLastSync(MarvelUtil.getLastSyncString(oCharacter.get().getLastSync()));

            for (Character character : characters) {
                CharacterResponseDTO.Character ch = new CharacterResponseDTO.Character();
                ch.setCharacter(character.getName());
                responseDTO.getCharacters().add(ch);

                List<String> lComics = character.getComics().stream().map(Comic::getName).collect(Collectors.toList());
                ch.setComics(lComics);
            }

            return Optional.of(responseDTO);
        }

        return Optional.empty();
    }

}
