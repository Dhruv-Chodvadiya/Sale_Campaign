package com.Sale_Campaign.System.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompaignsDTO {
    String startdate;
    String enddate;
    String title;
    List<ProductSale> campaigndiscount;
}