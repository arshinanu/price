package com.cat.stocks.price.controller.Response;

import java.util.List;

import com.cat.stocks.price.domain.Stock;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class StockInfoResponse {

    private List<Stock> stocks;
    private double maxStockPrice;
    private double minStockPricek;
    private double avgStockPrice;
}
