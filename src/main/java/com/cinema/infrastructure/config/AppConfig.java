package com.cinema.infrastructure.config;

import com.cinema.domain.service.ProgramStateMachine;
import com.cinema.domain.service.ScreeningStateMachine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;


@Configuration
public class AppConfig {



    @Bean
    public Clock systemClock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public ProgramStateMachine programStateMachine() {
        return new ProgramStateMachine();
    }

    @Bean
    public ScreeningStateMachine screeningStateMachine() {
        return new ScreeningStateMachine();
    }


}
