package mx.sjahl.avengers.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.sjahl.avengers.AvengersApp;
import mx.sjahl.avengers.domain.Character;
import mx.sjahl.avengers.repository.CharacterRepository;
import mx.sjahl.avengers.repository.ColaboratorRepository;
import mx.sjahl.avengers.repository.ComicRepository;
import mx.sjahl.avengers.repository.CreatorRepository;
import mx.sjahl.avengers.service.CharacterService;
import mx.sjahl.avengers.service.dto.CharacterResponseDTO;
import mx.sjahl.avengers.service.impl.CharacterServiceImpl;
import mx.sjahl.avengers.service.marvel.MarvelService;
import mx.sjahl.avengers.service.marvel.dto.MarvelCharacterResponseDTO;
import mx.sjahl.avengers.service.marvel.dto.MarvelComicCharacterResponseDTO;
import mx.sjahl.avengers.service.marvel.dto.MarvelComicResponseDTO;
import mx.sjahl.avengers.service.marvel.impl.MarvelServiceImpl;
import mx.sjahl.avengers.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Optional;

import static mx.sjahl.avengers.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@Link CharacterResource} REST controller.
 */
@SpringBootTest(classes = AvengersApp.class)
public class CharacterResourceIT {

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCharacterMockMvc;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ColaboratorRepository colaboratorRepository;

    @Autowired
    private CreatorRepository creatorRepository;

    private CharacterService characterService;

    @BeforeEach
    public void setup() throws IOException {
        MarvelService marvelService = new MarvelServiceImpl(restTemplate(),
                "http://localhost", "public", "private");

        characterService = new CharacterServiceImpl(marvelService, characterRepository,
                comicRepository, colaboratorRepository, creatorRepository);
        //characterService = mock(CharacterServiceImpl.class);

        MockitoAnnotations.initMocks(this);
        final CharacterResource characterResource = new CharacterResource(characterService);
        this.restCharacterMockMvc = MockMvcBuilders.standaloneSetup(characterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @Test
    @Transactional
    public void getCharacter() throws Exception {
        characterService.syncCharacter("iron man");

        Optional<CharacterResponseDTO> characterResponseDTO = characterService.getCharacters("iron man");

        restCharacterMockMvc.perform(get("/characters/{name}", "iron man"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Character.class);
        Character character1 = new Character();
        character1.setId(1L);
        Character character2 = new Character();
        character2.setId(character1.getId());
        assertThat(character1).isEqualTo(character2);
        character2.setId(2L);
        assertThat(character1).isNotEqualTo(character2);
        character1.setId(null);
        assertThat(character1).isNotEqualTo(character2);
    }


    public RestTemplate restTemplate() throws IOException {

        RestTemplate mock = Mockito.mock(RestTemplate.class);

        when(mock.getForEntity(any(URI.class), eq(MarvelCharacterResponseDTO.class)))
                .thenReturn(new ResponseEntity<>(makeMarvelCharacterResponseDTO(), HttpStatus.OK));

        when(mock.getForEntity(anyString(), eq(MarvelComicResponseDTO.class)))
                .thenReturn(new ResponseEntity<>(makeMarvelComicResponseDTO(), HttpStatus.OK));

        when(mock.getForEntity(anyString(), eq(MarvelComicCharacterResponseDTO.class)))
                .thenReturn(new ResponseEntity<>(makeMarvelComicCharacterResponseDTO(), HttpStatus.OK));

        return mock;
    }

    private MarvelCharacterResponseDTO makeMarvelCharacterResponseDTO() throws IOException {
        InputStream inJson = MarvelCharacterResponseDTO.class
                .getResourceAsStream("/marvelCharacterResponse.json");
        return new ObjectMapper().readValue(inJson, MarvelCharacterResponseDTO.class);
    }

    private MarvelComicResponseDTO makeMarvelComicResponseDTO() throws IOException {
        InputStream inJson = MarvelComicResponseDTO.class
                .getResourceAsStream("/marvelComicResponse.json");
        return new ObjectMapper().readValue(inJson, MarvelComicResponseDTO.class);

    }

    private MarvelComicCharacterResponseDTO makeMarvelComicCharacterResponseDTO() throws IOException {
        InputStream inJson = MarvelComicCharacterResponseDTO.class
                .getResourceAsStream("/marvelComicCharacterResponse.json");
        return new ObjectMapper().readValue(inJson, MarvelComicCharacterResponseDTO.class);

    }
}
