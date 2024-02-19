package com.Sale_Campaign.System.Controller;

import com.Sale_Campaign.System.Service.ProductService;
import com.Sale_Campaign.System.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.Sale_Campaign.System.Model.DTO.ProductDTO;
import java.security.Provider;
import java.util.List;

@RestController
@RequestMapping("Product")
public class ProductController {
    @Autowired
    ProductService productService;


    @PostMapping("/add")
    public List<Product> add(@RequestBody List<Product> p){
        return productService.save(p);
    }

    @GetMapping("/get")
    public ProductDTO get(@RequestParam Integer page, Integer pageSize){
        return productService.get(page,pageSize);
    }

}
