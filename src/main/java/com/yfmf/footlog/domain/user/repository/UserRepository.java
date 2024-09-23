package com.yfmf.footlog.domain.user.repository;


import com.yfmf.footlog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class UserRepository implements JpaRepository<User, Long> {
}
