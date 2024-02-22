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
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
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
        LocalDate current=LocalDate.now();
//        Date startDate =sdf.parse(campaigns.getStartdate());
//        Date endDate =sdf.parse(campaigns.getEnddate());
        LocalDate startDate= LocalDate.parse(campaigns.getStartdate());
        LocalDate endDate= LocalDate.parse(campaigns.getEnddate());

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

                if (current.isAfter(startDate) && current.isBefore(endDate)) {
                    Double newPrice = product.getCurrentPrice() - (product.getCurrentPrice() * campaigns1.getDiscount() / 100);
                    product.setDiscount(100 - (newPrice * 100 / product.getMrp()));

                    PriceHistory priceHistory = new PriceHistory();
                    priceHistory.setProductId(product.getId());
                    priceHistory.setPrice(product.getCurrentPrice());
                    priceHistory.setNewPrice(newPrice);

                    product.setCurrentPrice(newPrice);

                    priceHistoryRepo.save(priceHistory);
                    productRepo.save(product);

                } else if (current.isEqual(startDate)) {
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

                if (current.isEqual(startDate) || current.isAfter(startDate)){
                    campaigns1.setStatus("Current");
                }
                if (current.isAfter(endDate) && current.isAfter(startDate)) {
                    campaigns1.setStatus("Past");
                }else {
                    campaigns1.setStatus("UpComing");
                }

                campaignsRepo.save(campaigns1);
            } else {
                System.out.println("Id not Found" + productId);
            }
        }
        return campaigns1;
    }
}
