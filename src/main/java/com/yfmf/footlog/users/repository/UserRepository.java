package com.yfmf.footlog.users.repository;

import com.yfmf.footlog.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
