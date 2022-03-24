package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.Transaction;
import com.cryptolisting.springreactjs.models.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public ResponseEntity<?> save(TransactionRequest request) {

        Transaction transaction = new Transaction();
        transaction.setDate(request.getDate());
        transaction.setPortfolio(request.getPortfolio());
        transaction.setPrice(request.getPrice());
        transaction.setQuantity(request.getQuantity());
        transaction.setType(request.getType());
        transaction.setPair(request.getPair());

        transactionRepository.save(transaction);

        return ResponseEntity.ok(request.toString() + " was successfully saved!");
    }

    public ResponseEntity<?> delete(Integer id) {
        transactionRepository.deleteById(id);

        return ResponseEntity.ok("Transaction #" + id + " was successfully deleted.");
    }

    public ResponseEntity<?> loadAllByPortfolioId(String portfolio) {
        List<Transaction> list = transactionRepository.findByPortfolio(portfolio);
        if (list.size() == 0)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        StringBuilder str = new StringBuilder();

        list.forEach((transaction) -> {
            str.append(transaction.toString());
        });

        return ResponseEntity.ok(list.toString());
    }
}
