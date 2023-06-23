package br.com.fiap.powersave.exceptions;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(Long id) {
        super(String.format("Person com ID: %s não encontrado(a)", id));
    }
}
