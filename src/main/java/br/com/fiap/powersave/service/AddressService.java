package br.com.fiap.powersave.service;

import br.com.fiap.powersave.exceptions.AddressNotFoundException;
import br.com.fiap.powersave.exceptions.BrazilianStateNotFound;
import br.com.fiap.powersave.model.entity.Address;
import br.com.fiap.powersave.model.enums.BrazilianState;
import br.com.fiap.powersave.records.AddressRecord;
import br.com.fiap.powersave.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public Page<Address> findAll(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
    }

    public Address create(AddressRecord addressRecord) {
        String validState = isValidState(addressRecord.state());
        Address address = Address.builder()
                .street(addressRecord.street())
                .number(addressRecord.number())
                .district(addressRecord.district())
                .state(validState)
                .city(addressRecord.city())
                .build();
        return addressRepository.save(address);
    }

    public void delete(Long id) {
        findById(id);
        addressRepository.deleteById(id);
    }

    public void update(Long id, AddressRecord obj) {
        Address entity = findById(id);
        updateData(entity, obj);
        addressRepository.save(entity);
    }

    private void updateData(Address entity, AddressRecord obj) {
        entity.setStreet(obj.street());
        entity.setNumber(obj.number());
        entity.setCity(obj.city());
        entity.setDistrict(obj.district());
        entity.setState(obj.state());
    }

    private String isValidState(String state) {
        BrazilianState brazilianState = BrazilianState.get(state);
        if (brazilianState == null) {
            brazilianState = BrazilianState.getBrazilianStateByAbbreviation(state);
        }
        if (brazilianState == null) {
            throw new BrazilianStateNotFound(state);
        }
        return brazilianState.getDescription();
    }
}