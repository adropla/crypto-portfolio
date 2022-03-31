package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.Transaction;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @NonNull Optional<Transaction> findById(@NonNull Integer id);
    List<Transaction> findByPortfolio(Integer portfolio);
}
