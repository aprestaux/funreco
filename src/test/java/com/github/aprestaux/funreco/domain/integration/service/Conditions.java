package com.github.aprestaux.funreco.domain.integration.service;

import com.github.aprestaux.funreco.api.Profile;

public class Conditions {
    public static SameProfile sameProfile(Profile reference) {
        return new SameProfile(reference);
    }
}
