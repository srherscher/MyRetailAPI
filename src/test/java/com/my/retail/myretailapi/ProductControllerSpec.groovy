package com.my.retail.myretailapi

import com.my.retail.myretailapi.businesslogic.Product
import com.my.retail.myretailapi.controller.ProductController
import com.my.retail.myretailapi.data.PriceVO
import com.my.retail.myretailapi.data.ProductVO
import spock.lang.Specification

class ProductControllerSpec extends Specification {
    ProductController productController = new ProductController()
    Product product = Mock()

    def testGetResponse() {
        given:
        productController.setRequiredProductReader(product)

        when:
        ProductVO productVO = productController.getProductByID(123)

        then:
        1 * product.getResponse('https://redsky.target.com/v2/pdp/tcin/', 123) >> new ProductVO(123, "Test", 12.45, "USD")
        0 * _

        and:
        productVO != null
        productVO.getName() == "Test"
    }

    def testPutProductByID() {
        given:
        productController.setRequiredProductReader(product)

        when:
        PriceVO priceVO = productController.putProductByID(1234, "testBody")

        then:
        1 * product.applyPutRequest(1234, 'testBody') >> new PriceVO(1234, 12.45, "USD")
        0 * _

        and:
        priceVO
        priceVO.getId() == 1234

    }

    def testGetAllPriceIDs() {
        given:
        productController.setRequiredProductReader(product)
        List<Integer> listOfIntegers = new ArrayList<>()
        listOfIntegers.add(1234)
        listOfIntegers.add(5678)

        when:
        List<Integer> idList = productController.getAllPriceIDs()

        then:
        1 * product.getAllIDs() >> listOfIntegers
        0 * _

        and:
        idList
        idList.size() == 2
        idList.get(0) == 1234
        idList.get(1) == 5678
    }
}
