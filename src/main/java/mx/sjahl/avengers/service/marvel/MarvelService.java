package mx.sjahl.avengers.service.marvel;

import mx.sjahl.avengers.exception.MarvelException;
import mx.sjahl.avengers.service.marvel.dto.MCharacterDTO;
import mx.sjahl.avengers.service.marvel.dto.MComicDTO;

import java.util.List;

public interface MarvelService {

    int limitApiMarvel = 3;
    String CHARACTERS = "characters";
    String COMICS = "comics";

    MCharacterDTO findCharacterByName(String name) throws MarvelException;

    List<MComicDTO> findComicsByCharacterId(Long characterId) throws MarvelException;

    List<MCharacterDTO> findCharactersByComicId(Long comicId) throws MarvelException;
}
