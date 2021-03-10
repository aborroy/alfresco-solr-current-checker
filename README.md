# Alfresco SOLR current checker

This project checks peridically the `current` status of a SOLR Core.

It can be used to verify that no ongoing indexing process (Metadata, Content, ACL, Cascade) are being executed in Alfresco Search Services.

## Compiling the project

Use default Maven command.

```
$ mvn clean package
```

## Configuration options

Default values can be modified in `application.properties` file.

```
$ cat src/main/resources/application.properties

# Spring Boot properties
spring.main.banner-mode=off
logging.level.org.springframework=ERROR
logging.level.org.alfresco=DEBUG
logging.pattern.console=%d{HH:mm:ss.SSS} - %msg%n

# SOLR Server URL
solr.server.url=http://localhost:8983/solr

# Consecutive cycles with "current" value equals to true
# Recommended a value greater than 1
consecutive.current.checks=2

# Period between checks in milliseconds
waiting.period.checks=30000
```

Modifying these values requires to re-compile the project.

## Running the command

Verify that SOLR Server is running and that is reachable from your computer (default URL is http://localhost:8983/solr)

Run the program from command line.

```
$ java -jar target/alfresco-solr-current-checker-0.8.0.jar
 Waiting...
 Current count: 1
 Current count: 2
 ... SOLR Core is current!
```

Once the SOLR Core is in `current` state (no updatings have been done during the latest seconds), the program will exit.
