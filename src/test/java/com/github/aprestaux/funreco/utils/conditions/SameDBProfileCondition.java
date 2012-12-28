package com.github.aprestaux.funreco.utils.conditions;

import org.fest.assertions.core.Condition;

import com.github.aprestaux.funreco.api.Profile;
import com.github.aprestaux.funreco.domain.DBProfile;

public class SameDBProfileCondition extends Condition<DBProfile> {

    private Profile reference;

    public SameDBProfileCondition(Profile reference) {
        this.reference = reference;
    }

    @Override
    public boolean matches(DBProfile profile) {
        return reference.getFacebookId().equals(profile.getFacebookId())
                && reference.getEmail().equals(profile.getEmail())
                && reference.getName().equals(profile.getName());
    }
}
