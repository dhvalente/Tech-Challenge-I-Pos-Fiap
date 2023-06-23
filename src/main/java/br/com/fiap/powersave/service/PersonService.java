package br.com.fiap.powersave.service;

import br.com.fiap.powersave.exceptions.PersonNotFoundException;
import br.com.fiap.powersave.model.entity.Person;
import br.com.fiap.powersave.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public Collection<Person> findAll(){
        return Collections.unmodifiableCollection(repository.findAll());
    }

    public Person findById(Long id){
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    public Person create(Person person){
        return repository.save(person);
    }

    public Person update(Long id, Person person){
        var personActual = findById(id);
        personActual.setName(person.getName());
        personActual.setBirthDate(person.getBirthDate());
        personActual.setGender(person.getGender());
        return create(personActual);
    }

    public void delete(Long id){
        findById(id);
        repository.deleteById(id);
    }

}