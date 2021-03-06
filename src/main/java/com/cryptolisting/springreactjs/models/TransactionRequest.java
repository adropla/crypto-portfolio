package com.cryptolisting.springreactjs.models;

public class TransactionRequest {

    private Integer portfolio;
    private String pair;
    private Long date;
    private Double price;
    private Double quantity;
    private String type;

    public TransactionRequest() {
    }

    public TransactionRequest(Integer portfolio, String pair, Long date, Double price, Double quantity, String type) {
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

    public Integer getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Integer portfolio) {
        this.portfolio = portfolio;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
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
