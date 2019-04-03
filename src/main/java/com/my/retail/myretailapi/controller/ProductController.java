package com.my.retail.myretailapi.controller;

import com.my.retail.myretailapi.businesslogic.ProductReader;
import com.my.retail.myretailapi.data.PriceVO;
import com.my.retail.myretailapi.data.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    String targetURL = "https://redsky.target.com/v2/pdp/tcin/";
    @Autowired
    ProductReader productReader;

    public void setRequiredProductReader(ProductReader productReader) {
        this.productReader = productReader;
    }

    @GetMapping("/v2/products/")
    public List<Integer> getAllPriceIDs() {
        List<Integer> allIDs = productReader.getAllIDs();
        return allIDs;
    }

    @GetMapping("/v2/products/{productID}")
    public ProductVO getProductByID(@PathVariable(value = "productID") long productID) {
        ProductVO productVOResponse = productReader.getResponse(targetURL, productID);
        return productVOResponse;
    }

    @PutMapping("/v2/products/{productID}")
    public PriceVO putProductByID(@PathVariable(value = "productID") long productID, @RequestBody String body) {
        PriceVO priceVO = productReader.applyPutRequest(productID, body);
        return priceVO;
    }
}
