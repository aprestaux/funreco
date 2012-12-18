package com.github.aprestaux.funreco.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
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
