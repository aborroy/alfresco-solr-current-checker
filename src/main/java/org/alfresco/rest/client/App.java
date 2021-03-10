package org.alfresco.rest.client;

import org.alfresco.rest.client.rest.SolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class App implements CommandLineRunner {

    static final Logger LOG = LoggerFactory.getLogger(App.class);

    @Autowired
    SolrClient solrClient;

    @Value("${consecutive.current.checks}")
    Integer checksGoal;

    // Count of consecutive checks with value "true"
    int checksCount = 0;

    @Scheduled(fixedRateString = "${waiting.period.checks}")
    public void checkCurrent() throws Exception {
        checksCount = (solrClient.isCurrent() ? (checksCount + 1) : 0);
        LOG.debug("Current count: {}", checksCount);
        if (checksCount == checksGoal) {
            LOG.info("... SOLR Core is current!");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        LOG.info("Waiting...");
    }
}
