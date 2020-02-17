package mx.sjahl.avengers.service.marvel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarvelCharacterResponseDTO extends MarvelResponseDTO<MCharacterDataDTO> {

    private MCharacterDataDTO data;

    public MCharacterDataDTO getData() {
        return data;
    }

    public void setData(MCharacterDataDTO data) {
        this.data = data;
    }
}
