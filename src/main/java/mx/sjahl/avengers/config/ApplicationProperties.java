package mx.sjahl.avengers.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to Avengers.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final CorsConfiguration cors = new CorsConfiguration();

    public CorsConfiguration getCors() {
        return this.cors;
    }
}
