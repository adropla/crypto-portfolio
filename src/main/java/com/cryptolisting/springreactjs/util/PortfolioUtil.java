package com.cryptolisting.springreactjs.util;

import com.cryptolisting.springreactjs.models.HistoricalPrice;
import com.cryptolisting.springreactjs.models.MarketChartRangeResponse;
import com.cryptolisting.springreactjs.models.Transaction;
import com.cryptolisting.springreactjs.service.TransactionService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

@Service
public class PortfolioUtil {

    @Autowired
    private TransactionService transactionService;

    public HashMap<String, List<HistoricalPrice>> getHistoricalValues(List<Transaction> transactions) {
        Long firstDate = getFirstDate(transactions);
        HashSet<String> currencies = getCurrencies(transactions);

        HashMap<String, List<HistoricalPrice>> historicalData = new HashMap<>();

        for (String currency : currencies) {
            List<HistoricalPrice> temp = getHistoricalData(currency, firstDate, System.currentTimeMillis());
            historicalData.put(currency, temp);
        }

        return historicalData;
    }

    public Long getFirstDate (List<Transaction> transactions) {
        Long date = System.currentTimeMillis() / 1000L;

        for (Transaction transaction : transactions) {
            Long temp = transaction.getDate();
            if (temp < date) {
                date = temp;
            }
        }

        return date;
    }

    public HashSet<String> getCurrencies(List<Transaction> transactions) {
        HashSet<String> currencies = new HashSet<>();

        for (Transaction transaction : transactions) {
            String currency = transaction.getPair().split(",")[0];
            if (!currencies.contains(currency)) {
                currencies.add(currency);
            }
        }
        return currencies;
    }

    public List<HistoricalPrice> getHistoricalData(String currency, Long startDate, Long endDate) {
        String httpsUrl = String.format("https://api.coingecko.com/api/v3/coins/%s/market_chart/range?vs_currency=usd&from=%d&to=%d", currency, startDate, endDate);

        URL url;
        StringBuilder strb = new StringBuilder();
        try {

            url = new URL(httpsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(con.getInputStream()));

            String input;

            while ((input = br.readLine()) != null){
                strb.append(input);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String json = strb.toString();

        Gson gson = new Gson();
        List<HistoricalPrice> prices = new ArrayList<>();
        try {
            MarketChartRangeResponse response = gson.fromJson(json, MarketChartRangeResponse.class);
            Object[][] temp = response.getPrices();
            for (Object[] obj : temp) {
                HistoricalPrice tempPrice = new HistoricalPrice();
                Object rawDate = obj[0];
                Long date = null;

                if (rawDate instanceof Double) {
                    date = ((Double) rawDate).longValue();
                }

                Double price = null;
                price = (Double) obj[1];
                tempPrice.setDate(date);
                tempPrice.setPrice(price);
                prices.add(tempPrice);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return prices;
    }

}
