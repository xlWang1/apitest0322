package model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private Integer id;
    private String description;
    private String ownModel;
    private String requestMethod;
    private String url;
    private String parameters;
    private String contentType;
    private String exceptString;
    private String actual;

}
