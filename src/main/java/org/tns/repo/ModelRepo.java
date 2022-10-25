package org.tns.repo;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.tns.dto.Model;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ModelRepo implements PanacheMongoRepository<Model> {

    public List<Model> getAllByName(String name){
        return find("modelName",name).list();
    }
}
