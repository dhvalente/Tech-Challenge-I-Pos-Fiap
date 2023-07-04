package br.com.fiap.powersave.controller;

import br.com.fiap.powersave.model.entity.Appliance;
import br.com.fiap.powersave.service.ApplianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@Tag(name = "Appliance Controller", description = "Appliance Controller exposes REST APIs for Appliance")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/appliance")
public class ApplianceController {

    private final ApplianceService service;

    @Operation(summary = "Save Appliance REST API", description="Save appliance object in a database")
    @PostMapping
    public ResponseEntity<Appliance> create(@RequestBody @Valid Appliance appliance){
        Appliance applianceResponse = service.create(appliance);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(applianceResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(applianceResponse);
    }
    @Operation(summary = "Get Appliance REST API", description="Get all appliance from database")
    @GetMapping
    public ResponseEntity<Collection<Appliance>> findAll(){

        Collection<Appliance> applianceCollection = service.findAll();

        if(applianceCollection.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(applianceCollection);
    }

    @Operation(summary = "Get Appliance REST API by ID", description="Get appliance by ID from database")
    @GetMapping("/{id}")
    public ResponseEntity<Appliance> findById(@PathVariable Long id){
        Appliance applianceResponse = service.findById(id);
        return ResponseEntity.ok(applianceResponse);
    }
    @Operation(summary = "Put Appliance REST API", description="Put appliance object by ID in a database")
    @PutMapping("/{id}")
    public ResponseEntity<Appliance> update(@PathVariable Long id, @RequestBody @Valid Appliance appliance){
        Appliance applianceResponse = service.update(id, appliance);
        return ResponseEntity.ok(applianceResponse);
    }

    @Operation(summary = "Delete Appliance REST API", description="Delete appliance object by ID in a database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
