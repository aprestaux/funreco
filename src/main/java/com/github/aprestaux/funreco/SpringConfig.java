package com.github.aprestaux.funreco;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.github.aprestaux.funreco.domain.DBAction;
import com.github.aprestaux.funreco.service.RecommendationFacade;
import com.github.aprestaux.funreco.service.RecommendationFacadeMongo;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@Configuration
@Profile("default")
public class SpringConfig {
    public static final String DB_NAME = "funreco";
    private static final String DB_USER_NAME = "basilic";
    private static final String DB_PASSWORD = "basilicECM";

    @Bean
    public RecommendationFacade recommendationFacade() {
        return new RecommendationFacadeMongo();
    }

    @Bean
    public Datastore datastore() throws MongoException, UnknownHostException {
        Morphia morphia = new Morphia();

        morphia.map(DBAction.class);

        return morphia.createDatastore(new Mongo(), DB_NAME, DB_USER_NAME, DB_PASSWORD.toCharArray());
    }
}
