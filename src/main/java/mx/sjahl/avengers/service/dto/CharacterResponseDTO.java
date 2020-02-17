package mx.sjahl.avengers.service.dto;

import java.util.ArrayList;
import java.util.List;

public class CharacterResponseDTO {

    private String lastSync;
    private List<Character> characters = new ArrayList<>();

    public String getLastSync() {
        return lastSync;
    }

    public void setLastSync(String lastSync) {
        this.lastSync = lastSync;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public static class Character {

        private String character;
        private List<String> comics;

        public String getCharacter() {
            return character;
        }

        public void setCharacter(String character) {
            this.character = character;
        }

        public List<String> getComics() {
            return comics;
        }

        public void setComics(List<String> comics) {
            this.comics = comics;
        }
    }
}
