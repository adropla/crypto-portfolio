package com.cryptolisting.springreactjs.models;

public class HistoricalPrice {
    private Long date;
    private Double price;

    public HistoricalPrice() {
    }

    public HistoricalPrice(Long date, Double price) {
        this.date = date;
        this.price = price;
    }

    @Override
    public String toString() {
        return "HistoricalPrice{" +
                "date=" + date +
                ", price=" + price +
                '}';
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getDate() {
        return date;
    }

    public Double getPrice() {
        return price;
    }
}
