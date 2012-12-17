package com.github.aprestaux.funreco.domain.integration.service;

import org.fest.assertions.Condition;

import com.github.aprestaux.funreco.api.Profile;
import com.github.aprestaux.funreco.domain.DBProfile;

public class SameProfile extends Condition<Object> {

    private Profile reference;

    public SameProfile(Profile reference) {
        this.reference = reference;
    }

    @Override
    public boolean matches(Object value) {
        DBProfile profile = (DBProfile) value;

        return reference.getFacebookId().equals(profile.getFacebookId())
                && reference.getEmail().equals(profile.getEmail())
                && reference.getName().equals(profile.getName());
    }
}
