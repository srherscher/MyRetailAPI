package com.my.retail.myretailapi

import com.my.retail.myretailapi.businesslogic.Connection
import com.my.retail.myretailapi.businesslogic.impl.ConnectionImpl
import spock.lang.Specification

class ConnectionSpec extends Specification {

    Connection connection = new ConnectionImpl()

    def TestGetResponseFromURL() {
        when:
        String response = connection.getResponseFromURL("https://redsky.target.com/v2/pdp/tcin/13860420")

        then:
        response
    }
}
