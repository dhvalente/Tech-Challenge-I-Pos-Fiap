package br.com.fiap.powersave.service;

import br.com.fiap.powersave.exceptions.ApplianceNotFoundException;
import br.com.fiap.powersave.exceptions.DatabaseException;
import br.com.fiap.powersave.exceptions.PersonNotFoundException;
import br.com.fiap.powersave.model.dto.PersonRelationRequestDto;
import br.com.fiap.powersave.model.dto.PersonRequestDto;
import br.com.fiap.powersave.model.dto.PersonResponseFullDto;
import br.com.fiap.powersave.model.dto.PersonResponseSimpleDto;
import br.com.fiap.powersave.model.entity.Address;
import br.com.fiap.powersave.model.entity.Appliance;
import br.com.fiap.powersave.model.entity.Kinship;
import br.com.fiap.powersave.model.entity.Person;
import br.com.fiap.powersave.repository.KinshipRepository;
import br.com.fiap.powersave.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final KinshipRepository kinshipRepository;
    private final AddressService addressService;
    private final ApplianceService applianceService;
    private final ModelMapper mapper;

    public PersonResponseSimpleDto create(PersonRequestDto personRequestDto){

        log.info("START - [PERSON] - create");

        try {

            if(personRequestDto.getPersonRelations().isEmpty())
                return mapper.map(createWithoutRelation(personRequestDto), PersonResponseSimpleDto.class);

            return mapper.map(createWithRelation(personRequestDto), PersonResponseSimpleDto.class);

        }catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [PERSON] - create");
        }

    }

    public Collection<PersonResponseFullDto> findAll(){

        log.info("START - [PERSON] - findAll");

        try {

            return Collections.unmodifiableCollection(personRepository.findAll()
                    .stream()
                    .map(person -> mapper.map(person, PersonResponseFullDto.class))
                    .collect(Collectors.toList()));

        }catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [PERSON] - findAll");
        }

    }

    public PersonResponseFullDto findById(Long id){

        log.info("START - [PERSON] - findById - PARAMETER[{}]", id);

        try {
            return mapper.map(findPersonById(id), PersonResponseFullDto.class);
        }catch(Exception e) {
            throw new ApplianceNotFoundException(id);
        }finally {
            log.info("FINAL - [PERSON] - findById - PARAMETER[{}]", id);
        }

    }

    public void update(Long id, PersonRequestDto person){

        log.info("START - [PERSON] - update - PARAMETER[{}}]", id);

        try {

            Person personActual = findPersonById(id);
            personActual.setName(person.getName());
            personActual.setBirthDate(person.getBirthDate());
            personActual.setGender(person.getGender());
            personRepository.save(personActual);

        }catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [PERSON] - update - PARAMETER[{}}]", id);
        }

    }

    public void delete(Long id){

        log.info("START - [PERSON] - delete - PARAMETER[{}}]", id);

        try {

            findPersonById(id);
            personRepository.deleteById(id);

        }catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [PERSON] - delete - PARAMETER[{}}]", id);
        }

    }

    public void associateAddress(Long personId, Long addressId){

        log.info("START - [PERSON] - associateAddress - PARAMETER[ PERSON:%s - ADDRESS: %s]", personId, addressId);

        try {

            Person person = findPersonById(personId);
            Address address = mapper.map(addressService.findById(addressId), Address.class);

            person.getAddresses().add(address);
            personRepository.save(person);

        }catch (Exception e){
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [PERSON] - associateAddress - PARAMETER[ PERSON:%s - ADDRESS: %s]", personId, addressId);
        }

    }

    public void associateAppliance(Long personId, Long applianceId){

        log.info("START - [PERSON] - associateAddress - PARAMETER[ PERSON:{} - ADDRESS: {}]", personId, applianceId);

        try {

            Person person = findPersonById(personId);
            Appliance appliance = mapper.map(applianceService.findById(applianceId), Appliance.class);

            person.getAppliances().add(appliance);
            personRepository.save(person);

        }catch (Exception e){
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [PERSON] - associateAddress - PARAMETER[ PERSON:{} - ADDRESS: {}]", personId, applianceId);
        }

    }

    private Person createWithoutRelation(PersonRequestDto personRequestDto){
        Person person = mapper.map(personRequestDto, Person.class);
        return personRepository.save(person);
    }

    private Person createWithRelation(PersonRequestDto personRequestDto){

        Person person = mapper.map(personRequestDto, Person.class);
        personRepository.save(person);

        Collection<Kinship> kinships = getKinships(person.getId(), personRequestDto.getPersonRelations());

        for (Kinship kinship: kinships) {
            kinshipRepository.save(kinship);
        }

        return person;
    }

    private Person findPersonById(Long id){
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    private Collection<Kinship> getKinships(Long id, Collection<PersonRelationRequestDto> personRelationRequests){

        List<Kinship> kinships =  Collections.emptyList();

        return Collections.unmodifiableCollection(kinships);

    }

}