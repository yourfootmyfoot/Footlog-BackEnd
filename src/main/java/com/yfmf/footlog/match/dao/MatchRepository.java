package com.yfmf.footlog.match.dao;

import com.yfmf.footlog.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MatchRepository extends JpaRepository<Match, Long> {
}
