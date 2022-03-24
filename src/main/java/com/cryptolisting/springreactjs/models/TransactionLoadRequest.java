package com.cryptolisting.springreactjs.models;

public class TransactionLoadRequest {

    private String portfolio;

    public TransactionLoadRequest() {
    }

    public TransactionLoadRequest(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getPortfolio() {
        return portfolio;
    }
}
