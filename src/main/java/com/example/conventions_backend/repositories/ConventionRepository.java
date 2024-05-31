package com.example.conventions_backend.repositories;

import com.example.conventions_backend.entities.AppUser;
import com.example.conventions_backend.entities.Convention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConventionRepository extends JpaRepository<Convention, Long>, JpaSpecificationExecutor<Convention> {

    List<Convention> findAllByUserId(Long id);

    @Query("SELECT c FROM Convention c " +
            "ORDER BY " +
            "CASE c.conventionStatus " +
            "WHEN com.example.conventions_backend.entities.ConventionStatus.ONGOING THEN 1 " +
            "WHEN com.example.conventions_backend.entities.ConventionStatus.UPCOMING THEN 2 " +
            "WHEN com.example.conventions_backend.entities.ConventionStatus.OVER THEN 3 " +
            "ELSE 4 " +
            "END, " +
            "c.startDate ASC")
    List<Convention> findAllByOrderByStatusAndStartDate();

    @Query("SELECT c FROM Convention c WHERE " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.address.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.address.country) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Convention> searchForConvention(String keyword);
}
