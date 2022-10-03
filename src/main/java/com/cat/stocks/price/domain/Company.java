package com.cat.stocks.price.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private int  cid;
    private String companyCode;
    private String companyName;
    private String companyCeo;
    private double companyTurnOver;
    private String companyWebsite;
    private String stockExchange;
}
