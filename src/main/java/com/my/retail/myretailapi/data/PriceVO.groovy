package com.my.retail.myretailapi.data

class PriceVO {
    long id
    double price
    String currencyType

    PriceVO(long id, double price, String currencyType) {
        this.id = id
        this.price = price
        this.currencyType = currencyType
    }

    PriceVO(){}

    String toString(){
        return "id: " + id + " price: " + price + " currency type: " + currencyType
    }
}
