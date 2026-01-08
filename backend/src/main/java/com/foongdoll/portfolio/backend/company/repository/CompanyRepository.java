package com.foongdoll.portfolio.backend.company.repository;

import com.foongdoll.portfolio.backend.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {
    Optional<List<Company>> findByBizNo(String bizNo);
}
