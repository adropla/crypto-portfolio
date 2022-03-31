package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.Portfolio;
import com.cryptolisting.springreactjs.models.Transaction;
import com.cryptolisting.springreactjs.models.TransactionRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PortfolioRepository portfolioRepository;

    public ResponseEntity<?> save(TransactionRequest request) {

        Integer portfolioId = request.getPortfolio();
        Optional<Portfolio> portfolioOptional = portfolioRepository.findById(portfolioId);
        portfolioOptional.orElseThrow(EntityNotFoundException::new);

        Portfolio portfolio = portfolioOptional.get();
        if (portfolioId.equals(portfolio.getId())) {
            Transaction transaction = new Transaction();
            transaction.setDate(request.getDate());
            transaction.setPortfolio(request.getPortfolio());
            transaction.setPrice(request.getPrice());
            transaction.setQuantity(request.getQuantity());
            transaction.setType(request.getType());
            transaction.setPair(request.getPair());

            transactionRepository.save(transaction);
            return ResponseEntity.ok(request + " was successfully saved!");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> delete(Integer id) {
        transactionRepository.deleteById(id);

        return ResponseEntity.ok("Transaction #" + id + " was successfully deleted.");
    }

    public ResponseEntity<?> loadAllByPortfolioId(Integer portfolioId) {
        List<Transaction> list = transactionRepository.findByPortfolio(portfolioId);
        if (list.size() == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Gson gson = new Gson();
        String json = gson.toJson(list);
        return ResponseEntity.ok(json);
    }

    public List<Transaction> getTransactions(Integer portfolioId) {
        return transactionRepository.findByPortfolio(portfolioId);
    }
}
