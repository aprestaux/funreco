package com.github.aprestaux.funreco.utils.conditions;

import org.fest.assertions.core.Condition;

import com.github.aprestaux.funreco.api.Profile;

public class SameProfileCondition extends Condition<Profile> {

    private Profile reference;

    public SameProfileCondition(Profile reference) {
        this.reference = reference;
    }

    @Override
    public boolean matches(Profile profile) {
        return reference.getId().equals(profile.getId())
                && reference.getEmail().equals(profile.getEmail())
                && reference.getName().equals(profile.getName());
    }
}
