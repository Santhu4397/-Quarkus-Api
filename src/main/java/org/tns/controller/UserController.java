package org.tns.controller;


import io.quarkus.logging.Log;
import org.tns.dao.ModelDao;
import org.tns.dao.UserDao;
import org.tns.dto.Model;
import org.tns.dto.RequestBody;
import org.tns.dto.User;
import org.tns.helper.DateCheckLogic;
import org.tns.helper.UserInfo;
import org.tns.repo.UserRepo;
import org.tns.service.Service;
import org.tns.util.ResponseBodySingle;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.*;


@ApplicationScoped
@Path("/training")
public class UserController {

    @Inject
    UserDao userDao;
    @Inject
    ModelDao modelDao;
    @Inject
    Service userService;
    @Inject
    UserRepo userRepo;
    @Inject
     Validator validator;
    @Inject
    DateCheckLogic dateCheckLogic;


    @POST
    @Path("/user")
    @RolesAllowed("ADMIN")
    @Consumes("application/json")
    @Produces("application/json")
    public Response saveUser( User user) {
        Log.info("------------Save User From Controller Started-------");
            Set<ConstraintViolation<User>> errors = validator.validate(user);
            if (errors.isEmpty()) {
                String message = userDao.saveUser(user);
                if(message!=null) {
                    userDao.saveUser(user);
                    Log.info("------------user DATA Saved to DATA Base----------");
                    return Response.status(CREATED).entity(ResponseBodySingle.success(null, "User Data Inserted")).build();
                }else{
                    Log.error("---------------User Data Not Inserted into Data Base-----------");
                    return Response.status(INTERNAL_SERVER_ERROR).entity(ResponseBodySingle.internalServerError(null,"User Data NOT Inserted")).build();

                }

            }else{
                Log.error("---------------User field validation Error -----------");
                String errorMsg=errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
                return Response.status(BAD_REQUEST).entity(ResponseBodySingle.Bad_Request(null,errorMsg+"")).build();
            }


    }


        @POST
    @Path("model")
        @RolesAllowed("ADMIN")
    @Consumes("application/json")
    @Produces("application/json")
    public Response saveModel(Model model){
            Log.info("-------Save Model from Controller Started----------");
     Model model1= modelDao.saveModel(model);
            Set<ConstraintViolation<Model>> errors = validator.validate(model);
            if(errors.isEmpty()) {
                if (model1 != null) {
                    Log.info("-----------Model Data Inserted in Data Base--------");
                    return Response.status(CREATED).entity(ResponseBodySingle.success(null, "Model Data Inserted")).build();
                } else {
                    Log.error("--------Model Data Did not inserted to Data Base----------");
                    return Response.status(INTERNAL_SERVER_ERROR).entity(ResponseBodySingle.internalServerError(null,"Model Data NOT Inserted")).build();

                }
            }else{
                Log.error("--------Model field validation Error ----------");
                String errorMsg=errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
                return Response.status(BAD_REQUEST).entity(ResponseBodySingle.Bad_Request(null,errorMsg+"")).build();
            }
    }
    @GET
    @RolesAllowed({"USER","ADMIN"})
    @Path("/getalluser/{username}")
    public Response getAll(@PathParam("username") String username) {
        Log.info("-----------Get All User Child Data Started-----------");
        UserInfo userInfo =userService.getListOFALlUser(username);
      if(userInfo!=null) {
          Log.info("-----------User Data fetched from Data Base");
          return Response.status(OK).entity(ResponseBodySingle.ok(userService.getListOFALlUser(username), "User Data fetched")).build();
      }else{
          Log.error("-------User Data Not Found---------");
          return Response.status(NOT_FOUND).entity(ResponseBodySingle.notFound( null,"User Data Not Found")).build();

      }
    }

