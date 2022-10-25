package org.tns.dto;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static org.tns.util.ApplicationUtils.DATE_FORMAT;
import static org.tns.util.ApplicationUtils.NOT_BLANK;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MongoEntity(collection = "Product")
public class Product extends PanacheMongoEntity {

    @NotBlank(message = "ModelName"+NOT_BLANK)
    private String model;
    private String productDes;
    @Pattern(regexp = "^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))" +
            "[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$"
            ,message = "productWarrantyEndDate"+DATE_FORMAT)
    private String productWarrantyEndDate;
    @Pattern(regexp = "^(((0[13-9]|1[012])[-/]?(0[1-9]|[12][0-9]|30)|(0[13578]|1[02])[-/]?31|02[-/]?(0[1-9]|1[0-9]|2[0-8]))" +
            "[-/]?[0-9]{4}|02[-/]?29[-/]?([0-9]{2}(([2468][048]|[02468][48])|[13579][26])|([13579][26]|[02468][048]|0[0-9]|1[0-6])00))$"
            ,message = "productWarrantyStartDate"+DATE_FORMAT)
    private String productWarrantyStartDate;
    @NotBlank(message = "ProductName can not be blank")
    private String productName;
    private Integer productPrice;
}
