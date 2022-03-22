package com.cryptolisting.springreactjs.models;

public class WatchlistRequest {
    private String watchlist;

    public WatchlistRequest() {
    }

    public WatchlistRequest(String watchlist) {
        this.watchlist = watchlist;
    }


    public String getWatchlist() {
        return watchlist;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
