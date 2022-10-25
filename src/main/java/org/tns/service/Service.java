package org.tns.service;


import org.tns.dto.Model;
import org.tns.dto.Product;
import org.tns.dto.User;
import org.tns.helper.DateCheckLogic;
import org.tns.helper.ModelInfo;
import org.tns.helper.ProductInfo;
import org.tns.helper.UserInfo;
import org.tns.repo.ModelRepo;
import org.tns.repo.ProductRepo;
import org.tns.repo.UserRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class Service {

    @Inject
    UserRepo userRepo;
    @Inject
    ModelRepo modelRepo;
    @Inject
    ProductRepo productRepo;
    @Inject
    DateCheckLogic dateCheckLogic;




    public UserInfo getListOFALlUser(String name) {
        UserInfo userInfo = new UserInfo();
        User user = userRepo.findByName(name);

        if (user != null) {
            List<ProductInfo> productInfos = getAllProductChild(user.getProductName());
            userInfo.setProductInfo(productInfos);
            userInfo.setUserEndDate(user.getUserEndDate());
            userInfo.setName(user.getName());
            userInfo.setUserStartDate(user.getUserStartDate());
            userInfo.setProductName(user.getProductName());
            userInfo.setEmail(user.getEmail());
            return userInfo;
        } else {
            return null;
        }

    }



    public   List<ProductInfo> getAllProductChild(String name) {


        List<Product> products = productRepo.getAllByName(name);
        List<ProductInfo> productInfos = new ArrayList<>();

        if (products != null && products.size()>0) {
            for (Product p : products) {
                ProductInfo productInfo = new ProductInfo();
                productInfo.setProductName(p.getProductName());
                productInfo.setProductPrice(p.getProductPrice());
                productInfo.setProductWarrantyEndDate(p.getProductWarrantyEndDate());
                productInfo.setProductWarrantyStartDate(p.getProductWarrantyStartDate());
                productInfo.setModel(p.getModel());
                productInfo.setProductDes(p.getProductDes());
                List<Model> model = modelRepo.getAllByName(p.getModel());
                if (model != null) {
                    List<ModelInfo> modelInfos = new ArrayList<>();
                    for (Model m : model) {
                        ModelInfo modelInfo = new ModelInfo();
                        modelInfo.setModelDes(m.getModelDes());
                        modelInfo.setModelName(m.getModelName());
                        modelInfo.setModelStorage(m.getModelStorage());
                        modelInfo.setModelOS(m.getModelOS());
                        modelInfo.setModelYear(m.getModelYear());
                        modelInfo.setModelWarrantyEndDate(m.getModelWarrantyEndDate());
                        modelInfo.setModelWarrantyStartDate(m.getModelWarrantyStartDate());
                        modelInfos.add(modelInfo);
                    }
                    productInfo.setModelInfo(modelInfos);

                } else {
                    productInfo.setModelInfo(null);

                }

                productInfos.add(productInfo);
            }
            return productInfos;
        } else {
            return null;
        }

    }
    public UserInfo getParticularUser(String userDate,String userName) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate currentDate =  LocalDate.parse(userDate, format);
        boolean result= dateCheckLogic.calculateEligibilityForUser(userDate,userName);
        if(result) {
            UserInfo userInfo = getListOFALlUser(userName);
            List<ProductInfo> productInfos = userInfo.getProductInfo();
            List<ModelInfo> modelInfos;
            List<ProductInfo> productInfos1 = productInfos.stream().filter(productInfo -> LocalDate.parse(productInfo.getProductWarrantyStartDate(), format).isBefore(currentDate)
                            || LocalDate.parse(productInfo.getProductWarrantyStartDate(), format).isEqual(currentDate))
                    .filter(productInfo -> LocalDate.parse(productInfo.getProductWarrantyEndDate(), format).isAfter(currentDate)
                            || LocalDate.parse(productInfo.getProductWarrantyEndDate(), format).isEqual(currentDate))
                    .collect(Collectors.toList());
            for (ProductInfo p : productInfos) {
                modelInfos = p.getModelInfo();

                List<ModelInfo> collect = modelInfos.stream().filter(modelInfo -> LocalDate.parse(modelInfo.getModelWarrantyStartDate(), format).isBefore(currentDate)
                                || LocalDate.parse(modelInfo.getModelWarrantyStartDate(), format).isEqual(currentDate))
                        .filter(modelInfo -> LocalDate.parse(modelInfo.getModelWarrantyStartDate(), format).isAfter(currentDate)
                                || LocalDate.parse(modelInfo.getModelWarrantyEndDate(), format).isEqual(currentDate))
                        .collect(Collectors.toList());
                p.setModelInfo(collect);
                userInfo.setProductInfo(productInfos1);
            }
            return userInfo;
        }
        return null;
    }
}
