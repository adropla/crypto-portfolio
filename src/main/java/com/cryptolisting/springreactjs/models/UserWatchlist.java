package com.cryptolisting.springreactjs.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "watchlists")
public class UserWatchlist {
    @Id
    private int id;

    @Column
    private String watchlist;

    public UserWatchlist() {
    }

    public UserWatchlist(int id, String watchlist) {
        this.id = id;
        this.watchlist = watchlist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(String watchlist) {
        this.watchlist = watchlist;
    }
}
