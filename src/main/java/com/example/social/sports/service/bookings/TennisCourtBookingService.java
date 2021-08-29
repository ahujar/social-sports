package com.example.social.sports.service.bookings;

import com.example.social.sports.data.sport.TennisCourtRepository;
import com.example.social.sports.factory.bookings.SportFacilityBooking;
import com.example.social.sports.model.bookings.BookingNotification;
import com.example.social.sports.model.bookings.BookingStatus;
import com.example.social.sports.model.bookings.Bookings;
import com.example.social.sports.model.sport.TennisCourt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TennisCourtBookingService extends BookingService {

    private final int maxBookingsPerCourt = 4;

    @PostConstruct
    public void init() {
        SportFacilityBooking.register("TENNIS", TennisCourtBookingService.class);
    }

    @Autowired
    private TennisCourtRepository tennisCourtRepository;

    @Override
    public synchronized BookingNotification registerBooking(Bookings item) {
        log.info("Registering... ");
        BookingNotification bookingNotification = BookingNotification.builder()
                .bookingsToBeConfirmed(new ArrayList<>())
                .requiresNotification(true).build();
        List<Bookings> allByBookingDate = bookingsRepository.findAllByBookingDate(item.getBookingDate());
        Map<String, List<Bookings>> bookingsByCourt = allByBookingDate.stream().collect(Collectors.groupingBy(Bookings::getFacilityName));
        Optional<TennisCourt> tennisCourt = assignCourt(bookingsByCourt);
        tennisCourt.ifPresentOrElse(tc -> {
            log.info("Assigning court : {}", tc.getCourtName());
            item.setStatus(BookingStatus.PENDING);
            item.setFacilityName(tc.getCourtName());
            item.setComments("Booking Saved, Waiting for Confirmation from other players");
        }, () -> {
            fullyBooked(item, bookingNotification);
        });
        bookingsRepository.save(item);
        allByBookingDate.add(item);
        return identifyEligiblePendingBookingsForNotification(bookingNotification, allByBookingDate);
    }

    private void fullyBooked(Bookings item, BookingNotification bookingNotification) {
        log.warn("No Free court found !");
        item.setStatus(BookingStatus.REJECTED);
        item.setComments("Courts Fully booked for " + item.getBookingDate());
        item.setFacilityName("INVALID");
        bookingNotification.setRequiresNotification(false);
    }

    private BookingNotification identifyEligiblePendingBookingsForNotification(BookingNotification bookingNotification, List<Bookings> allByBookingDate) {
        if (bookingNotification.isRequiresNotification()) {
            bookingNotification.setBookingsToBeConfirmed(new ArrayList<>());
            allByBookingDate.stream().filter(booking -> {
                        return booking.getStatus().equals(BookingStatus.PENDING);
                    }).collect(Collectors.groupingBy(Bookings::getFacilityName))
                    .forEach((faciltityName, bookings) -> {
                        if (bookings.size() == maxBookingsPerCourt) {
                            bookingNotification.getBookingsToBeConfirmed().addAll(bookings);
                        }
                    });
            if (bookingNotification.getBookingsToBeConfirmed().isEmpty()) {
                bookingNotification.setRequiresNotification(false);
            }
        }
        return bookingNotification;
    }

    private Optional<TennisCourt> assignCourt(Map<String, List<Bookings>> bookingsByCourt) {
        return tennisCourtRepository.findAll().stream().filter(tennisCourt -> CollectionUtils.isEmpty(bookingsByCourt.get(tennisCourt.getCourtName()))
                        || bookingsByCourt.get(tennisCourt.getCourtName()).size() < maxBookingsPerCourt)
                .findFirst();
    }
}
