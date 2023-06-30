package br.com.fiap.powersave.controller;

import br.com.fiap.powersave.model.entity.Address;
import br.com.fiap.powersave.records.AddressRecord;
import br.com.fiap.powersave.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    AddressService addressService;


    @GetMapping
    public ResponseEntity<Page<Address>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Address> list = addressService.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Address> findById(@PathVariable Long id) {
        Address obj = addressService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Address> create(@RequestBody AddressRecord addressRecord) {
        Address address = addressService.create(addressRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Address> update(@PathVariable Long id , @RequestBody Address obj) {
        obj = addressService.update(id, obj);
        return ResponseEntity.ok(obj);
    }
}