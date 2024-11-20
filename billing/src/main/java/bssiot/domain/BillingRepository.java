package bssiot.domain;

import bssiot.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "billings", path = "billings")
public interface BillingRepository
    extends PagingAndSortingRepository<Billing, Long> {}
