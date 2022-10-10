/*
package com.cat.stocks.price.util;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "feignDemo",url="http://localhost:8088/api/v1.0/market/company")
public interface FeignServiceUtil {

    @GetMapping("/info/{companyCode}")
    Response getCompany(@PathVariable(required = false) String companyCode);
}
*/
