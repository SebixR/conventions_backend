package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.TicketPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketPriceRepository extends JpaRepository<TicketPrice, Long> {

    List<TicketPrice> getTicketPricesByConventionId(Long id);
}
