package com.yfmf.footlog.guest.service;


import com.yfmf.footlog.guest.entity.Guest;
import com.yfmf.footlog.guest.repository.GuestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Transactional
    public Guest registGuest(Guest guest) {
        return guestRepository.save(guest);
    }

    @Transactional
    public Guest updateGuest(long guestId, Guest updatedGuest) {
        Optional<Guest> existingGuest = guestRepository.findById(guestId);
        if (existingGuest.isPresent()) {
            Guest guest = existingGuest.get();
            guest.setName(updatedGuest.getName());
            guest.setCreatedAt(updatedGuest.getCreatedAt());
            guest.setMainFoot(updatedGuest.getMainFoot());
            guest.setPosition(updatedGuest.getPosition());
            guest.setAvailable(updatedGuest.isAvailable());
            return guestRepository.save(guest);
        } else {
            throw new IllegalArgumentException("Guest not found with id: " + guestId);
        }
    }

    @Transactional
    public void deleteGuest(long guestId) {
        if (guestRepository.existsById(guestId)) {
            guestRepository.deleteById(guestId);
        } else {
            throw new IllegalArgumentException("Guest not found with id: " + guestId);
        }
    }
}
