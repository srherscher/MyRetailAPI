package com.my.retail.myretailapi

import com.my.retail.myretailapi.businesslogic.ProductReader
import com.my.retail.myretailapi.controller.ProductController
import com.my.retail.myretailapi.data.PriceVO

import com.my.retail.myretailapi.data.ProductVO
import spock.lang.Specification

class ProductControllerSpec extends Specification{
    ProductController productController = new ProductController()
    ProductReader productReader = Mock()

    def test1(){
        given:
//        ProductVO productVO = new ProductVO(new ProductInfoVO(123), "Test", new PriceVO())
        productController.setRequiredProductReader(productReader)

        when:
        ProductVO productVO = productController.getProductByID(123)

        then:
        1 * productReader.getJSONResponse('https://redsky.target.com/v2/pdp/tcin/', 123) >> new ProductVO(123, "Test", new PriceVO())
        0 * _

        and:
        productVO != null
        productVO.getProductInfo().getName() == "Test"
    }
}
