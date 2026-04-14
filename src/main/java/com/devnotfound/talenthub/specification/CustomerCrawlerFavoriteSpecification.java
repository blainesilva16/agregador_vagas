package com.devnotfound.talenthub.specification;

import com.devnotfound.talenthub.dto.CrawlerLogFilterDTO;
import com.devnotfound.talenthub.entity.CustomerCrawlerFavorite;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerCrawlerFavoriteSpecification {

    private CustomerCrawlerFavoriteSpecification() {
    }

    public static Specification<CustomerCrawlerFavorite> filter(Integer customerId, CrawlerLogFilterDTO dto) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Object, Object> crawlerLog = root.join("crawlerLog");

            predicates.add(cb.equal(root.get("customer").get("id"), customerId));

            if (dto == null) {
                return cb.and(predicates.toArray(new Predicate[0]));
            }

            if (dto.plataform() != null) {
                predicates.add(cb.equal(crawlerLog.get("plataform"), dto.plataform()));
            }

            if (dto.positionId() != null) {
                predicates.add(cb.equal(crawlerLog.get("position").get("id"), dto.positionId()));
            }

            if (dto.techId() != null) {
                predicates.add(cb.equal(crawlerLog.get("tech").get("id"), dto.techId()));
            }

            if (dto.startDate() != null && dto.endDate() != null) {
                predicates.add(
                        cb.between(crawlerLog.get("creationDate"), dto.startDate(), dto.endDate())
                );
            } else if (dto.startDate() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(crawlerLog.get("creationDate"), dto.startDate())
                );
            }

            if (dto.ufAbrev() != null) {
                predicates.add(cb.equal(crawlerLog.get("ufAbrev"), dto.ufAbrev()));
            }

            if (dto.hiringType() != null) {
                predicates.add(cb.equal(crawlerLog.get("hiringType"), dto.hiringType()));
            }

            if (dto.techLevel() != null) {
                predicates.add(cb.equal(crawlerLog.get("techLevel"), dto.techLevel()));
            }

            if (dto.salaryMin() != null && dto.salaryMax() != null) {
                predicates.add(
                        cb.between(crawlerLog.get("salaryRange"), dto.salaryMin(), dto.salaryMax())
                );
            } else if (dto.salaryMin() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(crawlerLog.get("salaryRange"), dto.salaryMin())
                );
            }

            if (dto.cityName() != null) {
                predicates.add(
                        cb.like(
                                cb.lower(crawlerLog.get("cityName")),
                                "%" + dto.cityName().toLowerCase() + "%"
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}