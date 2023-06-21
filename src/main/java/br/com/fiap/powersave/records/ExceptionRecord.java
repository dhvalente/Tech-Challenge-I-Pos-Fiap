package br.com.fiap.powersave.records;

import org.springframework.http.HttpStatus;

public record ExceptionRecord(HttpStatus statusCodeError, String messageError) {
}
