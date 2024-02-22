package com.Sale_Campaign.System.Service;
import com.Sale_Campaign.System.Model.Campaigns;
import com.Sale_Campaign.System.Model.DTO.CompaignsDTO;
import com.Sale_Campaign.System.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@EnableScheduling
@Service
public class CampaignsService {
    @Autowired
    ProductRepo productRepo;

    public Campaigns createCampaigns(CompaignsDTO campaigns) throws ParseException {
        return null;
    }
}
