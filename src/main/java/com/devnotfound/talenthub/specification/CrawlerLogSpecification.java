package com.devnotfound.talenthub.specification;

import com.devnotfound.talenthub.dto.CrawlerLogFilterDTO;
import com.devnotfound.talenthub.entity.CrawlerLog;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class CrawlerLogSpecification {
    public static Specification<CrawlerLog> filter(CrawlerLogFilterDTO dto) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.plataform() != null) {
                predicates.add(cb.equal(root.get("plataform"), dto.plataform()));
            }

            if (dto.positionId() != null) {
                predicates.add(cb.equal(root.get("position").get("id"), dto.positionId()));
            }

            if (dto.techId() != null) {
                predicates.add(cb.equal(root.get("tech").get("id"), dto.techId()));
            }

            if (dto.startDate() != null && dto.endDate() != null) {
                predicates.add(
                        cb.between(root.get("creationDate"), dto.startDate(), dto.endDate())
                );
            } else if (dto.startDate() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("creationDate"), dto.startDate())
                );
            }

            if (dto.ufAbrev() != null) {
                predicates.add(cb.equal(root.get("ufAbrev"), dto.ufAbrev()));
            }

            if (dto.hiringType() != null) {
                predicates.add(cb.equal(root.get("hiringType"), dto.hiringType()));
            }

            if (dto.techLevel() != null) {
                predicates.add(cb.equal(root.get("techLevel"), dto.techLevel()));
            }

            if (dto.salaryMin() != null && dto.salaryMax() != null) {
                predicates.add(
                        cb.between(root.get("salaryRange"), dto.salaryMin(), dto.salaryMax())
                );
            } else if (dto.salaryMin() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("salaryRange"), dto.salaryMin())
                );
            }

            if (dto.cityName() != null) {
                predicates.add(
                        cb.equal(root.get("cityName"), dto.cityName())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
