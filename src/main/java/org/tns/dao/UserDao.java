package org.tns.dao;


import io.quarkus.logging.Log;
import org.tns.dto.User;
import org.tns.repo.UserRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserDao {

    @Inject
    UserRepo userRepo;

    public String saveUser(User user){
        Log.info("Save User Dao started");
        try {
            userRepo.persist(user);

        }catch (Exception ex) {
            Log.error("!!!!!!!!!"+ex+"!!!!!!!");
        }
        return "Data Inserted";
    }



    public  String  updateUser(User user){
            userRepo.update(user);
        return "Data Inserted";

    }
    public User login(String email,String pws){

        User user=userRepo.findbyemail(email);
      try {
            if (user.getEmail().equals(email) && user.getPws().equals(pws)) {
                return user;
            }
        }catch (Exception ex) {
          Log.error("!!!!!!!!!"+ex+"!!!!!!!");

                }

        return null;
    }
    public String deleteUser(String name){
        User user=userRepo.findByName(name);
        if(user!=null) {
            userRepo.delete(user);
            return "User data removed";
        }
        return null;
    }
}
