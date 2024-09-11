package com.yfmf.footlog.domain.match.repository;

import com.yfmf.footlog.domain.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MatchRepository extends JpaRepository<Match, Long> {
}
