package br.com.fiap.powersave.controller;

import br.com.fiap.powersave.model.dto.AddressResponseFullDto;
import br.com.fiap.powersave.model.dto.AddressResponseSimpleDto;
import br.com.fiap.powersave.records.AddressRecord;
import br.com.fiap.powersave.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Address Controler", description = "Address Controller exposes REST APIs for Address")
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/address")
public class AddressController {

    private final  AddressService addressService;

    @Operation(summary = "Get Address REST API", description="Get all address from database")
    @GetMapping
    public ResponseEntity<Page<AddressResponseFullDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<AddressResponseFullDto> list = addressService.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Get Address REST API by ID", description="Get address by ID from database")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AddressResponseFullDto> findById(@PathVariable Long id) {
        AddressResponseFullDto obj = addressService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Save Address REST API", description="Save address object in a database")
    @PostMapping
    public ResponseEntity<AddressResponseSimpleDto> create(@Valid @RequestBody AddressRecord addressRecord) {
        AddressResponseSimpleDto address = addressService.create(addressRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @Operation(summary = "Put Address REST API", description="Put address object by ID in a database")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressRecord> update(@PathVariable Long id , @RequestBody AddressRecord addressRecord) {
        addressService.update(id, addressRecord);
        return ResponseEntity.ok(addressRecord);
    }

    @Operation(summary = "Delete Address REST API", description="Delete address object by ID in a database")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}