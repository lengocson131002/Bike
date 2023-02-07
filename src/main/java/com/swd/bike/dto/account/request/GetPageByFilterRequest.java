package com.swd.bike.dto.account.request;

import com.swd.bike.core.BasePagingAndSortingRequestData;
import com.swd.bike.entity.Account;
import com.swd.bike.entity.Station;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class GetPageByFilterRequest extends BasePagingAndSortingRequestData {
    private String partialName;
    private String phone;
    private String email;

    public Specification<Account> getSpecification() {
        return (root, cQuery, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(phone)) {
                predicates.add(builder.like(root.get("phone"), "%" + phone + "%"));
            }
            if (StringUtils.isNotBlank(partialName)) {
                log.info(partialName);
                predicates.add(builder.like(root.get("name"), "%" + partialName + "%"));
            }
            if (StringUtils.isNotBlank(email)) {
                predicates.add(builder.like(root.get("email"), "%" + email + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
