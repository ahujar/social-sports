package com.example.social.sports.service.bookings;

import com.example.social.sports.data.bookings.BookingsRepository;
import com.example.social.sports.model.bookings.BookingNotification;
import com.example.social.sports.model.bookings.BookingStatus;
import com.example.social.sports.model.bookings.Bookings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

@Component
@Slf4j
public abstract class BookingService extends SubmissionPublisher<BookingNotification> implements Flow.Processor<Bookings, BookingNotification> {

    public Flow.Subscription getSubscription() {
        return subscription;
    }

    private Flow.Subscription subscription;

    @Autowired
    protected BookingsRepository bookingsRepository;

    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(Long.MAX_VALUE);
    }

    public void onNext(Bookings item) {
        log.info("received booking {}", item);
        BookingNotification bookingNotification = registerBooking(item);
        this.submit(bookingNotification);
    }

    public void onError(Throwable throwable) {
        log.error("Error Occured: {}", throwable);
    }

    public void onComplete() {
        log.info("Booking Process Completed");
    }

    public Long getNextId() {
        return bookingsRepository.getNextBookingId();
    }

    public Optional<Bookings> getBooking(Long id) {
        return bookingsRepository.findById(id);
    }

    public abstract BookingNotification registerBooking(Bookings item);
}
