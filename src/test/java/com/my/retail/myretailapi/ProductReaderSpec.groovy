package com.my.retail.myretailapi

import com.my.retail.myretailapi.businesslogic.Connection
import com.my.retail.myretailapi.businesslogic.ProductReader
import com.my.retail.myretailapi.businesslogic.impl.ProductReaderImpl
import com.my.retail.myretailapi.controller.ProductController
import com.my.retail.myretailapi.data.ProductVO
import org.json.JSONObject
import spock.lang.Specification

class ProductReaderSpec extends Specification{
    ProductReader productReader = new ProductReaderImpl()
    Connection connection = Mock()

    def testGetProductNameFromJSON(){
        given:
        JSONObject jsonObject = new JSONObject("{\"item\": { \"product_description\": {\n" +
                "                \"title\": \"The Big Lebowski (Blu-ray)\",\n" +
                "                \"bullet_description\": [\n" +
                "                    \"<B>Movie Studio:</B> Universal Studios\",\n" +
                "                    \"<B>Movie Genre:</B> Comedy\",\n" +
                "                    \"<B>Software Format:</B> Blu-ray\"\n" +
                "                ]\n" +
                "            }}}")

        when:
        String title = productReader.getProductNameFromJSON(jsonObject)

        then:
        title
        title == "The Big Lebowski (Blu-ray)"
    }

    def testGetPriceFromJSON_OnSale(){
        given:
        JSONObject jsonObject = new JSONObject("{\"price\": {\n" +
                "            \"partNumber\": \"13860428\",\n" +
                "            \"channelAvailability\": \"0\",\n" +
                "            \"listPrice\": {\n" +
                "                \"minPrice\": 0,\n" +
                "                \"maxPrice\": 0,\n" +
                "                \"price\": 19.98,\n" +
                "                \"formattedPrice\": \"\$19.98\",\n" +
                "                \"priceType\": \"MSRP\",\n" +
                "                \"null\": false\n" +
                "            },\n" +
                "            \"offerPrice\": {\n" +
                "                \"minPrice\": 0,\n" +
                "                \"maxPrice\": 0,\n" +
                "                \"price\": 14.99,\n" +
                "                \"formattedPrice\": \"\$14.99\",\n" +
                "                \"priceType\": \"Reg\",\n" +
                "                \"startDate\": 1552546800000,\n" +
                "                \"endDate\": 253402236000000,\n" +
                "                \"saveDollar\": \"4.99\",\n" +
                "                \"savePercent\": \"25\",\n" +
                "                \"eyebrow\": \"\",\n" +
                "                \"null\": false\n" +
                "            },\n" +
                "            \"ppu\": \"\",\n" +
                "            \"mapPriceFlag\": \"N\"\n" +
                "        }}")
        when:
        double price = productReader.getPriceFromJSON(jsonObject)

        then:
        price
        price == 14.99
    }

    def testGetPriceFromJSON_NotOnSale(){
        given:
        JSONObject jsonObject = new JSONObject("{\"price\": {\n" +
                "            \"partNumber\": \"13860428\",\n" +
                "            \"channelAvailability\": \"0\",\n" +
                "            \"listPrice\": {\n" +
                "                \"minPrice\": 0,\n" +
                "                \"maxPrice\": 0,\n" +
                "                \"price\": 19.98,\n" +
                "                \"formattedPrice\": \"\$19.98\",\n" +
                "                \"priceType\": \"MSRP\",\n" +
                "                \"null\": false\n" +
                "            },\n" +
                "            \"offerPrice\": {\n" +
                "                \"minPrice\": 0,\n" +
                "                \"maxPrice\": 0,\n" +
                "                \"price\": 19.98,\n" +
                "                \"formattedPrice\": \"\$14.99\",\n" +
                "                \"priceType\": \"Reg\",\n" +
                "                \"startDate\": 1552546800000,\n" +
                "                \"endDate\": 253402236000000,\n" +
                "                \"saveDollar\": \"4.99\",\n" +
                "                \"savePercent\": \"25\",\n" +
                "                \"eyebrow\": \"\",\n" +
                "                \"null\": false\n" +
                "            },\n" +
                "            \"ppu\": \"\",\n" +
                "            \"mapPriceFlag\": \"N\"\n" +
                "        }}")
        when:
        double price = productReader.getPriceFromJSON(jsonObject)

        then:
        price
        price == 19.98
    }

    def Test1(){
        given:
        productReader.setRequiredConnection(connection)
        int id = 13860428
        String targetURL = "testURL"

        when:
        ProductVO productVO = productReader.getJSONResponse(targetURL, id)

        then:
        1 * connection.getResponseFromURL(targetURL, id) >> "{\n" +
                "    \"product\": {\n" +
                "\"price\": {\n" +
                "            \"partNumber\": \"13860428\",\n" +
                "            \"channelAvailability\": \"0\",\n" +
                "            \"listPrice\": {\n" +
                "                \"minPrice\": 0,\n" +
                "                \"maxPrice\": 0,\n" +
                "                \"price\": 19.98,\n" +
                "                \"formattedPrice\": \"\$19.98\",\n" +
                "                \"priceType\": \"MSRP\",\n" +
                "                \"null\": false\n" +
                "            },\n" +
                "            \"offerPrice\": {\n" +
                "                \"minPrice\": 0,\n" +
                "                \"maxPrice\": 0,\n" +
                "                \"price\": 14.99,\n" +
                "                \"formattedPrice\": \"\$14.99\",\n" +
                "                \"priceType\": \"Reg\",\n" +
                "                \"startDate\": 1552546800000,\n" +
                "                \"endDate\": 253402236000000,\n" +
                "                \"saveDollar\": \"4.99\",\n" +
                "                \"savePercent\": \"25\",\n" +
                "                \"eyebrow\": \"\",\n" +
                "                \"null\": false\n" +
                "            },\n" +
                "            \"ppu\": \"\",\n" +
                "            \"mapPriceFlag\": \"N\"\n" +
                "        },\n" +
                " \"item\": {\"product_description\": {\n" +
                "                \"title\": \"The Big Lebowski (Blu-ray)\",\n" +
                "                \"bullet_description\": [\n" +
                "                    \"<B>Movie Studio:</B> Universal Studios\",\n" +
                "                    \"<B>Movie Genre:</B> Comedy\",\n" +
                "                    \"<B>Software Format:</B> Blu-ray\"\n" +
                "                ]\n" +
                "            } }\n" +
                "}}"
        0 * _

        and:
        productVO
        productVO.getProductInfo().getName() == "The Big Lebowski (Blu-ray)"
        productVO.getProductInfo().getProductID() == id
        productVO.getCurrentPrice().getPrice() == 14.99
    }

}
