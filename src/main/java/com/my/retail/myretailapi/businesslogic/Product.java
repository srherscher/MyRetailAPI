package com.my.retail.myretailapi.businesslogic;

import com.my.retail.myretailapi.data.PriceVO;
import com.my.retail.myretailapi.data.ProductVO;

import java.util.List;

public interface Product {
    ProductVO getResponse(String targetURL, long id);

    PriceVO applyPutRequest(long id, String body);

    List<Integer> getAllIDs();
}
