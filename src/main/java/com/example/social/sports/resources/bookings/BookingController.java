package com.example.social.sports.resources.bookings;

import com.example.social.sports.model.bookings.Bookings;
import com.example.social.sports.service.bookings.BookingService;
import com.example.social.sports.service.manager.BookingsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController()
public class BookingController {

    @Autowired
    BookingsManager manager;

    @Autowired
    BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<Bookings> book(@RequestParam("playerName") String playerName, @RequestParam("date")
    @DateTimeFormat(pattern = "dd/MM/yyyy") Date date, @RequestParam("sportFacility") String facilityType) {
        Bookings booking = manager.processBookingRequest(playerName, date, facilityType, "log");
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/status")
    public ResponseEntity<Bookings> status(@RequestParam("bookingId") Long bookingId) {
        Optional<Bookings> optionalBooking = bookingService.getBooking(bookingId);
        return ResponseEntity.ok(optionalBooking.
                orElse(Bookings.builder().
                        id(bookingId).
                        comments("No Booking found with the id").build()));
    }
}
