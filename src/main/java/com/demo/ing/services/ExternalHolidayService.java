package com.demo.ing.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class ExternalHolidayService {

    @Bean
   public List<LocalDate> publicHolidaysService(){
        List<LocalDate> dates = Arrays.asList(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2020, 1, 2),
                LocalDate.of(2020, 5, 1),
                LocalDate.of(2020, 6, 1),
                LocalDate.of(2020, 11, 30),
                LocalDate.of(2020, 12, 1),
                LocalDate.of(2020, 12, 24),
                LocalDate.of(2020, 12, 25),
                LocalDate.of(2020, 12, 31)
                /*TODO add your own date for test*/
                /*,LocalDate.of(2020, 7, 30)*/
        );
        return dates;
    }
}
