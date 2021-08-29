package com.example.social.sports.service.notification;

import com.example.social.sports.data.bookings.BookingsRepository;
import com.example.social.sports.model.bookings.BookingNotification;
import com.example.social.sports.model.bookings.Bookings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

@Component
@Slf4j
public abstract class NotificationService implements Flow.Subscriber<BookingNotification> {

    @Autowired
    protected BookingsRepository bookingsRepository;

    private Flow.Subscription subscription;

    public Flow.Subscription getSubscription() {
        return subscription;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(BookingNotification notification) {
        log.info("Processing Notification");
        if(notification.isRequiresNotification()){
            notify(notification);
        }else {
            log.info("Notification not needed yet");
        }

    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error Occured: {}", throwable);
    }

    @Override
    public void onComplete() {
        log.info("Booking Process Completed");
    }

    public abstract void notify(BookingNotification notification);
}
