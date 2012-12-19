package com.github.aprestaux.funreco.utils;

import org.fest.assertions.core.Condition;

import com.github.aprestaux.funreco.api.Profile;

public class SameProfile extends Condition<Profile> {

    private Profile reference;

    public SameProfile(Profile reference) {
        this.reference = reference;
    }

    @Override
    public boolean matches(Profile profile) {
        return reference.getFacebookId().equals(profile.getFacebookId())
                && reference.getEmail().equals(profile.getEmail())
                && reference.getName().equals(profile.getName());
    }
}
