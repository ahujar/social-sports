package com.example.social.sports.data.sport;

import com.example.social.sports.model.sport.TennisCourt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TennisCourtRepository extends JpaRepository<TennisCourt,Long> {

}
