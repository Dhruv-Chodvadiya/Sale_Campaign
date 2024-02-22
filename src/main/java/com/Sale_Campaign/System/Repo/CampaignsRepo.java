package com.Sale_Campaign.System.Repo;

import com.Sale_Campaign.System.Model.Campaigns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignsRepo extends JpaRepository<Campaigns,Long> {
}