package org.tns.helper;

import io.quarkus.logging.Log;
import org.tns.dto.User;
import org.tns.repo.UserRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ApplicationScoped
public class DateCheckLogic {
    @Inject
    UserRepo userRepo;


    public  boolean calculateEligibilityForUser(String userDate, String userName) {
      User user=userRepo.findByName(userName);
        boolean b = false;
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate currentDate  =  LocalDate.parse(userDate, format);

            LocalDate startDate = LocalDate.parse(user.getUserStartDate(),format);

            LocalDate endDate = LocalDate.parse(user.getUserEndDate(),format);

            b=(startDate.isBefore(currentDate)||startDate.isEqual(currentDate))&&
                    (endDate.isAfter(currentDate)||endDate.isEqual(currentDate));


        } catch (Exception ex){
            Log.error("Date parse ex" +ex);
        }
        return b;
    }


}
