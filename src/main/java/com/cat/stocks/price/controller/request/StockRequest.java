package com.cat.stocks.price.controller.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockRequest {

    private String companyCode;
    private LocalDateTime startStockDate;
    private LocalDateTime endStockDate;
}
