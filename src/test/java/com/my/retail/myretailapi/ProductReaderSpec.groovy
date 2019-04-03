package com.my.retail.myretailapi


import com.my.retail.myretailapi.businesslogic.Connection
import com.my.retail.myretailapi.businesslogic.PriceRepository
import com.my.retail.myretailapi.businesslogic.Product
import com.my.retail.myretailapi.businesslogic.impl.ProductImpl
import com.my.retail.myretailapi.data.PriceVO
import com.my.retail.myretailapi.data.ProductVO
import org.json.JSONObject
import org.springframework.web.server.ResponseStatusException
import spock.lang.Specification

class ProductReaderSpec extends Specification {
    Connection connection = Mock()
    PriceRepository repository = Mock()
    Product product = new ProductImpl(repository, connection)

    def testGetAllIDs() {
        given:
        List<PriceVO> priceVOList = new ArrayList<>()
        priceVOList.add(new PriceVO(123, 12.22, "USD"))
        priceVOList.add(new PriceVO(345, 14.12, "USD"))

        when:
        List<Integer> allIDs = product.getAllIDs()

        then:
        1 * repository.findAll() >> priceVOList
        0 * _

        and:
        allIDs
        allIDs.size() == 2
        allIDs.get(0) == 123
        allIDs.get(1) == 345
    }

    def testConvertJSONToPriceVO() {
        given:
        String jsonString = "{\n" +
                "    \"id\": 13860428,\n" +
                "    \"price\": 20.45,\n" +
                "    \"currencyType\": \"USD\"\n" +
                "}"

        when:
        PriceVO priceVO = product.convertJSONToPriceVO(jsonString)

        then:
        0 * _

        and:
        priceVO
        priceVO.getId() == 13860428
        priceVO.getPrice() == 20.45
        priceVO.getCurrencyType() == "USD"
    }

    def testGetProductNameFromJSON() {
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
        String title = product.getProductNameFromJSON(jsonObject)

        then:
        title
        title == "The Big Lebowski (Blu-ray)"
    }

    def TestGetResponse() {
        given:
        long id = 13860428
        String targetURL = "testURL"

        when:
        ProductVO productVO = product.getResponse(targetURL, id)

        then:
        1 * connection.getResponseFromURL(targetURL + id) >> "{\n" +
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
        1 * repository.findByid(13860428) >> new PriceVO(13860428, 12.25, "USD")
        0 * _

        and:
        productVO
        productVO.getName() == "The Big Lebowski (Blu-ray)"
        productVO.getId() == id
        productVO.getPrice() == 12.25
    }

    def TestGetResponseExceptionThrown() {
        given:
        long id = 13860428
        String targetURL = "testURL"

        when:
        ProductVO productVO = product.getResponse(targetURL, id)

        then:
        1 * connection.getResponseFromURL(targetURL + id) >> "{\n" +
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
        1 * repository.findByid(13860428) >> null
        0 * _

        then:
        thrown(ResponseStatusException)
    }

    def testApplyPutRequest() {
        given:
        PriceVO oldPriceVO = new PriceVO(1234, 22.45, "USD")

        when:
        PriceVO newPriceVO = product.applyPutRequest(1234, "{\n" +
                "    \"id\": 1234,\n" +
                "    \"price\": 20.45,\n" +
                "    \"currencyType\": \"USD\"\n" +
                "}")

        then:
        1 * repository.findByid(1234) >> oldPriceVO
        1 * repository.save(_ as PriceVO)
        0 * _

        and:
        newPriceVO
        newPriceVO.getId() == 1234
        newPriceVO.getPrice() == 20.45
    }

    def testAppllyPutRequestError() {
        given:
        1 == 1

        when:
        product.applyPutRequest(1234, "{\n" +
                "    \"id\": 1234,\n" +
                "    \"price\": 20.45,\n" +
                "    \"currencyType\": \"USD\"\n" +
                "}")

        then:
        1 * repository.findByid(1234) >> null
        0 * _

        and:
        thrown(ResponseStatusException)
    }

}
