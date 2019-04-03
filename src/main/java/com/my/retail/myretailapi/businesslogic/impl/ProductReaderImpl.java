package com.my.retail.myretailapi.businesslogic.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.retail.myretailapi.businesslogic.Connection;
import com.my.retail.myretailapi.businesslogic.ProductReader;
import com.my.retail.myretailapi.businesslogic.ProductRepository;
import com.my.retail.myretailapi.data.PriceVO;
import com.my.retail.myretailapi.data.ProductVO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductReaderImpl implements ProductReader {

    @Autowired
    private ProductRepository repository;
    private Connection connection = new ConnectionImpl();
    private ObjectMapper mapper = new ObjectMapper();


    public List<Integer> getAllIDs() {
        List<Integer> allIDs = new ArrayList<>();
        for (PriceVO priceVO : repository.findAll()) {
            allIDs.add((int) priceVO.getId());
        }
        return allIDs;
    }

    //Add some sort of validation on the JSON you get?
    //Set response code?
    public ProductVO getResponse(String targetURL, long id) {
        ProductVO productVO = new ProductVO();
        try {
            String jsonString = connection.getResponseFromURL(targetURL, id);
            JSONObject jsonObject = new JSONObject(jsonString).getJSONObject("product");
            String productName = getProductNameFromJSON(jsonObject);
            PriceVO priceVO = repository.findByid(id);
            if (null == priceVO) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, id + ": not found");
            }
            productVO = new ProductVO(id, productName, priceVO.getPrice(), priceVO.getCurrencyType());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id + ": not found");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productVO;
    }

    private String getProductNameFromJSON(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("item").getJSONObject("product_description").getString("title");
    }

    public PriceVO applyPutRequest(long id, String body) {
        PriceVO currentPriceVO = repository.findByid(id);
        PriceVO newPriceVO = convertJSONToPriceVO(body);
        if (currentPriceVO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id + ": not found");
        }
        if (newPriceVO != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, id + ": incorrect JSON");
        }
        currentPriceVO.setPrice(newPriceVO.getPrice());
        currentPriceVO.setCurrencyType(newPriceVO.getCurrencyType());
        repository.save(newPriceVO);
        return newPriceVO;
    }

    private PriceVO convertJSONToPriceVO(String body) {
        PriceVO priceVO = null;
        try {
            priceVO = mapper.readValue(body, PriceVO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return priceVO;
    }

}
