package com.example.GreatEvents;


import com.example.GreatEvents.util.PasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Application {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
