package org.tns.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleProductInfo {
    private String model;
    private String productDes;
    private String productWarrantyEndDate;
    private String productWarrantyStartDate;
    private String productName;
    private Integer productPrice;
    private ModelInfo modelInfo;
}
