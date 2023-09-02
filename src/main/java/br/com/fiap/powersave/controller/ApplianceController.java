package br.com.fiap.powersave.controller;

import br.com.fiap.powersave.model.dto.ApplianceResponseFullDto;
import br.com.fiap.powersave.model.dto.ApplianceResponseSimpleDto;
import br.com.fiap.powersave.records.ApplianceRecord;
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
    public ResponseEntity<ApplianceResponseSimpleDto> create(@RequestBody @Valid ApplianceRecord applianceRecord){
        ApplianceResponseSimpleDto applianceResponse = service.create(applianceRecord);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(applianceResponse.getId()).toUri();
        return ResponseEntity.created(uri).body(applianceResponse);
    }

    @Operation(summary = "Get Appliance REST API", description="Get all appliance from database")
    @GetMapping
    public ResponseEntity<Collection<ApplianceResponseFullDto>> findAll(){

        Collection<ApplianceResponseFullDto> applianceCollection = service.findAll();

        if(applianceCollection.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(applianceCollection);
    }

    @Operation(summary = "Get Appliance REST API by ID", description="Get appliance by ID from database")
    @GetMapping("/{id}")
    public ResponseEntity<ApplianceResponseFullDto> findById(@PathVariable Long id){
        ApplianceResponseFullDto applianceResponse = service.findById(id);
        return ResponseEntity.ok(applianceResponse);
    }

    @Operation(summary = "Put Appliance REST API", description="Put appliance object by ID in a database")
    @PutMapping("/{id}")
    public ResponseEntity<ApplianceRecord> update(@PathVariable Long id, @RequestBody @Valid ApplianceRecord applianceRecord){
        service.update(id, applianceRecord);
        return ResponseEntity.ok(applianceRecord);
    }

    @Operation(summary = "Delete Appliance REST API", description="Delete appliance object by ID in a database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
