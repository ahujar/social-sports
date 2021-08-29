package com.example.social.sports.resources.bookings;

import com.example.social.sports.data.bookings.BookingsRepository;
import com.example.social.sports.data.sport.TennisCourtRepository;
import com.example.social.sports.model.bookings.BookingStatus;
import com.example.social.sports.model.bookings.Bookings;
import com.example.social.sports.model.sport.SportType;
import com.example.social.sports.model.sport.TennisCourt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class BookingControllerTest {

    @Autowired
    BookingController controller;

    @Autowired
    BookingsRepository bookingsRepository;

    @Autowired
    TennisCourtRepository tennisCourtRepository;

    @Test
    @Sql(value = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void book() throws Exception {
        controller.book("rishi", new Date(), "TENNIS");
        controller.book("rishi", new Date(), "TENNIS");
        controller.book("rishi", new Date(), "TENNIS");
        controller.book("rishi", new Date(), "TENNIS");
        controller.book("rishi", new Date(), "TENNIS");
        controller.book("rishi", new Date(), "TENNIS");
        controller.book("rishi", new Date(), "TENNIS");
        controller.book("rishi", new Date(), "TENNIS");
        Thread.sleep(30000);
        List<Bookings> allBookings = bookingsRepository.findAll();
        assertEquals(8, allBookings.size());
        assertEquals(8, allBookings.stream()
                .filter(bookings -> bookings.getStatus().equals(BookingStatus.CONFIRMED))
                .count());
        assertEquals(0, allBookings.stream()
                .filter(bookings -> bookings.getStatus().equals(BookingStatus.REJECTED))
                .count());
        tennisCourtRepository.save(TennisCourt.builder()
                .courtName("TC-4")
                .id(123)
                .sportType(SportType.TENNIS)
                .build());
    }
}