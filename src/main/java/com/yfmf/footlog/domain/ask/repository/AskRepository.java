package com.yfmf.footlog.domain.ask.repository;

import com.yfmf.footlog.domain.ask.entity.Ask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AskRepository extends JpaRepository<Ask, Long> {
}
