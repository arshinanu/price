package com.cat.stocks.price.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.cat.stocks.price.controller.Response.StockInfoResponse;
import com.cat.stocks.price.controller.Response.StockPriceResponse;
import com.cat.stocks.price.controller.request.StockPriceRequest;
import com.cat.stocks.price.controller.request.StockRequest;
import com.cat.stocks.price.domain.Company;
import com.cat.stocks.price.domain.Stock;
import com.cat.stocks.price.repositories.StockRepository;
/*import com.cat.stocks.price.util.FeignServiceUtil;
import com.cat.stocks.price.util.JsonFeignResponseUtil;*/
import com.fasterxml.jackson.databind.ObjectMapper;
/*import feign.Response;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class StockServiceImplementation implements StockService{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    StockRepository stockRepository;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public Stock createStockPrice(StockPriceRequest stockPriceRequest) {

        return stockRepository.save(buildStockPrice(stockPriceRequest));
    }

    /*@Override
    public StockPriceResponse fetchCompanyStock(String companyCode) {
        Object clazz;
        clazz= Company.class;
        StockPriceResponse.StockPriceResponseBuilder stockPriceResponse=StockPriceResponse.builder();
        Response response= feignServiceUtil.getCompany(companyCode);
        ResponseEntity<Object> responseEntity = JsonFeignResponseUtil.toResponseEntity(response, clazz);
        Object responseBody = responseEntity.getBody();
        stockPriceResponse.stock(stockRepository.getLatestStockDetails(companyCode)).company((Company) responseBody);
        return stockPriceResponse.build();
    }*/

    public StockPriceResponse fetchCompanyStock(String companyCode) {

        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity httpHeaders=new HttpEntity<String>("parameters",headers);
        System.out.println("calling Company service");

        Object clazz;
        clazz= Company.class;
        ResponseEntity<Object> responseEntity=restTemplate.exchange("http://company-service/api/v1.0/market/company/info/"+companyCode, HttpMethod.GET,httpHeaders,Object.class);
        /*Map response = new HashMap<>();*/
        Company response = objectMapper
            .convertValue(
                responseEntity.getBody(), Company.class);

        StockPriceResponse.StockPriceResponseBuilder stockPriceResponse=StockPriceResponse.builder();
        stockPriceResponse.stock(stockRepository.getLatestStockDetails(companyCode)).company(response);
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

    @Override
    @Transactional
    public void deleteStockForCompany(String companyCode) {
        System.out.println("com"+companyCode);
        stockRepository.deleteStockForCompany(companyCode);
    }

    public Stock buildStockPrice(StockPriceRequest stockPriceRequest) {
        return (Stock.builder().companyCode(stockPriceRequest.getCompanyCode())
            .price(stockPriceRequest.getPrice()).stockPriceTime(stockPriceRequest.getStockPriceTime())
            .stockExchange(stockPriceRequest.getStockExchange())
            .stockPriceTime(LocalDateTime.now())
            .build());
    }


}
