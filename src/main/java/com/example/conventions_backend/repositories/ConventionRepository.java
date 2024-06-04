package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.AppUser;
import com.example.conventions_backend.entities.Convention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConventionRepository extends JpaRepository<Convention, Long>, JpaSpecificationExecutor<Convention> {

    List<Convention> findAllByUserId(Long id);

    Page<Convention> findAll(Specification<Convention> spec, Pageable pageable);

    @Query("SELECT c FROM Convention c " +
            "ORDER BY c.startDate DESC")
    Page<Convention> findAllByOrderByStatusAndStartDate(Pageable pageable);

    @Query("SELECT c FROM Convention c WHERE " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.address.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.address.country) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Convention> searchForConvention(String keyword, Pageable pageable);
}
