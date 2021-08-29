package com.example.social.sports.model.sport;

import lombok.*;

import javax.persistence.*;


@Entity(name = "TENNIS_COURT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TennisCourt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String courtName;
    private String courtType;
    @Enumerated(EnumType.STRING)
    private SportType sportType;

}
