package com.cryptolisting.springreactjs.models;

public class TransactionRequest {

    private String portfolio;
    private String pair;
    private String date;
    private Double price;
    private Double quantity;
    private String type;

    public TransactionRequest() {
    }

    public TransactionRequest(String portfolio, String pair, String date, Double price, Double quantity, String type) {
        this.portfolio = portfolio;
        this.pair = pair;
        this.date = date;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "portfolio='" + portfolio + '\'' +
                ", pair='" + pair + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", type='" + type + '\'' +
                '}';
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
