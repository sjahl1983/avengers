package mx.sjahl.avengers.service.marvel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MCharacterDataDTO extends MDataDTO<MCharacterDTO> {

    private List<MCharacterDTO> results = new ArrayList<>();

    public List<MCharacterDTO> getResults() {
        return results;
    }

    public void setResults(List<MCharacterDTO> results) {
        this.results = results;
    }
}
