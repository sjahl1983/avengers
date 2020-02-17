package mx.sjahl.avengers.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Colaborator.
 */
@Entity
@Table(name = "colaborator")
public class Colaborator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Column(name = "marvel_id", nullable = false)
    private Long marvelId;

    @ManyToMany(mappedBy = "colaborators")
    @JsonIgnore
    private Set<Character> characters = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Colaborator name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMarvelId() {
        return marvelId;
    }

    public Colaborator marvelId(Long marvelId) {
        this.marvelId = marvelId;
        return this;
    }

    public void setMarvelId(Long marvelId) {
        this.marvelId = marvelId;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public Colaborator characters(Set<Character> characters) {
        this.characters = characters;
        return this;
    }

    public Colaborator addCharacters(Character character) {
        this.characters.add(character);
        character.getColaborators().add(this);
        return this;
    }

    public Colaborator removeCharacters(Character character) {
        this.characters.remove(character);
        character.getColaborators().remove(this);
        return this;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colaborator)) {
            return false;
        }
        return id != null && id.equals(((Colaborator) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Colaborator{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", marvelId=" + getMarvelId() +
            "}";
    }
}
