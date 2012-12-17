package com.github.aprestaux.funreco;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.aprestaux.funreco.domain.DBAction;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@Configuration
public class SpringConfig {
    public static final String DB_NAME = "funreco";

    @Bean
    public Datastore datastore() throws MongoException, UnknownHostException {
        Morphia morphia = new Morphia();

        morphia.map(DBAction.class);

        return morphia.createDatastore(new Mongo(), DB_NAME);
    }
}
