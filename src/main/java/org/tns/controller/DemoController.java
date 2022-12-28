package org.tns.controller;

import io.quarkus.logging.Log;
import org.tns.dao.ProductDao;
import org.tns.dao.UserDao;
import org.tns.dto.User;
import org.tns.service.Service;
import org.tns.util.ResponseBodySingle;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.*;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
@Path("training")
public class DemoController {

    @Inject
    Service userService;
    @Inject
    Validator validator;
    @Inject
    UserDao userDao;
    @GET
    @Path("demo")
    public Response demo(){
        Log.info("-----------Get All User Child Data Started-----------");
        List<User> userInfo =userService.getAllUser();
        if(userInfo!=null) {
            Log.info("-----------User Data fetched from Data Base");
            return Response.status(OK).entity(ResponseBodySingle.ok(userService.getAllUser(), "User Data fetched")).build();
        }else{
            Log.error("-------User Data Not Found---------");
            return Response.status(NOT_FOUND).entity(ResponseBodySingle.notFound( null,"User Data Not Found")).build();

        }

    }
    @POST
    @Path("demo/user")
    @Consumes("application/json")
    @Produces("application/json")
    public Response saveUser(User user){
        Log.info("------------Save User From Controller Started-------");
        Set<ConstraintViolation<User>> errors = validator.validate(user);
        if (errors.isEmpty()) {
            String message = userDao.saveUser(user);
            if(message!=null) {
                userDao.saveUser(user);
                Log.info("------------user DATA Saved to DATA Base----------");
                return Response.status(CREATED).entity(ResponseBodySingle.success(null, "Data Submit Successfully")).build();
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
    @PUT
    @Path("/demo/updateuser")
    public Response updateUser(User user){


        Log.info("------------Save User From Controller Started-------");
        Set<ConstraintViolation<User>> errors = validator.validate(user);
        if (errors.isEmpty()) {
            String message = userDao.updateUser(user);
            if(message!=null) {
                userDao.updateUser(user);
                Log.info("------------user DATA Saved to DATA Base----------");
                return Response.status(CREATED).entity(ResponseBodySingle.success(null, "Data Updated")).build();
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
    @DELETE
    @Path("/demo/deleteuser/{name}")
    public Response deleteUser(@javax.ws.rs.PathParam("name") String name){
        String message=userDao.deleteUser(name);
        if(message!=null) {
            return Response.status(OK).entity(ResponseBodySingle.ok(message, "User Data fetched")).build();
        }else{
            return Response.status(NOT_FOUND).entity(ResponseBodySingle.notFound( null,"User name "+name+" Not Found")).build();

        }
    }
    @GET
    @Path("/demo/login/{email}/{pws}")
    public Response login(@PathParam("email") String email,@PathParam("pws") String pws){
        User response =userDao.login(email,pws);
        System.out.println("email"+email+"pws"+pws);
        if(response!=null) {
            return Response.status(OK).entity(ResponseBodySingle.ok(response, "User Data fetched")).build();
        }else{
            return Response.status(NOT_FOUND).entity(ResponseBodySingle.notFound( null,"User Not Found")).build();

        }
    }

}
