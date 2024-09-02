package com.yfmf.footlog.guest.service;

import com.yfmf.footlog.guest.dto.CreateGuestDto;
import com.yfmf.footlog.guest.dto.UpdateGuestDto;
import com.yfmf.footlog.guest.entity.Guest;
import com.yfmf.footlog.guest.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    public Guest registerGuest(CreateGuestDto createGuestDto) {

        validateCreateGuestDto(createGuestDto);

        Guest guest = Guest.builder()
                .name(createGuestDto.getName())
                .isAvailable(createGuestDto.getIsAvailable())
                .build();

        return guestRepository.save(guest);
    }

    public Guest updateGuest(UpdateGuestDto updateGuestDto) {
        Guest existingGuest = guestRepository.findById(updateGuestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Guest not found"));

        Guest updatedGuest = Guest.builder()
                .id(existingGuest.getId())
                .name(updateGuestDto.getName() != null ? updateGuestDto.getName() : existingGuest.getName())
                .isAvailable(updateGuestDto.getIsAvailable() != null ? updateGuestDto.getIsAvailable() : existingGuest.getIsAvailable())
                .build();

        return guestRepository.save(updatedGuest);
    }

    public void deleteGuest(Long id) {
        if (guestRepository.existsById(id)) {
            guestRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Guest not found");
        }
    }

    private void validateCreateGuestDto(CreateGuestDto createGuestDto) {
        if (createGuestDto.getCreatedAt() == null) {
            throw new IllegalArgumentException("createdAt cannot be null");
        }
    }
}
