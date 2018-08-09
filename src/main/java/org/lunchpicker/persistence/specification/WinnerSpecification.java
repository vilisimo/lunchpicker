package org.lunchpicker.persistence.specification;

import org.lunchpicker.domain.Winner;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.Optional;

public final class WinnerSpecification {

    private WinnerSpecification() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static Specification<Winner> between(LocalDate from, LocalDate to) {
        return Specification.where(fromDate(from)).and(toDate(to));
    }

    private static Specification<Winner> fromDate(LocalDate fromDate) {
        return (root, query, builder) -> Optional.ofNullable(fromDate)
                .map(date -> builder.greaterThanOrEqualTo(root.get("date"), date))
                .orElse(isTrue(builder));
    }

    private static Specification<Winner> toDate(LocalDate instant) {
        return (root, query, builder) -> Optional.ofNullable(instant)
                .map(date -> builder.lessThanOrEqualTo(root.get("date"), date))
                .orElse(isTrue(builder));
    }

    private static Predicate isTrue(CriteriaBuilder builder) {
        return builder.isTrue(builder.literal(true));
    }
}
