package org.tns.repo;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import org.jboss.resteasy.annotations.Query;
import org.tns.dto.User;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepo implements PanacheMongoRepository<User> {

    public User findByName(String name){
        return  find("name",name).firstResult();
    }
    public User findbyemail(String email){
        return  find("email",email).firstResult();
    }

}
