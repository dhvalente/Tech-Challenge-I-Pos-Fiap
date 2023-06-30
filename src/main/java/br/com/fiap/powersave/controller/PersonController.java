package br.com.fiap.powersave.controller;

import br.com.fiap.powersave.model.dto.PersonRequestDto;
import br.com.fiap.powersave.model.dto.PersonResponseDto;
import br.com.fiap.powersave.model.entity.Person;
import br.com.fiap.powersave.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/people")
public class PersonController {

    private final PersonService service;

    @PostMapping
    public ResponseEntity<PersonResponseDto> create(@RequestBody @Valid PersonRequestDto personRequestDto){
        PersonResponseDto personResponseDto = service.create(personRequestDto);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(personResponseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(personResponseDto);
    }

    @GetMapping
    public ResponseEntity<Collection<PersonResponseDto>> findAll(){

        Collection<PersonResponseDto> personResponseDtoCollection = service.findAll();

        if(personResponseDtoCollection.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(personResponseDtoCollection);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDto> findById(@PathVariable Long id){
        PersonResponseDto personResponseDto = service.findById(id);
        return ResponseEntity.ok(personResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDto> update(@PathVariable Long id, @RequestBody Person person){
        PersonResponseDto personResponseDto = service.update(id, person);
        return ResponseEntity.ok(personResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}