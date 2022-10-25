package org.tns.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleUserInfo {
    private String name;
    private String email;
    private String productName;
    private String userEndDate;
    private String userStartDate;
    private SingleProductInfo productInfo;
}
