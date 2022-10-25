package org.tns.repo;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.tns.dto.User;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepo implements PanacheMongoRepository<User> {

    public User findByName(String name){
        return  find("name",name).firstResult();
    }
}
