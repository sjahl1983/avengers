package mx.sjahl.avengers.service.marvel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MComicDataDTO extends MDataDTO<MComicDTO> {

    private List<MComicDTO> results = new ArrayList<>();

    public List<MComicDTO> getResults() {
        return results;
    }

    public void setResults(List<MComicDTO> results) {
        this.results = results;
    }
}
