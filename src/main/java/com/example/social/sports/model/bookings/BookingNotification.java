package com.example.social.sports.model.bookings;


import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class BookingNotification {

    private List<Bookings> bookingsToBeConfirmed;
    private boolean requiresNotification;

}
