package com.Sale_Campaign.System.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Sale_Campaign.System.Model.PriceHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceHistoryRepo extends JpaRepository<PriceHistory,Long> {
}
