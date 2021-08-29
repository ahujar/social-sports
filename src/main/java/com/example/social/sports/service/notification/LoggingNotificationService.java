package com.example.social.sports.service.notification;

import com.example.social.sports.factory.notifications.NotificationTypeResolver;
import com.example.social.sports.model.bookings.BookingNotification;
import com.example.social.sports.model.bookings.BookingStatus;
import com.example.social.sports.model.bookings.Bookings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class LoggingNotificationService extends NotificationService {

    @PostConstruct
    public void init() {
        NotificationTypeResolver.register("log", LoggingNotificationService.class);
    }

    @Override
    public synchronized void notify(BookingNotification notification) {
        log.info("Identifying notifiers");
        notification.getBookingsToBeConfirmed().forEach(booking -> {
            booking.setStatus(BookingStatus.CONFIRMED);
            booking.setComments("Booking Confirmed.");
            booking.setNotified(true);
            bookingsRepository.save(booking);
            log.info("Booking Confirmed for {}, on {} , for sport: {} at facility Name: {}", booking.getPlayerName(),
                    booking.getBookingDate(), booking.getSportType(), booking.getFacilityName());
        });
    }


}
