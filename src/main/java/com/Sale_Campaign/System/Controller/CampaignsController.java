package com.Sale_Campaign.System.Controller;
import com.Sale_Campaign.System.Service.CampaignsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("CreateCampaign")
public class CampaignsController {
    @Autowired
    CampaignsService campaignsService;


}
