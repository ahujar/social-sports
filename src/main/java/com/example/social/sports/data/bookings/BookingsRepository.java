package com.example.social.sports.data.bookings;

import com.example.social.sports.model.bookings.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Book;
import java.util.Date;
import java.util.List;

public interface BookingsRepository extends JpaRepository<Bookings,Long> {

    public List<Bookings> findAllByBookingDate(Date bookingDate);

    @Query(value = "select BOOKING_SEQUENCE.nextval from dual",nativeQuery = true)
    public Long getNextBookingId();
}
