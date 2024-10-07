package com.yfmf.footlog.domain.guest.service;

import com.yfmf.footlog.domain.guest.entity.Guest;
import com.yfmf.footlog.domain.guest.repository.GuestRepository;
import com.yfmf.footlog.domain.guest.dto.GuestSaveRequestDto;
import com.yfmf.footlog.domain.guest.dto.GuestUpdateRequestDto;
import com.yfmf.footlog.error.ApplicationException;
import com.yfmf.footlog.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    @Transactional
    public Guest registerGuest(Long memberId, GuestSaveRequestDto requestDto) {
        Guest guest = Guest.builder()
                .memberId(memberId)
                .location(requestDto.getLocation())
                .age(requestDto.getAge())
                .scheduleDate(requestDto.getScheduleDate())
                .scheduleDay(requestDto.getScheduleDate() != null ? requestDto.getScheduleDate().getDayOfWeek() : null)
                .scheduleStartTime(requestDto.getScheduleStartTime())
                .scheduleEndTime(requestDto.getScheduleEndTime())
                .specialRequests(requestDto.getSpecialRequests())
                .build();

        return guestRepository.save(guest);
    }

    @Transactional(readOnly = true)
    public List<Guest> findAllGuests() {
        return guestRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Guest> findAvailableGuests(LocalDateTime date) {
        return guestRepository.findByScheduleDateGreaterThanEqual(date);
    }

    @Transactional(readOnly = true)
    public Guest findGuestById(Long guestId) {
        return guestRepository.findById(guestId)
                .orElseThrow(() -> new ApplicationException(com.yfmf.footlog.error.ErrorCode.GUEST_NOT_FOUND, "[GuestService] GUEST_NOT_FOUND"));
    }

    @Transactional
    public Guest updateGuest(Long guestId, GuestUpdateRequestDto requestDto) {
        Guest guest = findGuestById(guestId);

        guest.update(
                requestDto.getLocation(),
                requestDto.getAge(),
                requestDto.getScheduleDate(),
                requestDto.getScheduleDate() != null ? requestDto.getScheduleDate().getDayOfWeek() : null,
                requestDto.getScheduleStartTime(),
                requestDto.getScheduleEndTime(),
                requestDto.getSpecialRequests()
        );

        return guestRepository.save(guest);
    }

    @Transactional
    public Guest updateGuestAvailability(Long guestId, boolean available) {
        Guest guest = findGuestById(guestId);
        guest.updateAvailability(available);
        return guestRepository.save(guest);
    }

    @Transactional
    public void deleteGuest(Long guestId) {
        if (!guestRepository.existsById(guestId)) {
            throw new ApplicationException(ErrorCode.GUEST_NOT_FOUND, "[GuestService] GUEST_NOT_FOUND");
        }
        guestRepository.deleteById(guestId);
    }

    @Transactional(readOnly = true)
    public List<Guest> findGuestsByLocation(String location) {
        return guestRepository.findByLocation(location);
    }

    @Transactional(readOnly = true)
    public List<Guest> findGuestsByAgeRange(int minAge, int maxAge) {
        return guestRepository.findByAgeBetween(minAge, maxAge);
    }
}
