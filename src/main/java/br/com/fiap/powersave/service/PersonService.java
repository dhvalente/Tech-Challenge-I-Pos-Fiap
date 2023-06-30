package br.com.fiap.powersave.service;

import br.com.fiap.powersave.exceptions.PersonNotFoundException;
import br.com.fiap.powersave.model.dto.PersonRelationRequestDto;
import br.com.fiap.powersave.model.dto.PersonRequestDto;
import br.com.fiap.powersave.model.dto.PersonResponseDto;
import br.com.fiap.powersave.model.entity.Kinship;
import br.com.fiap.powersave.model.entity.Person;
import br.com.fiap.powersave.repository.KinshipRepository;
import br.com.fiap.powersave.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final KinshipRepository kinshipRepository;
    private final ModelMapper mapper;

    public Collection<PersonResponseDto> findAll(){

        return Collections.unmodifiableCollection(personRepository.findAll()
                .stream()
                .map(person -> mapper.map(person, PersonResponseDto.class))
                .collect(Collectors.toList()));

    }

    public PersonResponseDto findById(Long id){
        return mapper.map(findPersonById(id), PersonResponseDto.class);
    }

    public PersonResponseDto create(PersonRequestDto personRequestDto){

        if(personRequestDto.getPersonRelations().isEmpty())
            return mapper.map(createWithoutRelation(personRequestDto), PersonResponseDto.class);

        return mapper.map(createWithRelation(personRequestDto), PersonResponseDto.class);

    }

    public PersonResponseDto update(Long id, Person person){

        Person personActual = findPersonById(id);
        personActual.setName(person.getName());
        personActual.setBirthDate(person.getBirthDate());
        personActual.setGender(person.getGender());
        personRepository.save(personActual);

        return mapper.map(personActual, PersonResponseDto.class);
    }

    public void delete(Long id){
        findPersonById(id);
        personRepository.deleteById(id);
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

        List<Kinship> kinships =  personRelationRequests
                .stream()
                .map(personRelationRequest -> {
                        findPersonById(personRelationRequest.getPersonId());
                        return new Kinship(null, personRelationRequest.getKinshipType(), personRelationRequest.getPersonId(), id);
                })
                .collect(Collectors.toList());

        return Collections.unmodifiableCollection(kinships);

    }

}