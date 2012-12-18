package com.github.aprestaux.funreco;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.github.aprestaux.funreco.domain.DBAction;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * Only loaded when profile is set to "integration"
 */
@Configuration
@Profile("integration")
public class IntegrationSpringConfig {
    public static final String DB_NAME = "funreco-test";

    @Bean
    public Datastore datastore() throws MongoException, UnknownHostException {
        Morphia morphia = new Morphia();

        morphia.map(DBAction.class);

        return morphia.createDatastore(mongo(), DB_NAME);
    }

    @Bean
    public Mongo mongo() throws UnknownHostException {
        return new Mongo();
    }
}
