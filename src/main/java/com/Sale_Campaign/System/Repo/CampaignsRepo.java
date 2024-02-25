package com.Sale_Campaign.System.Repo;

import com.Sale_Campaign.System.Model.Campaigns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface CampaignsRepo extends JpaRepository<Campaigns,Long> {

    @Query(value = "select * from campaigns where startDate=:currentDate")
    default List<Campaigns> findActiveCampaigns(String currentDate) {
        return null;

    }
//    List<Campaigns> findByStartDate(LocalDate startDate);

//    @Query(value = "select * from campaigns where endDate=:formattedDate")
//    List<Campaigns> findEndCampaigns(String formattedDate);
//    List<Campaigns> findByEndDate(LocalDate endDate);
}