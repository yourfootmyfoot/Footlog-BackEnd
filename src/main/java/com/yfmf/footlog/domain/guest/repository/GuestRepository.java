package com.yfmf.footlog.domain.guest.repository;


import com.yfmf.footlog.domain.guest.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}