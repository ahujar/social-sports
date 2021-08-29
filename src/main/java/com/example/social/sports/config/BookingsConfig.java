package com.example.social.sports.config;

import com.example.social.sports.model.bookings.Bookings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.SubmissionPublisher;

@Configuration
public class BookingsConfig {

    @Bean
    public SubmissionPublisher<Bookings> bookingsSubmissionPublisher() {
        return new SubmissionPublisher<Bookings>();
    }
}
