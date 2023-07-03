package br.com.fiap.powersave.exceptions;


public class BrazilianStateNotFound  extends RuntimeException  {

    public BrazilianStateNotFound(String  name) {
        super("Brazilian state with name: " + name + " not found");
    }
}
