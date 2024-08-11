package com.yfmf.footlog.guest.repository;


import com.yfmf.footlog.guest.Entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}