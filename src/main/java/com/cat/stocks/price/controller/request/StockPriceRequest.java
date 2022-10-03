package com.cat.stocks.price.controller.request;

import javax.persistence.Column;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockPriceRequest {

    private String companyCode;
    private double price;
    private LocalDateTime stockPriceTime;
    private String stockExchange;

}
