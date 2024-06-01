package com.example.conventions_backend.dto;

import com.example.conventions_backend.entities.Address;
import com.example.conventions_backend.entities.Convention;
import com.example.conventions_backend.entities.Tag;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ConventionSpecifications {
    public static Specification<Convention> withFilters(FilterRequestDto filterRequestDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterRequestDto.getName() != null && !filterRequestDto.getName().isEmpty()) {
                String pattern = "%" + filterRequestDto.getName().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), pattern));
            }

            if (filterRequestDto.getCity() != null && !filterRequestDto.getCity().isEmpty()) {
                Join<Convention, Address> cityJoin = root.join("address", JoinType.INNER);
                String pattern = "%" + filterRequestDto.getCity().toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(cityJoin.get("city")), pattern));
            }

            if (filterRequestDto.getDate() != null && !filterRequestDto.getDate().isEmpty()) {
                Predicate startDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), LocalDate.parse(filterRequestDto.getDate()));
                Predicate endDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), LocalDate.parse(filterRequestDto.getDate()));
                predicates.add(criteriaBuilder.and(startDatePredicate, endDatePredicate));
            }

            if (filterRequestDto.getSelectedTags() != null && !filterRequestDto.getSelectedTags().isEmpty()) {
                Join<Convention, Tag> tagJoin = root.join("tags", JoinType.INNER);
                predicates.add(tagJoin.get("tag").in(filterRequestDto.getSelectedTags()));
            }

            if (filterRequestDto.getSelectedStatuses() != null && !filterRequestDto.getSelectedStatuses().isEmpty()) {
                Expression<String> statusExpression = root.get("conventionStatus"); //just to get to the status, without comparing it with an array while adding
                predicates.add(statusExpression.in(filterRequestDto.getSelectedStatuses()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
