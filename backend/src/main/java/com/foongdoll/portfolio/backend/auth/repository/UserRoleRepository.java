package com.foongdoll.portfolio.backend.auth.repository;

import com.foongdoll.portfolio.backend.auth.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    boolean existsByUserIdAndRoleId(Long id, Long id1);
}
