package mx.sjahl.avengers.service.marvel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MCreatorDataDTO {

    private long available;
    private List<MCreatorDTO> items;

    public long getAvailable() {
        return available;
    }

    public void setAvailable(long available) {
        this.available = available;
    }

    public List<MCreatorDTO> getItems() {
        return items;
    }

    public void setItems(List<MCreatorDTO> items) {
        this.items = items;
    }
}
