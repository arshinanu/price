package com.cat.stocks.price.controller;

import com.cat.stocks.price.Service.StockService;
import com.cat.stocks.price.controller.Response.StockPriceResponse;
import com.cat.stocks.price.controller.request.StockPriceRequest;
import com.cat.stocks.price.controller.request.StockRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1.0/market/stock")
public class StockController {

    @Autowired
    StockService stockService;

    @PostMapping("/add/{companyCode}")
    public ResponseEntity<Object> createStockPrice(@PathVariable(required = false) String companyCode,
                                                   @RequestBody StockPriceRequest stockPriceRequest){
        stockPriceRequest.setCompanyCode(companyCode);
        return ResponseEntity.status(200).body(stockService.createStockPrice(stockPriceRequest));
    }

    @GetMapping("/find/{companyCode}")
    public ResponseEntity<StockPriceResponse> getCompany(@PathVariable(required = false) String companyCode)
    {
        System.out.println(companyCode);
        return ResponseEntity.status(200).body(stockService.fetchCompanyStock(companyCode));

    }

    @GetMapping("/getall")
    public ResponseEntity<Object> getStockOfAll()
    {
        return ResponseEntity.status(200).body(stockService.fetchAllLatestStocks());
    }

    @PostMapping(value = "/getStocks")
    public ResponseEntity<Object> getStockInfo(@RequestBody StockRequest stockRequest)
    {
        return ResponseEntity.status(201).body(stockService.retrieveStock(stockRequest));
    }
}
