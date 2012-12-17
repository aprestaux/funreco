package com.github.aprestaux.funreco.service;

public class ProfileNotFoundException extends Exception {
    public ProfileNotFoundException() {
    }

    public ProfileNotFoundException(String s) {
        super(s);
    }

    public ProfileNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ProfileNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
