package mx.sjahl.avengers.service.marvel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MarvelComicResponseDTO extends MarvelResponseDTO<MComicDataDTO> {

    private MComicDataDTO data;

    @Override
    public MComicDataDTO getData() {
        return data;
    }

    @Override
    public void setData(MComicDataDTO data) {
        this.data = data;
    }
}
