package com.cat.stocks.price.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.cat.stocks.price.controller.Response.StockInfoResponse;
import com.cat.stocks.price.controller.Response.StockPriceResponse;
import com.cat.stocks.price.controller.request.StockPriceRequest;
import com.cat.stocks.price.controller.request.StockRequest;
import com.cat.stocks.price.domain.Company;
import com.cat.stocks.price.domain.Stock;
import com.cat.stocks.price.repositories.StockRepository;
import com.cat.stocks.price.util.FeignServiceUtil;
import com.cat.stocks.price.util.JsonFeignResponseUtil;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImplementation implements StockService{

    @Autowired
    StockRepository stockRepository;
    @Autowired
    FeignServiceUtil feignServiceUtil;

    @Override
    public Stock createStockPrice(StockPriceRequest stockPriceRequest) {

        return stockRepository.save(buildStockPrice(stockPriceRequest));
    }

    @Override
    public StockPriceResponse fetchCompanyStock(String companyCode) {
        Object clazz;
        clazz= Company.class;
        StockPriceResponse.StockPriceResponseBuilder stockPriceResponse=StockPriceResponse.builder();
        Response response= feignServiceUtil.getCompany(companyCode);
        ResponseEntity<Object> responseEntity = JsonFeignResponseUtil.toResponseEntity(response, clazz);
        Object responseBody = responseEntity.getBody();
        stockPriceResponse.stock(stockRepository.getLatestStockDetails(companyCode)).company((Company) responseBody);
        return stockPriceResponse.build();
    }

    @Override
    public List<Stock> fetchStockPrice(String companyCode) {
        return stockRepository.getCompanyStockInfo(companyCode);
    }

    @Override
    public List<Stock> fetchAllLatestStocks() {
        List<Stock> allStocks=stockRepository.findAll();
        List<Integer> latestStockId=stockRepository.getMaxofStockId();
        return allStocks.stream().filter(p->latestStockId.stream().anyMatch(id->id==p.getSid())).toList();
    }

    @Override
    public StockInfoResponse retrieveStock(StockRequest stockRequest) {
        List<Stock> stocksTemp=stockRepository.getStockValues(stockRequest
            .getCompanyCode(),stockRequest.getStartStockDate(),stockRequest.getEndStockDate());
        return (StockInfoResponse.builder().stocks(stocksTemp).avgStockPrice(stocksTemp.stream().mapToDouble(
            Stock::getPrice).average().getAsDouble()).maxStockPrice(stocksTemp.stream().mapToDouble(
            Stock::getPrice).max().getAsDouble()).minStockPricek(stocksTemp.stream().mapToDouble(
            Stock::getPrice).min().getAsDouble()).build());
    }

    public Stock buildStockPrice(StockPriceRequest stockPriceRequest) {
        return (Stock.builder().companyCode(stockPriceRequest.getCompanyCode())
            .price(stockPriceRequest.getPrice()).stockPriceTime(stockPriceRequest.getStockPriceTime())
            .stockExchange(stockPriceRequest.getStockExchange())
            .stockPriceTime(LocalDateTime.now())
            .build());
    }


}
