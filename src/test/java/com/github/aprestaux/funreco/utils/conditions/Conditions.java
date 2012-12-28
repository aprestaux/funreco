package com.github.aprestaux.funreco.utils.conditions;

import com.github.aprestaux.funreco.api.Profile;

public class Conditions {
    public static SameDBProfileCondition sameAsDBProfile(Profile reference) {
        return new SameDBProfileCondition(reference);
    }

    public static SameProfileCondition sameAsProfile(Profile reference) {
        return new SameProfileCondition(reference);
    }
}
