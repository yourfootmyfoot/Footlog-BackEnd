package com.yfmf.footlog.domain.club;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ClubRepository {

    @PersistenceContext
    private EntityManager manager;

    public void save(Club club) {
        manager.persist(club);
    }
}
