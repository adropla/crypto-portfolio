package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    Optional<Portfolio> findById(Integer id);
    List<Portfolio> findByEmail(String email);
}
