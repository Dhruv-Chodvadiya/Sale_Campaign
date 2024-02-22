package com.Sale_Campaign.System.Controller;
import com.Sale_Campaign.System.Service.CampaignsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Sale_Campaign.System.Model.Campaigns;

import java.text.ParseException;

@RestController
@RequestMapping("CreateCampaign")
public class CampaignsController {
    @Autowired
    CampaignsService campaignsService;

//    @PostMapping("/campaign")
//    public Campaigns campaigns(@RequestBody CompaignsDTO campaigns) throws ParseException {
//        return campaignsService.createCampaigns(campaigns);
//    }
}
