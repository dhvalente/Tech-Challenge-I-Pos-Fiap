package br.com.fiap.powersave.service;

import br.com.fiap.powersave.exceptions.ApplianceNotFoundException;
import br.com.fiap.powersave.exceptions.DatabaseException;
import br.com.fiap.powersave.model.dto.ApplianceResponseFullDto;
import br.com.fiap.powersave.model.dto.ApplianceResponseSimpleDto;
import br.com.fiap.powersave.model.entity.Appliance;
import br.com.fiap.powersave.records.ApplianceRecord;
import br.com.fiap.powersave.repository.ApplianceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplianceService {

    private final ApplianceRepository applianceRepository;
    private final ModelMapper mapper;

    public ApplianceResponseSimpleDto create(ApplianceRecord applianceRecord){

        log.info("START - [APPLIANCE] - create");

        try {

            Appliance appliance = Appliance.builder()
                    .name(applianceRecord.name())
                    .model(applianceRecord.model())
                    .potency(applianceRecord.potency())
                    .build();

            return mapper.map(applianceRepository.save(appliance), ApplianceResponseSimpleDto.class);

        }catch (DataIntegrityViolationException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [APPLIANCE] - create");
        }

    }

    public Collection<ApplianceResponseFullDto> findAll(){

        log.info("START - [APPLIANCE] - findAll");

        try {

            return Collections.unmodifiableCollection(
                    applianceRepository.findAll()
                    .stream()
                    .map(appliance -> mapper.map(appliance, ApplianceResponseFullDto.class))
                    .collect(Collectors.toList()));

        }catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [APPLIANCE] - findAll");
        }

    }

    public ApplianceResponseFullDto findById(Long id){

        log.info("START - [APPLIANCE] - findById - PARAMETER[{}]", id);

        try {
            return mapper.map(applianceRepository.findById(id).get(), ApplianceResponseFullDto.class);
        }catch(Exception e) {
            throw new ApplianceNotFoundException(id);
        }finally {
            log.info("FINAL - [APPLIANCE] - findById - PARAMETER[{}]", id);
        }

    }

    public void update(Long id, ApplianceRecord applianceRecord){

        log.info("START - [APPLIANCE] - update - PARAMETER[{}}]", id);

        try {

            Appliance applianceActual = mapper.map(findById(id), Appliance.class);
            applianceActual.setName(applianceRecord.name());
            applianceActual.setModel(applianceRecord.model());
            applianceActual.setPotency(applianceRecord.potency());
            applianceRepository.save(applianceActual);

        }catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [APPLIANCE] - update - PARAMETER[{}}]", id);
        }

    }

    public void delete(Long id){

        log.info("START - [APPLIANCE] - delete - PARAMETER[{}}]", id);

        try {

            findById(id);
            applianceRepository.deleteById(id);

        }catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }finally {
            log.info("FINAL - [APPLIANCE] - delete - PARAMETER[{}}]", id);
        }


    }

}