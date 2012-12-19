package com.github.aprestaux.funreco.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.github.aprestaux.funreco.api.Profile;
import com.github.aprestaux.funreco.service.ProfileNotFoundException;
import com.github.aprestaux.funreco.service.RecommendationFacade;
import com.github.aprestaux.funreco.utils.TestData;

import static com.github.aprestaux.funreco.utils.Conditions.sameAsProfile;
import static com.github.aprestaux.funreco.utils.TestData.testProfile;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestControllerTest {
    private RestController restController = new RestController();

    private RecommendationFacade facade = mock(RecommendationFacade.class);

    @Before
    public void setup() {
        ReflectionTestUtils.setField(restController, "recommendationFacade", facade);
    }

    @Test
    public void getProfile() throws ProfileNotFoundException {
        when(facade.findProfile(TestData.FB_ID)).thenReturn(testProfile());

        Profile profile = restController.getProfile(TestData.FB_ID);

        assertThat(profile).is(sameAsProfile(testProfile()));
    }
}
