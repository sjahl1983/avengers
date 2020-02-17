package mx.sjahl.avengers.web.rest;

import mx.sjahl.avengers.AvengersApp;
import mx.sjahl.avengers.domain.Colaborator;
import mx.sjahl.avengers.repository.ColaboratorRepository;
import mx.sjahl.avengers.service.ColaboratorService;
import mx.sjahl.avengers.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static mx.sjahl.avengers.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ColaboratorResource} REST controller.
 */
@SpringBootTest(classes = AvengersApp.class)
public class ColaboratorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_MARVEL_ID = 1L;
    private static final Long UPDATED_MARVEL_ID = 2L;

    @Autowired
    private ColaboratorRepository colaboratorRepository;

    @Autowired
    private ColaboratorService colaboratorService;

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

    private MockMvc restColaboratorMockMvc;

    private Colaborator colaborator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColaboratorResource colaboratorResource = new ColaboratorResource(colaboratorService);
        this.restColaboratorMockMvc = MockMvcBuilders.standaloneSetup(colaboratorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Colaborator.class);
        Colaborator colaborator1 = new Colaborator();
        colaborator1.setId(1L);
        Colaborator colaborator2 = new Colaborator();
        colaborator2.setId(colaborator1.getId());
        assertThat(colaborator1).isEqualTo(colaborator2);
        colaborator2.setId(2L);
        assertThat(colaborator1).isNotEqualTo(colaborator2);
        colaborator1.setId(null);
        assertThat(colaborator1).isNotEqualTo(colaborator2);
    }
}
