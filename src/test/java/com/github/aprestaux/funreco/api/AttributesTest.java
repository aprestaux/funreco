package com.github.aprestaux.funreco.api;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author damien bourdette
 */
public class AttributesTest {
    private Attributes attributes = new Attributes();

    @Before
    public void reset() {
        attributes.clear();
    }

    @Test
    public void put() {
        attributes.put("key", "value1", "value2");

        assertThat(attributes.get("key")).containsExactly("value1", "value2");
    }
}
