package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
