package com.Sale_Campaign.System.Service;
import com.Sale_Campaign.System.Model.Campaigns;
import com.Sale_Campaign.System.Model.DTO.CompaignsDTO;
import com.Sale_Campaign.System.Model.DTO.ProductSale;
import com.Sale_Campaign.System.Model.PriceHistory;
import com.Sale_Campaign.System.Model.Product;
import com.Sale_Campaign.System.Repo.CampaignsRepo;
import com.Sale_Campaign.System.Repo.PriceHistoryRepo;
import com.Sale_Campaign.System.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@EnableScheduling
@Service
public class CampaignsService {
    @Autowired
    ProductRepo productRepo;

    @Autowired
    PriceHistoryRepo priceHistoryRepo;

    @Autowired
    CampaignsRepo campaignsRepo;

    public Campaigns createCampaigns(CompaignsDTO campaigns) throws ParseException {
//        LocalDate current=LocalDate.now();
//        LocalDate startDate= LocalDate.parse(campaigns.getStartdate());
//        LocalDate endDate= LocalDate.parse(campaigns.getEnddate());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate =sdf.parse(campaigns.getStartdate());
        Date endDate =sdf.parse(campaigns.getEnddate());
        Date current = new Date();

        Campaigns campaigns1 = null;
        List<ProductSale> productList = campaigns.getCampaigndiscount();

        for (ProductSale productSale : productList) {
            campaigns1 = new Campaigns();
            campaigns1.setStartDate(campaigns.getStartdate());
            campaigns1.setEndDate(campaigns.getEnddate());
            campaigns1.setTitle(campaigns.getTitle());
            Long productId = productSale.getProductid();
            Optional<Product> productOptional = productRepo.findById(productId);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                campaigns1.setProductId(productId);
                campaigns1.setDiscount(productSale.getDiscount());
                campaigns1.setOldPrice(product.getCurrentPrice());


                if (current.after(startDate) && current.before(endDate)) {
                    Double newPrice = product.getCurrentPrice() - (product.getCurrentPrice() * campaigns1.getDiscount() / 100);
                    product.setDiscount(100 - (newPrice * 100 / product.getMrp()));

                    PriceHistory priceHistory = new PriceHistory();
                    priceHistory.setProductId(product.getId());
                    priceHistory.setPrice(product.getCurrentPrice());
                    priceHistory.setNewPrice(newPrice);

                    product.setCurrentPrice(newPrice);


                    priceHistoryRepo.save(priceHistory);
                    productRepo.save(product);

                } else if (current.equals(startDate)) {
                    Double newPrice = product.getCurrentPrice() - (product.getCurrentPrice()*campaigns1.getDiscount()/100);
                    product.setDiscount(100-(newPrice*100/product.getMrp()));

                    PriceHistory priceHistory= new PriceHistory();
                    priceHistory.setProductId(product.getId());
                    priceHistory.setPrice(product.getCurrentPrice());
                    priceHistory.setNewPrice(newPrice);

                    product.setCurrentPrice(newPrice);

                    priceHistoryRepo.save(priceHistory);
                    productRepo.save(product);

                }
                if (current.equals(startDate) || current.after(startDate)){
                    campaigns1.setStatus("Current");
                }
                if (current.after(endDate) && current.after(startDate)) {
                    campaigns1.setStatus("Past");
                }else if(current.before(startDate)){
                    campaigns1.setStatus("UpComing");
                }
                campaignsRepo.save(campaigns1);
            } else {
                System.out.println("Id not Found" + productId);
            }
        }
        return campaigns1;
    }
//    @Scheduled(fixedRate = 86400000)
    public List<Campaigns> scheduledMethod() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(currentDate);

        List<Campaigns> activeCampaigns = campaignsRepo.findActiveCampaigns(formattedDate);

        List<Campaigns> active = null;

        if(!activeCampaigns.isEmpty()){
            active = startCampaign(activeCampaigns,formattedDate);
        }
//        List<Campaigns> endCampaigns = campaignsRepo.findByEndDate(LocalDate.parse(formattedDate));
//        if(!endCampaigns.isEmpty()){
//            endCampaign(endCampaigns);
//        }
        return active;
    }

    public List<Campaigns> startCampaign(List<Campaigns> activeCampaigns, String formattedDate){
        for(Campaigns campaign : activeCampaigns){
            Optional<Product> product = productRepo.findById(campaign.getProductId());
            Product product1 = product.get();

            Double newPrice = product1.getCurrentPrice() - (product1.getCurrentPrice()*campaign.getDiscount()/100);
            product1.setDiscount(100-(newPrice*100/product1.getMrp()));

            PriceHistory priceHistory= new PriceHistory();
            priceHistory.setProductId(product1.getId());
            priceHistory.setPrice(product1.getCurrentPrice());
            priceHistory.setNewPrice(newPrice);

            product1.setCurrentPrice(newPrice);

            campaign.setStatus("Current");

            campaignsRepo.save(campaign);
            priceHistoryRepo.save(priceHistory);
            productRepo.save(product1);
        }
        return campaignsRepo.findActiveCampaigns(formattedDate);
    }
    public void endCampaign(List<Campaigns> endCampaigns){
        for(Campaigns campaign : endCampaigns){
            Optional<Product> product = productRepo.findById(campaign.getProductId());
            Product product1 = product.get();

            Double newPrice = (product1.getCurrentPrice()*100) / (100- campaign.getDiscount());
            product1.setDiscount(100-(newPrice*100/product1.getMrp()));

            PriceHistory priceHistory= new PriceHistory();
            priceHistory.setProductId(product1.getId());
            priceHistory.setPrice(product1.getCurrentPrice());
            priceHistory.setNewPrice(newPrice);

            product1.setCurrentPrice(newPrice);

            campaign.setStatus("Past");

            campaignsRepo.save(campaign);
            priceHistoryRepo.save(priceHistory);
            productRepo.save(product1);
        }
    }
}
