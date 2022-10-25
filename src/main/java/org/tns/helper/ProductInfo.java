package org.tns.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {

    private String model;
    private String productDes;
    private String productWarrantyEndDate;
    private String productWarrantyStartDate;
    private String productName;
    private Integer productPrice;
    private List<ModelInfo> modelInfo;

}
