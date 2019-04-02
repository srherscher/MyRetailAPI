package com.my.retail.myretailapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.retail.myretailapi.businesslogic.ProductRepository;
import com.my.retail.myretailapi.data.PriceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyretailapiApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(MyretailapiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repository.deleteAll();

        repository.save(new PriceVO(13860428, 22.45, "USD"));
        repository.save(new PriceVO(13860429, 109.65, "USD"));
        repository.save(new PriceVO(13860420, 8.67, "USD"));
        repository.save(new PriceVO(13860427, 14.67, "USD"));

        System.out.println("Prices found with findAll():");
        for (PriceVO priceVO : repository.findAll()) {
            System.out.println(priceVO);
        }
        System.out.println();

        System.out.println("Prices found with findByID(13860429):");
        System.out.println(repository.findByid(13860429));


    }

}
