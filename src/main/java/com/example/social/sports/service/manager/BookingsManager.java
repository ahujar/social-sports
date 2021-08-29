package com.example.social.sports.service.manager;

import com.example.social.sports.factory.bookings.SportFacilityBooking;
import com.example.social.sports.factory.notifications.NotificationTypeResolver;
import com.example.social.sports.model.bookings.BookingStatus;
import com.example.social.sports.model.bookings.Bookings;
import com.example.social.sports.model.sport.SportType;
import com.example.social.sports.service.bookings.BookingService;
import com.example.social.sports.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.SubmissionPublisher;

@Component
public class BookingsManager {

    @Autowired
    SubmissionPublisher<Bookings> bookingsSubmissionPublisher;

    public Bookings processBookingRequest(String playerName, Date date, String sportType, String notifier) {
        BookingService bookingService = SportFacilityBooking.getInstance(sportType);
        NotificationService notificationService = NotificationTypeResolver.getInstance(notifier);
        Bookings booking = Bookings.builder()
                .playerName(playerName).id(bookingService.getNextId())
                .sportType(SportType.valueOf(sportType.toUpperCase()))
                .status(BookingStatus.CREATED).comments("Processing..")
                .bookingDate(date)
                .notified(false)
                .build();
        setupSubscription(bookingService, notificationService);
        bookingsSubmissionPublisher.submit(booking);
        return booking;
    }

    private void setupSubscription(BookingService bookingService, NotificationService notificationService) {
        if (!bookingsSubmissionPublisher.isSubscribed(bookingService)) {
            bookingsSubmissionPublisher.subscribe(bookingService);
        }
        if (!bookingService.isSubscribed(notificationService)) {
            bookingService.subscribe(notificationService);
        }
    }
}
