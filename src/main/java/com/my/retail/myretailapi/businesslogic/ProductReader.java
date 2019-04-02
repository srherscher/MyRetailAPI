package com.my.retail.myretailapi.businesslogic;

import com.my.retail.myretailapi.data.PriceVO;
import com.my.retail.myretailapi.data.ProductVO;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public interface ProductReader {
    ProductVO getResponse(String targetURL, long id);

    void applyPutRequest(long id, String body);

    List<Integer> getAllIDs();

}
