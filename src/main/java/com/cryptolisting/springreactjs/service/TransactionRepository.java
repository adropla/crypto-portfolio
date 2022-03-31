package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findById(Integer id);
    List<Transaction> findByPortfolio(Integer portfolio);
}
