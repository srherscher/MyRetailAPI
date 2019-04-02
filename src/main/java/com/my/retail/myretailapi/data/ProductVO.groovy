package com.my.retail.myretailapi.data

class ProductVO {
    long id
    String name
    double price
    String currencyType

    ProductVO() {}

    ProductVO(long id, String name, double price, String currencyType) {
        this.id = id
        this.name = name
        this.price = price
        this.currencyType = currencyType
    }

}
