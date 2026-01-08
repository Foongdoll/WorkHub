package com.foongdoll.portfolio.backend.company.repository;

import com.foongdoll.portfolio.backend.company.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMemberShipRepository extends JpaRepository<UserMembership, Long> {
}
