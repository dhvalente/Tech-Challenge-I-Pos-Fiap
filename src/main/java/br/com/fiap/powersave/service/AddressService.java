package br.com.fiap.powersave.service;

import br.com.fiap.powersave.model.entity.Address;
import br.com.fiap.powersave.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(()-> new RuntimeException());
    }

    public Address create(Address obj) {
        return addressRepository.save(obj);
    }

    public void delete(Long id) {
        addressRepository.deleteById(id);

    }

    public Address update(Long id, Address obj) {
        Address entity = addressRepository.getReferenceById(id);
        updateData(entity, obj);
        return addressRepository.save(entity);
    }

    private void updateData(Address entity, Address obj) {
        entity.setStreet(obj.getStreet());
        entity.setNumber(obj.getNumber());
        entity.setCity(obj.getCity());
        entity.setDistrict(obj.getDistrict());
        entity.setState(obj.getState());
        entity.setZipcode(obj.getZipcode());
    }

    private boolean validaCep(String cep) {
        if (!cep.matches("\\d{8}")) {
            return false;
        }

        return true;
    }
}