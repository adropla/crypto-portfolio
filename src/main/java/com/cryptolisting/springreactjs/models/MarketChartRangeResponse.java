package com.cryptolisting.springreactjs.models;

import java.util.Arrays;

public class MarketChartRangeResponse {
    private Object[][] prices;
    private Object[][] market_caps;
    private Object[][] total_volumes;

    public MarketChartRangeResponse() {
    }

    public MarketChartRangeResponse(Object[][] prices, Object[][] market_caps, Object[][] total_volumes) {
        this.prices = prices;
        this.market_caps = market_caps;
        this.total_volumes = total_volumes;
    }

    @Override
    public String toString() {
        return "MarketChartRangeResponse{" +
                "prices=" + Arrays.deepToString(prices) +
                ", market_caps=" + Arrays.deepToString(market_caps) +
                ", total_volumes=" + Arrays.deepToString(total_volumes) +
                '}';
    }

    public Object[][] getPrices() {
        return prices;
    }

    public Object[][] getMarket_caps() {
        return market_caps;
    }

    public Object[][] getTotal_volumes() {
        return total_volumes;
    }
}
