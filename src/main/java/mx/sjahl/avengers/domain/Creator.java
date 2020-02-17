package mx.sjahl.avengers.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Creator.
 */
@Entity
@Table(name = "creator")
public class Creator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @ManyToOne
    @JsonIgnoreProperties("creators")
    private Comic comic;

    @ManyToOne
    @JsonIgnoreProperties("creators")
    private Colaborator colaborator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Creator type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Comic getComic() {
        return comic;
    }

    public Creator comic(Comic comic) {
        this.comic = comic;
        return this;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public Colaborator getColaborator() {
        return colaborator;
    }

    public Creator colaborator(Colaborator colaborator) {
        this.colaborator = colaborator;
        return this;
    }

    public void setColaborator(Colaborator colaborator) {
        this.colaborator = colaborator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Creator)) {
            return false;
        }
        return id != null && id.equals(((Creator) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Creator{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
