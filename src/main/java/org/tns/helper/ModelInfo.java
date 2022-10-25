package org.tns.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelInfo {
    private String modelDes;
    private String modelOS;
    private String modelWarrantyEndDate;
    private String modelWarrantyStartDate;
    private String modelYear;
    private String modelName;
    private String modelStorage;
}
