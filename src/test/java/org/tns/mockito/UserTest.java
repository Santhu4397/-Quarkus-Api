package org.tns.mockito;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.tns.controller.UserController;
import org.tns.dto.User;
import org.tns.helper.ModelInfo;
import org.tns.helper.ProductInfo;
import org.tns.helper.UserInfo;
import org.tns.repo.UserRepo;
import org.tns.service.Service;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
public class UserTest {
    @InjectMock
    Service service;
    @InjectMock
    UserRepo userRepo;
    @Inject
    UserController userController;
    UserInfo userInfo;
    ModelInfo modelInfo;
    ProductInfo productInfo;
    List<ProductInfo> productInfos=new ArrayList<>();
    List<ModelInfo> modelInfos=new ArrayList<>();
    @BeforeEach
    public void setup(){
        modelInfo=new ModelInfo("4GB RAM Intel core i3","Vivo","10-12-2022","12-11-2022","2022","HP","126gb");
        modelInfos.add(modelInfo);
        productInfo=new ProductInfo("Lap","it is an Intel core i5","10-29-2022","10-13-2022","Lap",15000,modelInfos);
        productInfos.add(productInfo);
        userInfo=new UserInfo("Santhosh","Santh@gmial.com","Lap","09-12-2022","10-10-2022",productInfos);
         userInfo=Mockito.mock(UserInfo.class);
    }
    @Test
    public void succcesRateCase(){
        List<UserInfo> users=new ArrayList<>();
        users.add(userInfo);
        User user=new User("Santhosh","Santh@gmial.com","Lap","09-12-2022","10-10-2022");
        when(service.getListOFALlUser("santhosh")).thenReturn(userInfo);
        when(userRepo.findByName("santhosh")).thenReturn(user);
        Response getAllResponse = userController.getAll("santhosh");
      Response saveResponse=userController.saveUser(new User("gururaj@gmail.com","gururaj","TV","12-24-2022","10-24-2022"));
      Response getResponse= userController.get("santhosh");

      assertEquals(Response.Status.OK.getStatusCode(),getResponse.getStatus());
        assertEquals(Response.Status.OK.getStatusCode(),getAllResponse.getStatus());
        assertNotNull(getAllResponse.getEntity());
        assertEquals(Response.Status.CREATED.getStatusCode(),saveResponse.getStatus());
        assertNotNull(saveResponse.getEntity());


    }
    @Test
    public  void notFoundTestCase(){
        List<UserInfo> users=new ArrayList<>();
        users.add(userInfo);
        when(service.getListOFALlUser("santhosh")).thenReturn(userInfo);
        Response response = userController.getAll("santhosh1");
       assertNotNull(response);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),response.getStatus());
       assertNotNull(response.getEntity());
    }

}
