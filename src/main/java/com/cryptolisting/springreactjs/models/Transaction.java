package com.cryptolisting.springreactjs.models;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer portfolio;

    @Column(nullable = false)
    private String pair;

    @Column(nullable = false)
    private Long date;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private String type;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", portfolio='" + portfolio + '\'' +
                ", pair='" + pair + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", type='" + type + '\'' +
                '}';
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