    @GET
    @RolesAllowed({"USER","ADMIN"})
    @Path("/searchbyname/{name}")
    public  Response get(@PathParam("name") String name){
        Log.info("-------Get User By Name from Controller Started----------");
        User  user = userRepo.findByName(name);
        if(user!=null) {
            Log.info("-------User Data fetched----------");
            return Response.status(OK).entity(ResponseBodySingle.ok(user, "User Data fetched")).build();
        }else{
            Log.error("---------User Data Not Found---------");
            return Response.status(NOT_FOUND).entity(ResponseBodySingle.notFound( null,"User name "+name+" Not Found")).build();

        }
    }

    @PUT
    @RolesAllowed("ADMIN")
    @Path("/updateuser/{name}")
    public Response updateUser(User user){
        userDao.updateUser(user);
        return Response.status(CREATED).entity(ResponseBodySingle.success(null, "User Data Inserted")).build();

    }
    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/deleteuser/{name}")
    public Response deleteUser(@PathParam("name") String name){
        String message=userDao.deleteUser(name);
        if(message!=null) {
            return Response.status(OK).entity(ResponseBodySingle.ok(message, "User Data fetched")).build();
        }else{
            return Response.status(NOT_FOUND).entity(ResponseBodySingle.notFound( null,"User name "+name+" Not Found")).build();

        }
    }
    @GET
    @RolesAllowed({"USER","ADMIN"})
    @Path("eligibleForUser/{date}/{name}")
    public Response getDate(@PathParam("date") String date, @PathParam("name")String name){
        System.out.println("Check point1");

      boolean result=  dateCheckLogic.calculateEligibilityForUser(date,name);
      if(result){
          User user=userRepo.findByName(name);
          return Response.status(OK).entity(ResponseBodySingle.ok(user, "User Data fetched")).build();
      }else {
          return Response.status(NOT_FOUND).entity(ResponseBodySingle.notFound( null,"User name "+name+" Not eligible")).build();

      }

    }
    @GET
    @RolesAllowed({"USER","ADMIN"})
    @Path("eligibleForChildUser/{date}/{name}")
    public Response checkForChildUser(@PathParam("date") String date, @PathParam("name")String name){
        System.out.println("Check point1");
        boolean result=  dateCheckLogic.calculateEligibilityForUser(date,name);
        if(result){
            UserInfo user=userService.getListOFALlUser(name);
            return Response.status(OK).entity(ResponseBodySingle.ok(user, "User Data fetched")).build();
        }else {
            return Response.status(NOT_FOUND).entity(ResponseBodySingle.notFound( null,"User name "+name+" Not eligible")).build();

        }
    }
    @GET
    @Consumes("application/json")
    @Path("eligibleForSpecificProduct")
    @RolesAllowed({"USER","ADMIN"})
    @Produces("application/json")
    public Response checkForProduct(RequestBody requestBody){
        Log.info("------------Get eligible User From Controller Started-------");
        Set<ConstraintViolation<RequestBody>> errors = validator.validate(requestBody);
        if (errors.isEmpty()) {
            User user = userRepo.findByName(requestBody.getName());

            UserInfo userInfo = userService.getParticularUser(requestBody.getDate(), requestBody.getName());
            if (userInfo != null) {
                    Log.info("------------------eligible User Data Fetched--------------");
                return Response.status(OK).entity(ResponseBodySingle.ok(userInfo, "User Data Fetched")).build();
            } else if (user == null) {
                Log.error("------------------ User Name Not Found --------------");
                return Response.status(NOT_FOUND).entity(ResponseBodySingle.notFound(null, "User name " + requestBody.getName() + " Not exist")).build();
            } else {
                Log.error("----------------------User Not Eligible ------------");
                return Response.status(NOT_FOUND).entity(ResponseBodySingle.notFound(null, "User name " + requestBody.getName() + " Not eligible")).build();

            }
        }else {
            Log.error("---------------Get eligible User Data  field validation Error -----------");
            String errorMsg=errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            return Response.status(BAD_REQUEST).entity(ResponseBodySingle.Bad_Request(null,errorMsg+"")).build();
        }
    }
}
