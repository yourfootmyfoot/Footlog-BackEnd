package com.yfmf.footlog.match.repository;

import com.yfmf.footlog.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MatchRepository extends JpaRepository<Match, Long> {
}
