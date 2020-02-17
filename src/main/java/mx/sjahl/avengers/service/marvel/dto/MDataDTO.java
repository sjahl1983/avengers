package mx.sjahl.avengers.service.marvel.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MDataDTO<T> {

    private long total;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    abstract List<T> getResults();
    abstract void setResults(List<T> results);

}
