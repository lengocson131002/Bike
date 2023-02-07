package com.swd.bike.core;

import com.swd.bike.common.PagingAndSortConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasePagingAndSortingRequestData extends BaseRequestData {
    private Integer pageSize = PagingAndSortConstant.DEFAULT_PAGE_SIZE;
    private Integer pageNumber = PagingAndSortConstant.DEFAULT_PAGE_NUMBER;
    private String sortBy = PagingAndSortConstant.DEFAULT_SORT_BY;
    private Sort.Direction sortDirection = PagingAndSortConstant.DEFAULT_SORT_DIRECTION;

    public Pageable getPageable() {
        return PageRequest.of(pageNumber, pageSize, sortDirection, sortBy);
    }
}