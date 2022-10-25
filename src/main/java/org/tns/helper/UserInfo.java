package org.tns.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String name;
    private String email;
    private String productName;
    private String userEndDate;
    private String userStartDate;
    private List<ProductInfo>  productInfo;
}
