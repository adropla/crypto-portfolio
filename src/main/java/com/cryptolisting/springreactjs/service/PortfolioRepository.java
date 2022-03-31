package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.Portfolio;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    @NonNull Optional<Portfolio> findById(@NonNull Integer id);
    List<Portfolio> findByEmail(String email);
}
