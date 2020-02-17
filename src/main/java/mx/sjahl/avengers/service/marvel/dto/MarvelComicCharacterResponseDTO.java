package mx.sjahl.avengers.service.marvel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarvelComicCharacterResponseDTO extends MarvelResponseDTO<MComicCharacterDTO> {

    private MComicCharacterDTO data;

    @Override
    public MComicCharacterDTO getData() {
        return data;
    }

    @Override
    public void setData(MComicCharacterDTO data) {
        this.data = data;
    }
}
