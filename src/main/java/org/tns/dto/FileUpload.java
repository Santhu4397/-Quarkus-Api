package org.tns.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FileUpload {

    private byte[] bytes;
    private String fileName;
}
