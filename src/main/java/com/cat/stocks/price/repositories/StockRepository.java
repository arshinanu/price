package com.cat.stocks.price.repositories;

import java.time.LocalDateTime;
import java.util.List;


import static com.cat.stocks.price.domain.QueryConstant.StockQuery;

import com.cat.stocks.price.controller.request.StockRequest;
import com.cat.stocks.price.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    @Query(value = "select s from Stock s where s.companyCode=:companyCode and s.sid in ( select max(sn.sid) from  "
    +" Stock sn where sn.companyCode=:companyCode )")
    public Stock getLatestStockDetails(String companyCode);

    @Query("from Stock where company_code=:companyCode")
    public List<Stock> getCompanyStockInfo(String companyCode);

   /* @Query(value = "select bs from stock bs inner join "
                    + "( select max(s.sid) as cid ,s.company_code from stock s group by s.company_code ) "
                    +" a on a.cid=bs.sid ")
    public List<Stock> getAllStocks();*/
    @Query(value = "select max(s.sid) as cid ,s.companyCode from Stock s group by s.companyCode")
     List<Integer> getMaxofStockId();

    @Query(value = "select s from Stock s where s.companyCode=:companyCode "
        +"and s.stockPriceTime between :startStockDate and :endStockDate  "  )
    List<Stock> getStockValues(String companyCode, LocalDateTime startStockDate,LocalDateTime endStockDate );

    @Modifying
    @Query("Delete from Stock s where s.companyCode=:companyCode")
    void deleteStockForCompany(String companyCode);
}
