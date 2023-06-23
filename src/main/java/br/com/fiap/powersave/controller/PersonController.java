package br.com.fiap.powersave.controller;

import br.com.fiap.powersave.model.entity.Person;
import br.com.fiap.powersave.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/people")
public class PersonController {

    private final PersonService service;

    @GetMapping
    public ResponseEntity findAll(){

        var response = service.findAll();

        if(response.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Person person){
        var response = service.create(person);
        var uri = fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Person person){
        return ResponseEntity.ok(service.update(id, person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
