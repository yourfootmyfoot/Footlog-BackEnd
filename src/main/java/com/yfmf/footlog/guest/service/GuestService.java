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
                .createdAt(createGuestDto.getCreatedAt())
                .mainFoot(createGuestDto.getMainFoot())
                .position(createGuestDto.getPosition())
                .isAvailable(createGuestDto.isAvailable())
                .build();

        return guestRepository.save(guest);
    }

    public Guest updateGuest(UpdateGuestDto updateGuestDto) {
        Guest existingGuest = guestRepository.findById(updateGuestDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Guest not found"));

            Guest updatedGuest = Guest.builder()
                    .id(existingGuest.getId())
                    .name(updateGuestDto.getName() != null ? updateGuestDto.getName() : existingGuest.getName())
                    .createdAt(existingGuest.getCreatedAt())
                    .mainFoot(updateGuestDto.getMainFoot() != null ? updateGuestDto.getMainFoot() : existingGuest.getMainFoot())
                    .position(updateGuestDto.getPosition() != null ? updateGuestDto.getPosition() : existingGuest.getPosition())
                    .isAvailable(updateGuestDto.isAvailable())
                    .build();

            return guestRepository.save(updatedGuest);
        } else {
            throw new IllegalArgumentException("Guest not found");
        }
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
