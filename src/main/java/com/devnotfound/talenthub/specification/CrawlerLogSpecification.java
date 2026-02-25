package com.devnotfound.talenthub.specification;

import com.devnotfound.talenthub.entity.CrawlerLog;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class CrawlerLogSpecification {
    public static Specification<CrawlerLog> filter(
            String plataform,
            Integer positionId,
            Integer techId,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (plataform != null) {
                predicates.add(cb.equal(root.get("plataform"), plataform));
            }

            if (positionId != null) {
                predicates.add(cb.equal(root.get("position").get("id"), positionId));
            }

            if (techId != null) {
                predicates.add(cb.equal(root.get("tech").get("id"), techId));
            }

            if (startDate != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("creationDate"), startDate));
            }

            if (endDate != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("creationDate"), endDate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
