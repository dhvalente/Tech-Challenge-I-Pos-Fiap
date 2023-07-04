package br.com.fiap.powersave.controller;

import br.com.fiap.powersave.model.dto.PersonRequestDto;
import br.com.fiap.powersave.model.dto.PersonResponseDto;
import br.com.fiap.powersave.model.entity.Person;
import br.com.fiap.powersave.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Tag(name = "Person Controller", description = "Person Controller exposes REST APIs for Person")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/people")
public class PersonController {

    private final PersonService service;

    @Operation(summary = "Save Person REST API", description="Save person object in a database")
    @PostMapping
    public ResponseEntity<PersonResponseDto> create(@RequestBody @Valid PersonRequestDto personRequestDto){
        PersonResponseDto personResponseDto = service.create(personRequestDto);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(personResponseDto.getId()).toUri();
        return ResponseEntity.created(uri).body(personResponseDto);
    }

    @Operation(summary = "Get Person REST API", description="Get all person from database")
    @GetMapping
    public ResponseEntity<Collection<PersonResponseDto>> findAll(){

        Collection<PersonResponseDto> personResponseDtoCollection = service.findAll();

        if(personResponseDtoCollection.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(personResponseDtoCollection);

    }

    @Operation(summary = "Get Address REST API by ID", description="Get person by ID from database")
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDto> findById(@PathVariable Long id){
        PersonResponseDto personResponseDto = service.findById(id);
        return ResponseEntity.ok(personResponseDto);
    }


    @Operation(summary = "Put Person REST API", description="Put person object by ID in a database")
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDto> update(@PathVariable Long id, @RequestBody Person person){
        PersonResponseDto personResponseDto = service.update(id, person);
        return ResponseEntity.ok(personResponseDto);
    }

    @Operation(summary = "Delete Person REST API", description="Delete person object by ID in a database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}