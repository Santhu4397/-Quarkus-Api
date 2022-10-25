package org.tns.controller;

import io.quarkus.logging.Log;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.tns.dao.ProductDao;
import org.tns.dto.Product;
import org.tns.helper.ProductInfo;
import org.tns.service.Service;
import org.tns.util.ResponseBodySingle;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.*;

@Path("training")
public class ProductController {

    @Inject
    ProductDao productDao;
    @Inject
    Service userService;
    @Inject
    Validator validator;

    @POST
    @Path("product")
    @RolesAllowed("ADMIN")
    @Consumes("application/json")
    @Produces("application/json")
    public Response saveProduct(Product product){
        Log.info("--------Save Product Started From Controller-------");
        Set<ConstraintViolation<Product>> errors = validator.validate(product);
        if(errors.isEmpty()) {
            Product product1 = productDao.saveProduct(product);
            if (product1 != null) {
                Log.info("---------Product Data Inserted Into Data Base---------");
                return Response.status(CREATED).entity(ResponseBodySingle.success(null, "Product Data Inserted")).build();
            } else {
                Log.error("------------Product Data Not Inserted Into Data Base---------");
                return Response.status(INTERNAL_SERVER_ERROR).entity(ResponseBodySingle.internalServerError(null,"Product Data NOT Inserted")).build();

            }
        }else {
            Log.error("--------Product field validation Error----------");
            String errorMsg=errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            return Response.status(BAD_REQUEST).entity(ResponseBodySingle.Bad_Request(null,errorMsg+"")).build();

        }

    }
    @GET
    @PermitAll
    @Path("getProductInfo/{productname}")
    public  Response getallchild(@PathParam("productname") String productName){
        Log.info("---------Get All Product Child Data Started From Controller----------");
        List<ProductInfo> allProductChild = userService.getAllProductChild(productName);
        if(allProductChild!=null) {
            Log.info("---------Get All Product Child Data fetched from Data Base ----------");
            return Response.status(OK).entity(ResponseBodySingle.ok(allProductChild, "Product Data fetched")).build();
        }else{
            Log.error("-------Product Data Not Found----------");
            return Response.status(NOT_FOUND).entity(ResponseBodySingle.notFound( null,"Product Name "+productName+" Not Found")).build();

        }
    }
}
