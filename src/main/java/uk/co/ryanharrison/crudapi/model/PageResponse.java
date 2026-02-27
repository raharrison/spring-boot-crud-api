package uk.co.ryanharrison.crudapi.model;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;

import java.util.List;

@NullMarked
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages) {

    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());
    }
}
