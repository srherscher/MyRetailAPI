package com.my.retail.myretailapi.businesslogic;

import java.io.IOException;

public interface Connection {
    String getResponseFromURL(String targetURL, long id) throws IOException;
}
