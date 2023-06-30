package br.com.fiap.powersave.exceptions;

public class ApplianceNotFoundException extends RuntimeException {

    public ApplianceNotFoundException(Long id) {
        super(String.format("Appliance com ID: %s não encontrado(a)", id));
    }
}
