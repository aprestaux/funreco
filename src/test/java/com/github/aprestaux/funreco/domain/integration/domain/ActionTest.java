package com.github.aprestaux.funreco.domain.integration.domain;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.github.aprestaux.funreco.SpringConfig;
import com.github.aprestaux.funreco.domain.Action;
import com.google.code.morphia.Datastore;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class ActionTest {
    @Configuration
    @Import(SpringConfig.class)
    static class ContextConfiguration {

    }

    @Inject
    private Datastore datastore;

    @Before
    public void clean() {
        datastore.delete(datastore.find(Action.class));
    }

    @Test
    public void save() {
        datastore.save(new Action());

        assertThat(datastore.find(Action.class).countAll()).isEqualTo(1);
    }
}
