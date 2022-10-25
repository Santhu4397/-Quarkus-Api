package org.tns.dao;

import io.quarkus.logging.Log;
import org.tns.dto.Product;
import org.tns.repo.ProductRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProductDao {

    @Inject
    ProductRepo productRepo;
    public Product saveProduct(Product product){
        try{
            productRepo.persist(product);
        }catch (Exception ex){
            Log.error("!!!!!!!!!"+ex+"!!!!!!!");
        }

        return product;
    }

}
