package com.cat.stocks.price.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    @Id
    @Column(name = "sid", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int sid;

    private String companyCode;

    private double price;
    private LocalDateTime stockPriceTime;
    @Column(name = "stock_exchange")
    private String stockExchange;




}
