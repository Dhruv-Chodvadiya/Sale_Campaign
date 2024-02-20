package com.Sale_Campaign.System.Service;
import com.Sale_Campaign.System.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class CampaignsService {
    @Autowired
    ProductRepo productRepo;
}
