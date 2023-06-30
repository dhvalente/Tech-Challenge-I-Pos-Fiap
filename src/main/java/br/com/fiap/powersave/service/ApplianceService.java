package br.com.fiap.powersave.service;

import br.com.fiap.powersave.exceptions.ApplianceNotFoundException;
import br.com.fiap.powersave.exceptions.PersonNotFoundException;
import br.com.fiap.powersave.model.entity.Appliance;
import br.com.fiap.powersave.model.entity.Person;
import br.com.fiap.powersave.repository.ApplianceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ApplianceService {

    private final ApplianceRepository applianceRepository;

    public Appliance create(Appliance appliance){
        return applianceRepository.save(appliance);
    }

    public Collection<Appliance> findAll(){
        return Collections.unmodifiableCollection(applianceRepository.findAll());
    }

    public Appliance findById(Long id){
        return applianceRepository.findById(id).orElseThrow(() -> new ApplianceNotFoundException(id));
    }

    public Appliance update(Long id, Appliance appliance){
        Appliance applianceActual = findById(id);
        applianceActual.setName(appliance.getName());
        applianceActual.setModel(appliance.getModel());
        applianceActual.setPotency(appliance.getPotency());
        return applianceRepository.save(applianceActual);
    }

    public void delete(Long id){
        findById(id);
        applianceRepository.deleteById(id);
    }

}