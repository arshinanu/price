package com.cat.stocks.price.domain;

public final class  QueryConstant {
    public static final String StockQuery ="""
        select bs from stock bs inner join (select max(s.sid) as cid ,s.company_code
                                                	  from stock s group by s.company_code) a on a.cid=bs.sid """;
}
