package com.yfmf.footlog.domain.guest.repository;

import com.yfmf.footlog.domain.guest.entity.GuestRecruitment;
import com.yfmf.footlog.domain.guest.enums.RecruitmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GuestRecruitmentRepository extends JpaRepository<GuestRecruitment, Long> {
    List<GuestRecruitment> findByStatus(RecruitmentStatus status);
    List<GuestRecruitment> findByClubId(Long clubId);
    List<GuestRecruitment> findByMatchDateTimeGreaterThanEqual(LocalDateTime dateTime);
    Page<GuestRecruitment> findAll(Pageable pageable);
}
