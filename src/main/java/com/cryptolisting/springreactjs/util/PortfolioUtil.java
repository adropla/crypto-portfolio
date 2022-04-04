package com.cryptolisting.springreactjs.util;

import com.cryptolisting.springreactjs.models.HistoricalPrice;
import com.cryptolisting.springreactjs.models.MarketChartRangeResponse;
import com.cryptolisting.springreactjs.models.Transaction;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PortfolioUtil {

    public HashMap<String, List<HistoricalPrice>> getHistoricalPrices(List<Transaction> transactions) {
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
            currencies.add(currency);
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
            assert response != null;
            Object[][] temp = response.getPrices();
            for (Object[] obj : temp) {
                HistoricalPrice tempPrice = new HistoricalPrice();
                Object rawDate = obj[0];
                Long date = null;

                if (rawDate instanceof Double) {
                    date = ((Double) rawDate).longValue();
                }

                Double price;
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

    public TreeMap<Long, HashMap<String, Double>> getCurrenciesQuantities(List<Transaction> transactions) {
        List<Transaction> sortedTransactions = transactions.stream()
                .sorted(Comparator.comparing(Transaction::getDate))
                .collect(Collectors.toList());

        TreeMap<Long, HashMap<String, Double>> quantites = new TreeMap<>();
        HashMap<String, Double> current = new HashMap<>();

        for (Transaction transaction : sortedTransactions) {
            Long date = transaction.getDate();
            String type = transaction.getType();
            Double transactionQuantity = transaction.getQuantity();
            String currency = transaction.getPair().split(",")[0];
            HashMap<String, Double> data;

            if (quantites.containsKey(date)) {
                data = quantites.get(date);

            } else {
                data = new HashMap<>(current);
            }

            if (data.containsKey(currency)) {
                Double quantity = data.get(currency);
                if (type.equals("sell")) {
                    quantity -= transactionQuantity;
                } else if (type.equals("buy")) {
                    quantity += transactionQuantity;
                }
                data.put(currency, quantity);
            } else {
                data.put(currency, transactionQuantity);
            }

            quantites.put(date, data);
            current.putAll(data);
        }
        return quantites;
    }


    public TreeMap<Long, Double> getPortfolioHistoryValue(HashMap<String, List<HistoricalPrice>> prices, TreeMap<Long, HashMap<String, Double>> quantities) {

            TreeMap<Long, Double> portfolioValues = new TreeMap<>();
        try {
            List<Long> dates = new ArrayList<>(quantities.keySet());
            int currentDateIndex = 0;

            // prices идут в порядке возрастания, quantities - тоже
            Long currentDate = dates.get(0) * 1000;
            System.out.println(dates);
            HashMap<String, Double> currentQ = quantities.get(currentDate / 1000);


            for (Map.Entry<String, List<HistoricalPrice>> entry : prices.entrySet()) {
                System.out.println(entry);
                String currency = entry.getKey();
                List<HistoricalPrice> pricesList = entry.getValue();
                for (HistoricalPrice data : pricesList) {
                    System.out.println(currentQ);
                    Long date = data.getDate();
                    Double currencyPrice = data.getPrice();
                    if (currentDateIndex < dates.size() - 1 && date >= dates.get(currentDateIndex + 1) * 1000) {
                        currentDateIndex++;
                        currentDate = dates.get(currentDateIndex) * 1000;
                        currentQ = quantities.get(currentDate / 1000);
                    }
                    Double currencyQuantity = currentQ.get(currency);
                    if (currencyQuantity == null)
                        continue;

                    double portfolioPrice = 0.0;

                    if (portfolioValues.containsKey(date)) {
                        portfolioPrice = portfolioValues.get(date);
                    }
                    portfolioPrice += currencyPrice * currencyQuantity;
                    if (portfolioPrice < 0) {
                        portfolioPrice = 0.0;
                    }
                    portfolioValues.put(date, portfolioPrice);
                }
                currentDateIndex = 0;
                currentDate = dates.get(0) * 1000;
                currentQ = quantities.get(currentDate / 1000);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return portfolioValues;
    }
}
