package com.Sale_Campaign.System.Controller;
import com.Sale_Campaign.System.Service.CampaignsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import com.Sale_Campaign.System.Model.DTO.CompaignsDTO;
import com.Sale_Campaign.System.Model.Campaigns;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("CreateCampaign")
public class CampaignsController {
    @Autowired
    CampaignsService campaignsService;

    @PostMapping("/campaign")
    public Campaigns campaigns(@RequestBody CompaignsDTO campaigns) throws ParseException {
        return campaignsService.createCampaigns(campaigns);
    }

    @PostMapping("startAndEndCampaign")
    public List<Campaigns> campaigns(@RequestParam String date){
//        return campaignsService.scheduledMethod();
        List<Campaigns> active=campaignsService.scheduledMethod();
        return active;
    }
}
