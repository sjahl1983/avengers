package mx.sjahl.avengers.service.marvel.impl;

import mx.sjahl.avengers.exception.MarvelException;
import mx.sjahl.avengers.service.marvel.MarvelService;
import mx.sjahl.avengers.service.marvel.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Component
public class MarvelServiceImpl implements MarvelService {

    private String endpoint;

    private String publicKey;

    private String privateKey;

    private final RestTemplate restTemplate;

    public MarvelServiceImpl(RestTemplate restTemplate,
                             @Value("${marvel.endpoint}") String endpoint,
                             @Value("${marvel.public-key}") String publicKey,
                             @Value("${marvel.private-key}") String privateKey) {
        this.restTemplate = restTemplate;
        this.endpoint = endpoint;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public MCharacterDTO findCharacterByName(String name) throws MarvelException {

        UriComponentsBuilder uriBuilder = uriBuilder(endpoint + CHARACTERS);
        uriBuilder.queryParam("name", name);

        ResponseEntity<MarvelCharacterResponseDTO> entityResponse = restTemplate.getForEntity(URI.create(uriBuilder.toUriString()), MarvelCharacterResponseDTO.class);

        if (entityResponse.getStatusCode() == HttpStatus.OK && entityResponse.getBody() != null &&
            entityResponse.getBody().getData() != null && entityResponse.getBody().getData().getTotal() > 0) {
            return entityResponse.getBody().getData().getResults().get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<MComicDTO> findComicsByCharacterId(Long characterId) throws MarvelException {

        UriComponentsBuilder uriBuilder = uriBuilder(endpoint + CHARACTERS + "/" + characterId + "/" + COMICS);
        uriBuilder.queryParam("limit", limitApiMarvel);

        ResponseEntity<MarvelComicResponseDTO> entityResponse = restTemplate.getForEntity(uriBuilder.toUriString(), MarvelComicResponseDTO.class);

        if (entityResponse.getStatusCode() == HttpStatus.OK && entityResponse.getBody() != null &&
            entityResponse.getBody().getData() != null && entityResponse.getBody().getData().getTotal() > 0) {
            return entityResponse.getBody().getData().getResults();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<MCharacterDTO> findCharactersByComicId(Long comicId) throws MarvelException {

        UriComponentsBuilder uriBuilder = uriBuilder(endpoint + COMICS + "/" + comicId + "/" + CHARACTERS);

        ResponseEntity<MarvelComicCharacterResponseDTO> entityResponse = restTemplate.getForEntity(uriBuilder.toUriString(), MarvelComicCharacterResponseDTO.class);

        if (entityResponse.getStatusCode() == HttpStatus.OK && entityResponse.getBody() != null &&
            entityResponse.getBody().getData() != null && entityResponse.getBody().getData().getTotal() > 0) {
            return entityResponse.getBody().getData().getResults();
        } else {
            return Collections.emptyList();
        }
    }

    private UriComponentsBuilder uriBuilder(String url) throws MarvelException {

        Long timeInMillis = Calendar.getInstance().getTimeInMillis();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("apikey", publicKey)
            .queryParam("ts", timeInMillis.toString())
            .queryParam("hash", md5Hash(timeInMillis + privateKey + publicKey));

        return builder;
    }

    private String md5Hash(String value) throws MarvelException {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new MarvelException(e);
        }
    }
}
