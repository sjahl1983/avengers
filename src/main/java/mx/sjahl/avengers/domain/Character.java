package mx.sjahl.avengers.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Character.
 */
@Entity
@Table(name = "av_character")
public class Character implements Serializable {

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

    @NotNull
    @Column(name = "last_sync", nullable = false)
    private Instant lastSync;

    @ManyToMany
    @JoinTable(name = "av_character_colaborators",
               joinColumns = @JoinColumn(name = "character_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "colaborators_id", referencedColumnName = "id"))
    private Set<Colaborator> colaborators = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "av_character_comics",
               joinColumns = @JoinColumn(name = "character_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "comics_id", referencedColumnName = "id"))
    private Set<Comic> comics = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Character name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMarvelId() {
        return marvelId;
    }

    public Character marvelId(Long marvelId) {
        this.marvelId = marvelId;
        return this;
    }

    public void setMarvelId(Long marvelId) {
        this.marvelId = marvelId;
    }

    public Instant getLastSync() {
        return lastSync;
    }

    public Character lastSync(Instant lastSync) {
        this.lastSync = lastSync;
        return this;
    }

    public void setLastSync(Instant lastSync) {
        this.lastSync = lastSync;
    }

    public Set<Colaborator> getColaborators() {
        return colaborators;
    }

    public Character colaborators(Set<Colaborator> colaborators) {
        this.colaborators = colaborators;
        return this;
    }

    public Character addColaborators(Colaborator colaborator) {
        this.colaborators.add(colaborator);
        colaborator.getCharacters().add(this);
        return this;
    }

    public Character removeColaborators(Colaborator colaborator) {
        this.colaborators.remove(colaborator);
        colaborator.getCharacters().remove(this);
        return this;
    }

    public void setColaborators(Set<Colaborator> colaborators) {
        this.colaborators = colaborators;
    }

    public Set<Comic> getComics() {
        return comics;
    }

    public Character comics(Set<Comic> comics) {
        this.comics = comics;
        return this;
    }

    public Character addComics(Comic comic) {
        this.comics.add(comic);
        comic.getCharacters().add(this);
        return this;
    }

    public Character removeComics(Comic comic) {
        this.comics.remove(comic);
        comic.getCharacters().remove(this);
        return this;
    }

    public void setComics(Set<Comic> comics) {
        this.comics = comics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Character)) {
            return false;
        }
        return id != null && id.equals(((Character) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Character{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", marvelId=" + getMarvelId() +
            ", lastSync='" + getLastSync() + "'" +
            "}";
    }
}
