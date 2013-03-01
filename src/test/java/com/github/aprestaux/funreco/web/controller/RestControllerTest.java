package com.github.aprestaux.funreco.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.aprestaux.funreco.api.Attributes;
import com.github.aprestaux.funreco.service.ProfileNotFoundException;
import com.github.aprestaux.funreco.service.RecommendationFacade;
import com.github.aprestaux.funreco.utils.TestData;

import static com.github.aprestaux.funreco.utils.TestData.testProfileAttributes;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestControllerTest {
    @Mock
    private RecommendationFacade facade;

    @InjectMocks
    private RestController restController = new RestController();

    @Test
    public void getProfile() throws ProfileNotFoundException {
        when(facade.findProfile(TestData.FB_ID)).thenReturn(testProfileAttributes());

        Attributes attributes = restController.getProfile(TestData.FB_ID);

        assertThat(attributes).isEqualTo(testProfileAttributes());
    }
}
