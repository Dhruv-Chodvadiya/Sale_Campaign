package com.Sale_Campaign.System.Model.DTO;
import com.Sale_Campaign.System.Model.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private List<Product> product;
    private Integer page;
    private Integer pageSize;
    private Integer totalPage;
}
