package com.yfmf.footlog.guest.repository;


import com.yfmf.footlog.guest.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}