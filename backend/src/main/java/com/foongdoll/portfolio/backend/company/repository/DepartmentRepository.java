package com.foongdoll.portfolio.backend.company.repository;

import com.foongdoll.portfolio.backend.company.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
