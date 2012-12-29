package com.github.aprestaux.funreco;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.github.aprestaux.funreco.service.RecommandationFacadeStub;
import com.github.aprestaux.funreco.service.RecommendationFacade;

/**
 * Only loaded when profile is set to "functional"
 * This profile is used for functional tests.
 */
@Configuration
@Profile("functional")
public class FunctionalSpringConfig {
    @Bean
    public RecommendationFacade recommendationFacade() {
        return new RecommandationFacadeStub();
    }
}
