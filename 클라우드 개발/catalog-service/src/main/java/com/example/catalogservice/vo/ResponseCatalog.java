package com.example.catalogservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // 널값은 전달하지 않는다
public class ResponseCatalog {
    private String productId;
    private String productName;

    private Integer unitPrice;
    private Integer stock;

    private Date createdAt;
}
