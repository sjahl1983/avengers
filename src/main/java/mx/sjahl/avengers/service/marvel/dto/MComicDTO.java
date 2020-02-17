package mx.sjahl.avengers.service.marvel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MComicDTO {

    private Long id;
    private String title;
    private MCreatorDataDTO creators;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MCreatorDataDTO getCreators() {
        return creators;
    }

    public void setCreators(MCreatorDataDTO creators) {
        this.creators = creators;
    }
}
