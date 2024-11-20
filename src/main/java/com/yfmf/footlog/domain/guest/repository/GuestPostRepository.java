package com.yfmf.footlog.domain.guest.repository;

import com.yfmf.footlog.domain.guest.entity.GuestPost;
import com.yfmf.footlog.domain.guest.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GuestPostRepository extends JpaRepository<GuestPost, Long> {
    List<GuestPost> findByStatus(PostStatus status);
    List<GuestPost> findByMemberId(Long memberId);
    List<GuestPost> findByAvailableDateGreaterThanEqual(LocalDate date);
    Page<GuestPost> findAll(Pageable pageable);
}
