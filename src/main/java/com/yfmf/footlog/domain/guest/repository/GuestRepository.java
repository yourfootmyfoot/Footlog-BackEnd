package com.yfmf.footlog.domain.guest.repository;


import com.yfmf.footlog.domain.guest.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByScheduleDateGreaterThanEqual(LocalDateTime date);
    List<Guest> findByLocation(String location);
    List<Guest> findByAgeBetween(int minAge, int maxAge);
}