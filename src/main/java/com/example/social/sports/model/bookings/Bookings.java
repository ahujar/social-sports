package com.example.social.sports.model.bookings;

import com.example.social.sports.model.sport.SportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
public class Bookings {

    public Bookings() {
    }

    @Id
    private Long id;
    private String playerName;
    @Enumerated(EnumType.STRING)
    private SportType sportType;
    private Date bookingDate;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private String comments;
    private boolean notified;
    private String facilityName;
}
