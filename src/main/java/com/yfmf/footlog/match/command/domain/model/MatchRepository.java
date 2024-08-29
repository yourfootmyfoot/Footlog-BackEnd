package com.yfmf.footlog.match.command.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MatchRepository extends JpaRepository<Match, Long> {
}
