package br.com.fiap.powersave.controller;

import br.com.fiap.powersave.model.dto.PersonResponseDto;
import br.com.fiap.powersave.model.entity.Appliance;
import br.com.fiap.powersave.service.ApplianceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/appliance")
public class ApplianceController {

    private final ApplianceService service;

    @PostMapping
    public ResponseEntity<Appliance> create(@RequestBody @Valid Appliance appliance){
        Appliance applianceResponse = service.create(appliance);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(applianceResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(applianceResponse);
    }

    @GetMapping
    public ResponseEntity<Collection<Appliance>> findAll(){

        Collection<Appliance> applianceCollection = service.findAll();

        if(applianceCollection.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(applianceCollection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appliance> findById(@PathVariable Long id){
        Appliance applianceResponse = service.findById(id);
        return ResponseEntity.ok(applianceResponse);
    }

    @PutMapping
    public ResponseEntity<Appliance> update(@PathVariable Long id, @RequestBody @Valid Appliance appliance){
        Appliance applianceResponse = service.update(id, appliance);
        return ResponseEntity.ok(applianceResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
