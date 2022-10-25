package org.tns.repo;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.tns.dto.Product;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ProductRepo implements PanacheMongoRepository<Product> {

    public List<Product> getAllByName(String name){
        return  find("productName",name).list();
    }
}
