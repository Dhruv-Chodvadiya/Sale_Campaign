package com.Sale_Campaign.System.Service;

import com.Sale_Campaign.System.Repo.ProductRepo;
import com.Sale_Campaign.System.Model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.Sale_Campaign.System.Model.DTO.ProductDTO;

import java.util.List;
@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    public List<Product> save(List<Product> p) {
        return productRepo.saveAll(p);
    }

    public ProductDTO get(Integer page, Integer pageSize) {
        Page<Product> productPage = productRepo.findAll(PageRequest.of(page - 1, pageSize));

        List<Product> productList = productPage.getContent();
        int totalPage = (productPage).getTotalPages();

        ProductDTO ans = new ProductDTO();
        ans.setProduct(productList);
        ans.setPage(page);
        ans.setTotalPage(totalPage);
        ans.setPageSize(pageSize);

        return ans;
    }
}
