package mx.sjahl.avengers.service.marvel.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MarvelResponseDTO<T> {

    private Integer code;
    private String status;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract T getData();

    public abstract void setData(T data);
}
