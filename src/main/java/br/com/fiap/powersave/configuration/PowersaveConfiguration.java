package br.com.fiap.powersave.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PowersaveConfiguration {

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

}