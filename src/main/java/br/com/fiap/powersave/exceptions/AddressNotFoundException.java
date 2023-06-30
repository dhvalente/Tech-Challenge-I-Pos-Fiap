package br.com.fiap.powersave.exceptions;

public class AddressNotFoundException  extends RuntimeException  {

    public AddressNotFoundException(Long id) {
        super("Endereço com ID: "+ id + " não encontrado(a)");
    }
}
