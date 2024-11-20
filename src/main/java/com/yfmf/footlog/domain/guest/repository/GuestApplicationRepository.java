package com.yfmf.footlog.domain.guest.repository;

import com.yfmf.footlog.domain.guest.entity.GuestApplication;
import com.yfmf.footlog.domain.guest.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestApplicationRepository extends JpaRepository<GuestApplication, Long> {
    List<GuestApplication> findByRecruitmentId(Long recruitmentId);
    List<GuestApplication> findByApplicantId(Long applicantId);
    boolean existsByRecruitmentIdAndApplicantId(Long recruitmentId, Long applicantId);
    List<GuestApplication> findByRecruitmentIdAndStatus(Long recruitmentId, ApplicationStatus status);
}

