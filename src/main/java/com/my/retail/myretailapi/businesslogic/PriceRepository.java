package com.my.retail.myretailapi.businesslogic;

import com.my.retail.myretailapi.data.PriceVO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends MongoRepository<PriceVO, String> {

    PriceVO findByid(long id);
}
