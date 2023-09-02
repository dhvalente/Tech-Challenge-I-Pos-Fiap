package br.com.fiap.powersave.controller;

import br.com.fiap.powersave.model.dto.PersonRequestDto;
import br.com.fiap.powersave.model.dto.PersonResponseFullDto;
import br.com.fiap.powersave.model.dto.PersonResponseSimpleDto;
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
    public ResponseEntity<PersonResponseSimpleDto> create(@RequestBody @Valid PersonRequestDto personRequestDto){
        PersonResponseSimpleDto personResponseFullDto = service.create(personRequestDto);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(personResponseFullDto.getId()).toUri();
        return ResponseEntity.created(uri).body(personResponseFullDto);
    }

    @Operation(summary = "Get Person REST API", description="Get all person from database")
    @GetMapping
    public ResponseEntity<Collection<PersonResponseFullDto>> findAll(){

        Collection<PersonResponseFullDto> personResponseFullDtoCollection = service.findAll();

        if(personResponseFullDtoCollection.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(personResponseFullDtoCollection);

    }

    @Operation(summary = "Get Address REST API by ID", description="Get person by ID from database")
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseFullDto> findById(@PathVariable Long id){
        PersonResponseFullDto personResponseFullDto = service.findById(id);
        return ResponseEntity.ok(personResponseFullDto);
    }


    @Operation(summary = "Put Person REST API", description="Put person object by ID in a database")
    @PutMapping("/{id}")
    public ResponseEntity<PersonRequestDto> update(@PathVariable Long id, @RequestBody PersonRequestDto person){
        service.update(id, person);
        return ResponseEntity.ok(person);
    }

    @Operation(summary = "Delete Person REST API", description="Delete person object by ID in a database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Save Associate Address REST API", description="Associate person to a address in a database")
    @PostMapping("/{personId}/associate-address/{addressId}")
    public ResponseEntity<Void> associateAddress(@PathVariable Long personId, @PathVariable Long addressId) {
        service.associateAddress(personId, addressId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Save Associate Appliance REST API", description="Associate person to a appliance in a database")
    @PostMapping("/{personId}/associate-appliance/{applianceId}")
    public ResponseEntity<Void> associateAppliance(@PathVariable Long personId, @PathVariable Long applianceId) {
        service.associateAppliance(personId, applianceId);
        return ResponseEntity.noContent().build();
    }

}