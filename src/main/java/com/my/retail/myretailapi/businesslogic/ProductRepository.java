package com.my.retail.myretailapi.businesslogic;

import com.my.retail.myretailapi.data.PriceVO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<PriceVO, String> {

    public PriceVO findByid(long id);
}
