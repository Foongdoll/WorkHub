package com.foongdoll.portfolio.backend.auth.repository;

import com.foongdoll.portfolio.backend.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
