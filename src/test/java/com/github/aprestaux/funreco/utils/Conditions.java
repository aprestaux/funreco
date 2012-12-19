package com.github.aprestaux.funreco.utils;

import com.github.aprestaux.funreco.api.Profile;

public class Conditions {
    public static SameDBProfile sameAsDBProfile(Profile reference) {
        return new SameDBProfile(reference);
    }

    public static SameProfile sameAsProfile(Profile reference) {
        return new SameProfile(reference);
    }
}
