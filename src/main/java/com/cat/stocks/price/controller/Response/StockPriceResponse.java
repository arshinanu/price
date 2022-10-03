package com.cat.stocks.price.controller.Response;

import com.cat.stocks.price.domain.Company;
import com.cat.stocks.price.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockPriceResponse {
    private Company company;
    private Stock stock;


}
