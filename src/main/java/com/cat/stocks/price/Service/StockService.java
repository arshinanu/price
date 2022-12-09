package com.cat.stocks.price.Service;

import java.util.List;

import com.cat.stocks.price.controller.Response.StockInfoResponse;
import com.cat.stocks.price.controller.Response.StockPriceResponse;
import com.cat.stocks.price.controller.request.StockPriceRequest;
import com.cat.stocks.price.controller.request.StockRequest;
import com.cat.stocks.price.domain.Stock;
import org.springframework.stereotype.Service;

@Service
public interface StockService {

    Stock createStockPrice(StockPriceRequest stockPriceRequest);
    StockPriceResponse fetchCompanyStock(String companyCode);
    List<Stock> fetchStockPrice(String companyCode);
    List<Stock> fetchAllLatestStocks();
    StockInfoResponse retrieveStock(StockRequest stockRequest);
    void deleteStockForCompany(String companyCode);
}
