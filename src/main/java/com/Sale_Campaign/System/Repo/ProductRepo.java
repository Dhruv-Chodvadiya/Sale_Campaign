package com.Sale_Campaign.System.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Sale_Campaign.System.Model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
}
