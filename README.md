# avengers

This application was create ussing the [Marvel API][].

This is a "microservice" application intended to be part of a microservice architecture

This application is configured for Service Discovery and Configuration with . On launch, it will refuse to start if it is not able to connect to .

## Development

To start your application in the dev profile, simply run:

    ./mvnw

## Building for production

### Packaging as jar

To build the final jar and optimize the avengers application for production, run:

    ./mvnw -Pprod clean verify

To ensure everything worked, run:

    java -jar target/*.jar

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./mvnw -Pprod,war clean verify

## Testing

To launch your application's tests, run:

    ./mvnw verify

### Code quality

Sonar is used to analyse code quality

You can run a Sonar analysis with using the [sonar-scanner][] or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```


[marvel api]: https://developer.marvel.com/
[sonar-scanner]: https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner