package br.com.fiap.powersave.service;

import br.com.fiap.powersave.exceptions.AddressNotFoundException;
import br.com.fiap.powersave.exceptions.BrazilianStateNotFound;
import br.com.fiap.powersave.exceptions.DatabaseException;
import br.com.fiap.powersave.model.dto.AddressResponseFullDto;
import br.com.fiap.powersave.model.dto.AddressResponseSimpleDto;
import br.com.fiap.powersave.model.entity.Address;
import br.com.fiap.powersave.model.enums.BrazilianState;
import br.com.fiap.powersave.records.AddressRecord;
import br.com.fiap.powersave.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper mapper;

    public AddressResponseSimpleDto create(AddressRecord addressRecord) {

        log.info("START - [ADDRESS] - create");

        try {

            Address address = Address.builder()
                    .type(addressRecord.type())
                    .street(addressRecord.street())
                    .number(addressRecord.number())
                    .district(addressRecord.district())
                    .state(isValidState(addressRecord.state()))
                    .city(addressRecord.city())
                    .build();

            return mapper.map(addressRepository.save(address), AddressResponseSimpleDto.class);

        }catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [ADDRESS] - create");
        }

    }

    public Page<AddressResponseFullDto> findAll(Pageable pageable) {

        log.info("START - [ADDRESS] - findAll");

        try {

            return new PageImpl<>(addressRepository.findAll(pageable).stream()
                            .map(address -> mapper.map(address, AddressResponseFullDto.class))
                            .collect(Collectors.toList()));

        }catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [ADDRESS] - findAll");
        }

    }

    public AddressResponseFullDto findById(Long id) {

        log.info("START - [ADDRESS] - findById - PARAMETER[{}]", id);

        try {
            return mapper.map(addressRepository.findById(id).get(), AddressResponseFullDto.class);
        }catch(Exception e) {
            throw new AddressNotFoundException(id);
        }finally {
            log.info("FINAL - [ADDRESS] - findById - PARAMETER[{}]", id);
        }

    }

    public void update(Long id, AddressRecord addressRecord) {

        log.info("START - [ADDRESS] - update - PARAMETER[{}}]", id);

        try {

            Address entity = mapper.map(addressRepository.findById(id), Address.class);
            updateData(entity, addressRecord);
            addressRepository.save(entity);

        }catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [ADDRESS] - update - PARAMETER[{}}]", id);
        }

    }

    public void delete(Long id) {

        log.info("START - [ADDRESS] - delete - PARAMETER[{}}]", id);

        try {
            findById(id);
            addressRepository.deleteById(id);
        }catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [ADDRESS] - delete - PARAMETER[{}}]", id);
        }

    }

    private void updateData(Address entity, AddressRecord addressRecord) {
        entity.setStreet(addressRecord.street());
        entity.setNumber(addressRecord.number());
        entity.setCity(addressRecord.city());
        entity.setDistrict(addressRecord.district());
        entity.setState(addressRecord.state());
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