package com.experiment.departamentopessoal.auth.application;

public class TooManyLoginAttemptsException extends RuntimeException {

    public TooManyLoginAttemptsException() {
        super("Muitas tentativas de login. Tente novamente mais tarde.");
    }
}
