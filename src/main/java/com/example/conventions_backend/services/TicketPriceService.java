package com.example.conventions_backend.services;

import com.example.conventions_backend.entities.TicketPrice;
import com.example.conventions_backend.repositories.TicketPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketPriceService {

    private final TicketPriceRepository ticketPriceRepository;

    @Autowired
    public TicketPriceService(TicketPriceRepository ticketPriceRepository) {
        this.ticketPriceRepository = ticketPriceRepository;
    }

    public TicketPrice saveTicketPrice(TicketPrice ticketPrice) {
        return ticketPriceRepository.save(ticketPrice);
    }

    public List<TicketPrice> getTicketPricesByConventionId(Long id) {
        return ticketPriceRepository.getTicketPricesByConventionId(id);
    }
}
