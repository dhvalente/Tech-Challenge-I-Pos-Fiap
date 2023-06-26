package br.com.fiap.powersave.exceptions;


public class BrazilianStateNotFound  extends RuntimeException  {

    public BrazilianStateNotFound(String  name) {
        super("Estado brasileiro com nome: "+ name + " não encontrado(a)");
    }
}
