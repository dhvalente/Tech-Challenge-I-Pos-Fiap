package br.com.fiap.powersave.exceptions;

import br.com.fiap.powersave.records.ExceptionRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ExceptionRecord> userNotFoundException(AddressNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionRecord(HttpStatus.NOT_FOUND, e.getMessage())
        );
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ExceptionRecord> personNotFoundException(PersonNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionRecord(HttpStatus.NOT_FOUND, e.getMessage())
        );
    }
}
