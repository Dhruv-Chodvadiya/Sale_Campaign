package com.Sale_Campaign.System.Repo;

import com.Sale_Campaign.System.Model.Campaigns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignsRepo extends JpaRepository<Campaigns,Long> {
    @Query(value = "select * from campaigns where startDate=:currentDate ",nativeQuery = true)
    List<Campaigns> findActiveCampaigns(String currentDate);

    @Query(value = "select * from campaigns where endDate=:formattedDate ",nativeQuery = true)
    List<Campaigns> findEndCampaigns(String formattedDate);
}