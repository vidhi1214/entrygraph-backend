package com.entrygraph.backend.specification;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.entrygraph.backend.entity.Destination;
import com.entrygraph.backend.enums.DestinationCategory;
import com.entrygraph.backend.enums.DestinationStatus;

public final class DestinationSpecification {

    private DestinationSpecification() {
    }

    public static Specification<Destination> search(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return cb.conjunction();
            }

            String pattern = "%" + search.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("address")), pattern)
            );
        };
    }

    public static Specification<Destination> hasCategory(
            DestinationCategory category) {

        return (root, query, cb) -> {
            if (category == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("category"), category);
        };
    }

    public static Specification<Destination> hasStatus(
            DestinationStatus status) {

        return (root, query, cb) -> {
            if (status == null) {
                return cb.conjunction();
            }

            return cb.equal(root.get("status"), status);
        };
    }

    public static Specification<Destination> hasTag(UUID tagId) {
        return (root, query, cb) -> {
            if (tagId == null) {
                return cb.conjunction();
            }

            query.distinct(true);

            return cb.equal(
                    root.join("tags").get("id"),
                    tagId
            );
        };
    }
}
